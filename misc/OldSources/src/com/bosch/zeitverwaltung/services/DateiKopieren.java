package com.bosch.zeitverwaltung.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <p>
 * Hilfsklasse zum byteweisen Kopieren einer Datei. Wird leider benötigt, da die
 * Umbenennungs- und Löschfunktion der Klasse <em>File</em> unzuverlässig
 * sind.
 * </p>
 * 
 * @author Lars Geyer
 */
public final class DateiKopieren {
	/**
	 * <p>
	 * Kopiere byteweise eine Datei von der <em>quelle</em> zum <em>ziel</em>.
	 * Eine eventuell existierende Datei wird dabei überschrieben.
	 * </p>
	 * 
	 * @param quelle
	 *            Quelldatei
	 * @param ziel
	 *            Senkendatei
	 * @throws IOException
	 *             Bei Dateizugriffproblemen
	 */
	public void kopieren(File quelle, File ziel) throws IOException {
		if (quelle.exists()) {
			FileInputStream in = new FileInputStream(quelle);
			FileOutputStream out = new FileOutputStream(ziel);

			int data;

			while ((data = in.read()) >= 0) {
				out.write(data);
			}

			in.close();
			out.close();
		}
	}
}
