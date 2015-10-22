package com.bosch.zeitverwaltung.event;

/**
 * <p>
 * Basis-Listener-Schnittstelle, von der alle Listener-Interfaces abgeleitet
 * sind.
 * </p>
 * 
 * @author Lars Geyer
 * @see BasisEvent
 * @see EventVerteiler
 * 
 * @param <Event>
 *            Eventtyp, der von einem abgeleiteten Listener-Interface
 *            spezifiziert wird
 */
public interface BasisListener<Event extends BasisEvent> {
	/**
	 * <p>
	 * Ein Event ist passiert, die Event-Quelle erzeugt den Event und versendet
	 * ihn mittels dieser Methode an alle Listener. Dabei benutzt kann sie ein
	 * <em>EventVerteiler</em>-Objekt verwenden.
	 * </p>
	 * 
	 * @param evt
	 */
	public void event(Event evt);
}
