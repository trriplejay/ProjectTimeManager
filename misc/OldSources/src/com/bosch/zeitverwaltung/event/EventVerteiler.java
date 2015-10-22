package com.bosch.zeitverwaltung.event;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * <p>
 * Event-Verteiler, er kapselt die Funktionalität zum Verteilen eines Events.
 * Eine Event-Quelle muss lediglich den Event erzeugen und dieser wird dann über
 * den Event-Verteiler an alle Event-Senken verteilt.
 * </p>
 * 
 * @author Lars Geyer
 * @see BasisEvent
 * @see BasisListener
 */
public final class EventVerteiler<Event extends BasisEvent, Listener extends BasisListener<Event>> {
	Collection<Listener> listener = new LinkedList<Listener>();

	/**
	 * <p>
	 * Methode zum Hinzufügen einer Event-Senke
	 * </p>
	 * 
	 * @param listener
	 *            Neue Event-Senke
	 */
	public void addEventListener(Listener listener) {
		if (!this.listener.contains(listener)) {
			this.listener.add(listener);
		}
	}

	/**
	 * <p>
	 * Methode zum Entfernen einer Event-Senke
	 * </p>
	 * 
	 * @param listener
	 *            Zu entfernende Event-Senke
	 */
	public void delEventListener(Listener listener) {
		this.listener.remove(listener);
	}

	/**
	 * <p>
	 * Methode zum Senden eines Events
	 * </p>
	 * 
	 * @param evt
	 *            Zu versendender Event
	 */
	public void event(Event evt) {
		Iterator<Listener> iter = listener.iterator();
		while (iter.hasNext()) {
			iter.next().event(evt);
		}
	}
}
