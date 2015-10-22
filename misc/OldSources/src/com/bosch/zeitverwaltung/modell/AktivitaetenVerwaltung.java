package com.bosch.zeitverwaltung.modell;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.elemente.AktivitaetsSortierung;
import com.bosch.zeitverwaltung.modell.listener.AktivitaetenChangedListener;
import com.bosch.zeitverwaltung.modell.listener.SortierungChangedListener;

/**
 * <p>
 * Schnittstelle zur Aktivitäten-Verwaltung. In ihr werden die aktuell
 * auswählbaren Aktivitäten verwaltet. Historische Aktivitäten sind nur in den
 * Buchungen der entsprechenden Buchungstage gespeichert und können nicht mehr
 * in die aktuelle Liste aufgenommen werden. Von Hand kann natürlich die
 * Aktivität wieder in die Konfigurationsdatei hineingezogen werden.
 * </p>
 * 
 * @author Lars Geyer
 * @see BasisModell
 */
public interface AktivitaetenVerwaltung extends BasisModell<Aktivitaet> {
	/**
	 * <p>
	 * Methode zum Setzen der Aktivitaets-Sortierung.
	 * </p>
	 * 
	 * @param sortierung
	 *            Die gewählte Aktivitätssortierung
	 */
	public void setSortierung(AktivitaetsSortierung sortierung);

	/**
	 * <p>
	 * Gibt die aktuelle Aktivitätssortierung zurück
	 * </p>
	 * 
	 * @return Aktuelle Sortierung
	 */
	public AktivitaetsSortierung sortierung();

	/**
	 * <p>
	 * Anmeldung eines Listeners, der informiert werden möchte, wenn sich an der
	 * Sortierung der Aktivitäten etwas verändert.
	 * </p>
	 * 
	 * @param listener
	 *            Möchte über Änderungen der Aktivitätensortierung informiert
	 *            werden
	 */
	public void addSortierungChangedListener(SortierungChangedListener listener);

	/**
	 * <p>
	 * Abmeldung eines Listeners, der informiert werden möchte, wenn sich an der
	 * Sortierung der Aktivitäten etwas verändert.
	 * </p>
	 * 
	 * @param listener
	 *            Zu entfernender Listener
	 */
	public void removeSortierungChangedListener(
			SortierungChangedListener listener);

	/**
	 * <p>
	 * Anmeldung eines Listeners, der informiert werden möchte, wenn sich an der
	 * Liste der Aktivitäten etwas verändert.
	 * </p>
	 * 
	 * @param listener
	 *            Möchte über Änderungen der Aktivitäten informiert werden
	 */
	public void addAktivitaetenChangedListener(
			AktivitaetenChangedListener listener);

	/**
	 * <p>
	 * Abmeldung eines Listeners, der informiert werden möchte, wenn sich an der
	 * Liste der Aktivitäten etwas verändert.
	 * </p>
	 * 
	 * @param listener
	 *            Zu entfernender Listener
	 */
	public void removeAktivitaetenChangedListener(
			AktivitaetenChangedListener listener);
}