package com.bosch.zeitverwaltung.modell.listener;

import com.bosch.zeitverwaltung.event.BasisListener;
import com.bosch.zeitverwaltung.modell.event.TagChangedEvent;

/**
 * <p>
 * Mit dieser Schnittstelle zeigt die Modellkomponenten an, dass der Buchungstag
 * verändert wurde.
 * </p>
 * 
 * @author Lars Geyer
 */
public interface TagChangedListener extends
		BasisListener<TagChangedEvent> {

}
