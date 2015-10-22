package com.bosch.zeitverwaltung.modell;

import com.bosch.zeitverwaltung.elemente.MinutenDelta;
import com.bosch.zeitverwaltung.modell.listener.DeltaChangedListener;

/**
 * <p>
 * Dieses Interface beschreibt die Schnittstelle zur Minuten-Delta-Verwaltung.
 * </p>
 * 
 * @author Lars Geyer
 * @see com.bosch.zeitverwaltung.elemente.Uhrzeit
 */
public interface MinutenDeltaManager {
	/**
	 * <p>
	 * Setzt Minuten-Delta auf eine der vier in <em>MinutenDelta</em>
	 * definierten Konstanten. Das Delta wird nicht umgesetzt, falls ein
	 * temporäres Minuten-Delta gesetzt wurde.
	 * </p>
	 * 
	 * @param minutenDelta
	 *            Das Minuten-Delta
	 */
	public void setMinutenDelta(MinutenDelta minutenDelta);

	/**
	 * <p>
	 * Gibt Minuten-Delta als eine der vier in <em>MinutenDelta</em>
	 * definierten Konstanten zurück
	 * </p>
	 * 
	 * @return Minuten-Delta
	 */
	public MinutenDelta getMinutenDelta();

	/**
	 * <p>
	 * Füge einen Listener hinzu, der auf Delta-Changed-Events hört
	 * </p>
	 * 
	 * @param listener
	 *            An Delta-Changed-Events interessierter Listener
	 */
	public void addDeltaChangedListener(DeltaChangedListener listener);

	/**
	 * <p>
	 * Entfernt einen Listener, der auf Delta-Changed-Events hört
	 * </p>
	 * 
	 * @param listener
	 *            Zu entfernender Listener
	 */
	public void removeDeltaChangedListener(DeltaChangedListener listener);
}
