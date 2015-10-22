package com.bosch.zeitverwaltung.speicher;

import java.io.IOException;

import com.bosch.zeitverwaltung.modell.TagesBuchungen;

/**
 * <p>
 * Schnittstelle zum Speichern von Buchungen eines Tages. Über diese
 * Schnittstelle speichert die Zeitvewaltung Buchungen persistent.
 * </p>
 * 
 * @author Lars Geyer
 * @see BuchungenLaden
 */
public interface BuchungenSpeichern {
	/**
	 * <p>
	 * Methode zum Speichern der Buchungen eines Tages-Modells
	 * </p>
	 * 
	 * @param buchungen
	 *            Modell der Buchungen des zu speichernden Tages
	 */
	public void speichereBuchungen(TagesBuchungen buchungen) throws IOException;
}
