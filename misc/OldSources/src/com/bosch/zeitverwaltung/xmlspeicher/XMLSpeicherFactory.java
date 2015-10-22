package com.bosch.zeitverwaltung.xmlspeicher;

import com.bosch.zeitverwaltung.speicher.BuchungenLaden;
import com.bosch.zeitverwaltung.speicher.BuchungenSpeichern;
import com.bosch.zeitverwaltung.speicher.SpeicherFactory;

/**
 * <p>
 * Factory-Implementierung für die XML-Buchungsspeicherung
 * </p>
 * 
 * <p>
 * Das Package speichert Buchungsdaten für jeden einzelnen Tag in einer
 * XML-Datei im Anwendungsdaten-Verzeichnis von Windows unter dem in
 * <em>XMLDienste</em> definierten Unterverzeichnis ab.
 * </p>
 * 
 * @author Lars Geyer
 * @see SpeicherFactory
 * @see com.bosch.zeitverwaltung.factory.ZeitverwaltungFactory
 * @see XMLDienste
 */
public class XMLSpeicherFactory extends SpeicherFactory {

	/**
	 * <p>
	 * Konstruktor, er registriert die Factory als die <em>SpeicherFactory</em>
	 * der Anwendung
	 * </p>
	 */
	public XMLSpeicherFactory() {
		setFactory(this);
	}

	/**
	 * <p>
	 * Erzeugt ein Handle für den lesenden Zugriff auf Buchungsdateien.
	 * </p>
	 * 
	 * @return Objekt zum Einlesen und Verwalten von Buchungsdateien
	 */
	public BuchungenLaden getBuchungsReader() {
		return new XMLBuchungsReader();
	}

	/**
	 * <p>
	 * Erzeugt ein Handle für den schreibenden Zugriff auf Buchungsdateien
	 * 
	 * @return Objekt zum Schreiben von Buchungsdateien
	 */
	public BuchungenSpeichern getBuchungsWriter() {
		return new XMLBuchungsWriter();
	}
}
