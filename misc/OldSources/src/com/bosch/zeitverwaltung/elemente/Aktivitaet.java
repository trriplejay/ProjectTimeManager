package com.bosch.zeitverwaltung.elemente;

/**
 * <p>
 * Datenstruktur zur Speicherung einer Aktivität
 * </p>
 * 
 * @author Lars Geyer
 * @see TagesEreignis
 */
public class Aktivitaet {
	/**
	 * <p>
	 * Die Aktivität beinhaltet Reisetätigkeiten mit dem Privatfahrzeug, d.h.
	 * Abrechnung in Kilometer
	 * </p>
	 */
	private static final int AbrechnungKm = 1;
	/**
	 * <p>
	 * Die Aktivität beinhaltet Reisetätigkeiten mit öffentlichen
	 * Verkehrsmitteln, d.h. Abrechnung in Euro
	 * </p>
	 */
	private static final int AbrechnungEuro = 2;
	/**
	 * <p>
	 * Die Aktivität beinhaltet keine abrechnungsrelevanten Reisetätigkeiten
	 * </p>
	 */
	private static final int KeineAbrechnung = 3;

	/**
	 * <p>
	 * Keine Reisetätigkeit
	 * </p>
	 */
	private static final int KeineReisezeit = 1;
	/**
	 * <p>
	 * Aktive Reisezeit
	 * </p>
	 */
	private static final int AktiveReisezeit = 2;
	/**
	 * <p>
	 * Passive Reisezeit
	 * </p>
	 */
	private static final int PassiveReisezeit = 3;

	private final String aktivitaet;
	private final String kategorie;
	private final String buchungsNummer;
	private final boolean projektAktivitaet;
	private int abrechnungsTyp;
	private int abrechnungsInfo;
	private int reisezeit;

	/**
	 * <p>
	 * Konstruktor, er initialisiert die Datenstruktur zu einer
	 * Nicht-Reiseaktivität. Reisedaten können danach über Set-Methoden
	 * übergeben werden.
	 * </p>
	 * 
	 * @param aktivitaet
	 *            Name der Aktivität
	 * @param kategorie
	 *            Kategorie der Aktivität, muss für die gleiche Buchungsnummer
	 *            gleich sein
	 * @param buchungsNummer
	 *            Buchungsnummer der Aktivität, für jede Kategorie gibt es eine
	 *            Buchungsnummer
	 * @param projektAktivitaet
	 *            Ist die Aktivität eine Projektaktivität (<em>true</em>) oder
	 *            eine Linienaktivität
	 */
	public Aktivitaet(final String aktivitaet, final String kategorie,
			final String buchungsNummer, final boolean projektAktivitaet) {
		this.aktivitaet = (aktivitaet != null) ? aktivitaet : kategorie;
		this.kategorie = kategorie;
		this.buchungsNummer = buchungsNummer;
		this.projektAktivitaet = projektAktivitaet;
		this.abrechnungsTyp = KeineAbrechnung;
		this.abrechnungsInfo = 0;
		this.reisezeit = KeineReisezeit;
	}

	/**
	 * <p>
	 * Mache die Aktivität zu einer passiven Reiseaktivität und trägt die
	 * Abrechnungsinformationen ein.
	 * </p>
	 * 
	 * @param euro
	 *            Betrag, der abzurechnen ist (auf ein 100stel Cent genau wg.
	 *            VVS)
	 */
	public void setEuro(final double euro) {
		this.abrechnungsTyp = AbrechnungEuro;
		this.abrechnungsInfo = (int) (euro * 10000.0);
		this.setReisezeit(false);
	}

	/**
	 * <p>
	 * Mache die Aktivität zu einer aktiven Reiseaktivität und trägt die
	 * Abrechnungsinformationen ein.
	 * </p>
	 * 
	 * @param km
	 *            Kilometer, die durch die Reisetätigkeit abzurechnen sind
	 */
	public void setKm(final int km) {
		this.abrechnungsTyp = AbrechnungKm;
		this.abrechnungsInfo = km;
		this.setReisezeit(true);
	}

	/**
	 * <p>
	 * Mache die Aktivität zu einer Reiseaktivität ohne Abrechnung
	 * </p>
	 * 
	 * @param aktiv
	 *            true heißt aktive Reisezeit, false passive Reisezeit
	 */
	public void setReisezeit(final boolean aktiv) {
		this.reisezeit = (aktiv) ? AktiveReisezeit : PassiveReisezeit;
	}

	/**
	 * <p>
	 * Gibt Namen der Aktivität zurück
	 * </p>
	 * 
	 * @return Name der Aktivität
	 */
	public String getAktivitaet() {
		return this.aktivitaet;
	}

	/**
	 * <p>
	 * Gibt Kategorie der Aktivität zurück
	 * </p>
	 * 
	 * @return Kategorie der Aktivität
	 */
	public String getKategorie() {
		return this.kategorie;
	}

	/**
	 * <p>
	 * Gibt Buchungsnummer der Aktivität zurück
	 * </p>
	 * 
	 * @return Buchungsnummer der Aktivität
	 */
	public String getBuchungsNummer() {
		return this.buchungsNummer;
	}

