package com.bosch.zeitverwaltung.view.event;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.elemente.Buchung;

/**
 * <p>
 * Factory für <em>BuchungsEvent</em>-Objekte
 * </p>
 * 
 * @author Lars Geyer
 * @see BuchungsEvent
 */
public final class BuchungsEventFactory {
	/**
	 * <p>
	 * Erzeugt einen <em>BuchungsEvent</em>, der eine neue Buchung ohne
	 * Aktivitätsangabe signalisiert
	 * </p>
	 * 
	 * @return BuchungsEvent
	 */
	public BuchungsEvent neueBuchungEvent() {
		return new NeueBuchungEvent();
	}

	/**
	 * <p>
	 * Erzeugt einen <em>BuchungsEvent</em>, der eine neue Buchung mit
	 * Aktivitätsangabe signalisiert
	 * </p>
	 * 
	 * @return BuchungsEvent
	 */
	public BuchungsEvent neueBuchungEvent(final Aktivitaet aktivitaet) {
		return new NeueBuchungMitAktivitaetEvent(aktivitaet);
	}

	/**
	 * <p>
	 * Erzeugt einen <em>BuchungsEvent</em>, der eine neue Unterbrechung
	 * signalisiert
	 * </p>
	 * 
	 * @return BuchungsEvent
	 */
	public BuchungsEvent neueUnterbrechungEvent() {
		return new NeueUnterbrechungEvent();
	}

	/**
	 * <p>
	 * Erzeugt einen <em>BuchungsEvent</em>, der das Beenden einer Buchung
	 * signalisiert
	 * </p>
	 * 
	 * @return BuchungsEvent
	 */
	public BuchungsEvent beendenEvent() {
		return new BuchungBeendenEvent();
	}

	/**
	 * <p>
	 * Erzeugt einen <em>BuchungsEvent</em>, der das Löschen einer Buchung
	 * signalisiert
	 * </p>
	 * 
	 * @return BuchungsEvent
	 */
	public BuchungsEvent loeschenEvent(final Buchung buchung) {
		return new BuchungLoeschenEvent(buchung);
	}
}
