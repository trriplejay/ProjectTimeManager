package com.bosch.zeitverwaltung.auswertung.framework.swing_view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <p>
 * Panel zum Schlieﬂen des Auswertungsdialogs. Es enth‰lt einen einfachen
 * Schlieﬂen-Button
 * </p>
 * 
 * @author Lars Geyer
 */
public class SchliessenPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel fueller = null;
	private JButton schliessenButton = null;

	private JDialog meinDialog;

	/**
	 * <p>
	 * Konstruktor, er speichert eine Referenz auf den Dialog, in dem das Panel
	 * zum Einsatz kommt. Er wird ¸ber eine Aktivierung des Schlieﬂen-Buttons
	 * informiert.
	 * </p>
	 * 
	 * @param meinDialog
	 *            Dialog, in dem Panel benutzt wird
	 */
	public SchliessenPanel(JDialog meinDialog) {
		super();
		this.meinDialog = meinDialog;
		initialize();
	}

	/**
	 * <p>
	 * Initialisiert Panel
	 * </p>
	 */
	private void initialize() {
		this.setLayout(new GridBagLayout());

		fueller = new JLabel();
		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1.gridx = 0;
		constraints1.gridy = 0;
		constraints1.fill = GridBagConstraints.HORIZONTAL;
		constraints1.insets = new Insets(5, 5, 5, 5);
		constraints1.weightx = 1.0;
		constraints1.weighty = 0.0;
		this.add(fueller, constraints1);

		GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2.gridx = 1;
		constraints2.gridy = 0;
		constraints2.fill = GridBagConstraints.NONE;
		constraints2.insets = new Insets(5, 5, 5, 5);
		constraints2.weightx = 0.0;
		constraints2.weighty = 0.0;
		this.add(getSchliessenButton(), constraints2);
	}

	/**
	 * <p>
	 * Initialisiert den Schlieﬂen-Button
	 * </p>
	 * 
	 * @return Referenz auf den Schlieﬂen-Button
	 */
	private JButton getSchliessenButton() {
		if (schliessenButton == null) {
			schliessenButton = new JButton();
			schliessenButton.setText("Schlieﬂen");
			schliessenButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					meinDialog.setVisible(false);
				}
			});
		}
		return schliessenButton;
	}
}
