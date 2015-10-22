package com.bosch.zeitverwaltung.view.listener;

import com.bosch.zeitverwaltung.event.BasisListener;
import com.bosch.zeitverwaltung.view.event.BuchungsEvent;

/**
 * <p>
 * Wird benutzt, um Buchungs-Events zu signalisieren.
 * </p>
 * 
 * @author Lars Geyer
 * @see com.bosch.zeitverwaltung.view.MainWindow
 */
public interface BuchungsListener extends BasisListener<BuchungsEvent> {
}