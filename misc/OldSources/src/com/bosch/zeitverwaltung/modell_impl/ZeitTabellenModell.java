package com.bosch.zeitverwaltung.modell_impl;

import java.util.ArrayList;
import java.util.Date;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.elemente.Buchung;
import com.bosch.zeitverwaltung.elemente.Tag;
import com.bosch.zeitverwaltung.elemente.Uhrzeit;
import com.bosch.zeitverwaltung.elemente.Unterbrechung;
import com.bosch.zeitverwaltung.event.EventVerteiler;
import com.bosch.zeitverwaltung.modell.TagesBuchungen;
import com.bosch.zeitverwaltung.modell.event.BuchungenChangedEvent;
import com.bosch.zeitverwaltung.modell.event.BuchungenChangedEventFactory;
import com.bosch.zeitverwaltung.modell.listener.BuchungenChangedListener;

/**
 * <p>
 * Die Klasse ist für die Verwaltung der Buchungen eines Tages verantwortlich.
 * </p>
 * 
 * @author Lars Geyer
 */
public final class ZeitTabellenModell implements TagesBuchungen {
	private static final long serialVersionUID = 1L;

	private Tag buchungsTag = null;

	private ArrayList<Buchung> buchungen = new ArrayList<Buchung>(20);

	private boolean veraendert = false;

	private EventVerteiler<BuchungenChangedEvent, BuchungenChangedListener> listenerManager =
		new EventVerteiler<BuchungenChangedEvent, BuchungenChangedListener>();

	/**
	 * <p>
	 * Gibt den Tag zurück, für den das Objekt Buchungen speichert.
	 * </p>
	 * 
	 * @return Buchungstag
	 */
	public Tag getBuchungsTag() {
		return buchungsTag;
	}

	/**
	 * <p>
	 * Übergabe des Buchungstages durch die Factory
	 * </p>
	 * 
	 * @param buchungsTag
	 *            Buchungstag
	 */
	void setBuchungsTag(Tag buchungsTag) {
		this.buchungsTag = buchungsTag;
	}

	/**
	 * {@inheritDoc}
	 */
	public int addBuchung(Aktivitaet aktivitaet) {
		Uhrzeit zeitpunkt = null;
		int index = buchungen.size() - 1;
		if ((index >= 0) && (buchungen.get(index).getEndeZeit() != null)) {
			zeitpunkt = buchungen.get(index).getEndeZeit();
		} else {
			zeitpunkt = new Uhrzeit(new Date());
			if (buchungen.size() > 0) {
				Buchung vorherigeBuchung = buchungen.get(buchungen.size() - 1);
				if (zeitpunkt.compareTo(vorherigeBuchung.getStartZeit()) < 0) {
					return -1;
				}
				if (zeitpunkt.compareTo(vorherigeBuchung.getStartZeit()) == 0) {
					buchungen.remove(index);
				} else {
					vorherigeBuchung.setEndeZeit(zeitpunkt);
				}
			}
		}
		Buchung neueBuchung;
		if (aktivitaet != null) {
			neueBuchung = new Buchung(zeitpunkt, aktivitaet);
		} else {
			neueBuchung = new Unterbrechung(zeitpunkt);
		}
		buchungen.add(neueBuchung);
		beendeAenderung();
		return buchungen.size()-1;
	}

	/**
	 * {@inheritDoc}
	 */
	public int addUnterbrechung() {
		if (buchungen.size() > 0) {
			return addBuchung(null);
		}
		return -1;
	}

	/**
	 * <p>
	 * Beendet die aktuelle Buchung, d.h. deren Endezeitpunkt wird auf die
	 * aktuelle Uhrzeit gesetzt.
	 * </p>
	 */
	public void beendeBuchung() {
		int index = buchungen.size() - 1;
		if (index >= 0) {
			Buchung buchung = buchungen.get(index);
			if (buchung.getEndeZeit() == null) {
				Uhrzeit zeitpunkt = new Uhrzeit(new Date());
				if (zeitpunkt.compareTo(buchung.getStartZeit()) > 0) {
					buchung.setEndeZeit(zeitpunkt);
					beendeAenderung();
				}
			}
		}
	}

