package com.bosch.zeitverwaltung.view.dialog;

import com.bosch.zeitverwaltung.view.listener.DialogSchliessenListener;

/**
 * <p>
 * Grundlegende Editoren-Schnittstelle. Sie beherbergt die Funktionslität zur
 * Darstellung und Beendigung des Dialogs
 * </p>
 * 
 * @author Lars Geyer
 * @see com.bosch.zeitverwaltung.control.dialog.BasisEditorControl
 */
public interface BasisDialog {
	/**
	 * <p>
	 * Füge einen Listener hinzu, der Dialog-Schließen-Events mitgeteilt
	 * bekommen möchte. nach dem Schließen kann die Verarbeitung der Änderung
	 * durch den Listener erfolgen.
	 * </p>
	 * 
	 * @param listener
	 *            Listener für Dialog-Schließen-Events
	 */
	public void addDialogSchliessenListener(DialogSchliessenListener listener);

	/**
	 * <p>
	 * Entfernt einen Listener, der Dialog-Schließen-Events mitgeteilt bekommen
	 * möchte.
	 * </p>
	 * 
	 * @param listener
	 *            Listener für Dialog-Schließen-Events
	 */
	public void removeDialogSchliessenListener(DialogSchliessenListener listener);

	/**
	 * <p>
	 * Mittels dieser Methode wird der Dialog auf den Bildschirm gebracht. Der
	 * Dialog ist modal und die Methode kehrt erst zurück, wenn die Auswahl
	 * erfolgt ist. Danach ist der Dialog wieder vom Bildschirm entfernt und
	 * kann entsorgt werden.
	 * </p>
	 */
	public void showDialog();
}
