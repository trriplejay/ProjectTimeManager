package com.bosch.zeitverwaltung.auswertung.framework.modell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.elemente.Buchung;

/**
 * <p>
 * Klasse führt die Auswertung der Buchungen eines Tages durch.
 * </p>
 * 
 * @author Lars Geyer
 * @see BuchungsAuswertung
 * @see Zusammenfassung
 */
public class BuchungsBerechnung {
	private Collection<Buchung> buchungsListe = null;

	/**
	 * <p>
	 * Setzt die Liste mit den Buchungen für die Auswertung
	 * </p>
	 * 
	 * @param buchungen
	 *            Die Liste mit den auszuwertenden Buchungen
	 */
	protected void setBuchungen(Collection<Buchung> buchungen) {
		buchungsListe = buchungen;
	}

	/**
	 * <p>
	 * Sortiert die mittels buchungen übergebenen Buchungen nach Aktivitäten und
	 * summiert die für die Aktivität jeweils gebuchten Minuten.
	 * </p>
	 * 
	 * @return Liste mit <em>BuchungsAuswertung</em>-Objekten für jede
	 *         Aktivität eines
	 */
	public List<BuchungsAuswertung> getAbbildungAktivitaetStunden() {
		AktivitaetsZugriff meinZugriff = new AktivitaetsZugriff() {
			public String getReferenz(Aktivitaet aktv) {
				return aktv.getAktivitaet();
			}
		};
		return berechneAbbildung(buchungsListe, meinZugriff);
	}

	/**
	 * <p>
	 * Sortiert die mittels buchungen übergebenen Buchungen nach
	 * Aktivitätskategorien und summiert die für die Kategorie jeweils gebuchten
	 * Minuten.
	 * </p>
	 * 
	 * @return Liste mit <em>BuchungsAuswertung</em>-Objekten für jede
	 *         Kategorie eines
	 */
	public List<BuchungsAuswertung> getAbbildungKategorieStunden() {
		AktivitaetsZugriff meinZugriff = new AktivitaetsZugriff() {
			public String getReferenz(Aktivitaet aktv) {
				return aktv.getKategorie();
			}
		};
		return berechneAbbildung(buchungsListe, meinZugriff);
	}

	/**
	 * <p>
	 * Gibt die Zusammenfassung der übergebenen Buchungen zurück
	 * </p>
	 * 
	 * @return <em>Zusammenfassung</em>-Objekt mit der Zusammenfassung der
	 *         Buchungen
	 */
	public Zusammenfassung getZusammenfassung() {
		return new Zusammenfassung(getArbeitszeit(buchungsListe),
				getProjektzeit(buchungsListe));
	}

	/**
	 * <p>
	 * Eigentliche Ermittlung von Aktivitäten/kategorien und Aufsummierung. Wird
	 * von den beiden Methoden <em>getAbbildungKategorieStunden</em> und
	 * <em>getAbbildungAktivitaetStunden</em> benutzt. Der
	 * <em>AktivitaetsZugriff</em> ist die Möglichkeit, zwischen Aktivitaet
	 * und Kategorie zu unterscheiden, ansonsten ist der Algorithmus gleich.
	 * </p>
	 * 
	 * @param buchungen
	 *            Buchungen
	 * @param meinZugriff
	 *            Zugriff auf die Aktivität wird gekapselt, um verschiedene
	 *            Zugriffe und somit Sortierungskriterien zu ermöglichen
	 * @return Liste mit <em>BuchungsAuswertung</em>-Objekten für jede
	 *         Aktitaet/Kategorie eines
	 */
	private List<BuchungsAuswertung> berechneAbbildung(
			Collection<Buchung> buchungen, AktivitaetsZugriff meinZugriff) {
		SortedSet<BuchungsAuswertung> back = new TreeSet<BuchungsAuswertung>(
				BuchungsAuswertung.getComparator());

		List<Aktivitaet> aktivitaeten = extrahiereElemente(buchungen,
				meinZugriff);

		int[] minuten = berechneMinuten(aktivitaeten, buchungen, meinZugriff);
		int arbeitszeit = getArbeitszeit(buchungen);
		int projektzeit = getProjektzeit(buchungen);
		Aktivitaet aktv;

		for (int i = 0; i < minuten.length; i++) {
			aktv = aktivitaeten.get(i);
			back.add(new BuchungsAuswertung(meinZugriff.getReferenz(aktv), aktv
					.getBuchungsNummer(), minuten[i], arbeitszeit, aktv
					.istProjektAktivitaet() ? projektzeit : -1));
		}

		return new LinkedList<BuchungsAuswertung>(back);
	}

	/**
	 * <p>
	 * Summiert Arbeitszeit, die in den übergebenen Buchungen gespeichert sind
	 * zur Gesamtarbeitszeit
	 * </p>
	 * 
	 * @param buchungen
	 *            Buchungen
	 * @return Summe der gespeicherten Arbeitszeiten in Minuten
	 */
	private int getArbeitszeit(Collection<Buchung> buchungen) {
		int arbeitsminuten = 0;
		Iterator<Buchung> iter = buchungen.iterator();
		while (iter.hasNext()) {
			Buchung buchung = iter.next();
			if (buchung.getEndeZeit() != null) {
				arbeitsminuten += buchung.berechneBuchungsdauer();
			}
		}
		return arbeitsminuten;
	}

