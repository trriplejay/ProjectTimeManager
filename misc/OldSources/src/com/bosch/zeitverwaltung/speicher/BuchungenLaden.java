package com.bosch.zeitverwaltung.speicher;

import java.io.IOException;

import com.bosch.zeitverwaltung.elemente.Tag;
import com.bosch.zeitverwaltung.modell.TagesBuchungen;

/**
 * <p>
 * Schnittstelle zum Einladen und Verwalten von Buchungen eines Tages. Über
 * diese Schnittstelle lädt die Zeitvewaltung Buchungen ein.
 * </p>
 * 
 * @author Lars Geyer
 * @see BuchungenSpeichern
 */
public interface BuchungenLaden {
	/**
	 * <p>
	 * Methode überprüft, ob für den übergebenen Tag bereits Buchungen
	 * gespeichert wurden.
	 * </p>
	 * 
	 * @param tag
	 *            Tag, für den erfragt wird, ob Buchungen gespeichert sind
	 * @return true, falls Buchungen für den übergebenen Tag gespeichert sind
	 */
	public boolean existierenBuchungen(Tag tag);

	/**
	 * <p>
	 * Methode zum Einlesen der Buchungen eines Tages.
	 * </p>
	 * 
	 * @param tag
	 *            Tag, für den die Buchungen eingelesen werden sollen
	 * @return Ein TagesBuchungen-Modell mit den Buchungen des Tages
	 * @throws IOException
	 *             Falls Datei nicht existiert oder aus anderen Gründen nicht
	 *             darauf zugegriffen werden kann.
	 */
	public TagesBuchungen ladeBuchungen(Tag tag) throws IOException;
}
