package com.bosch.zeitverwaltung.view.event;

/**
 * <p>
 * Ein Dialog wurde durch Drücken des OK-Buttons geschlossen.
 * </p>
 * 
 * @author Lars Geyer
 */
public final class DialogCommitEvent<Element> extends DialogSchliessenEvent {
	private Element botschaft;

	/**
	 * <p>
	 * Erzeugt einen Commit ohne Zusatzbotschaft
	 * </p>
	 */
	DialogCommitEvent() {
		botschaft = null;
	}
	
	/**
	 * <p>
	 * Erzeugt einen Commit mit einer Zusatzbotschaft
	 * </p>
	 * 
	 * @param botschaft Schliessenbotschaft
	 */
	DialogCommitEvent(Element botschaft) {
		this.botschaft = botschaft;
	}
	
	/**
	 * <p>
	 * Gibt die Botschaft zurück.
	 * </p>
	 * 
	 * @return Botschaft des Commits
	 */
	public Element getBotschaft() {
		return botschaft;
	}
}
