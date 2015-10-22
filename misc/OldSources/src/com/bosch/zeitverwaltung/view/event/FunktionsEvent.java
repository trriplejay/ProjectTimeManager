package com.bosch.zeitverwaltung.view.event;

import com.bosch.zeitverwaltung.event.BasisEvent;

/**
 * <p>
 * Signalisiert den Wunsch nach einer Auswertung
 * </p>
 * 
 * <p>
 * Auswertungen werden durch einen Plug-In-Mechanismus beim System angemeldet.
 * Dabei wird ein Name angegeben, der zur Identifikation der Funktionalität
 * dient.
 * </p>
 * 
 * @author Lars Geyer
 * 
 */
public final class FunktionsEvent extends BasisEvent {
	private String funktionsName;

	/**
	 * <p>
	 * Erzeugt den Event
	 * </p>
	 * 
	 * @param funktionsName
	 *            Name der Auswertungsfunktion. Dieser muss vorher mit dem
	 *            Menüpunkt registriert worden sein.
	 */
	FunktionsEvent(String funktionsName) {
		this.funktionsName = funktionsName;
	}

	/**
	 * <p>
	 * Gibt den Auswertungsfunktions-Namen zurück.<
	 * </p>
	 * 
	 * @return Referenz auf die Auswertungsfunktion
	 */
	public String getFunktionsName() {
		return funktionsName;
	}
}
