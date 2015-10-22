package com.bosch.zeitverwaltung.elemente;

/**
 * <p>
 * Diese Klasse repräsentiert eine Buchung. Eine Buchung ist spezifiziert durch
 * einen Start- und einen Endezeitpunkt, sowie einer Aktivität und ggf. einem
 * Kommentar.
 * </p>
 * 
 * @author Lars Geyer
 */
public class Buchung {
	private Zeitspanne zeitdaten;
	private Aktivitaet aktivitaet;
	private String kommentar = "";

	/**
	 * <p>
	 * Konstruktor zur Erzeugung einer Buchung. Beim Erzeugen einer Buchung ist
	 * typischerweise der Endzeitpunkt nicht bekannt und wird daher erst später
	 * festgelegt.
	 * </p>
	 * 
	 * @param startZeit
	 * @param aktivitaet
	 */
	public Buchung(Uhrzeit startZeit, Aktivitaet aktivitaet) {
		zeitdaten = new Zeitspanne(startZeit, null);
		this.aktivitaet = aktivitaet;
	}

	/**
	 * <p>
	 * Umstellen des Startzeitpunkts auf eine neue Zeit
	 * </p>
	 * 
	 * @param startZeit
	 *            Neuer Startzeitpunkt
	 */
	public void setStartZeit(Uhrzeit startZeit) {
		zeitdaten = new Zeitspanne(startZeit, zeitdaten.getEndeZeit());
	}

	/**
	 * <p>
	 * Gibt den Startzeitpunkt der Buchung zurück
	 * </p>
	 * 
	 * @return Startzeitpunkt
	 */
	public Uhrzeit getStartZeit() {
		return zeitdaten.getStartZeit();
	}

	/**
	 * <p>
	 * Setzen des Endezeitpunkts auf eine neue Zeit.
	 * </p>
	 * 
	 * @param endeZeit
	 *            Neuer Endezeitpunkt
	 */
	public void setEndeZeit(Uhrzeit endeZeit) {
		zeitdaten = new Zeitspanne(zeitdaten.getStartZeit(), endeZeit);
	}

	/**
	 * <p>
	 * Gibt den Endezeitpunkt der Buchung zurück
	 * </p>
	 * 
	 * @return Endezeitpunkt
	 */
	public Uhrzeit getEndeZeit() {
		return zeitdaten.getEndeZeit();
	}

	/**
	 * <p>
	 * Berechnet die Anwesenheitszeit der Buchung in Minuten
	 * </p>
	 * 
	 * @return Buchungsdauer
	 */
	public int berechneBuchungsdauer() {
		return zeitdaten.berechneMinutenDifferenz();
	}

	/**
	 * <p>
	 * Abfrage der Aktivität, die der Buchung zugeordnet ist.
	 * </p>
	 * 
	 * @return Aktivität
	 */
	public Aktivitaet getAktivitaet() {
		return aktivitaet;
	}

	/**
	 * <p>
	 * Setzen der Aktivität, die der Buchung zugeordnet ist.
	 * </p>
	 * 
	 * @param aktivitaet
	 *            Neue Aktivität
	 */
	public void setAktivitaet(Aktivitaet aktivitaet) {
		this.aktivitaet = aktivitaet;
	}

	/**
	 * <p>
	 * Abfragen eines ggf. vorhandenen Buchungskommentars oder <em>null</em>,
	 * falls keiner vorhanden ist.
	 * </p>
	 * 
	 * @return Buchungskommentar
	 */
	public String getKommentar() {
		return kommentar;
	}

	/**
	 * <p>
	 * Setzen eines Buchungskommentars. Löschen erfolgt durch eine Übergabe von
	 * <em>null</em>.
	 * </p>
	 * 
	 * @param kommentar
	 *            Buchungskommentar
	 */
	public void setKommentar(String kommentar) {
		if (kommentar != null) {
			this.kommentar = kommentar;
		} else {
			this.kommentar = "";
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return aktivitaet.toString();
	}
}
