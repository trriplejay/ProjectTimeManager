package com.bosch.zeitverwaltung.swing_view.dialog;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.bosch.zeitverwaltung.event.EventVerteiler;
import com.bosch.zeitverwaltung.view.dialog.AboutInformation;
import com.bosch.zeitverwaltung.view.event.DialogSchliessenEvent;
import com.bosch.zeitverwaltung.view.event.DialogSchliessenEventFactory;
import com.bosch.zeitverwaltung.view.listener.DialogSchliessenListener;

/**
 * <p>
 * About-Dialog
 * </p>
 * 
 * @author Lars Geyer
 * @see AboutInformation
 */
public class AboutInformationImpl implements AboutInformation {
	private Frame hauptFenster;
	private EventVerteiler<DialogSchliessenEvent, DialogSchliessenListener> listenerMgmt =
		new EventVerteiler<DialogSchliessenEvent, DialogSchliessenListener>();

	/**
	 * <p>
	 * Konstruktor, er speichert das Hauptfenster, um den Dialog anzeigen zu
	 * können.
	 * </p>
	 * 
	 * @param hauptFenster
	 *            Hauptfenster, in dessen Kontext Dialog dargestellt werden soll
	 */
	public AboutInformationImpl(Frame hauptFenster) {
		this.hauptFenster = hauptFenster;
	}

	/**
	 * <p>
	 * Methode startet die Darstellung des About-Dialogs. Die Dialog ist modal,
	 * d.h. die Methode gibt nach Schließen des Dialogs den Kontrollfluss
	 * zurück.
	 * </p>
	 */
	public void showDialog() {
		String text[] = { "Zeitverwaltung V2.0", " ",
				"Bosch Anwesenheits- und", "Buchungsverwaltung", " ",
				"von Lars Geyer" };

		JOptionPane pane = new JOptionPane(text,
				JOptionPane.INFORMATION_MESSAGE);
		final JDialog dialog = pane.createDialog(hauptFenster,
				"Über Zeitverwaltung");
		dialog.setVisible(true);
		listenerMgmt.event(new DialogSchliessenEventFactory().commitEvent());
	}

	/**
	 * {@inheritDoc}
	 */
	public void addDialogSchliessenListener(DialogSchliessenListener listener) {
		listenerMgmt.addEventListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeDialogSchliessenListener(DialogSchliessenListener listener) {
		listenerMgmt.delEventListener(listener);
	}
}
