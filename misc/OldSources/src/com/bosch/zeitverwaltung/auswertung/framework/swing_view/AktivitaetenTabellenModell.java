package com.bosch.zeitverwaltung.auswertung.framework.swing_view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.bosch.zeitverwaltung.auswertung.framework.modell.BuchungsAuswertung;

/**
 * <p>
 * Implementierung des Interfaces <em>TableModel</em>, um die Daten für die
 * Darstellung aufzubereiten.
 * </p>
 * 
 * @author Lars Geyer
 * @see AktivitaetenPanel
 */
public class AktivitaetenTabellenModell extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	// Spaltennamen
	private String[] spaltenNamen = { "Aktivität", "Buchungsnummern",
			"Stunden", "Anteil Projektzeit", "Anteil Arbeitszeit" };
	private List<BuchungsAuswertung> daten;

	/**
	 * <p>
	 * Konstruktor, er übernimmt die Daten und setzt den Titel der ersten Spalte
	 * </p>
	 * 
	 * @param datenModell
	 *            Daten
	 * @param ersteSpalte
	 *            Titel der ersten Spalte
	 */
	AktivitaetenTabellenModell(List<BuchungsAuswertung> datenModell,
			String ersteSpalte) {
		daten = datenModell;
		spaltenNamen[0] = ersteSpalte;
	}

	/**
	 * <p>
	 * Gibt den Namen einer Spalte zurück
	 * </p>
	 * 
	 * @param colIndex
	 *            Spalte, für die Name erfragt wird
	 * @return Name der Spalte
	 */
	public String getColumnName(int colIndex) {
		return spaltenNamen[colIndex];
	}

	/**
	 * <p>
	 * Gibt die Anzahl der Spalten zurück
	 * </p>
	 * 
	 * @return Anzahl der Spalten
	 */
	public int getColumnCount() {
		return 5;
	}

	/**
	 * <p>
	 * Gibt die Anzahl der anzuzeigenden Zeilen ohne Titelleiste zurück
	 * </p>
	 * 
	 * @return Anzahl der Zeilen
	 */
	public int getRowCount() {
		return daten.size();
	}

	/**
	 * <p>
	 * Gibt den Wert einer Tabellenzelle zurück
	 * </p>
	 * 
	 * @param rowIndex
	 *            Zeile der abgefragten Tabellenzelle
	 * @param colIndex
	 *            Spalte der abgefragten Tabellenzelle
	 * @return Inhalt der abgefragten Tabellenzelle
	 */
	public Object getValueAt(int rowIndex, int colIndex) {
		Object back = null;

		BuchungsAuswertung ausw = daten.get(rowIndex);
		switch (colIndex) {
		case 0:
			back = ausw.getName();
			break;
		case 1:
			back = ausw.getBuchungsNummer();
			break;
		case 2:
			back = ausw.getDauer();
			break;
		case 3:
			back = ausw.getProjektAnteil();
			break;
		case 4:
			back = ausw.getAnteil();
			break;
		}

		return back;
	}
}