	/**
	 * <p>
	 * Abfrage, ob Aktivität Projekt- (true) oder Linientätigkeit (false) ist.
	 * </p>
	 * 
	 * @return Ist Aktivität Projekttätigkeit
	 */
	public boolean istProjektAktivitaet() {
		return this.projektAktivitaet;
	}

	/**
	 * <p>
	 * Ist die Aktivität eine Reiseaktivität?
	 * </p>
	 * 
	 * @return Reiseaktivität?
	 */
	public boolean istReisezeit() {
		return (this.reisezeit != KeineReisezeit);
	}

	/**
	 * <p>
	 * Ist die Aktivität eine aktive Reiseaktivität?
	 * </p>
	 * 
	 * @return Aktive Reiseaktivität?
	 */
	public boolean istAktiveReisezeit() {
		return (this.reisezeit == AktiveReisezeit);
	}

	/**
	 * <p>
	 * Ist die Aktivität eine passive Reiseaktivität?
	 * </p>
	 * 
	 * @return Passive Reiseaktivität?
	 */
	public boolean istPassiveReisezeit() {
		return (this.reisezeit == PassiveReisezeit);
	}

	/**
	 * <p>
	 * Ist Aktivität für Reiseabrechnungen relevant
	 * </p>
	 * 
	 * @return Abrechnungsrelevanz
	 */
	public boolean abrechungsRelevant() {
		return (this.abrechnungsTyp != KeineAbrechnung);
	}

	/**
	 * <p>
	 * Werden Reisetätigkeiten in Kilometern abgerechnet
	 * </p>
	 * 
	 * @return Abrechnung in Kilometer
	 */
	public boolean abrechnungInKm() {
		return (this.abrechnungsTyp == AbrechnungKm);
	}

	/**
	 * <p>
	 * Werden Reisetätigkeiten in Euro abgerechnet
	 * </p>
	 * 
	 * @return Abrechnung in Euro
	 */
	public boolean abrechnungInEuro() {
		return (this.abrechnungsTyp == AbrechnungEuro);
	}

	/**
	 * <p>
	 * Ausgabe der Abrechnungsinformationen als String, z.B. um sie in einem
	 * Dialog darzustellen
	 * </p>
	 * 
	 * @return Abrechungsinfo als String
	 */
	public String getAbrechnungsInfo() {
		String back = "";
		if (this.abrechnungsTyp == AbrechnungEuro) {
			back = Integer.toString(this.abrechnungsInfo / 10000) + ",";
			if (((this.abrechnungsInfo % 10000) % 10) != 0) {
				back = back + Integer.toString(this.abrechnungsInfo % 10000)
						+ " €";
			} else if (((this.abrechnungsInfo % 10000) % 100) != 0) {
				back = back
						+ Integer.toString(this.abrechnungsInfo % 10000 / 10)
						+ " €";
			} else {
				back = back
						+ Integer
								.toString((this.abrechnungsInfo % 10000) / 100)
						+ " €";
			}
		} else if (this.abrechnungsTyp == AbrechnungKm) {
			back = Integer.toString(this.abrechnungsInfo) + " km";
		}
		return back;
	}

	/**
	 * <p>
	 * Ausgabe des Abrechnungsbetrages, bei Abrechnung in Euro der Betrag, bei
	 * Abrechnung in Kilometer Kilometer multipliziert mit dem Kilometer-Betrag.
	 * Dieser ist hartkodiert 0,30€.
	 * </p>
	 * 
	 * @return Abrechnungsbetrag
	 */
	public double getAbrechnungsBetrag() {
		double back = 0.0;
		if (this.abrechnungsTyp == AbrechnungEuro) {
			back = this.abrechnungsInfo / 10000.0;
		} else if (this.abrechnungsTyp == AbrechnungKm) {
			back = this.abrechnungsInfo * 0.3;
		}
		return back;
	}

	/**
	 * <p>
	 * Ausgabe des Kilometerwertes bei einer Abrechnung in Kilometer-Aktivität.
	 * </p>
	 * 
	 * @return Kilometerwert
	 */
	public int getAbrechnungsKm() {
		int back = 0;
		if (this.abrechnungsTyp == AbrechnungKm) {
			back = this.abrechnungsInfo;
		}
		return back;
	}

	/**
	 * <p>
	 * Stringausgabe der Aktivität, besteht aus dem Aktivitäts-Namen
	 * </p>
	 * 
	 * @return Stringrepräsentation der Aktivität
	 */
	@Override
	public String toString() {
		return this.aktivitaet;
	}

	/**
	 * <p>
	 * Vergleicht zwei Aktivitäten bzgl. Gleichheit
	 * </p>
	 * 
	 * @param obj
	 *            Vergleichsobjekt
	 * @return Gleichheit der beiden Objekte
	 */
	@Override
	public boolean equals(final Object obj) {
		boolean back = false;
		if (obj instanceof Aktivitaet) {
			final Aktivitaet vgl = (Aktivitaet) obj;
			back = true;
			if (!this.aktivitaet.equals(vgl.getAktivitaet())) {
				back = false;
			} else if (!this.kategorie.equals(vgl.getKategorie())) {
				back = false;
			} else if (!this.buchungsNummer.equals(vgl.getBuchungsNummer())) {
				back = false;
			}
		}
		return back;
	}
}
