package com.bosch.zeitverwaltung.view.event;

/**
 * <p>
 * Loescht ein Element aus einem Editor.
 * </p>
 * 
 * @author Lars Geyer
 */
public final class DialogLoeschenEvent<Element> extends DialogAktionEvent {
	private Element selektion;

	/**
	 * <p>
	 * Erzeugt einen Element löschen Event
	 * </p>
	 * 
	 * @param selektion
	 *            Das zu loeschende Element
	 */
	DialogLoeschenEvent(Element selektion) {
		this.selektion = selektion;
	}

	/**
	 * <p>
	 * Gibt zu loeschendes Element zurück
	 * </p>
	 * 
	 * @return Zu loeschendes Element
	 */
	public Element getSelektion() {
		return selektion;
	}
}
