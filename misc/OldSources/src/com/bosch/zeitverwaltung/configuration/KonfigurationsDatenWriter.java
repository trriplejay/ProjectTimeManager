package com.bosch.zeitverwaltung.configuration;

import java.io.IOException;

import com.bosch.zeitverwaltung.modell.AktivitaetenVerwaltung;
import com.bosch.zeitverwaltung.modell.ModellFactory;

/**
 * <p>
 * Schnittstelle zum Schreiben von Konfigurationsdaten
 * </p>
 * 
 * @author Lars Geyer
 * @see KonfigurationsDatenHandler
 */
public abstract class KonfigurationsDatenWriter extends
		KonfigurationsDatenHandler {
	/**
	 * <p>
	 * Die implementierende Klasse realisiert in dieser Methode den
	 * Schreibzugriff auf die Konfigurationsdatei.
	 * </p>
	 * 
	 * @throws IOException
	 *             Falls beim Dateizugriff Probleme entstanden sind.
	 */
	protected abstract void schreibeKonfigurationsDaten() throws IOException;

	/**
	 * <p>
	 * Schnittstellen-Methode zum Schreiben von Konfigurationsdaten. Die Daten
	 * werden aus den Modellen ausgelesen, danach wird mittels
	 * <em>schreibeKonfigurationsDaten</em> die Daten in die
	 * Konfigurationsdatei geschrieben.
	 * </p>
	 * 
	 * @throws IOException
	 *             Falls beim Dateizugriff Probleme entstanden sind.
	 */
	public void speicherKonfigurationsDaten() throws IOException {
		ModellFactory factory = ModellFactory.getFactory();
		AktivitaetenVerwaltung aktivitaeten = factory
				.getAktivitaetenVerwaltung();

		setAktivitaeten(aktivitaeten.elemente());
		this.setSortierung(aktivitaeten.sortierung());
		setMinutenDelta(factory.getMinutenDelta().getMinutenDelta());
		schreibeKonfigurationsDaten();
	}
}