	/**
	 * <p>
	 * Methode löscht die übergebene Buchung aus der Liste der Buchungen. Die
	 * Methode setzt die Startzeit der nachfolgenden Buchung auf die Startzeit
	 * der zu löschenden Buchung. Bei der letzten Buchung wird die Endezeit auf
	 * die Endezeit der vorhergehenden Buchung gesetzt.
	 * </p>
	 * 
	 * @param buchung
	 *            Zu löschende Buchung
	 */
	public void loescheBuchung(Buchung buchung) {
		int index = buchungen.indexOf(buchung);
		if (index >= 0) {
			Buchung nachbarBuchung = null;
			if (index < buchungen.size() - 1) {
				nachbarBuchung = buchungen.get(index + 1);
				nachbarBuchung.setStartZeit(buchung.getStartZeit());
			} else if (index > 0) {
				nachbarBuchung = buchungen.get(index - 1);
				nachbarBuchung.setEndeZeit(buchung.getEndeZeit());
			}
			buchungen.remove(index);
			beendeAenderung();
		}
	}

	/**
	 * <p>
	 * Verändert die Startzeit einer Buchung. Diese Methode ist auf die
	 * Darstellung der Buchungen in einer Liste ausgelegt, da sie die Buchung an
	 * Hand ihrer Position in der aktuellen Buchungsreihenfolge identifiziert.
	 * </p>
	 * 
	 * <p>
	 * Die Methode macht Konsistenzchecks und ändert die Buchung nur, wenn sie
	 * im Kontext der anderen Buchungen Sinn macht, d.h. Startzeit ist vor dem
	 * Endezeitpunkt, die Startzeit ist nicht früher als die Startzeiten der
	 * vorhergehenden Buchung, etc.
	 * </p>
	 * 
	 * @param index
	 *            Buchung in Form der Position in der Buchungsreihenfolge
	 * @param neueStartzeit
	 *            Neue Startzeit der Buchung
	 */
	public void aendereStartzeit(int index, Uhrzeit neueStartzeit) {
		Buchung buchung = buchungen.get(index);
		if (index > 0) {
			Buchung nachbarBuchung = buchungen.get(index - 1);
			Uhrzeit vergleichsZeit = nachbarBuchung.getStartZeit();
			if (neueStartzeit.compareTo(vergleichsZeit) < 0) {
				return;
			}
			nachbarBuchung.setEndeZeit(neueStartzeit);
		}
		buchung.setStartZeit(neueStartzeit);
		beendeAenderung();
	}

	/**
	 * <p>
	 * Verändert die Endezeit einer Buchung. Diese Methode ist auf die
	 * Darstellung der Buchungen in einer Liste ausgelegt, da sie die Buchung an
	 * Hand ihrer Position in der aktuellen Buchungsreihenfolge identifiziert.
	 * </p>
	 * 
	 * <p>
	 * Die Methode macht Konsistenzchecks und ändert die Buchung nur, wenn sie
	 * im Kontext der anderen Buchungen Sinn macht, d.h. Endezeit ist nach dem
	 * Startzeitpunkt, die Endezeit ist nicht später als die Endezeiten der
	 * nachfolgenden Buchung, etc.
	 * </p>
	 * 
	 * @param index
	 *            Buchung in Form der Position in der Buchungsreihenfolge
	 * @param neueEndezeit
	 *            Neue Endezeit der Buchung
	 */
	public void aendereEndezeit(int index, Uhrzeit neueEndezeit) {
		Buchung buchung = buchungen.get(index);
		if (buchung.getStartZeit().compareTo(neueEndezeit) > 0) {
			return;
		}
		if ((index + 1) < buchungen.size()) {
			Buchung nachbarBuchung = buchungen.get(index + 1);
			Uhrzeit vergleichsZeit = nachbarBuchung.getEndeZeit();
			if (vergleichsZeit != null) {
				if (neueEndezeit.compareTo(vergleichsZeit) >= 0) {
					return;
				}
			}
			nachbarBuchung.setStartZeit(neueEndezeit);
		}
		buchung.setEndeZeit(neueEndezeit);
		beendeAenderung();
	}

