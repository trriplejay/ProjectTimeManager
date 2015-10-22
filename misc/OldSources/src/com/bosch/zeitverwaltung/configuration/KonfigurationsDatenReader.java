package com.bosch.zeitverwaltung.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.modell.AktivitaetenVerwaltung;
import com.bosch.zeitverwaltung.modell.ModellFactory;
import com.bosch.zeitverwaltung.services.ClasspathSuche;

/**
 * <p>
 * Schnittstelle zum Einlesen der Konfigurationsdaten
 * </p>
 * 
 * @author Lars Geyer
 * @see KonfigurationsDatenHandler
 */
public abstract class KonfigurationsDatenReader extends
		KonfigurationsDatenHandler {
	/**
	 * <p>
	 * Methode muss von einer implementierenden Klasse definiert werden. In ihr
	 * werden die Konfigurationsdaten eingelesen und in den Speicherobjekten des
	 * <em>KonfigurationsDatenHandler</em> abgelegt
	 * </p>
	 * 
	 * @throws IOException
	 *             Bei Dateizugriffsproblemen
	 */
	protected abstract void leseKonfigurationsDaten() throws IOException;

	/**
	 * <p>
	 * Die Anwendung beinhaltet eine Basiskonfiguration, die dann eingelesen
	 * wird, wenn noch keine Benutzer-Konfiguration besteht. Die Methode gibt
	 * einen InputStream auf diese Konfiguration zurück. Sie wird von der
	 * implementierenden Klasse aufgerufen, wenn der Aufruf der
	 * Benutzer-Konfiguration fehlschlägt.
	 * </p>
	 * 
	 * @return <em>InputStream</em> auf die Basiskonfigurations-Datei
	 * @throws IOException
	 *             Wenn die Basiskonfiguration nicht oder mehrere gefunden
	 *             wurden.
	 */
	protected InputStream getBasisKonfiguration() throws IOException {
		InputStream back = null;

		ClasspathSuche confDatei = new ClasspathSuche(
				getKonfigurationsDateiname());
		if (!confDatei.hasFiles()) {
			throw new IOException("Keine Basiskonfiguration gefunden");
		}
		back = confDatei.nextInputStream();
		if (confDatei.hasFiles()) {
			throw new IOException("Mehrere Basiskonfigurationen gefunden");
		}

		return back;
	}

	/**
	 * <p>
	 * Eigentlich Schnittstelle zum Einladen der Konfigurationsdaten. Die Daten
	 * werden eingelesen und danach werden die Modelle mit den
	 * Konfigurationsdaten versorgt. Nach Aufruf der Methode sind die Modelle
	 * startbereit.
	 * </p>
	 * 
	 * @throws IOException
	 *             Beim Versuch auf die Konfigurationsdaten zuzugreifen sind
	 *             Probleme entstanden
	 */
	public void ladeKonfigurationsDaten() throws IOException {
		ModellFactory factory = ModellFactory.getFactory();
		AktivitaetenVerwaltung aktivitaeten = factory
				.getAktivitaetenVerwaltung();

		leseKonfigurationsDaten();

		Iterator<Aktivitaet> iter = getAktivitaeten().iterator();
		while (iter.hasNext()) {
			try {
				aktivitaeten.elementHinzufuegen(iter.next());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		aktivitaeten.setSortierung(this.getSortierung());
		aktivitaeten.commitAenderung();

		factory.getMinutenDelta().setMinutenDelta(getMinutenDelta());
	}
}
