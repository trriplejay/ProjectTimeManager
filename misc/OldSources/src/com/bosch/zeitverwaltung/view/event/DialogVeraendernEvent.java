package com.bosch.zeitverwaltung.view.event;

/**
 * <p>
 * Ein Element soll wie übergeben verändert werden.
 * </p>
 * 
 * @author Lars Geyer
 *
 */
public final class DialogVeraendernEvent<Element> extends DialogAktionEvent {
	private Element selektion;
	private Element eingabe;
	
	/**
	 * <p>
	 * Erzeugt einen Element verändern Event
	 * </p>
	 * 
	 * @param selektion Das zu verändernde Element
	 */
	DialogVeraendernEvent(Element selektion, Element eingabe) {
		this.selektion = selektion;
		this.eingabe = eingabe;
	}

	/**
	 * <p>
	 * Gibt zu loeschendes Element zurück
	 * </p>
	 * 
	 * @return Zu loeschendes Element
	 */
	public Element getElement() {
		return selektion;
	}

	/**
	 * <p>
	 * Gibt die Änderungen zurück
	 * </p>
	 * 
	 * @return Die durchzuführenden Änderungen
	 */
	public Element getEingabe() {
		return eingabe;
	}
}
