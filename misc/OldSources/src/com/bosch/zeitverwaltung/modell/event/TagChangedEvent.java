package com.bosch.zeitverwaltung.modell.event;

import com.bosch.zeitverwaltung.event.BasisEvent;
import com.bosch.zeitverwaltung.modell.TagesBuchungen;

/**
 * <p>
 * Event signalisiert eine Veränderung des Buchungstages
 * </p>
 * 
 * @author Lars Geyer
 */
public final class TagChangedEvent extends BasisEvent {
	private TagesBuchungen buchungen;

	/**
	 * <p>
	 * Erzeugt einen Event. Diese Methode wird durch die Event-Factory
	 * aufgerufen.
	 * </p>
	 * 
	 * @param buchungen
	 *            Aktuelles <em>TagesBuchungen</em>-Objekt
	 */
	TagChangedEvent(TagesBuchungen buchungen) {
		this.buchungen = buchungen;
	}

	/**
	 * <p>
	 * Gibt das aktuelle Buchungs-Management-Objekt zurück.
	 * </p>
	 * 
	 * @return Aktuelles <em>TagesBuchungen</em>-Objekt
	 */
	public TagesBuchungen getBuchungsManager() {
		return buchungen;
	}
}
