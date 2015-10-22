package com.bosch.zeitverwaltung.modell.event;

import com.bosch.zeitverwaltung.event.BasisEvent;
import com.bosch.zeitverwaltung.modell.TagesBuchungen;

/**
 * <p>
 * Event signalisiert eine Veränderung in den Buchungen des aktuellen Tages
 * </p>
 * 
 * @author Lars Geyer
 */
public final class BuchungenChangedEvent extends BasisEvent {
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
	BuchungenChangedEvent(TagesBuchungen buchungen) {
		this.buchungen = buchungen;
	}

	/**
	 * <p>
	 * Gibt die aktuellen Buchungen zurück
	 * </p>
	 * 
	 * @return Aktuelles <em>TagesBuchungen</em>-Objekt
	 */
	public TagesBuchungen getBuchungen() {
		return buchungen;
	}
}
