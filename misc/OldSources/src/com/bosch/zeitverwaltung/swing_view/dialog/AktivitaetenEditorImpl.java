package com.bosch.zeitverwaltung.swing_view.dialog;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.modell.AktivitaetenVerwaltung;
import com.bosch.zeitverwaltung.modell.ModellFactory;
import com.bosch.zeitverwaltung.view.dialog.AktivitaetenEditor;

/**
 * <p>
 * Editor für die Bearbeitung der aktuellen Aktivitäten
 * </p>
 * 
 * @author Lars Geyer
 * @see ListEditorImpl
 * @see BasisDialogImpl
 * @see AktivitaetenEditor
 * @see com.bosch.zeitverwaltung.view.dialog.ListEditor
 * @see com.bosch.zeitverwaltung.view.dialog.BasisDialog
 */
public class AktivitaetenEditorImpl extends ListEditorImpl<Aktivitaet>
		implements AktivitaetenEditor {
	private static final long serialVersionUID = 1L;

	private JPanel aktivitaetsEingabe = null;
	private JLabel jLabel = null;
	private JTextField bezeichnungsFeld = null;
	private JLabel jLabel1 = null;
	private JTextField buchungsnummerFeld = null;
	private JPanel abrechnungsEingabe = null;
	private JRadioButton keineAbrechnung = null;
	private JRadioButton abrechnungKm = null;
	private JTextField kmAbrechnung = null;
	private JRadioButton abrechnungOPNV = null;
	private JTextField opnvAbrechnung = null;
	private ButtonGroup abrechnungsGruppe = null; // @jve:decl-index=0:
	private JLabel jLabel2 = null;
	private JTextField kategorieEingabe = null;
	private JLabel jLabel3 = null;
	private JCheckBox projektAktivitaet = null;
	private JPanel reiseEingabe = null;
	private JRadioButton keineReisezeit = null;
	private JRadioButton aktiveReisezeit = null;
	private JRadioButton passiveReisezeit = null;
	private ButtonGroup reisezeitGruppe = null;

	/**
	 * <p>
	 * Konstruktor, er initialisiert den Dialog
	 * </p>
	 * 
	 * @param meinFrame
	 *            Hauptfenster, in dessen Kontext Dialog dargestellt wird
	 */
	public AktivitaetenEditorImpl(final Frame meinFrame) {
		super(meinFrame);
		this.initialize();
	}

	/**
	 * <p>
	 * Der Dialog enthält ein Feld, in dem die Eigenschaften des aktuell
	 * editierten Objekts dargestellt werden. Mit Hilfe dieser Methode können
	 * die Werte dieses Feldes ausgelesen werden und zu einem Objekt
	 * zusammengefasst werden.
	 * </p>
	 * 
	 * @return Die gerade im Editierfeld dargestellte Aktivität
	 */
	@Override
	protected Aktivitaet getAktuelleEingabe() {
		Aktivitaet aktAktivitaet = null;

		final String aktivitaet = this.getBezeichnungsFeld().getText();
		final String buchungsnummer = this.getBuchungsnummerFeld().getText();
		final String zuordnung = this.getKategorieEingabe().getText();
		final boolean projektAktivitaet = this.getProjektAktivitaet()
				.isSelected();

		if (!aktivitaet.equals("") && !zuordnung.equals("")
				&& !buchungsnummer.equals("")) {
			try {
				aktAktivitaet = new Aktivitaet(aktivitaet, zuordnung,
						buchungsnummer, projektAktivitaet);
				if (this.getAktiveReisezeit().isSelected()) {
					aktAktivitaet.setReisezeit(true);
				} else if (this.getPassiveReisezeit().isSelected()) {
					aktAktivitaet.setReisezeit(false);
				}
				if (this.getAbrechnungKm().isSelected()) {
					aktAktivitaet.setKm(Integer.parseInt(this.getKmAbrechnung()
							.getText().trim().split("[^0-9]")[0]));
				} else if (this.getAbrechnungOPNV().isSelected()) {
					final String[] temp = this.getOpnvAbrechnung().getText()
							.trim().split("[,.]");
					int euro = 0;
					int cent = 0;
					double betrag = 0.0;
					if (temp.length > 1) {
						euro = Integer.parseInt(temp[0]);
						cent = Integer.parseInt(temp[1].split("[^0-9]")[0]);
						if (cent > 10000) {
							throw new NumberFormatException();
						}
						betrag = euro;
						if (cent > 1000) {
							betrag += cent / 10000.0;
						} else if (cent > 100) {
							betrag += cent / 1000.0;
						} else if (cent > 10) {
							betrag += cent / 100.0;
						} else {
							betrag += cent / 10.0;
						}
					} else {
						betrag = Integer.parseInt(temp[0].split("[^0-9]")[0]);
					}
					aktAktivitaet.setEuro(betrag);
				}
			} catch (final NumberFormatException e1) {
				aktAktivitaet = null;
			} catch (final ArrayIndexOutOfBoundsException e2) {
				aktAktivitaet = null;
			}
		}

		return aktAktivitaet;
	}

	/**
	 * <p>
	 * Der Benutzer hat eine andere Aktivität selektiert und deren Daten müssen
	 * nun in das Editierfeld eingetragen werden. Diese Methode wird von
	 * <em>ListEditorImpl</em> aufgerufen.
	 * </p>
	 * 
	 * @param eingabeBasis
	 *            Die neu selektierte Aktivität oder <em>null</em>, falls
	 *            Editierfeld geleert werden soll.
	 */
	@Override
	protected void setEingabe(final Aktivitaet eingabeBasis) {
		if (eingabeBasis != null) {
			this.getBezeichnungsFeld().setText(eingabeBasis.getAktivitaet());
			this.getBuchungsnummerFeld().setText(
					eingabeBasis.getBuchungsNummer());
			this.getKategorieEingabe().setText(eingabeBasis.getKategorie());
			this.getProjektAktivitaet().setSelected(
					eingabeBasis.istProjektAktivitaet());
			if (eingabeBasis.istReisezeit()) {
				if (eingabeBasis.istAktiveReisezeit()) {
					this.getAktiveReisezeit().setSelected(true);
				} else {
					this.getPassiveReisezeit().setSelected(true);
				}
			} else {
				this.getKeineReisezeit().setSelected(true);
			}
			if (eingabeBasis.abrechungsRelevant()) {
				if (eingabeBasis.abrechnungInEuro()) {
					this.getAbrechnungOPNV().setSelected(true);
					this.getKmAbrechnung().setEnabled(false);
					this.getOpnvAbrechnung().setEnabled(true);
					this.getKmAbrechnung().setText("");
					this.getOpnvAbrechnung().setText(
							eingabeBasis.getAbrechnungsInfo());
				} else {
					this.getAbrechnungKm().setSelected(true);
					this.getKmAbrechnung().setEnabled(true);
					this.getOpnvAbrechnung().setEnabled(false);
					this.getKmAbrechnung().setText(
							eingabeBasis.getAbrechnungsInfo());
					this.getOpnvAbrechnung().setText("");
				}
			} else {
				this.getKeineAbrechnung().setSelected(true);
				this.getKmAbrechnung().setEnabled(false);
				this.getOpnvAbrechnung().setEnabled(false);
				this.getKmAbrechnung().setText("");
				this.getOpnvAbrechnung().setText("");
			}
		} else {
			this.getBezeichnungsFeld().setText("");
			this.getBuchungsnummerFeld().setText("");
			this.getKategorieEingabe().setText("");
			this.getProjektAktivitaet().setSelected(false);
			this.getKeineReisezeit().setSelected(true);
			this.getKeineAbrechnung().setSelected(true);
			this.getKmAbrechnung().setEnabled(false);
			this.getOpnvAbrechnung().setEnabled(false);
			this.getKmAbrechnung().setText("");
			this.getOpnvAbrechnung().setText("");
		}
	}

	/**
	 * <p>
	 * Wird von <em>BasisDialogImpl</em> während der Initialisierung dazu
	 * benutzt, den Dialog-Fenstertitel abzufragen
	 * </p>
	 * 
	 * @return Fenstertitel
	 */
	@Override
	protected String getTitel() {
		return "Aktivitäteneditor";
	}

	/**
	 * <p>
	 * Wird von <em>ListEditorImpl</em> während der Initialisierung dazu
	 * benutzt, den Titel des Listenrahmens abzufragen
	 * </p>
	 * 
	 * @return Titel des Listenrahmens
	 */
	@Override
	protected String getListenTitel() {
		return "Aktivitätenliste";
	}

	/**
	 * <p>
	 * Wird von <em>ListEditorImpl</em> dazu benutzt, Zugriff auf das Modell zu
	 * erhalten. Er verlangt dabei Zugriff auf das <em>BasisModell</em>
	 * -Interface
	 * </p>
	 * 
	 * @return Aktivitäten-Modell
	 */
	@Override
	protected AktivitaetenVerwaltung getModell() {
		return ModellFactory.getFactory().getAktivitaetenVerwaltung();
	}

	/**
	 * <p>
	 * Initialisierung des Eingabepanels. Dieses wird anwendungsspezifisch in
	 * einer abgeleiteten Klasse definiert.
	 * </p>
	 * 
	 * @return Eingabepanel
	 */
	@Override
	protected JPanel getEingabePanel() {
		if (this.aktivitaetsEingabe == null) {
			this.aktivitaetsEingabe = new JPanel();
			this.aktivitaetsEingabe.setLayout(new GridBagLayout());
			final TitledBorder umrandung = BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
					"Aktivität");
			this.aktivitaetsEingabe.setBorder(umrandung);

			this.jLabel = new JLabel();
			this.jLabel.setText("Bezeichnung: ");
			final GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			this.aktivitaetsEingabe.add(this.jLabel, gridBagConstraints1);

			final GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.weightx = 1.0;
			this.aktivitaetsEingabe.add(this.getBezeichnungsFeld(),
					gridBagConstraints2);

			this.jLabel2 = new JLabel();
			this.jLabel2.setText("Kategorie: ");
			final GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 1;
			this.aktivitaetsEingabe.add(this.jLabel2, gridBagConstraints3);

			final GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 1;
			gridBagConstraints4.weightx = 1.0;
			this.aktivitaetsEingabe.add(this.getKategorieEingabe(),
					gridBagConstraints4);

			this.jLabel1 = new JLabel();
			this.jLabel1.setText("Buchungsnummer: ");
			final GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 2;
			this.aktivitaetsEingabe.add(this.jLabel1, gridBagConstraints5);

			final GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.gridy = 2;
			gridBagConstraints6.weightx = 1.0;
			this.aktivitaetsEingabe.add(this.getBuchungsnummerFeld(),
					gridBagConstraints6);

			this.jLabel3 = new JLabel();
			this.jLabel3.setText("Projekt-Aktivität: ");
			final GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints7.anchor = GridBagConstraints.WEST;
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 3;
			this.aktivitaetsEingabe.add(this.jLabel3, gridBagConstraints7);

			final GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints8.gridx = 1;
			gridBagConstraints8.gridy = 3;
			gridBagConstraints8.weightx = 1.0;
			this.aktivitaetsEingabe.add(this.getProjektAktivitaet(),
					gridBagConstraints8);

			final GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.insets = new Insets(5, 5, 2, 5);
			gridBagConstraints9.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints9.gridwidth = 2;
			gridBagConstraints9.gridx = 0;
			gridBagConstraints9.gridy = 5;
			gridBagConstraints9.ipadx = 5;
			gridBagConstraints9.ipady = 5;
			gridBagConstraints9.weightx = 1.0;
			this.aktivitaetsEingabe.add(this.getReiseEingabe(),
					gridBagConstraints9);
		}
		return this.aktivitaetsEingabe;
	}

	/**
	 * <p>
	 * Initialisierung des Bezeichnungsfeldes
	 * </p>
	 * 
	 * @return Textfeld für Bezeichnung
	 */
	private JTextField getBezeichnungsFeld() {
		if (this.bezeichnungsFeld == null) {
			this.bezeichnungsFeld = new JTextField();
		}
		return this.bezeichnungsFeld;
	}

	/**
	 * <p>
	 * Initialisierung des Buchungsnummernfeldes
	 * </p>
	 * 
	 * @return Textfeld für Buchungsnummer
	 */
	private JTextField getBuchungsnummerFeld() {
		if (this.buchungsnummerFeld == null) {
			this.buchungsnummerFeld = new JTextField();
		}
		return this.buchungsnummerFeld;
	}

	/**
	 * <p>
	 * Initialisierung des Kategoriefeldes
	 * </p>
	 * 
	 * @return Textfeld für Kategorie
	 */
	private JTextField getKategorieEingabe() {
		if (this.kategorieEingabe == null) {
			this.kategorieEingabe = new JTextField();
		}
		return this.kategorieEingabe;
	}

	/**
	 * <p>
	 * Initialisierung der Projekt-Aktivitäts-Checkbox
	 * </p>
	 * 
	 * @return Checkbox für Projekt-Aktivität
	 */
	private JCheckBox getProjektAktivitaet() {
		if (this.projektAktivitaet == null) {
			this.projektAktivitaet = new JCheckBox();
		}
		return this.projektAktivitaet;
	}

	/**
	 * <p>
	 * Initialisierung des Panels zur Eingabe von Reisetätigkeiten
	 * </p>
	 * 
	 * @return Reise-Panel
	 */
	private JPanel getReiseEingabe() {
		if (this.reiseEingabe == null) {
			this.reiseEingabe = new JPanel();
			this.reiseEingabe.setLayout(new GridBagLayout());
			final TitledBorder umrandung = BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
					"Reisezeit");
			this.reiseEingabe.setBorder(umrandung);

			final GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			this.reiseEingabe
					.add(this.getKeineReisezeit(), gridBagConstraints1);

			final GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 0;
			this.reiseEingabe.add(this.getAktiveReisezeit(),
					gridBagConstraints2);

			final GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.gridx = 2;
			gridBagConstraints3.gridy = 0;
			this.reiseEingabe.add(this.getPassiveReisezeit(),
					gridBagConstraints3);

			this.reisezeitGruppe = new ButtonGroup();
			this.reisezeitGruppe.add(this.getKeineReisezeit());
			this.reisezeitGruppe.add(this.getAktiveReisezeit());
			this.reisezeitGruppe.add(this.getPassiveReisezeit());

			this.getKeineReisezeit().setSelected(true);

			final GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.insets = new Insets(5, 5, 2, 5);
			gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.gridwidth = 3;
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 1;
			gridBagConstraints4.ipadx = 5;
			gridBagConstraints4.ipady = 5;
			gridBagConstraints4.weightx = 1.0;
			this.reiseEingabe.add(this.getAbrechnungsEingabe(),
					gridBagConstraints4);
		}
		return this.reiseEingabe;
	}

	/**
	 * <p>
	 * Initialisierung des Keine Reisezeit Radiobuttons
	 * </p>
	 * 
	 * @return Keine Reisezeit Button
	 */
	private JRadioButton getKeineReisezeit() {
		if (this.keineReisezeit == null) {
			this.keineReisezeit = new JRadioButton();
			this.keineReisezeit.setText("Keine Reisezeit");
		}
		return this.keineReisezeit;
	}

	/**
	 * <p>
	 * Initialisierung des Aktive Reisezeit Radiobuttons
	 * </p>
	 * 
	 * @return Aktive Reisezeit Button
	 */
	private JRadioButton getAktiveReisezeit() {
		if (this.aktiveReisezeit == null) {
			this.aktiveReisezeit = new JRadioButton();
			this.aktiveReisezeit.setText("Aktive Reisezeit");
		}
		return this.aktiveReisezeit;
	}

	/**
	 * <p>
	 * Initialisierung des Passive Reisezeit Radiobuttons
	 * </p>
	 * 
	 * @return Passive Reisezeit Button
	 */
	private JRadioButton getPassiveReisezeit() {
		if (this.passiveReisezeit == null) {
			this.passiveReisezeit = new JRadioButton();
			this.passiveReisezeit.setText("Passive Reisezeit");
		}
		return this.passiveReisezeit;
	}

	/**
	 * <p>
	 * Initialisierung des Panels zur Eingabe von Abrechnungsinformationen
	 * </p>
	 * 
	 * @return Abrechnungs-Panel
	 */
	private JPanel getAbrechnungsEingabe() {
		if (this.abrechnungsEingabe == null) {
			this.abrechnungsEingabe = new JPanel();
			this.abrechnungsEingabe.setLayout(new GridBagLayout());
			final TitledBorder umrandung = BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
					"Abrechnung");
			this.abrechnungsEingabe.setBorder(umrandung);

			final GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			this.abrechnungsEingabe.add(this.getKeineAbrechnung(),
					gridBagConstraints1);

			final GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 1;
			this.abrechnungsEingabe.add(this.getAbrechnungKm(),
					gridBagConstraints2);

			final GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 1.0;
			this.abrechnungsEingabe.add(this.getKmAbrechnung(),
					gridBagConstraints3);

			final GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 2;
			this.abrechnungsEingabe.add(this.getAbrechnungOPNV(),
					gridBagConstraints4);

			final GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.weightx = 1.0;
			this.abrechnungsEingabe.add(this.getOpnvAbrechnung(),
					gridBagConstraints5);

			this.abrechnungsGruppe = new ButtonGroup();
			this.abrechnungsGruppe.add(this.getKeineAbrechnung());
			this.abrechnungsGruppe.add(this.getAbrechnungKm());
			this.abrechnungsGruppe.add(this.getAbrechnungOPNV());

			this.getKeineAbrechnung().setSelected(true);
			this.getKmAbrechnung().setEnabled(false);
			this.getOpnvAbrechnung().setEnabled(false);
		}
		return this.abrechnungsEingabe;
	}

	/**
	 * <p>
	 * Initialisierung des Keine Abrechnung Radiobuttons
	 * </p>
	 * 
	 * @return Keine Abrechnung Button
	 */
	private JRadioButton getKeineAbrechnung() {
		if (this.keineAbrechnung == null) {
			this.keineAbrechnung = new JRadioButton();
			this.keineAbrechnung.setText("Keine Abrechnung");
			this.keineAbrechnung.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					if (AktivitaetenEditorImpl.this.keineAbrechnung
							.isSelected()) {
						AktivitaetenEditorImpl.this.getKmAbrechnung()
								.setEnabled(false);
						AktivitaetenEditorImpl.this.getOpnvAbrechnung()
								.setEnabled(false);
					}
				}
			});
		}
		return this.keineAbrechnung;
	}

	/**
	 * <p>
	 * Initialisierung des Abrechnung in Km Radiobuttons
	 * </p>
	 * 
	 * @return Km Abrechnung Button
	 */
	private JRadioButton getAbrechnungKm() {
		if (this.abrechnungKm == null) {
			this.abrechnungKm = new JRadioButton();
			this.abrechnungKm.setText("Abrechnung in KM");
			this.abrechnungKm.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					if (AktivitaetenEditorImpl.this.abrechnungKm.isSelected()) {
						AktivitaetenEditorImpl.this.getKmAbrechnung()
								.setEnabled(true);
						AktivitaetenEditorImpl.this.getOpnvAbrechnung()
								.setEnabled(false);
					}
				}
			});
		}
		return this.abrechnungKm;
	}

	/**
	 * <p>
	 * Initialisierung des Kmfeldes
	 * </p>
	 * 
	 * @return Textfeld für Kilometer
	 */
	private JTextField getKmAbrechnung() {
		if (this.kmAbrechnung == null) {
			this.kmAbrechnung = new JTextField();
		}
		return this.kmAbrechnung;
	}

	/**
	 * <p>
	 * Initialisierung des Abrechnung in Euro Radiobuttons
	 * </p>
	 * 
	 * @return Euro Abrechnung Button
	 */
	private JRadioButton getAbrechnungOPNV() {
		if (this.abrechnungOPNV == null) {
			this.abrechnungOPNV = new JRadioButton();
			this.abrechnungOPNV.setText("Abrechnung ÖPNV");
			this.abrechnungOPNV.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					if (AktivitaetenEditorImpl.this.abrechnungOPNV.isSelected()) {
						AktivitaetenEditorImpl.this.getKmAbrechnung()
								.setEnabled(false);
						AktivitaetenEditorImpl.this.getOpnvAbrechnung()
								.setEnabled(true);
					}
				}
			});
		}
		return this.abrechnungOPNV;
	}

	/**
	 * <p>
	 * Initialisierung des Eurofeldes
	 * </p>
	 * 
	 * @return Textfeld für Euro
	 */
	private JTextField getOpnvAbrechnung() {
		if (this.opnvAbrechnung == null) {
			this.opnvAbrechnung = new JTextField();
		}
		return this.opnvAbrechnung;
	}
}
