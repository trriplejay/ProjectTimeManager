/**
 * 
 */
package com.bosch.zeitverwaltung.elemente;

/**
 * <p>
 * Buchung, die eine Unterbrechung repräsentiert.
 * </p>
 * 
 * <p>
 * Die Klasse definiert die spezifische Funktionalität einer Unterbrechung.
 * </p>
 * 
 * @author Lars Geyer
 */
public final class Unterbrechung extends Buchung {

	/**
	 * <p>
	 * Konstruktor, er erhält die Startzeit der Buchung und definiert die
	 * Unterbrechungs-Aktivität als Buchungsaktivität.
	 * </p>
	 * 
	 * @param startZeit
	 *            Startzeit der Unterbrechung
	 */
	public Unterbrechung(Uhrzeit startZeit) {
		super(startZeit, new UnterbrechungsAktivitaet());
	}

	/**
	 * <p>
	 * Die Aktivität einer Unterbrechung ist unveränderbar.
	 * </p>
	 * 
	 * @param aktivitaet
	 *            Nicht benutzt
	 */
	@Override
	public void setAktivitaet(Aktivitaet aktivitaet) {

	}

	/**
	 * <p>
	 * Berechnet die Anwesenheitsminuten, da eine Unterbrechung einer
	 * Nichtanwesenheit entspricht, gibt die Methode 0 zurück.
	 * </p>
	 * 
	 * @return 0
	 */
	@Override
	public int berechneBuchungsdauer() {
		return 0;
	}

}
