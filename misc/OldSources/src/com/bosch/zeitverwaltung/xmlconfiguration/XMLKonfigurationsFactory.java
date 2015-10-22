package com.bosch.zeitverwaltung.xmlconfiguration;

import com.bosch.zeitverwaltung.configuration.KonfigurationsFactory;
import com.bosch.zeitverwaltung.configuration.KonfigurationsDatenReader;
import com.bosch.zeitverwaltung.configuration.KonfigurationsDatenWriter;

/**
 * <p>
 * Factory-Implementierung für die XML-Konfigurationssicherung
 * </p>
 * 
 * <p>
 * Das Package speichert Konfigurationsdaten in einer XML-Datei im
 * Anwendungsdaten-Verzeichnis von Windows ab. Beim Starten der Anwendung wird
 * in diesem Verzeichnis nach einer Konfigurationsdatei gesucht. Wird diese
 * nicht gefunden, wird im Classpath der Anwendung nach der Basiskonfiguration
 * gesucht. Diese muss vorhanden sein, damit die Anwendung starten kann, da sie
 * die Basisinitialisierungen beinhaltet.
 * </p>
 * 
 * @author Lars Geyer
 * @see KonfigurationsFactory
 * @see com.bosch.zeitverwaltung.factory.ZeitverwaltungFactory
 */
public class XMLKonfigurationsFactory extends KonfigurationsFactory {
	/**
	 * <p>
	 * Konstruktor, registiert sich als <em>KonfigurationsFactory</em> der
	 * Anwendung
	 * </p>
	 */
	public XMLKonfigurationsFactory() {
		setFactory(this);
	}

	/**
	 * <p>
	 * Gibt ein Handle auf die Konfigurationsdatei zum Lesen zurück
	 * </p>
	 * 
	 * @return Ein Objekt zum Lesen von Konfigurationsdaten
	 */
	public KonfigurationsDatenReader getKonfigurationsReader() {
		return new XMLKonfigurationsDatenReader();
	}

	/**
	 * <p>
	 * Gibt ein Handle auf die Konfigurationsdatei zum Schreiben zurück
	 * </p>
	 * 
	 * @return Ein Objekt zum Schreiben von Konfigurationsdaten
	 */
	public KonfigurationsDatenWriter getKonfigurationsWriter() {
		return new XMLKonfigurationsDatenWriter();
	}
}
