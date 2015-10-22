package com.bosch.zeitverwaltung.view;

import com.bosch.zeitverwaltung.view.dialog.UIDialogFactory;

/**
 * <p>
 * Das UIFactory-Singleton dient dazu, als abstrakte Schnittstelle die Erzeugung
 * aller relevanter GUI-Elemente zu ermöglichen. Eine konkrete GUI-Factory muss
 * diese Schnittstelle implementieren und so Links auf die GUI-Elemente
 * zurückgeben.
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
 */
public abstract class UIFactory {
	private static UIFactory uiFactory = null;

	/**
	 * <p>
	 * Setzen der Factory-Variable. Wird von einer abgeleiteten Factory-Klasse
	 * aufgerufen, um das konkrete Factory-Objekt bekannt zu machen.
	 * </p>
	 * 
	 * @param factory
	 *            Die abgeleitete UI-Factory
	 */
	protected static void setFactory(UIFactory factory) {
		if (uiFactory == null) {
			uiFactory = factory;
		} else {
			throw new IllegalArgumentException(
					"Singleton zum zweitenmal erzeugt");
		}
	}

	/**
	 * <p>
	 * Zugriff auf die UI-Factory, um GUI-Elemente zu erzeugen.
	 * </p>
	 * 
	 * @return Link auf die UI-Factory
	 */
	public static UIFactory getFactory() {
		return uiFactory;
	}

	/**
	 * <p>
	 * Link auf das Hauptfenster. Es wird nur einmalig erzeugt, der Link wird
	 * dann immer wieder zurückgegeben.
	 * </p>
	 * 
	 * @return Link auf das Hauptfenster
	 */
	public abstract MainWindow erzeugeMainWindow();

	/**
	 * <p>
	 * Gibt eine Dialog-Factory zurück.
	 * </p>
	 * 
	 * @return Dialog-Factory
	 */
	public abstract UIDialogFactory getDialogFactory();
}
