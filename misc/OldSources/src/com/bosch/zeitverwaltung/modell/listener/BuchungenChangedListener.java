package com.bosch.zeitverwaltung.modell.listener;

import com.bosch.zeitverwaltung.event.BasisListener;
import com.bosch.zeitverwaltung.modell.event.BuchungenChangedEvent;

/**
 * <p>
 * Mit dieser Schnittstelle zeigt die Modellkomponenten an, dass die Liste der
 * aktuellen Buchungen verändert wurde.
 * </p>
 * 
 * @author Lars Geyer
 */
public interface BuchungenChangedListener extends
		BasisListener<BuchungenChangedEvent> {

}
