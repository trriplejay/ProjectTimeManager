package com.bosch.zeitverwaltung.view.event;

/**
 * <p>
 * Factory für <em>HilfeEvent</em>-Objekte
 * </p>
 * 
 * @author Lars Geyer
 * @see HilfeEvent
 */
public class HilfeEventFactory {
	/**
	 * <p>
	 * Erzeugt einen AboutInformationEvent
	 * </p>
	 * 
	 * @return HilfeEvent
	 */
	public HilfeEvent aboutEvent() {
		return new AboutEvent();
	}
}
