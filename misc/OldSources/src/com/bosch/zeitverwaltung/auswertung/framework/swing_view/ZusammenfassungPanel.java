package com.bosch.zeitverwaltung.auswertung.framework.swing_view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.bosch.zeitverwaltung.auswertung.framework.modell.Zusammenfassung;

/**
 * <p>
 * Panel zur Ausgabe der Zusammenfassung einer Liste mit Buchungen
 * </p>
 * 
 * @author Lars Geyer
 * @see ZusammenfassungTabellenModell
 * @see AktivitaetenPanel
 */
public class ZusammenfassungPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JScrollPane zusammenfassungsScrollPane = null;
	private JTable zusammenfassungsTable = null;

	/**
	 * <p>
	 * Konstruktor, er konstruiert das Panel
	 * </p>
	 */
	public ZusammenfassungPanel() {
		super();
		initialize();
	}

	/**
	 * <p>
	 * Übergabe der darzustellenden Daten
	 * </p>
	 * 
	 * @param daten
	 *            Darzustellende Daten
	 */
	public void setModell(Zusammenfassung daten) {
		getZusammenfassungsTable().setModel(
				new ZusammenfassungTabellenModell(daten));
		setPreferredSize(new Dimension(550, 123));
	}

	/**
	 * <p>
	 * Initialisierung des Panels
	 * </p>
	 */
	private void initialize() {
		setLayout(new GridBagLayout());
		TitledBorder umrandung = BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED), "Zusammenfassung");
		setBorder(umrandung);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(5, 5, 5, 5);
		add(getZusammenfassungsScrollPane(), constraints);
	}

	/**
	 * <p>
	 * Initialisierung des Scoll-Pane, in dem die Inhalts-Tabelle dargestellt
	 * wird
	 * </p>
	 * 
	 * @return Scroll-Pane zur Darstellung der Daten
	 */
	private JScrollPane getZusammenfassungsScrollPane() {
		if (zusammenfassungsScrollPane == null) {
			zusammenfassungsScrollPane = new JScrollPane();
			zusammenfassungsScrollPane
					.setViewportView(getZusammenfassungsTable());
		}
		return zusammenfassungsScrollPane;
	}

	/**
	 * <p>
	 * Tabelle, die Daten präsentiert
	 * </p>
	 * 
	 * @return Tabelle mit Daten
	 */
	private JTable getZusammenfassungsTable() {
		if (zusammenfassungsTable == null) {
			zusammenfassungsTable = new JTable();
		}
		return zusammenfassungsTable;
	}
}
