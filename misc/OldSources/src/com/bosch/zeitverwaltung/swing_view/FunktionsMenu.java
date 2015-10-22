package com.bosch.zeitverwaltung.swing_view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.bosch.zeitverwaltung.event.EventVerteiler;
import com.bosch.zeitverwaltung.view.FunktionsTrigger;
import com.bosch.zeitverwaltung.view.event.FunktionsEvent;
import com.bosch.zeitverwaltung.view.event.FunktionsEventFactory;
import com.bosch.zeitverwaltung.view.listener.FunktionsListener;

/**
 * <p>
 * Klasse definiert das Auswertungsmenü der Zeitverwaltung
 * </p>
 * 
 * @author Lars Geyer
 */
public class FunktionsMenu extends JMenu implements FunktionsTrigger {
	private static final long serialVersionUID = 1L;

	private EventVerteiler<FunktionsEvent, FunktionsListener> listenerMgmt = 
		new EventVerteiler<FunktionsEvent, FunktionsListener>();

	/**
	 * <p>
	 * Der Konstruktor erzeugt die Swing-Objektstruktur des Menüs.
	 * </p>
	 */
	FunktionsMenu() {
		initialisiere();
	}

	/**
	 * <p>
	 * Fügt Listener für Menüevents hinzu
	 * </p>
	 * 
	 * @param listener
	 *            Listener
	 */
	public void addFunktionsListener(FunktionsListener listener) {
		listenerMgmt.addEventListener(listener);
	}

	/**
	 * <p>
	 * Entfernt Listener für Menüevents
	 * </p>
	 * 
	 * @param listener
	 *            Listener
	 */
	public void removeFunktionsListener(FunktionsListener listener) {
		listenerMgmt.delEventListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addFunktion(String name, int kurzwahl) {
		add(getMenuItem(name, kurzwahl));
	}

	/**
	 * <p>
	 * Initialisierung des Menüs
	 * </p>
	 */
	private void initialisiere() {
		setText("Auswertungen");
		setMnemonic(KeyEvent.VK_A);
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Neuen Menüpunkt pro Funktion
	 */
	private JMenuItem getMenuItem(String name, int kurzwahl) {
		JMenuItem back = new JMenuItem();
		back.setText(name);
		back.setActionCommand(name);
		if (kurzwahl > 0) {
			back.setMnemonic(kurzwahl);
		}
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				listenerMgmt.event(new FunktionsEventFactory()
						.funktionsEvent(evt.getActionCommand()));
			}
		});
		return back;
	}
}
