package com.bosch.zeitverwaltung.speicher;

/**
 * <p>
 * Das SpeicherFactory-Singleton dient dazu, als abstrakte Schnittstelle die
 * Erzeugung aller relevanter Speicher-Elemente zu ermöglichen. Eine konkrete
 * Speicher-Factory muss diese Schnittstelle implementieren und so Links auf die
 * Speicher-Elemente zurückgeben.
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
 * @see BuchungenLaden
 * @see BuchungenSpeichern
 */
public abstract class SpeicherFactory {
	private static SpeicherFactory speicherFactory = null;

	/**
	 * <p>
	 * Setzen der Factory-Variable. Wird von einer abgeleiteten Factory-Klasse
	 * aufgerufen, um das konkrete Factory-Objekt bekannt zu machen.
	 * </p>
	 * 
	 * @param factory
	 *            Die abgeleitete Speicher-Factory
	 */
	protected static void setFactory(SpeicherFactory factory) {
		if (speicherFactory == null) {
			speicherFactory = factory;
		} else {
			throw new IllegalArgumentException(
					"Singleton zum zweitenmal erzeugt");
		}
	}

	/**
	 * <p>
	 * Zugriff auf die Speicher-Factory, um Speicher-Elemente zu erzeugen.
	 * </p>
	 * 
	 * @return Link auf die Speicher-Factory
	 */
	public static SpeicherFactory getFactory() {
		return speicherFactory;
	}

	/**
	 * <p>
	 * Gibt ein Handle auf einen Buchungs-Quelle zurück. Mit dessem Interface
	 * können Buchungen eines Tages eingelesen werden.
	 * </p>
	 * 
	 * @return Buchungs-Quelle
	 */
	public abstract BuchungenLaden getBuchungsReader();

	/**
	 * <p>
	 * Gibt ein Handle auf einen Buchungs-Senke zurück. Mit dessem Interface
	 * können Buchungen eines Tages gespeichert werden.
	 * </p>
	 * 
	 * @return Buchungs-Senke
	 */
	public abstract BuchungenSpeichern getBuchungsWriter();
}
