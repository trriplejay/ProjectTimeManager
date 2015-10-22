package com.bosch.zeitverwaltung.modell.event;

import com.bosch.zeitverwaltung.elemente.MinutenDelta;
import com.bosch.zeitverwaltung.event.BasisEvent;

/**
 * <p>
 * Event, der eine Änderung im Minutendelta anzeigt.
 * </p>
 * 
 * @author Lars Geyer
 * @see com.bosch.zeitverwaltung.modell.listener.DeltaChangedListener
 * @see com.bosch.zeitverwaltung.modell.MinutenDeltaManager
 */
public final class DeltaChangedEvent extends BasisEvent {
	private MinutenDelta minutenDelta;

	/**
	 * <p>
	 * Kontruktor, er wird nur von der DeltaChangedEvent-Factory aufgerufen.
	 * </p>
	 * 
	 * @param minutenDelta
	 *            Das neue Minutendelta
	 */
	DeltaChangedEvent(MinutenDelta minutenDelta) {
		this.minutenDelta = minutenDelta;
	}

	/**
	 * <p>
	 * Gibt das aktuelle Minutendelta zurück
	 * </p>
	 * 
	 * @return Das aktuelle Minutendelta
	 */
	public MinutenDelta getMinutenDelta() {
		return minutenDelta;
	}

	/**
	 * <p>
	 * Ist Minuten-Delta eine Minuten
	 * </p>
	 * 
	 * @return Minuten-Delta ist eine Minute
	 */
	public boolean istMinutenDeltaEineMinute() {
		return (minutenDelta == MinutenDelta.MinutenDeltaEineMinute);
	}

	/**
	 * <p>
	 * Ist Minuten-Delta drei Minuten
	 * </p>
	 * 
	 * @return Minunten-Delta ist drei Minuten
	 */
	public boolean istMinutenDeltaDreiMinuten() {
		return (minutenDelta == MinutenDelta.MinutenDeltaDreiMinuten);
	}

	/**
	 * <p>
	 * Ist Minuten-Delta fünf Minuten
	 * </p>
	 * 
	 * @return Minunten-Delta ist fünf Minuten
	 */
	public boolean istMinutenDeltaFuenfMinuten() {
		return (minutenDelta == MinutenDelta.MinutenDeltaFuenfMinuten);
	}

	/**
	 * <p>
	 * Ist Minuten-Delta fünfzehn Minuten
	 * </p>
	 * 
	 * @return Minunten-Delta ist fünfzehn Minuten
	 */
	public boolean istMinutenDeltaFuenfzehnMinuten() {
		return (minutenDelta == MinutenDelta.MinutenDeltaFuenfzehnMinuten);
	}
}
