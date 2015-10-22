package com.bosch.zeitverwaltung.modell.event;

import com.bosch.zeitverwaltung.modell.TagesBuchungen;

/**
 * <p>
 * Factory für <em>TagChangedEvent</em>-Objekte
 * </p>
 * 
 * @author Lars Geyer
 * @see TagChangedEvent
 */
public final class TagChangedEventFactory {
	/**
	 * <p>
	 * Erzeugt einen <em>TagChangedEvent</em>
	 * </p>
	 * 
	 * @param buchungen
	 *            Aktuelles <em>TagesBuchungen</em>-Objekt
	 * @return Event
	 */
	public TagChangedEvent tagChangedEvent(TagesBuchungen buchungen) {
		return new TagChangedEvent(buchungen);
	}
}
