package com.bosch.zeitverwaltung.xmlconfiguration;

/**
 * <p>
 * Klasse, die alle im Package benötigten Konstanten enthält.
 * </p>
 * 
 * @author Lars Geyer
 */
class XMLKonstanten {
	/*
	 * Name der Konfigurationsdatei, in der die Konfigurationsdaten gespeichert
	 * werden
	 */
	static final String Konfiguration = "zvw_configuration.xml";

	/*
	 * Liste der Tags, die in der Konfigurationsdatei zum Einsatz kommen
	 */
	static final String tagWurzel = "configuration";
	static final String tagAktivitaeten = "aktivitaeten";
	static final String tagAktivitaetsSortierung = "sortierung";
	static final String tagAktivitaetsSortierungName = "name";
	static final String tagAktivitaetsSortierungNummer = "nummer";
	static final String tagAktivitaetsSortierungKategorie = "kategorie";
	static final String tagSpezialAktivitaeten = "spezial";
	static final String tagSpezialAktivitaet = "sonderaktivitaet";
	static final String tagSpezialAktivitaetTyp = "typ";
	static final String tagSpezialAktivitaetNummer = "buchung";
	static final String tagMinutenDelta = "delta";
	static final String tagMinutenDeltaDauer = "dauer";
	static final String tagPausen = "pausen";
	static final String tagPause = "pause";
}
