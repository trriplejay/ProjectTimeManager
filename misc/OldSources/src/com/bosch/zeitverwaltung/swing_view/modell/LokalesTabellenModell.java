package com.bosch.zeitverwaltung.swing_view.modell;

import java.text.ParseException;

import javax.swing.table.AbstractTableModel;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.elemente.Buchung;
import com.bosch.zeitverwaltung.elemente.Uhrzeit;
import com.bosch.zeitverwaltung.modell.TagesBuchungen;
import com.bosch.zeitverwaltung.modell.event.BuchungenChangedEvent;
import com.bosch.zeitverwaltung.modell.event.DeltaChangedEvent;
import com.bosch.zeitverwaltung.modell.event.TagChangedEvent;
import com.bosch.zeitverwaltung.modell.listener.DeltaChangedListener;
import com.bosch.zeitverwaltung.modell.listener.BuchungenChangedListener;
import com.bosch.zeitverwaltung.modell.listener.TagChangedListener;

/**
 * <p>
 * Tabellenmodell für die JTable. Basiert auf dem Tages-Buchungenmodell der
 * Anwendung und transformiert das Modell auf Swing.
 * </p>
 * 
 * @author Lars Geyer
 */
public class LokalesTabellenModell extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private static final int anzahlZeilen = 20;
	private static final int anzahlSpalten = 6;

	private final String[] spaltenNamen = { "Beginn", "Ende", "Aktivität",
			"Buchungsnummer", "Kommentar", "RZ (km/€)", };

	private TagesBuchungen meinModell = null;
	private boolean editierbar = true;

	/**
	 * {@inheritDoc}
	 */
	public int getColumnCount() {
		return anzahlSpalten;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getRowCount() {
		return anzahlZeilen;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getColumnName(int columnIndex) {
		if ((columnIndex < 0) || (columnIndex >= anzahlSpalten)) {
			return "ERROR";
		}
		return spaltenNamen[columnIndex];
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<?> getColumnClass(int columnIndex) {
		if ((columnIndex < 0) || (columnIndex >= anzahlSpalten)) {
			return null;
		}
		return String.class;
	}

	/**
	 * <p>
	 * Der Editierbar-Mechanismus erlaubt es, die Reaktion auf Tastaturevents
	 * abzuschalten, wenn der Benutzer eine Control-Taste gedrückt hält. Dies
	 * ermöglicht die Benutzung von Tastatur-Shortcuts zum Auswählen von
	 * Menüpunkten.
	 * </p>
	 * 
	 * @param editierbar
	 *            Tabelle gerade editierbar
	 */
	public void setEditierbar(boolean editierbar) {
		this.editierbar = editierbar;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (!editierbar) {
			return false;
		} else if ((columnIndex < 0) || (columnIndex >= anzahlSpalten)) {
			return false;
		} else if (columnIndex == 3) {
			return false;
		} else if (columnIndex == 5) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * <p>
	 * Gibt das Buchungsobjekt zurück, das in einer speziellen Spalte
	 * gespeichert ist. Dies wird für die Ermittlung der selektierten Buchung
	 * benötigt, die z.B. beim Löschen einer Buchung relevant ist.
	 * </p>
	 * 
	 * <p>
	 * Methode wird lediglich in <em>MainWindowImpl</em> benötigt.
	 * </p>
	 * 
	 * @param index
	 *            Index der selektierten Buchung
	 * @return Selektierte Buchung
	 */
	public Buchung getBuchung(int index) {
		return meinModell.getBuchung(index);
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		String back = "";
		if ((rowIndex < 0) || (rowIndex >= anzahlZeilen)) {
			return null;
		}

		if ((meinModell != null)
				&& (rowIndex < meinModell.getAnzahlBuchungen())) {
			Buchung buchung = meinModell.getBuchung(rowIndex);
			switch (columnIndex) {
			case 0:
				if (buchung.getStartZeit() == null) {
					back = "";
				} else {
					back = buchung.getStartZeit().toString();
				}
				break;
			case 1:
				if (buchung.getEndeZeit() == null) {
					back = "";
				} else {
					back = buchung.getEndeZeit().toString();
				}
				break;
			case 2:
				back = buchung.toString();
				break;
			case 3:
				back = buchung.getAktivitaet().getBuchungsNummer();
				break;
			case 4:
				back = buchung.getKommentar();
				break;
			case 5:
				if (buchung.getAktivitaet().abrechungsRelevant()) {
					back = buchung.getAktivitaet().getAbrechnungsInfo();
				}
				break;
			default:
				break;
			}
		}

		return back;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if ((meinModell != null) && (rowIndex >= 0)
				&& (rowIndex < meinModell.getAnzahlBuchungen())) {
			Uhrzeit neueZeit = null;

			switch (columnIndex) {
			case 0:
				try {
					neueZeit = new Uhrzeit((String) aValue);
				} catch (ParseException e) {
					break;
				}
				meinModell.aendereStartzeit(rowIndex, neueZeit);
				break;
			case 1:
				try {
					neueZeit = new Uhrzeit((String) aValue);
				} catch (ParseException e) {
					break;
				}
				meinModell.aendereEndezeit(rowIndex, neueZeit);
				break;
			case 2:
				meinModell.aendereAktivitaet(rowIndex, (Aktivitaet) aValue);
				break;
			case 4:
				meinModell.aendereKommentar(rowIndex, (String) aValue);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * <p>
	 * Das Objekt muss auf Änderungen des Minuten-Deltas hören, da dadurch die
	 * Darstellung der Buchungen verändert wird.
	 * </p>
	 * 
	 * @return Minuten-Delta-Listener
	 */
	public DeltaChangedListener getDeltaChangedListener() {
		return new DeltaChangedListener() {
			public void event(DeltaChangedEvent evt) {
				fireTableDataChanged();
			}
		};
	}

	/**
	 * <p>
	 * Das Objekt muss auf Änderungen in den Buchungen reagieren, da diese dann
	 * dargestellt werden müssen.
	 * </p>
	 * 
	 * @return Buchungen Listener
	 */
	public BuchungenChangedListener getBuchungenChangedListener() {
		return new BuchungenChangedListener() {
			public void event(BuchungenChangedEvent evt) {
				fireTableDataChanged();
			}
		};
	}

	/**
	 * <p>
	 * Das Objekt muss auf Änderungen des Buchungstages reagieren, da dies
	 * dargestellt werden muss.
	 * </p>
	 * 
	 * @return Tages Listener
	 */
	public TagChangedListener getTagChangedListener() {
		return new TagChangedListener() {
			public void event(TagChangedEvent evt) {
				meinModell = evt.getBuchungsManager();
				evt.getBuchungsManager().addBuchungenChangedListener(
						getBuchungenChangedListener());
				fireTableDataChanged();
			}
		};
	}
}
