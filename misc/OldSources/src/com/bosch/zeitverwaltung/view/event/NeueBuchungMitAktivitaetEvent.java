package com.bosch.zeitverwaltung.view.event;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;

/**
 * <p>
 * Signalisiert einen neuen Buchungswunsch mit einer bereits ausgewählten
 * Aktivität.
 * </p>
 * 
 * @author Lars Geyer
 */
public class NeueBuchungMitAktivitaetEvent extends BuchungsEvent {
	private Aktivitaet aktivitaet;

	/**
	 * <p>
	 * Erzeugt den Event
	 * </p>
	 * 
	 * @param aktivitaet
	 *            Ausgewählte Aktivität
	 */
	NeueBuchungMitAktivitaetEvent(Aktivitaet aktivitaet) {
		this.aktivitaet = aktivitaet;
	}

	/**
	 * <p>
	 * Gibt ausgewählte Aktivität zurück
	 * </p>
	 * 
	 * @return Aktivität
	 */
	public Aktivitaet getAktivitaet() {
		return aktivitaet;
	}
}
