package com.bosch.zeitverwaltung.modell.listener;

import com.bosch.zeitverwaltung.event.BasisListener;
import com.bosch.zeitverwaltung.modell.event.AktivitaetenChangedEvent;

/**
 * <p>
 * Mit dieser Schnittstelle zeigt die Modellkomponenten an, dass die Liste der
 * Aktivitäten verändert wurde.
 * </p>
 * 
 * @author Lars Geyer
 */
public interface AktivitaetenChangedListener extends
		BasisListener<AktivitaetenChangedEvent> {

}
