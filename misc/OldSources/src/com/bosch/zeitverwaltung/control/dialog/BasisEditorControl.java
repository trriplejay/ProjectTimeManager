package com.bosch.zeitverwaltung.control.dialog;

import com.bosch.zeitverwaltung.view.dialog.BasisDialog;
import com.bosch.zeitverwaltung.view.event.DialogCommitEvent;
import com.bosch.zeitverwaltung.view.event.DialogSchliessenEvent;
import com.bosch.zeitverwaltung.view.listener.DialogSchliessenListener;

/**
 * <p>
 * Basis-Control, es bietet für Dialoge die Schnittstelle, um auf
 * Dialog-Schließen Events zu reagieren. Die konkrete Funktionalität wird in
 * abgeleiteten Klassen realisiert. Die Klasse agiert als Listener auf
 * <em>DialogSchliessenEvents</em>
 * 
 * @author Lars Geyer
 * @see DialogSchliessenListener
 * @see DialogSchliessenEvent
 */
public abstract class BasisEditorControl<Element> {
	/**
	 * <p>
	 * Der Dialog wurde mit einem Commit beendet. Dies wird mittels dieser
	 * Methode den abgeleiteten Klassen signalisiert. Die konkrete Aktion muss
	 * vom abgeleiteten Control realisiert werden.
	 * </p>
	 * 
	 * @param evt
	 *            Eventdaten
	 */
	protected abstract void commit(DialogCommitEvent<Element> evt);

	/**
	 * <p>
	 * Der Dialog wurde mit Abbruch beendet. Dies wird mittels dieser Methode
	 * den abgeleiteten Klassen signalisiert. Die konkrete Aktion muss vom
	 * abgeleiteten Control realisiert werden. Die leere Implementierung hier
	 * macht Sinn, da nicht alle Dialogs bei einem Abbruch etwas tun müssen.
	 * </p>
	 */
	protected void abort() {
	}

	/**
	 * <p>
	 * Aufforderung, den Dialog darzustellen
	 * </p>
	 */
	public void showDialog() {
		getEditor().showDialog();
	}

	/**
	 * <p>
	 * Initialisierung des Controls, muss im Konstruktor der abgeleiteten
	 * Klassen aufgerufen werden.
	 * </p>
	 */
	protected void initialize() {
		getEditor().addDialogSchliessenListener(new DialogSchliessenListener() {
			/**
			 * <p>
			 * Implementierung des <em>DialogSchliessenListener</em>-Interfaces.
			 * Ankommende Dialog-Schliessen- Events werden an die abgeleitete
			 * Klasse weitergegeben.
			 * </p>
			 * 
			 * @param evt
			 *            Dialog-Schliessen-Event-Information
			 */
			@SuppressWarnings("unchecked")
			public void event(DialogSchliessenEvent evt) {
				if (evt instanceof DialogCommitEvent) {
					commit((DialogCommitEvent<Element>)evt);
				} else {
					abort();
				}
			}
		});
	}

	/**
	 * <p>
	 * Zugriff auf den Dialog. Dieser ist abhängig vom abgeleiteten Control und
	 * wird daher dort erzeugt. Der Dialog muss das <em>BasisDialog</em>-Interface
	 * implementieren.
	 * </p>
	 * 
	 * @return Link auf den Dialog des Controls
	 */
	protected abstract BasisDialog getEditor();
}