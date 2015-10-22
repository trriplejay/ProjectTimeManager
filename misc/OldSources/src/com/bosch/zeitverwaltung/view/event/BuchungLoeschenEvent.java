package com.bosch.zeitverwaltung.view.event;

import com.bosch.zeitverwaltung.elemente.Buchung;

/**
 * <p>
 * Signalisiert den Wunsch eine Buchung zu löschen
 * </p>
 * 
 * @author Lars Geyer
 */
public final class BuchungLoeschenEvent extends BuchungsEvent {
	private Buchung buchung;

	/**
	 * <p>
	 * Erzeugt den Event
	 * </p>
	 * 
	 * @param buchung
	 *            Zu löschende Buchung
	 */
	BuchungLoeschenEvent(final Buchung buchung) {
		this.buchung = buchung;
	}

	/**
	 * <p>
	 * Gibt zu löschende Buchung zurück
	 * </p>
	 * 
	 * @return Buchung
	 */
	public Buchung getBuchung() {
		return buchung;
	}
}
