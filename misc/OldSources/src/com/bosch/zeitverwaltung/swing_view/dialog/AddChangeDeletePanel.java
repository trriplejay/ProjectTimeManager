package com.bosch.zeitverwaltung.swing_view.dialog;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <p>
 * Basis-Panel, dass zwei oder drei Buttons verwaltet. Die Buttons haben die
 * Semantik, Hinzufügen, Ändern oder Löschen eines Elementes in einer Liste. Der
 * Verändern-Button ist optional.
 * </p>
 * 
 * @author Lars Geyer
 * @see ListEditorImpl
 */
public class AddChangeDeletePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	static final String hinzufuegenKommando = "h";
	static final String veraendernKommando = "v";
	static final String loeschenKommando = "l";

	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JButton hinzufuegenButton = null;
	private JButton veraendernButton = null;
	private JButton loeschenButton = null;

	/**
	 * <p>
	 * Konstruktor, er initialisiert das Panel
	 * </p>
	 * 
	 * @param listener
	 *            Action-Listener, der auf die Button-Events reagiert
	 * @param changeButton
	 *            Soll der Veränderungsbutton angezeigt werden (<em>true</em>)
	 *            oder nicht (<em>false</em>)
	 */
	AddChangeDeletePanel(ActionListener listener, boolean changeButton) {
		jLabel1 = new JLabel();
		jLabel1.setText("  ");
		GridLayout gridLayout = new GridLayout(4, 1);
		gridLayout.setHgap(10);
		gridLayout.setVgap(5);
		setLayout(gridLayout);
		add(getHinzufuegenButton());
		getHinzufuegenButton().addActionListener(listener);
		if (changeButton) {
			add(getVeraendernButton());
			getVeraendernButton().addActionListener(listener);
		}
		add(getLoeschenButton());
		getLoeschenButton().addActionListener(listener);
		add(jLabel1);
		if (!changeButton) {
			jLabel2 = new JLabel();
			jLabel2.setText("  ");
			add(jLabel2);
		}
	}

	/**
	 * <p>
	 * Initialisierung des Hinzufügen-Buttons
	 * </p>
	 * 
	 * @return Hinzufügen-Button
	 */
	private JButton getHinzufuegenButton() {
		if (hinzufuegenButton == null) {
			hinzufuegenButton = new JButton();
			hinzufuegenButton.setText("Hinzufügen");
			hinzufuegenButton.setActionCommand(hinzufuegenKommando);
		}
		return hinzufuegenButton;
	}

	/**
	 * <p>
	 * Initialisierung des Verändern-Buttons
	 * </p>
	 * 
	 * @return Verändern-Button
	 */
	private JButton getVeraendernButton() {
		if (veraendernButton == null) {
			veraendernButton = new JButton();
			veraendernButton.setText("Verändern");
			veraendernButton.setActionCommand(veraendernKommando);
		}
		return hinzufuegenButton;
	}

	/**
	 * <p>
	 * Initialisierung des Löschen-Buttons
	 * </p>
	 * 
	 * @return Löschen-Button
	 */
	private JButton getLoeschenButton() {
		if (loeschenButton == null) {
			loeschenButton = new JButton();
			loeschenButton.setText("Entfernen");
			loeschenButton.setActionCommand(loeschenKommando);
		}
		return loeschenButton;
	}
}
