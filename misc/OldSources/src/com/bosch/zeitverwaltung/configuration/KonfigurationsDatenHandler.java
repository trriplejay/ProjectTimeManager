package com.bosch.zeitverwaltung.configuration;

import java.io.File;
import java.util.Collection;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.elemente.AktivitaetsSortierung;
import com.bosch.zeitverwaltung.elemente.MinutenDelta;
import com.bosch.zeitverwaltung.services.ApplikationsDatenVerzeichnis;

/**
 * <p>
 * Abstrakte Basisklasse der Konfigurationsdatenbehandlung. Die Klasse
 * beinhaltet alle relevanten Konfigurationsdaten und bietet protected Accessors
 * für diese Daten. Abgeleitete Klassen lesen die Daten aus dem System und
 * sichern sie persistent, oder sie lesen sie aus einer persistenten Sicherung
 * und konfigurieren danach das System.
 * </p>
 * 
 * @author Lars Geyer
 * @see KonfigurationsDatenReader
 * @see KonfigurationsDatenWriter
 */
abstract class KonfigurationsDatenHandler {
	private MinutenDelta minutenDelta;
	private Collection<Aktivitaet> aktivitaeten;
//	private Map<String, Aktivitaet> spezielleAktivitaeten;
	private AktivitaetsSortierung sortierung;

	private String konfigurationsDatei;

	/**
	 * <p>
	 * Zugriff auf den Speicher für die Aktivitäten der Konfiguration
	 * </p>
	 * 
	 * @return Liste mit allen Aktivitäten der Konfiguration
	 */
	protected Collection<Aktivitaet> getAktivitaeten() {
		return aktivitaeten;
	}

	/**
	 * <p>
	 * Speichere die Aktivitäten der Konfiguration
	 * </p>
	 * 
	 * @param aktivitaeten
	 *            Die Aktivitäten der Konfiguration
	 */
	protected void setAktivitaeten(Collection<Aktivitaet> aktivitaeten) {
		this.aktivitaeten = aktivitaeten;
	}

	/**
	 * <p>
	 * Gibt Aktivitaets-Sortierung zurück
	 * </p>
	 * 
	 * @return Aktivitäts-Sortierung
	 */
	protected AktivitaetsSortierung getSortierung() {
		return sortierung;
	}

	/**
	 * <p>
	 * Setze die Aktivitäts-Sortierung der Konfiguration
	 * </p>
	 * 
	 * @param sortierung
	 *            Die Aktivitäts-Sortierung
	 */
	protected void setSortierung(AktivitaetsSortierung sortierung) {
		this.sortierung = sortierung;
	}

	/**
	 * <p>
	 * Lese das Minuten-Delta der Konfiguration
	 * </p>
	 * 
	 * @return Das Minuten-Delta der Konfiguration
	 */
	protected MinutenDelta getMinutenDelta() {
		return minutenDelta;
	}

	/**
	 * <p>
	 * Setze das Minuten-Delta der Konfiguration
	 * </p>
	 * 
	 * @param minutenDelta
	 *            Das Minuten-Delta der Konfiguration
	 */
	protected void setMinutenDelta(MinutenDelta minutenDelta) {
		this.minutenDelta = minutenDelta;
	}

	/**
	 * <p>
	 * Der Name der Konfigurationsdatei wird von einer implementierenden Klasse
	 * definiert, da er von der konkreten Implementierung des
	 * Konfigurationssicherungsmechanismus abhängt. Mittels dieser Methode wird
	 * der Name dem Handler mitgeteilt. Er kann dann daraus den kompletten Pfad
	 * machen, da die Speicherung immer im Anwendungsdaten-Verzeichnis von
	 * Windows in einem Unterverzeichnis stattfindet.
	 * </p>
	 * 
	 * @param dateiname
	 *            Name der Konfigurationsdatei
	 */
	protected void setKonfigurationsDatei(String dateiname) {
		konfigurationsDatei = dateiname;
	}

	/**
	 * <p>
	 * Gibt den Namen der Konfigurationsdatei zurück. Dieser wird nur in
	 * <em>KonfigurationsDatenReader</em> verwendet, um die Basiskonfiguration
	 * zu finden.
	 * </p>
	 * 
	 * @return Den Dateinamen der Konfigurationsdatei
	 */
	protected String getKonfigurationsDateiname() {
		return konfigurationsDatei;
	}

	/**
	 * <p>
	 * Diese Methode benutzen konkrete Konfigurations-Sicherungsmechanismen, um
	 * ein Handle auf die Konfigurationsdateien zu bekommen. Diese befindet sich
	 * in einem Unterverzeichnis des Anwendungsdaten-Verzeichnis von Windows.
	 * </p>
	 * 
	 * @return <em>File</em>-Handle auf die Konfigurationsdatei
	 */
	protected File getKonfigurationsDatei() {
		File back = null;

		File appdataVerzeichnis = new ApplikationsDatenVerzeichnis()
				.getAppdataVerzeichnis();
		if (appdataVerzeichnis != null) {
			back = new File(appdataVerzeichnis, konfigurationsDatei);
		}

		return back;
	}
}