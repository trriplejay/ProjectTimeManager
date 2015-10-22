package com.bosch.zeitverwaltung.modell.listener;

import com.bosch.zeitverwaltung.event.BasisListener;
import com.bosch.zeitverwaltung.modell.event.SortierungChangedEvent;

/**
 * <p>
 * Mit dieser Schnittstelle zeigt die Modellkomponenten an, dass die
 * Aktivitätssortierung verändert wurde.
 * </p>
 * 
 * @author Lars Geyer
 */
public interface SortierungChangedListener extends
		BasisListener<SortierungChangedEvent> {
}
