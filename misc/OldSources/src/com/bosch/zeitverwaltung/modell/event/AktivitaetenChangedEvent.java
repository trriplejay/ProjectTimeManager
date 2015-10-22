package com.bosch.zeitverwaltung.modell.event;

import java.util.Collection;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.event.BasisEvent;

/**
 * <p>
 * Event signalisiert eine Veränderung in der Liste der aktuell verfügbaren
 * Aktivitäten.
 * </p>
 * 
 * @author Lars Geyer
 */
public final class AktivitaetenChangedEvent extends BasisEvent {
	private Collection<Aktivitaet> neueListe;

	/**
	 * <p>
	 * Erzeugt einen Event. Diese Methode wird durch die Event-Factory
	 * aufgerufen.
	 * </p>
	 * 
	 * @param neueListe
	 *            Liste der Aktivitäten nach der Änderung
	 */
	AktivitaetenChangedEvent(Collection<Aktivitaet> neueListe) {
		this.neueListe = neueListe;
	}

	/**
	 * <p>
	 * Gibt die Liste der aktuell gültigen Aktivitäten zurück
	 * </p>
	 * 
	 * @return Liste mit Aktivitäten
	 */
	public Collection<Aktivitaet> getAktivitaetenListe() {
		return neueListe;
	}
}
