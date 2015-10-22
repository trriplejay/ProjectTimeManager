package com.bosch.zeitverwaltung.modell.event;

import com.bosch.zeitverwaltung.elemente.AktivitaetsSortierung;

/**
 * <p>
 * Factory für <em>SortierungChangedEvent</em>-Objekte
 * </p>
 * 
 * @author Lars Geyer
 * @see SortierungChangedEvent
 */
public final class SortierungChangedEventFactory {
	/**
	 * <p>
	 * Neuer SortierungChangedEvent
	 * </p>
	 * 
	 * @param sortierung
	 *            Die neue Sortierung
	 * @return <em>SortierungChangedEvent</em>
	 */
	public SortierungChangedEvent sortierungsChangedEvent(
			AktivitaetsSortierung sortierung) {
		return new SortierungChangedEvent(sortierung);
	}
}
