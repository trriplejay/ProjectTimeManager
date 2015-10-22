package com.bosch.zeitverwaltung.view.event;

/**
 * <p>
 * Factory für <em>DialogAktionEvent</em>-Objekte
 * </p>
 * 
 * @author Lars Geyer
 * @see DialogAktionEvent
 */
public final class DialogAktionEventFactory {
	/**
	 * <p>
	 * Erzeugt einen Hinzufügen-Event
	 * </p>
	 * 
	 * @return DialogAktionEvent
	 */
	public <Element> DialogAktionEvent hinzufuegenEvent(Element eingabe) {
		return new DialogHinzufuegenEvent<Element>(eingabe);
	}

	/**
	 * <p>
	 * Erzeugt einen Verändern-Event
	 * </p>
	 * 
	 * @return DialogAktionEvent
	 */
	public <Element> DialogAktionEvent veraendernEvent(Element auswahl, Element eingabe) {
		return new DialogVeraendernEvent<Element>(auswahl, eingabe);
	}

	/**
	 * <p>
	 * Erzeugt einen Löschen-Event
	 * </p>
	 * 
	 * @return DialogAktionEvent
	 */
	public <Element> DialogAktionEvent loeschenEvent(Element auswahl) {
		return new DialogLoeschenEvent<Element>(auswahl);
	}
}
