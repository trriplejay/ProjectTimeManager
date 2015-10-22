package com.bosch.zeitverwaltung.swing_view.dialog;

import java.awt.Frame;

import javax.swing.JOptionPane;

import com.bosch.zeitverwaltung.event.EventVerteiler;
import com.bosch.zeitverwaltung.view.dialog.NachfragenBox;
import com.bosch.zeitverwaltung.view.event.DialogSchliessenEvent;
import com.bosch.zeitverwaltung.view.event.DialogSchliessenEventFactory;
import com.bosch.zeitverwaltung.view.listener.DialogSchliessenListener;

/**
 * <p>
 * Realisierung der Abfrage-Box, mit deren Hilfe dem Benutzer Fragen gestellt
 * werden können. Diese Box wird zum Beispiel zur Abfrage verwendet, ob der
 * Benutzer Daten speichern möchte, bevor er die Anwendung schließt.
 * </p>
 * 
 * @author Lars Geyer
 * @see NachfragenBox
 */
public class NachfragenBoxImpl implements NachfragenBox {
	private Frame hauptFenster;
	private boolean abbruchOption = false;
	private String[] text = null;
	private String titel = null;

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
	public NachfragenBoxImpl(Frame hauptFenster) {
		this.hauptFenster = hauptFenster;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAbbruchOption(boolean abbruchOption) {
		this.abbruchOption = abbruchOption;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setText(String[] text) {
		this.text = text;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setTitel(String titel) {
		this.titel = titel;
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

	/**
	 * {@inheritDoc}
	 */
	public void showDialog() {
		DialogSchliessenEventFactory factory = new DialogSchliessenEventFactory();
		if ((titel == null) || (text == null)) {
			listenerMgmt.event(factory.abbruchEvent());
		} else {
			int rueckgabe = JOptionPane.showConfirmDialog(hauptFenster, text,
					titel, abbruchOption ? JOptionPane.YES_NO_CANCEL_OPTION
							: JOptionPane.YES_NO_OPTION);
			if (rueckgabe == JOptionPane.YES_OPTION) {
				listenerMgmt.event(factory.commitEvent());
			} else if (rueckgabe == JOptionPane.NO_OPTION) {
				listenerMgmt.event(factory.neinEvent());
			} else {
				listenerMgmt.event(factory.abbruchEvent());
			}
		}
	}
}
