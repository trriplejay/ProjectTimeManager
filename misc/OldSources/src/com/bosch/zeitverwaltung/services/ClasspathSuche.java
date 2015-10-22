package com.bosch.zeitverwaltung.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * <p>
 * Hilfsklasse, die einen Service zum Durchsuchen des Classpaths nach Dateien
 * eines bestimmten Namens zur Verfügung stellt. Die Klasse ist ein Iterator,
 * der nach der Suche erlaubt, durch die gefundenen Dateien zu iterieren.
 * </p>
 * 
 * @author Lars Geyer
 */
public final class ClasspathSuche {
	private List<String> dateien = new LinkedList<String>();

	private int aktIndex = 0;

	/**
	 * <p>
	 * Konstruktor, er führt die Suche durch. Er untersucht sowohl Verzeichnisse
	 * als auch jar-Files
	 * </p>
	 * 
	 * @param dateiname
	 *            Name der Datei, nach der gesucht werden soll.
	 */
	public ClasspathSuche(String dateiname) {
		String classpath = System.getProperty("java.class.path");

		StringTokenizer stz = new StringTokenizer(classpath, ";");
		while (stz.hasMoreTokens()) {
			File aktDatei = new File(stz.nextToken());
			if (aktDatei.isDirectory()) {
				sucheDateien(dateien, aktDatei, dateiname);
			} else if (aktDatei.getName().endsWith(".jar")) {
				sucheInJarFile(dateien, aktDatei, dateiname);
			}
		}
	}

	/**
	 * <p>
	 * Iterator-Funktion, die abfrägt, ob noch weitere Dateien existieren, über
	 * die iteriert werden soll
	 * </p>
	 * 
	 * @return true, falls noch Dateien gefunden wurden, über die iteriert
	 *         werden soll
	 */
	public boolean hasFiles() {
		return (aktIndex != dateien.size());
	}

	/**
	 * <p>
	 * Iterator-Funktion, die das nächste Element des Iterators zurückgibt. An
	 * dieser Stelle müssen <em>InputStream</em>-Objekte zurückgegeben
	 * werden, da auf Dateien in Jar-Files am Besten über den Input-Stream der
	 * <em>getResource</em>-Methode zugegriffen wird.
	 * </p>
	 * 
	 * @return Link auf Input-Stream, über den die Dateidaten gelesen werden
	 *         können
	 * @throws IOException
	 *             Bei Dateizugriffsproblemen
	 */
	public InputStream nextInputStream() throws IOException {
		InputStream back = null;
		if (!hasFiles()) {
			throw new IllegalArgumentException("Alle Elemente betrachtet");
		}

		String datei = dateien.get(aktIndex);
		aktIndex++;

		if (datei.startsWith("jar:")) {
			String resource = datei.substring(4);
			URL url = this.getClass().getClassLoader().getResource(resource);
			return url.openStream();
		} else {
			String dateiname = datei.substring(5);
			back = new FileInputStream(new File(dateiname));
		}
		return back;
	}

	/**
	 * <p>
	 * Methode zum Durchsuchen eines Jar-Files nach Dateien.
	 * </p>
	 * 
	 * @param ergebnis
	 *            Liste mit Ergebnissen, sie wird in der Methode mit neuen
	 *            Suchergebnissen gefüllt. Die Liste enthält entweder den
	 *            kompletten Pfad der Datei, wenn diese in einem Verzeichnis
	 *            liegt als String, oder den kompletten Resourcennamen innerhalb
	 *            des Jar-Files, in dem die Datei gefunden wurde als String. Zur
	 *            Unterscheidung wird ein "jar:" oder ein "file:" vorangestellt.
	 * @param datei
	 *            Jar-Datei, die durchsucht werden soll
	 * @param dateiname
	 *            Name der gesuchten Datei
	 */
	private void sucheInJarFile(Collection<String> ergebnis, File datei,
			String dateiname) {
		try {
			JarInputStream is = new JarInputStream(new FileInputStream(datei));
			JarEntry jarInhalt = null;

			while ((jarInhalt = is.getNextJarEntry()) != null) {
				if (jarInhalt.getName().endsWith(dateiname)) {
					ergebnis.add("jar:" + jarInhalt.getName());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Methode zum Durchsuchen eines Verzeichnis-Files nach Dateien.
	 * </p>
	 * 
	 * @param ergebnis
	 *            Liste mit Ergebnissen, sie wird in der Methode mit neuen
	 *            Suchergebnissen gefüllt. Die Liste enthält entweder den
	 *            kompletten Pfad der Datei, wenn diese in einem Verzeichnis
	 *            liegt als String, oder den kompletten Resourcennamen innerhalb
	 *            des Jar-Files, in dem die Datei gefunden wurde als String. Zur
	 *            Unterscheidung wird ein "jar:" oder ein "file:" vorangestellt.
	 * @param verzeichnis
	 *            Verzeichnis, das durchsucht werden soll
	 * @param dateiname
	 *            Name der gesuchten Datei
	 */
	private void sucheDateien(Collection<String> ergebnis, File verzeichnis,
			String dateiname) {
		File dateien[] = verzeichnis.listFiles();

		for (int i = 0; i < dateien.length; i++) {
			if (dateien[i].getName().equals(dateiname)) {
				ergebnis.add("file:" + dateien[i].getAbsolutePath());
			} else if (dateien[i].isDirectory()) {
				sucheDateien(ergebnis, dateien[i], dateiname);
			} else if (dateien[i].getName().endsWith(".jar")) {
				sucheInJarFile(ergebnis, dateien[i], dateiname);
			}
		}
	}
}