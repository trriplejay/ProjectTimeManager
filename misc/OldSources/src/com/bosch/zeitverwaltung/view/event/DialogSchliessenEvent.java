package com.bosch.zeitverwaltung.view.event;

import com.bosch.zeitverwaltung.event.BasisEvent;

/**
 * <p>
 * Event, der beim Schliessen von Eingabemasken verwendet wird. Er signalisiert
 * den Bedarf für Änderung, bzw. den Abbruch der Eingabe
 * </p>
 * 
 * @author Lars Geyer
 * @see BasisEvent
 * @see com.bosch.zeitverwaltung.view.listener.DialogSchliessenListener
 */
public abstract class DialogSchliessenEvent extends BasisEvent {

}
