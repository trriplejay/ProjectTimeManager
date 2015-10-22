package com.bosch.zeitverwaltung.modell_impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.elemente.AktivitaetsSortierung;
import com.bosch.zeitverwaltung.event.EventVerteiler;
import com.bosch.zeitverwaltung.modell.AktivitaetenVerwaltung;
import com.bosch.zeitverwaltung.modell.event.AktivitaetenChangedEvent;
import com.bosch.zeitverwaltung.modell.event.AktivitaetenChangedEventFactory;
import com.bosch.zeitverwaltung.modell.event.SortierungChangedEvent;
import com.bosch.zeitverwaltung.modell.event.SortierungChangedEventFactory;
import com.bosch.zeitverwaltung.modell.listener.AktivitaetenChangedListener;
import com.bosch.zeitverwaltung.modell.listener.SortierungChangedListener;

/**
 * <p>
 * Realisierung des <em>AktivitaetenVerwaltung</em>-Interfaces
 * </p>
 * 
 * @author Lars Geyer
 * @see AktivitaetenVerwaltung
 */
public final class AktivitaetenListe implements AktivitaetenVerwaltung {
	private AktivitaetsSortierung sortierung = AktivitaetsSortierung.AktivitaetsSortierungNachBuchungsnummer;

	private SortedSet<Aktivitaet> aktivitaeten = new TreeSet<Aktivitaet>(
			sortierung.getOrdnungsFunktion());

	private SortedSet<Aktivitaet> backupListe = null;

	private EventVerteiler<SortierungChangedEvent, SortierungChangedListener> sortierungsManager =
		new EventVerteiler<SortierungChangedEvent, SortierungChangedListener>();

	private EventVerteiler<AktivitaetenChangedEvent, AktivitaetenChangedListener> aktivitaetenManager =
		new EventVerteiler<AktivitaetenChangedEvent, AktivitaetenChangedListener>();

	/**
	 * <p>
	 * Hinzufügen einer neuen Aktivität in die Liste
	 * </p>
	 * 
	 * @param neuesElement
	 *            Einzufügende Aktivität
	 * @return Index der Aktivität in der Liste
	 * @throws IllegalArgumentException
	 *             Falls die Kategorie und Buchungsnummer keine 1:1 Abbildung
	 *             haben
	 */
	public int elementHinzufuegen(Aktivitaet neuesElement)
			throws IllegalArgumentException {
		int back = -1;

		if (!aktivitaeten.contains(neuesElement)) {
			if (kategorieInOrdnung(neuesElement)) {
				aktivitaeten.add(neuesElement);
				back = aktivitaeten.headSet(neuesElement).size();
			} else {
				throw new IllegalArgumentException(
						"Aktivitätskategorie und Buchungsnummer müssen eine 1:1 Abbildung haben");
			}
		}

		return back;
	}

	/**
	 * <p>
	 * Löschen der übergebenen Aktivität aus der Liste
	 * </p>
	 * 
	 * @param zuLoeschendes
	 *            Zu löschende Aktivität
	 */
	public void elementLoeschen(Aktivitaet zuLoeschendes) {
		aktivitaeten.remove(zuLoeschendes);
	}

	/**
	 * <p>
	 * Methode zur Überprüfung der 1:1 Verbindung zwischen Kategorie und
	 * Buchungsnummer einer Aktivität im Verhältnis zu den anderen Aktivitäten.
	 * </p>
	 * 
	 * @param pruefling
	 *            Zu überprüfende Aktivität
	 * @return <em>true</em>, falls die 1:1 Abbildung eingehalten wird
	 */
	private boolean kategorieInOrdnung(Aktivitaet pruefling) {
		boolean back = true;

		Iterator<Aktivitaet> iter = aktivitaeten.iterator();
		while (iter.hasNext()) {
			Aktivitaet aktuelle = iter.next();
			if (aktuelle.getBuchungsNummer().equals(
					pruefling.getBuchungsNummer())) {
				if (!aktuelle.getKategorie().equals(pruefling.getKategorie())) {
					back = false;
				}
			}
		}

		return back;
	}

	/**
	 * <p>
	 * Methode erlaubt den Zugriff auf die verwalteten Aktivitäten
	 * </p>
	 * 
	 * @return Liste der verwaltenten Aktivitäten
	 */
	public Collection<Aktivitaet> elemente() {
		return aktivitaeten;
	}

	/**
	 * <p>
	 * Wie sind die Aktivitäten aktuell sortiert
	 * </p>
	 * 
	 * @return Aktuelle Sortierung
	 */
	public AktivitaetsSortierung sortierung() {
		return sortierung;
	}

	/**
	 * <p>
	 * Methode zum Setzen der Aktivitaets-Sortierung.
	 * </p>
	 * 
	 * @param sortierung
	 *            Die neue Sortierung
	 */
	public void setSortierung(AktivitaetsSortierung sortierung) {
		this.sortierung = sortierung;
		SortedSet<Aktivitaet> temp = aktivitaeten;
		aktivitaeten = new TreeSet<Aktivitaet>(sortierung.getOrdnungsFunktion());
		aktivitaeten.addAll(temp);
		sortierungsManager.event(new SortierungChangedEventFactory()
				.sortierungsChangedEvent(sortierung));
		aktivitaetenManager.event(new AktivitaetenChangedEventFactory()
				.aktivitaetenChangedEvent(aktivitaeten));
	}

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
			AktivitaetenChangedListener listener) {
		aktivitaetenManager.addEventListener(listener);
	}

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
			AktivitaetenChangedListener listener) {
		aktivitaetenManager.delEventListener(listener);
	}

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
	public void addSortierungChangedListener(SortierungChangedListener listener) {
		sortierungsManager.addEventListener(listener);
	}

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
			SortierungChangedListener listener) {
		sortierungsManager.delEventListener(listener);
	}

	/**
	 * <p>
	 * Starten der Transaktion, das Modell muss den aktuellen Zustand
	 * sicherstellen, um ihn wiederherstellen zu können.
	 * </p>
	 */
	public void starteAenderung() {
		backupListe = new TreeSet<Aktivitaet>(sortierung.getOrdnungsFunktion());
		backupListe.addAll(aktivitaeten);
	}

	/**
	 * <p>
	 * Änderungen wurden abgeschlossen und sollen übernommen werden.
	 * </p>
	 */
	public void commitAenderung() {
		aktivitaetenManager.event(new AktivitaetenChangedEventFactory()
				.aktivitaetenChangedEvent(aktivitaeten));
	}

	/**
	 * <p>
	 * Der Änderungsvorgang wurde abgebrochen, der alte Zustand soll
	 * wiederhergestellt werden.
	 * </p>
	 */
	public void restoreAenderung() {
		if (backupListe != null) {
			aktivitaeten = backupListe;
		}
	}
}
