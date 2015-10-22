package com.bosch.zeitverwaltung.modell_impl;

import com.bosch.zeitverwaltung.elemente.MinutenDelta;
import com.bosch.zeitverwaltung.elemente.Uhrzeit;
import com.bosch.zeitverwaltung.event.EventVerteiler;
import com.bosch.zeitverwaltung.modell.MinutenDeltaManager;
import com.bosch.zeitverwaltung.modell.event.DeltaChangedEvent;
import com.bosch.zeitverwaltung.modell.event.DeltaChangedEventFactory;
import com.bosch.zeitverwaltung.modell.listener.DeltaChangedListener;

/**
 * <p>
 * Realisierung des Interface zur Minuten-Delta-Verwaltung.
 * </p>
 * 
 * @author Lars Geyer
 * @see MinutenDeltaManager
 * @see com.bosch.zeitverwaltung.elemente.Uhrzeit
 */
public final class MinutenDeltaImpl implements MinutenDeltaManager {
	private EventVerteiler<DeltaChangedEvent, DeltaChangedListener> listenerMgmt =
		new EventVerteiler<DeltaChangedEvent, DeltaChangedListener>();

	/**
	 * <p>
	 * Setzt Minuten-Delta auf eine der vier in <em>DeltaChangedEvent</em>
	 * definierten Konstanten
	 * </p>
	 * 
	 * @param minutenDelta
	 *            Das Minuten-Delta
	 */
	public void setMinutenDelta(MinutenDelta minutenDelta) {
		Uhrzeit.setMinutenDelta(minutenDelta);
		listenerMgmt.event(new DeltaChangedEventFactory()
				.deltaChangedEvent(minutenDelta));
	}

	/**
	 * <p>
	 * Gibt Minuten-Delta als eine der vier in <em>DeltaChangedEvent</em>
	 * definierten Konstanten zurück
	 * </p>
	 * 
	 * @return Minuten-Delta
	 */
	public MinutenDelta getMinutenDelta() {
		return Uhrzeit.getMinutenDelta();
	}

	/**
	 * <p>
	 * Füge einen Listener hinzu, der auf Delta-Changed-Events hört
	 * </p>
	 * 
	 * @param listener
	 *            An Delta-Changed-Events interessierter Listener
	 */
	public void addDeltaChangedListener(DeltaChangedListener listener) {
		listenerMgmt.addEventListener(listener);
	}

	/**
	 * <p>
	 * Entfernt einen Listener, der auf Delta-Changed-Events hört
	 * </p>
	 * 
	 * @param listener
	 *            Zu entfernender Listener
	 */
	public void removeDeltaChangedListener(DeltaChangedListener listener) {
		listenerMgmt.delEventListener(listener);
	}
}
