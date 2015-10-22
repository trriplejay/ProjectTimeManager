package com.bosch.zeitverwaltung.view.dialog;

import com.bosch.zeitverwaltung.modell.BasisModell;
import com.bosch.zeitverwaltung.view.listener.DialogAktionListener;

/**
 * <p>
 * Schnittstelle eines Listen-Editors, d.h. der Dialog hat eine Liste von
 * Elementen, in die neue hinzugefügt, alte verändert oder gelöscht werden
 * können. Der Dialog besteht aus einem Listenfeld, in dem alle verfügbaren
 * Objekte aufgelistet sind und ein Editierfeld, in dem die Eigenschaften des
 * aktuell ausgewählten Objekts editiert werden können.
 * </p>
 * 
 * @author Lars Geyer
 * @see BasisDialog
 * @see com.bosch.zeitverwaltung.control.dialog.ListEditorControl
 * @see BasisModell
 * 
 * @param <Element>
 *            Der Objekttyp, der in der Liste dargestellt werden soll.
 */
public interface ListEditor<Element> extends BasisDialog {
	/**
	 * <p>
	 * Diese Methode wird aufgerufen, wenn sich das Modell der zu editierenden
	 * Objekte verändert hat. Mittels des Index wird dem Objekt die neue
	 * Position des aktuell ausgewählten Objekts mitgeteilt. Falls keines
	 * selektiert werden soll, wird -1 übergeben.
	 * </p>
	 * 
	 * @param veraenderterIndex
	 *            Neuer Index des aktuell selektierten Objekts.
	 */
	public void listenModellVeraendert(int veraenderterIndex);

	/**
	 * <p>
	 * Übergibt einen Listener, der informiert werden möchte, wenn ein neues
	 * Objekt in die Liste eingefügt oder ein altes verändert bzw. gelöscht
	 * werden soll. Der Listener ist dann für die Durchführung der gewünschten
	 * Aktion verantwortlich.
	 * </p>
	 * 
	 * @param listener
	 *            Listener, der Änderungen an der Objektliste durchführt.
	 */
	public void addDialogAktionListener(DialogAktionListener listener);

	/**
	 * <p>
	 * Entfernt einen Listener, der informiert werden möchte, wenn ein neues
	 * Objekt in die Liste eingefügt oder ein altes verändert bzw. gelöscht
	 * werden soll.
	 * </p>
	 * 
	 * @param listener
	 *            Listener
	 */
	public void removeDialogAktionListener(DialogAktionListener listener);
}
