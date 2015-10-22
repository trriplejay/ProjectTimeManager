package com.bosch.zeitverwaltung.configuration;

/**
 * <p>
 * Das KonfigurationsFactory-Singleton dient dazu, als abstrakte Schnittstelle
 * die Erzeugung aller relevanter Konfigurations-Elemente zu ermöglichen. Eine
 * konkrete Konfigurations-Factory muss diese Schnittstelle implementieren und
 * so Links auf die Konfigurations-Elemente zurückgeben.
 * </p>
 * 
 * <p>
 * Die abstrakte Schnittstelle ist nach dem Muster für abstrakte Factories als
 * Singletons aufgebaut. Eine statische Variable ermöglicht mittels eines
 * Getters den Zugriff auf die Factory. Die abgeleitete Factory setzt die
 * Factory-Variable durch einen Aufruf von <em>setFactory</em>. Die
 * bereitstellenden Funktionen sind als abstrakte Methoden in dieser Klasse
 * definiert und werden von der abgeleiteten Factory realisiert.
 * </p>
 * 
 * @author Lars Geyer
 * @see com.bosch.zeitverwaltung.factory.ZeitverwaltungFactory
 * @see KonfigurationsDatenReader
 * @see KonfigurationsDatenWriter
 */
public abstract class KonfigurationsFactory {
	private static KonfigurationsFactory konfigurationsFactory = null;

	/**
	 * <p>
	 * Setzen der Factory-Variable. Wird von einer abgeleiteten Factory-Klasse
	 * aufgerufen, um das konkrete Factory-Objekt bekannt zu machen.
	 * </p>
	 * 
	 * @param factory
	 *            Die abgeleitete Konfigurations-Factory
	 */
	protected static void setFactory(KonfigurationsFactory factory) {
		if (konfigurationsFactory == null) {
			konfigurationsFactory = factory;
		} else {
			throw new IllegalArgumentException(
					"Singleton zum zweitenmal erzeugt");
		}
	}

	/**
	 * <p>
	 * Zugriff auf die Konfigurations-Factory, um Konfigurations-Elemente zu
	 * erzeugen.
	 * </p>
	 * 
	 * @return Link auf die Konfigurations-Factory
	 */
	public static KonfigurationsFactory getFactory() {
		return konfigurationsFactory;
	}

	/**
	 * <p>
	 * Gibt ein Handle auf die Konfigurationsdatei zum Lesen zurück
	 * </p>
	 * 
	 * @return Ein Objekt zum Lesen von Konfigurationsdaten
	 */
	public abstract KonfigurationsDatenReader getKonfigurationsReader();

	/**
	 * <p>
	 * Gibt ein Handle auf die Konfigurationsdatei zum Schreiben zurück
	 * </p>
	 * 
	 * @return Ein Objekt zum Schreiben von Konfigurationsdaten
	 */
	public abstract KonfigurationsDatenWriter getKonfigurationsWriter();
}
