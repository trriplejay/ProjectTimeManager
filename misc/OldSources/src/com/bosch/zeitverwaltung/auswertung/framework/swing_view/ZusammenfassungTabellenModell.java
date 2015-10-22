package com.bosch.zeitverwaltung.auswertung.framework.swing_view;

import javax.swing.table.AbstractTableModel;

import com.bosch.zeitverwaltung.auswertung.framework.modell.Zusammenfassung;

/**
 * <p>
 * Implementierung des Interfaces <em>TableModel</em>, um die Daten für die
 * Darstellung aufzubereiten.
 * </p>
 * 
 * @author Lars Geyer
 * @see ZusammenfassungPanel
 */
public class ZusammenfassungTabellenModell extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	// Spaltennamen
	private final String[] spaltenNamen = { "Kategorie", "Auswertung" };
	private Zusammenfassung daten;

	/**
	 * <p>
	 * Konstruktor, er übernimmt die Daten
	 * </p>
	 * 
	 * @param daten
	 *            Daten
	 */
	ZusammenfassungTabellenModell(Zusammenfassung daten) {
		this.daten = daten;
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
		return 2;
	}

	/**
	 * <p>
	 * Gibt die Anzahl der anzuzeigenden Zeilen ohne Titelleiste zurück
	 * </p>
	 * 
	 * @return Anzahl der Zeilen
	 */
	public int getRowCount() {
		return 4;
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

		if (colIndex == 0) {
			switch (rowIndex) {
			case 0:
				back = "Arbeitszeit: ";
				break;
			case 1:
				back = "Projekttätigkeiten: ";
				break;
			case 2:
				back = "Linientätigkeiten: ";
				break;
			case 3:
				back = "Anteil Projektzeit: ";
			}
		} else {
			switch (rowIndex) {
			case 0:
				back = daten.getArbeitszeit();
				break;
			case 1:
				back = daten.getProjektzeit();
				break;
			case 2:
				back = daten.getLinienzeit();
				break;
			case 3:
				back = daten.getProjektanteil();
			}
		}

		return back;
	}
}
