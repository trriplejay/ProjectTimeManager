package com.bosch.zeitverwaltung.view.listener;

import com.bosch.zeitverwaltung.event.BasisListener;
import com.bosch.zeitverwaltung.view.event.DialogAktionEvent;

/**
 * <p>
 * Schnittstelle, über die der der <em>ListEditor</em> Änderungsaufforderungen
 * mitteilt.
 * </p>
 * 
 * @author Lars Geyer
 * @see com.bosch.zeitverwaltung.view.dialog.ListEditor
 * 
 * @param <Element>
 *            Typ der Objekte, die verändert werden
 */
public interface DialogAktionListener extends BasisListener<DialogAktionEvent> {

}