	/**
	 * <p>
	 * Summiert Arbeitszeit, die in den übergebenen Buchungen gespeichert sind,
	 * sofern die Aktivität eine Projektaktivität ist sind.
	 * </p>
	 * 
	 * @param buchungen
	 *            Buchungen
	 * @return Summe der gespeicherten Projekt-Arbeitszeiten in Minuten
	 */
	private int getProjektzeit(Collection<Buchung> buchungen) {
		int arbeitsminuten = 0;
		Iterator<Buchung> iter = buchungen.iterator();
		while (iter.hasNext()) {
			Buchung buchung = iter.next();
			if (buchung.getAktivitaet().istProjektAktivitaet()) {
				if (buchung.getEndeZeit() != null) {
					arbeitsminuten += buchung.berechneBuchungsdauer();
				}
			}
		}
		return arbeitsminuten;
	}

	/**
	 * <p>
	 * Extrahiert aus einer Liste von Buchungen die Aktivitäten oder Kategorien,
	 * je nach <em>AktivitaetsZugriff</em>-Objekt.
	 * </p>
	 * 
	 * @param buchungen
	 *            Buchungen
	 * @param referenz
	 *            Zugriff auf Aktivität (Aktivität/Kategorie)
	 * @return Liste mit Aktivitäten/Kategorien, die in den Buchungen verwendet
	 *         werden
	 */
	private List<Aktivitaet> extrahiereElemente(Collection<Buchung> buchungen,
			AktivitaetsZugriff referenz) {
		List<Aktivitaet> back = new ArrayList<Aktivitaet>();

		Iterator<Buchung> iter = buchungen.iterator();
		while (iter.hasNext()) {
			Buchung buchung = iter.next();
			if (buchung.getEndeZeit() != null) {
				Aktivitaet aktAktivitaet = buchung.getAktivitaet();
				if (!enthaelt(back, aktAktivitaet, referenz)) {
					back.add(aktAktivitaet);
				}
			}
		}
		return back;
	}

	/**
	 * <p>
	 * Prüft, ob die Aktivität/Kategorie bereits in der Liste enthalten ist.
	 * </p>
	 * 
	 * @param liste
	 *            Liste mit Aktivitäten/Kategorien
	 * @param aktv
	 *            Zu prüfende Aktivität
	 * @param referenz
	 *            Zugriff auf Aktivität (Aktivität/Kategorie)
	 * @return true, falls die Aktivität bereits in der Liste enthalten ist
	 */
	private boolean enthaelt(List<Aktivitaet> liste, Aktivitaet aktv,
			AktivitaetsZugriff referenz) {
		return (sucheIndex(liste, aktv, referenz) > 0);
	}

	/**
	 * <p>
	 * Geht durch die Buchungen und summiert für jede Aktivität/Kategorie die
	 * dafür verwendeten Minuten auf.
	 * </p>
	 * 
	 * @param aktivitaeten
	 *            Liste mit Aktivitäten
	 * @param buchungen
	 *            Buchungen
	 * @param referenz
	 *            Zugriff auf Aktivität (Aktivität/Kategorie)
	 * @return Array mit Minuten, für jede Aktivität/Kategorie die aufsummierte
	 *         Anzahl
	 */
	private int[] berechneMinuten(List<Aktivitaet> aktivitaeten,
			Collection<Buchung> buchungen, AktivitaetsZugriff referenz) {
		int[] minuten = new int[aktivitaeten.size()];
		for (int i = 0; i < minuten.length; i++) {
			minuten[i] = 0;
		}

		Iterator<Buchung> iter = buchungen.iterator();
		while (iter.hasNext()) {
			Buchung buchung = iter.next();
			if (buchung.getEndeZeit() != null) {
				int index = sucheIndex(aktivitaeten, buchung.getAktivitaet(),
						referenz);
				minuten[index] += buchung.berechneBuchungsdauer();
			}
		}
		return minuten;
	}

	/**
	 * <p>
	 * Sucht in der Liste der Aktivitäten/Kategorien nach dem Index der
	 * aktuellen Aktivität
	 * </p>
	 * 
	 * @param liste
	 *            Liste der Aktivitäten/Kategorien
	 * @param aktv
	 *            Aktuelle Aktivität
	 * @param referenz
	 *            Zugriff auf die Aktivität (Aktivität/Kategorie)
	 * @return Index der Aktivität in der Liste
	 */
	private int sucheIndex(List<Aktivitaet> liste, Aktivitaet aktv,
			AktivitaetsZugriff referenz) {
		int back = -1;

		Iterator<Aktivitaet> iter = liste.iterator();
		String suche = referenz.getReferenz(aktv);
		Aktivitaet aktuell;
		int index = 0;
		while (iter.hasNext()) {
			aktuell = iter.next();
			if (suche.equals(referenz.getReferenz(aktuell))) {
				back = index;
				break;
			}
			index++;
		}
		return back;
	}

	/**
	 * <p>
	 * Mit Hilfe dieses Interfaces wird gewährleistet, dass die Funktionen zur
	 * Berechnung der Tages-Buchungen für Aktivitäten und Aktivitätskategorien
	 * gleich ist. Eine Implementierung des Interface stellt sicher, dass in der
	 * Berechnung auf den richtigen Wert zugegriffen wird.
	 * </p>
	 */
	private interface AktivitaetsZugriff {
		String getReferenz(Aktivitaet aktv);
	}
}
