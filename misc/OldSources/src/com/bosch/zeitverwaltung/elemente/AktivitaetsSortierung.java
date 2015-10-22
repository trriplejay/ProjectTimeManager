package com.bosch.zeitverwaltung.elemente;

import java.util.Comparator;

/**
 * <p>
 * Enumeration der Aktivitätssortierungen
 * </p>
 * 
 * @author Lars Geyer
 */
public final class AktivitaetsSortierung {
	public static final int AktivitaetsSortierungNachBuchungsnummerID = 1;
	public static final int AktivitaetsSortierungNachKategorieID = 2;
	public static final int AktivitaetsSortierungNachNameID = 3;

	public static final AktivitaetsSortierung AktivitaetsSortierungNachBuchungsnummer = new AktivitaetsSortierung(
			AktivitaetsSortierungNachBuchungsnummerID,
			new Comparator<Aktivitaet>() {
				/**
				 * <p>
				 * Vergleichsfunktion, die eine strenge Ordnung auf die
				 * Aktivitäten definiert
				 * </p>
				 * 
				 * @param akt1
				 *            Vergleichsobjekt 1
				 * @param akt2
				 *            Vergleichsobjekt 2
				 */
				public int compare(Aktivitaet akt1, Aktivitaet akt2) {
					int back = akt1.getBuchungsNummer().compareTo(
							akt2.getBuchungsNummer());
					if (back == 0) {
						back = akt1.getAktivitaet().compareToIgnoreCase(
								akt2.getAktivitaet());
					}
					return back;
				}
			});

	public static final AktivitaetsSortierung AktivitaetsSortierungNachKategorie = new AktivitaetsSortierung(
			AktivitaetsSortierungNachKategorieID, new Comparator<Aktivitaet>() {
				/**
				 * <p>
				 * Vergleichsfunktion, die eine strenge Ordnung auf die
				 * Aktivitäten definiert
				 * </p>
				 * 
				 * @param akt1
				 *            Vergleichsobjekt 1
				 * @param akt2
				 *            Vergleichsobjekt 2
				 */
				public int compare(Aktivitaet akt1, Aktivitaet akt2) {
					int back = akt1.getKategorie().compareTo(
							akt2.getKategorie());
					if (back == 0) {
						back = akt1.getAktivitaet().compareToIgnoreCase(
								akt2.getAktivitaet());
					}
					return back;
				}
			});

	public static final AktivitaetsSortierung AktivitaetsSortierungNachName = new AktivitaetsSortierung(
			AktivitaetsSortierungNachNameID, new Comparator<Aktivitaet>() {
				/**
				 * <p>
				 * Vergleichsfunktion, die eine strenge Ordnung auf die
				 * Aktivitäten definiert
				 * </p>
				 * 
				 * @param akt1
				 *            Vergleichsobjekt 1
				 * @param akt2
				 *            Vergleichsobjekt 2
				 */
				public int compare(Aktivitaet akt1, Aktivitaet akt2) {
					return akt1.getAktivitaet().compareToIgnoreCase(
							akt2.getAktivitaet());
				}
			});

	private Comparator<Aktivitaet> comparator;
	private int id;

	/**
	 * <p>
	 * Gibt das Enumerationsobjekt für eine ID zurück
	 * </p>
	 */
	public static AktivitaetsSortierung get(int id) {
		AktivitaetsSortierung back = null;
		switch (id) {
		case AktivitaetsSortierungNachBuchungsnummerID:
			back = AktivitaetsSortierungNachBuchungsnummer;
			break;
		case AktivitaetsSortierungNachKategorieID:
			back = AktivitaetsSortierungNachKategorie;
			break;
		case AktivitaetsSortierungNachNameID:
			back = AktivitaetsSortierungNachName;
			break;
		}
		return back;
	}

	/**
	 * <p>
	 * Erzeugt eine spezielle Instanz der Aufzählung
	 * </p>
	 * 
	 * @param id
	 *            ID der Sortierung
	 * @param comparator
	 *            Vergleicher für die Sortierung
	 */
	private AktivitaetsSortierung(int id, Comparator<Aktivitaet> comparator) {
		this.id = id;
		this.comparator = comparator;
	}

	/**
	 * <p>
	 * Gibt ID der Sortierung zurück.
	 * </p>
	 * 
	 * @retuzrn ID der Sortierung
	 */
	public int getID() {
		return id;
	}

	/**
	 * <p>
	 * Definition der Ordnungsfunktion, die eine strenge Ordnung auf die
	 * Aktivitäten definiert. Sie kann zum Sortieren der Aktivitäten verwendet
	 * werden.
	 * </p>
	 * 
	 * @return Vergleicher, der eine Ordnung auf Aktivitäten derfiniert.
	 */
	public Comparator<Aktivitaet> getOrdnungsFunktion() {
		return comparator;
	}
}
