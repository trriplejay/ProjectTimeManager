package com.bosch.zeitverwaltung.modell.event;

import com.bosch.zeitverwaltung.elemente.AktivitaetsSortierung;
import com.bosch.zeitverwaltung.event.BasisEvent;

/**
 * <p>
 * Event, der eine Änderung der Altivitätensortierung anzeigt.
 * </p>
 * 
 * @author Lars Geyer
 * @see AktivitaetsSortierung
 * @see com.bosch.zeitverwaltung.modell.listener.SortierungChangedListener
 * @see com.bosch.zeitverwaltung.modell.AktivitaetenVerwaltung
 */
public final class SortierungChangedEvent extends BasisEvent {
	private AktivitaetsSortierung sortierung;

	/**
	 * <p>
	 * Konstruktor, er wird nur von der <em>SortierungChangedEventFactory</em>
	 * aufgerufen.
	 * </p>
	 * 
	 * @param sortierung
	 *            Die neue Sortierung
	 */
	SortierungChangedEvent(AktivitaetsSortierung sortierung) {
		this.sortierung = sortierung;
	}

	/**
	 * <p>
	 * Die neue Sortierung
	 * </p>
	 * 
	 * @return Sortierung
	 */
	public AktivitaetsSortierung sortierung() {
		return sortierung;
	}

	/**
	 * <p>
	 * Sortierung ist nach Buchungsnummern
	 * </p>
	 * 
	 * @return Sortierung nach Buchungsnummern
	 */
	public boolean sortierungNachBuchungsnummern() {
		return (sortierung == AktivitaetsSortierung.AktivitaetsSortierungNachBuchungsnummer);
	}

	/**
	 * <p>
	 * Sortierung ist nach Aktivitätsname
	 * </p>
	 * 
	 * @return Sortierung nach Aktivitätsname
	 */
	public boolean sortierungNachAktivitätsname() {
		return (sortierung == AktivitaetsSortierung.AktivitaetsSortierungNachName);
	}

	/**
	 * <p>
	 * Sortierung ist nach Aktivitätskategorie
	 * </p>
	 * 
	 * @return Sortierung nach Aktivitätskategorie
	 */
	public boolean sortierungNachAktivitätskategorie() {
		return (sortierung == AktivitaetsSortierung.AktivitaetsSortierungNachKategorie);
	}
}
