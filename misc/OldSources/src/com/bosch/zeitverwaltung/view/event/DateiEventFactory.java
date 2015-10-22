package com.bosch.zeitverwaltung.view.event;

/**
 * <p>
 * Erzeugt Datei-Event-Objekte
 * </p>
 * 
 * @author Lars Geyer
 * @see DateiEvent
 */
public final class DateiEventFactory {
	/**
	 * <p>
	 * Erzeugt einen neuen Datei-Öffnen-Event
	 * </p>
	 * 
	 * @return DateiEvent
	 */
	public DateiEvent oeffnenEvent() {
		return new DateiOeffnenEvent();
	}
	
	/**
	 * <p>
	 * Erzeugt einen neuen Datei-Speichern-Event
	 * </p>
	 * 
	 * @return DateiEvent
	 */
	public DateiEvent speichernEvent() {
		return new DateiSpeichernEvent();
	}
}
