package com.bosch.zeitverwaltung.modell.event;

import com.bosch.zeitverwaltung.modell.TagesBuchungen;

/**
 * <p>
 * Factory für <em>BuchungenChangedEvent</em>-Objekte
 * </p>
 * 
 * @author Lars Geyer
 * @see BuchungenChangedEvent
 */
public final class BuchungenChangedEventFactory {
	/**
	 * <p>
	 * Erzeugt einen <em>BuchungenChangedEvent</em>
	 * </p>
	 * 
	 * @param buchungen
	 *            Aktuelles <em>TagesBuchungen</em>-Objekt
	 * @return Event
	 */
	public BuchungenChangedEvent buchungenChangedEvent(TagesBuchungen buchungen) {
		return new BuchungenChangedEvent(buchungen);
	}
}