	/**
	 * <p>
	 * Verändert die Aktivität einer Buchung. Diese Methode ist auf die
	 * Darstellung der Buchungen in einer Liste ausgelegt, da sie die Buchung an
	 * Hand ihrer Position in der aktuellen Buchungsreihenfolge identifiziert.
	 * </p>
	 * 
	 * @param index
	 *            Buchung in Form der Position in der Buchungsreihenfolge
	 * @param neueAktivitaet
	 *            Neue Aktivitaet der Buchung
	 */
	public void aendereAktivitaet(int index, Aktivitaet neueAktivitaet) {
		Buchung buchung = buchungen.get(index);
		buchung.setAktivitaet(neueAktivitaet);
		beendeAenderung();
	}

	/**
	 * <p>
	 * Verändert den Kommentar einer Buchung. Diese Methode ist auf die
	 * Darstellung der Buchungen in einer Liste ausgelegt, da sie die Buchung an
	 * Hand ihrer Position in der aktuellen Buchungsreihenfolge identifiziert.
	 * </p>
	 * 
	 * @param index
	 *            Buchung in Form der Position in der Buchungsreihenfolge
	 * @param neuerKommentar
	 *            Neuer Kommentar der Buchung
	 */
	public void aendereKommentar(int index, String neuerKommentar) {
		Buchung buchung = buchungen.get(index);
		buchung.setKommentar(neuerKommentar);
		beendeAenderung();
	}

	/**
	 * <p>
	 * Gibt die Anzahl der Buchungen für diesen Tag aus.
	 * </p>
	 * 
	 * @return Anzahl Buchungen
	 */
	public int getAnzahlBuchungen() {
		return buchungen.size();
	}

	/**
	 * <p>
	 * Gibt die Buchung an der übergebenen Position in der Reihenfolge der
	 * Buchungen für diesen Tag zurück.
	 * </p>
	 * 
	 * @param index
	 *            Position der abgefragten Buchung
	 * @return Buchung
	 */
	public Buchung getBuchung(int index) {
		return buchungen.get(index);
	}

	/**
	 * <p>
	 * Hilfsmethode, um bei einer Änderung die notwendigen Zustandsänderungen
	 * durchzuführen.
	 * </p>
	 */
	private void beendeAenderung() {
		listenerManager.event(new BuchungenChangedEventFactory()
				.buchungenChangedEvent(this));
		veraendert = true;
	}

	/**
	 * <p>
	 * Wurde das Modell verändert?
	 * </p>
	 * 
	 * @return Das Modell wurde verändert
	 */
	public boolean veraendert() {
		return veraendert;
	}

	/**
	 * <p>
	 * Eine Sicherung der Daten wurde vorgenommen, erst nach einer Änderung ist
	 * eine Sicherung wieder notwendig.
	 * </p>
	 */
	public void aenderungenGesichert() {
		veraendert = false;
	}

	/**
	 * <p>
	 * Ein Listener möchte Buchungsveränderungen signalisiert bekommen.
	 * </p>
	 * 
	 * @param listener
	 *            Listener, der an Buchungsänderungen interessiert ist
	 */
	public void addBuchungenChangedListener(BuchungenChangedListener listener) {
		listenerManager.addEventListener(listener);
	}

	/**
	 * <p>
	 * Entfernt einen Listener, der Buchungsänderungen signalisiert bekommen
	 * möchte.
	 * </p>
	 * 
	 * @param listener
	 *            Zu entfernender Listener
	 */
	public void removeBuchungenChangedListener(BuchungenChangedListener listener) {
		listenerManager.delEventListener(listener);
	}
}
