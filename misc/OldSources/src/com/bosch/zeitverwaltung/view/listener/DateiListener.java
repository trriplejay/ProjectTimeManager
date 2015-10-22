package com.bosch.zeitverwaltung.view.listener;

import com.bosch.zeitverwaltung.event.BasisListener;
import com.bosch.zeitverwaltung.view.event.DateiEvent;

/**
 * <p>
 * Schnittstelle, über die das GUI den Benutzerwunsch nach Dateioperationen
 * mitteilt.
 * </p>
 * 
 * @author Lars Geyer
 * @see com.bosch.zeitverwaltung.view.MainWindow
 */
public interface DateiListener extends BasisListener<DateiEvent> {
}
