package com.bosch.zeitverwaltung.view;

import com.bosch.zeitverwaltung.view.listener.FunktionsListener;

/**
 * <p>
 * Schnittstelle zur Anmeldung von Auswertungsfunktionen. Die Funktionen müssen
 * in der Reihenfolge, in der sie dargestellt werden sollen angemeldet werden.
 * Ein FunktionsListener wird aufgerufen, sobald eine Funktion ausgewählt wurde.
 * Als Parameter wird der Name der Funktion übergeben.
 * 
 * @author Lars Geyer
 * @see MainWindow
 * @see com.bosch.zeitverwaltung.funktion.FunktionsControl
 */
public interface FunktionsTrigger {
	/**
	 * <p>
	 * Funktion hinzufügen, die Reihenfolge der Aufrufe dieser Methode wird in
	 * der Darstellung berücksichtigt.
	 * </p>
	 * 
	 * @param name
	 *            Name der Funktion
	 * @param kurzwahl
	 *            Ein Buchstabe für die Kurzwahl der Funktion in einer Konstante
	 *            der Klasse KeyEvent
	 */
	public void addFunktion(String name, int kurzwahl);

	/**
	 * <p>
	 * Hinzufügen eines FunktionsListeners, der auf Funktionsauswahlen reagiert.
	 * Dem Listener wird der Name der Funktion mitgeteilt.
	 * </p>
	 * 
	 * @param listener
	 *            Listener, der auf Funktionsaufrufe reagieren soll
	 */
	public void addFunktionsListener(FunktionsListener listener);

	/**
	 * <p>
	 * Entfernen eines FunktionsListeners, der auf Funktionsauswahlen reagiert.
	 * </p>
	 * 
	 * @param listener
	 *            Listener, der nicht mehr auf Funktionsaufrufe reagieren soll
	 */
	public void removeFunktionsListener(FunktionsListener listener);
}
