package com.bosch.zeitverwaltung.view.dialog;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;

/**
 * <p>
 * Schnittstelle des Aktivitäten-Editors. Die Schnittstelle ist komplett durch
 * den List-Editor definiert. Die Unterscheidung liegt darin, dass das
 * <em>AktivitaetenEditor</em>-Interface festlegt, dass der List-Editor mit
 * <em>Aktivitaet</em>-Objekten arbeitet.
 * </p>
 * 
 * @author Lars Geyer
 * @see ListEditor
 * @see com.bosch.zeitverwaltung.control.dialog.AktivitaetenEditorControl
 * @see com.bosch.zeitverwaltung.control.dialog.ListEditorControl
 */
public interface AktivitaetenEditor extends ListEditor<Aktivitaet> {
}
