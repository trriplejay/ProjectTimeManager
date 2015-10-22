package com.bosch.zeitverwaltung.swing_view.dialog;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <p>
 * Panel, dass einen Ändern und einen Abbrechen-Button enthält. Es wird in
 * Dialogen zum Schliessen eingesetzt.
 * </p>
 * 
 * @author Lars Geyer
 * @see BasisDialogImpl
 */
public class DialogSchliessenPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	static final String commitKommando = "c";
	static final String abbruchKommando = "a";

	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JButton aendernButton = null;
	private JButton abbrechenButton = null;

	/**
	 * <p>
	 * Konstruktor, Initialisierung des Panels
	 * </p>
	 * 
	 * @param listener
	 *            Der Listener für Button-Events
	 */
	DialogSchliessenPanel(ActionListener listener) {
		super();
		jLabel2 = new JLabel();
		jLabel2.setText("");
		jLabel1 = new JLabel();
		jLabel1.setText("");
		GridLayout gridLayout = new GridLayout(1, 5);
		gridLayout.setHgap(5);
		gridLayout.setVgap(10);
		setLayout(gridLayout);
		add(jLabel1);
		add(jLabel2);
		add(getAendernButton());
		getAendernButton().addActionListener(listener);
		add(getAbbrechenButton());
		getAbbrechenButton().addActionListener(listener);
	}

	/**
	 * <p>
	 * Initialisierung des Commmit-Buttons
	 * </p>
	 * 
	 * @return Commit-Button
	 */
	private JButton getAendernButton() {
		if (aendernButton == null) {
			aendernButton = new JButton();
			aendernButton.setText("Ändern");
			aendernButton.setActionCommand(commitKommando);
		}
		return aendernButton;
	}

	/**
	 * <p>
	 * Initialisierung des Abbrechen-Buttons
	 * </p>
	 * 
	 * @return Abbrechen-Button
	 */
	private JButton getAbbrechenButton() {
		if (abbrechenButton == null) {
			abbrechenButton = new JButton();
			abbrechenButton.setText("Abbrechen");
			abbrechenButton.setActionCommand(abbruchKommando);
		}
		return abbrechenButton;
	}
}
