package com.bosch.zeitverwaltung.auswertung.framework.swing_view;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.bosch.zeitverwaltung.auswertung.framework.modell.BuchungsBerechnung;
import com.bosch.zeitverwaltung.auswertung.framework.modell.ListenBerechnung;
import com.bosch.zeitverwaltung.view.UIFactory;

/**
 * <p>
 * Einfacher Dialog zur Darstellung einer Buchungsauswertung. Es werden die
 * Abbildung von Aktivit‰ten auf Stunden, die Abbildung von Aktivit‰tskategorien
 * auf Stunden, sowie eine Zusammenfassung dargestellt.
 * </p>
 * 
 * @author Lars Geyer
 * @see AktivitaetenPanel
 * @see ZusammenfassungPanel
 * @see SchliessenPanel
 * @see ListenBerechnung
 */
public class SimplerAuswertungsDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private AktivitaetenPanel aktivitaeten = null;
	private AktivitaetenPanel typen = null;
	private ZusammenfassungPanel zusammenfassung = null;
	private SchliessenPanel schliessen = null;

	/**
	 * <p>
	 * Konstruktor, er konstruiert den Dialog
	 * </p>
	 * <p>
	 * Achtung: Der Dialog geht davon aus, dass der swing_view eingesetzt wird,
	 * und das Interface <em>MainWindow</em> auf ein Objekt vom Typ Frame
	 * casten l‰sst.
	 * </p>
	 */
	public SimplerAuswertungsDialog() {
		super((Frame) UIFactory.getFactory().erzeugeMainWindow());
		initialize();
	}

	/**
	 * <p>
	 * Setzt in den Dialogteilen die Daten
	 * </p>
	 * 
	 * @param modell
	 *            Darzustellendes Datenmodell
	 */
	protected void datenVerbinden(BuchungsBerechnung modell) {
		getAktivitaeten().setModell(modell.getAbbildungAktivitaetStunden(),
				"Aktivit‰t");
		getTypen()
				.setModell(modell.getAbbildungKategorieStunden(), "Kategorie");
		getZusammenfassung().setModell(modell.getZusammenfassung());
		pack();
		setLocationRelativeTo(this.getParent());
	}

	/**
	 * <p>
	 * Konstruiert den Dialog
	 * </p>
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
	}

	/**
	 * <p>
	 * Konstruiert das Inhalts-Panel
	 * </p>
	 * 
	 * @return Inhalts-Panel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());

			GridBagConstraints constraints1 = new GridBagConstraints();
			constraints1.gridx = 0;
			constraints1.gridy = 0;
			constraints1.fill = GridBagConstraints.BOTH;
			constraints1.insets = new Insets(5, 5, 5, 5);
			constraints1.weightx = 1.0;
			constraints1.weighty = 1.0;
			jContentPane.add(getAktivitaeten(), constraints1);

			GridBagConstraints constraints2 = new GridBagConstraints();
			constraints2.gridx = 0;
			constraints2.gridy = 1;
			constraints2.fill = GridBagConstraints.BOTH;
			constraints2.insets = new Insets(5, 5, 5, 5);
			constraints2.weightx = 1.0;
			constraints2.weighty = 1.0;
			jContentPane.add(getTypen(), constraints2);

			GridBagConstraints constraints3 = new GridBagConstraints();
			constraints3.gridx = 0;
			constraints3.gridy = 2;
			constraints3.fill = GridBagConstraints.BOTH;
			constraints3.insets = new Insets(5, 5, 5, 5);
			constraints3.weightx = 1.0;
			constraints3.weighty = 1.0;
			jContentPane.add(getZusammenfassung(), constraints3);

			GridBagConstraints constraints4 = new GridBagConstraints();
			constraints4.gridx = 0;
			constraints4.gridy = 3;
			constraints4.fill = GridBagConstraints.HORIZONTAL;
			constraints4.insets = new Insets(5, 5, 5, 5);
			constraints4.weightx = 1.0;
			constraints4.weighty = 0.0;
			jContentPane.add(getSchliessen(), constraints4);
		}
		return jContentPane;
	}

	/**
	 * <p>
	 * Konstruiert die Darstellung der Aktivit‰ts-Aufw‰nde
	 * </p>
	 * 
	 * @return Panel zur Darstellung der Aktivit‰ts-Aufw‰nde
	 */
	private AktivitaetenPanel getAktivitaeten() {
		if (aktivitaeten == null) {
			aktivitaeten = new AktivitaetenPanel("Buchungen nach Aktivit‰ten");
		}
		return aktivitaeten;
	}

	/**
	 * <p>
	 * Konstruiert die Darstellung der Kategorie-Aufw‰nde
	 * </p>
	 * 
	 * @return Panel zur Darstellung der Kategorie-Aufw‰nde
	 */
	private AktivitaetenPanel getTypen() {
		if (typen == null) {
			typen = new AktivitaetenPanel("Buchungen nach Buchungsnummern");
		}
		return typen;
	}

	/**
	 * <p>
	 * Konstruiert die Zusammenfassungs-Darstellung
	 * </p>
	 * 
	 * @return Panel zur Zusammenfassungs-Darstellung
	 */
	private ZusammenfassungPanel getZusammenfassung() {
		if (zusammenfassung == null) {
			zusammenfassung = new ZusammenfassungPanel();
		}
		return zusammenfassung;
	}

	/**
	 * <p>
	 * Konstruiert das Schlieﬂen-Panel zum Beenden der Auswertung
	 * </p>
	 * 
	 * @return Panel mit Schlieﬂen-Button
	 */
	private SchliessenPanel getSchliessen() {
		if (schliessen == null) {
			schliessen = new SchliessenPanel(this);
		}
		return schliessen;
	}
}