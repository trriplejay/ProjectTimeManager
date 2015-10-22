package com.bosch.zeitverwaltung.swing_view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.bosch.zeitverwaltung.event.EventVerteiler;
import com.bosch.zeitverwaltung.view.event.DateiEvent;
import com.bosch.zeitverwaltung.view.event.DateiEventFactory;
import com.bosch.zeitverwaltung.view.listener.DateiListener;

/**
 * <p>
 * Klasse definiert das Dateimenü der Zeitverwaltung
 * </p>
 * 
 * <p>
 * Das Dateimenü hat die Besonderheit, dass der Beenden-Menüpunkt durch das
 * Hauptmenü an das Menü angehängt wird, da auf diesen Punkt Beenden-Listener
 * und nicht DateiListener hören.
 * </p>
 * 
 * @author Lars Geyer
 */
class DateiMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	private EventVerteiler<DateiEvent, DateiListener> eventManager = 
		new EventVerteiler<DateiEvent, DateiListener>();

	private JMenuItem ladeBuchungen = null;
	private JMenuItem speicherBuchungen = null;

	/**
	 * <p>
	 * Der Konstruktor erzeugt die Swing-Objektstruktur des Menüs.
	 * </p>
	 */
	DateiMenu() {
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
	void addDateiListener(DateiListener listener) {
		eventManager.addEventListener(listener);
	}

	/**
	 * <p>
	 * Entfernt Listener für Menüevents
	 * </p>
	 * 
	 * @param listener
	 *            Listener
	 */
	void removeDateiListener(DateiListener listener) {
		eventManager.delEventListener(listener);
	}

	/**
	 * <p>
	 * Initialisierung des Menüs
	 * </p>
	 */
	private void initialisiere() {
		setText("Datei");
		setMnemonic(KeyEvent.VK_D);
		add(getLadeBuchungen());
		add(getSpeicherBuchungen());
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Speichern Menüpunkt
	 */
	private JMenuItem getSpeicherBuchungen() {
		if (speicherBuchungen == null) {
			speicherBuchungen = new JMenuItem();
			speicherBuchungen.setText("Speichern");
			speicherBuchungen.setMnemonic(KeyEvent.VK_S);
			speicherBuchungen.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_S, InputEvent.CTRL_MASK));
			speicherBuchungen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					eventManager
							.event(new DateiEventFactory().speichernEvent());
				}
			});
		}
		return speicherBuchungen;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Laden Menüpunkt
	 */
	private JMenuItem getLadeBuchungen() {
		if (ladeBuchungen == null) {
			ladeBuchungen = new JMenuItem();
			ladeBuchungen.setText("Öffnen");
			ladeBuchungen.setMnemonic(KeyEvent.VK_F);
			ladeBuchungen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
					InputEvent.CTRL_MASK));
			ladeBuchungen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					eventManager.event(new DateiEventFactory().oeffnenEvent());
				}
			});
		}
		return ladeBuchungen;
	}
}
