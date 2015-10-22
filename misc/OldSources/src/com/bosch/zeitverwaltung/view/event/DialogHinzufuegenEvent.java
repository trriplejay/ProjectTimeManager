package com.bosch.zeitverwaltung.view.event;

/**
 * <p>
 * Signalisiert die Eingabe eines neuen Elements
 * </p>
 * 
 * @author Lars Geyer
 */
public final class DialogHinzufuegenEvent<Element> extends DialogAktionEvent {
	private Element eingabe;

	/**
	 * <p>
	 * Die aktuelle Eingabe, die zu einem neuen Element gemacht werden soll.
	 * </p>
	 * 
	 * @param eingabe
	 *            Neues Element
	 */
	DialogHinzufuegenEvent(Element eingabe) {
		this.eingabe = eingabe;
	}

	/**
	 * <p>
	 * Gibt die neue Eingabe zurück
	 * </p>
	 * 
	 * @return Das neue Element
	 */
	public Element getEingabe() {
		return eingabe;
	}
}
