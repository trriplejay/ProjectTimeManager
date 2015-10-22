package com.bosch.zeitverwaltung.auswertung.framework.swing_view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.bosch.zeitverwaltung.auswertung.framework.modell.BuchungsAuswertung;

/**
 * <p>
 * Dieses Panel gibt den Inhalt einer Liste von <em>BuchungsAuswertung</em>-Objekte
 * aus.
 * </p>
 * 
 * @author Lars Geyer
 * @see AktivitaetenTabellenModell
 * @see BuchungsAuswertung
 * @see ZusammenfassungPanel
 */
public class AktivitaetenPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JScrollPane aktivitaetenPane = null;
	private JTable aktivitaetenTable = null;

	/**
	 * <p>
	 * Konstruktor, der Titel wird in einem Rahmen um den Inhalt dargestellt.
	 * </p>
	 * 
	 * @param titel
	 *            Titel des Panels
	 */
	public AktivitaetenPanel(String titel) {
		super();
		initialize(titel);
	}

	/**
	 * <p>
	 * Übergabe der darzustellenden Daten
	 * </p>
	 * 
	 * @param daten
	 *            Die darzustellenden Daten
	 * @param titelErsteSpalte
	 *            Die Darstellung erfolgt in Tabellenform, der Titel der ersten
	 *            Spalte kann angepasst werden, da dort Aktivitäten und
	 *            Aktivitätskategorien dargestellt werden können
	 */
	public void setModell(List<BuchungsAuswertung> daten,
			String titelErsteSpalte) {
		AktivitaetenTabellenModell modell = new AktivitaetenTabellenModell(
				daten, titelErsteSpalte);
		getAktivitaetenTable().setModel(modell);
		setPreferredSize(new Dimension(550, (59 + 16 * modell.getRowCount())));
	}

	/**
	 * <p>
	 * Anlegen des Dialogs
	 * </p>
	 * 
	 * @param titel
	 *            Titel des Rahmens um den Inhalt
	 */
	private void initialize(String titel) {
		setLayout(new GridBagLayout());
		TitledBorder umrandung = BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED), titel);
		setBorder(umrandung);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(5, 5, 5, 5);
		add(getAktivitaetenPane(), constraints);
	}

	/**
	 * <p>
	 * Initialisiert den Panelinhalt in Form eines <em>JScrollPane</em>, dass
	 * eine <em>JTable</em> enthält.
	 * </p>
	 * 
	 * @return Panelinhalt
	 */
	private JScrollPane getAktivitaetenPane() {
		if (aktivitaetenPane == null) {
			aktivitaetenPane = new JScrollPane();
			aktivitaetenPane.setViewportView(getAktivitaetenTable());
		}
		return aktivitaetenPane;
	}

	/**
	 * <p>
	 * Initialisiert die <em>JTable</em>, in der die Daten dargestellt werden
	 * </p>
	 * 
	 * @return Tabelle für den Panelinhalt
	 */
	private JTable getAktivitaetenTable() {
		if (aktivitaetenTable == null) {
			aktivitaetenTable = new JTable();
		}
		return aktivitaetenTable;
	}
}
