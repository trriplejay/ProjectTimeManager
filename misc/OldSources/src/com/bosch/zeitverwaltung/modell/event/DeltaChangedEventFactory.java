package com.bosch.zeitverwaltung.modell.event;

import com.bosch.zeitverwaltung.elemente.MinutenDelta;

/**
 * <p>
 * Factory für <em>DeltaChangedEvent</em>-Objekte
 * </p>
 * 
 * @author Lars Geyer
 * @see DeltaChangedEvent
 */
public final class DeltaChangedEventFactory {
	/**
	 * <p>
	 * Erzeugt einen <em>DeltaChangedEvent</em>
	 * </p>
	 * 
	 * @param minutenDelta
	 *            Minutendelta-Wert
	 * @return Event
	 */
	public DeltaChangedEvent deltaChangedEvent(MinutenDelta minutenDelta) {
		return new DeltaChangedEvent(minutenDelta);
	}
}
