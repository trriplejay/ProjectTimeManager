package com.bosch.zeitverwaltung.xmlbasics;

/**
 * <p>
 * Die Klasse enthält Tagnamen, die vom Basis-Writer und -Reader verwendet
 * werden, um Daten in Konfigurationen oder Buchungsfiles abzuspeichern und zu
 * lesen.
 * </p>
 * 
 * @author Lars Geyer
 * @see XMLBasisReader
 * @see XMLBasisWriter
 */
class XMLBasisKonstanten {
	/*
	 * Verwendete Tags als Konstanten
	 */
	static final String tagAktivitaet = "aktivitaet";
	static final String tagAktivitaetsName = "name";
	static final String tagAktivitaetUnterbrechung = "xmlunterbrechung";
	static final String tagAktivitaetsNummer = "buchungsnummer";
	static final String tagAktivitaetsKategorie = "kategorie";
	static final String tagProjektAktivitaet = "projekt";
	static final String tagReisezeitAktiv = "rza";
	static final String tagReisezeitPassiv = "rzb";
	static final String tagAktivitaetsAbrechnung = "abrechnung";
	static final String tagAktivitaetsAbrechnungKM = "km";
	static final String tagAktivitaetsAbrechnungEuro = "euro";

	static final String tagStartzeit = "startzeit";
	static final String tagEndezeit = "endezeit";

	static final String tagUhrzeit = "uhrzeit";
	static final String tagUhrzeitStunde = "stunde";
	static final String tagUhrzeitMinute = "minute";
}
