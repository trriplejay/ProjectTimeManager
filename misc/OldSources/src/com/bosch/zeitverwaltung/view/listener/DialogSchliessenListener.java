package com.bosch.zeitverwaltung.view.listener;

import com.bosch.zeitverwaltung.event.BasisListener;
import com.bosch.zeitverwaltung.view.event.DialogSchliessenEvent;

/**
 * <p>
 * Schnittstelle, über die der <em>BasisDialog</em> Aufforderungen zum Dialog
 * schließen verteilt.
 * </p>
 * 
 * @author Lars Geyer
 * @see com.bosch.zeitverwaltung.view.dialog.BasisDialog
 */
public interface DialogSchliessenListener extends
		BasisListener<DialogSchliessenEvent> {

}
