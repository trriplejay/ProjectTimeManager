package com.bosch.zeitverwaltung.modell.listener;

import com.bosch.zeitverwaltung.event.BasisListener;
import com.bosch.zeitverwaltung.modell.event.DeltaChangedEvent;

/**
 * <p>
 * Mit dieser Schnittstelle zeigt die Modellkomponenten an, dass das
 * Minuten-Delta, auf das die Buchungszeiten gerundet werden, neu gesetzt wurde.
 * </p>
 * 
 * @author Lars Geyer
 */
public interface DeltaChangedListener extends BasisListener<DeltaChangedEvent> {
}
