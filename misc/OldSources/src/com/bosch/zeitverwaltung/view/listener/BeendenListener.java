package com.bosch.zeitverwaltung.view.listener;

import com.bosch.zeitverwaltung.event.BasisListener;
import com.bosch.zeitverwaltung.view.event.BeendenEvent;

/**
 * <p>
 * Über diese Schnittstelle signalisiert das GUI den interessierten Listenern,
 * dass die Anwendung beendet werden soll.
 * </p>
 * 
 * @author Lars Geyer
 * @see com.bosch.zeitverwaltung.view.MainWindow
 */
public interface BeendenListener extends BasisListener<BeendenEvent>{
}
