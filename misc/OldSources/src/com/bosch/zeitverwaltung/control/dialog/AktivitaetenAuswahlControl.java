package com.bosch.zeitverwaltung.control.dialog;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.view.UIFactory;
import com.bosch.zeitverwaltung.view.dialog.AktivitaetenAuswahl;
import com.bosch.zeitverwaltung.view.event.DialogCommitEvent;
import com.bosch.zeitverwaltung.view.event.DialogSchliessenEvent;
import com.bosch.zeitverwaltung.view.listener.DialogSchliessenListener;

/**
 * <p>
 * BasisControl für Aktivitätsauswahlen
 * </p>
 * 
 * @author Lars Geyer
 */
public abstract class AktivitaetenAuswahlControl implements
		DialogSchliessenListener {
	protected AktivitaetenAuswahl editor;

	/**
	 * <p>
	 * Konstruktor erzeugt Dialog und initialisiert diesen.
	 * </p>
	 */
	public AktivitaetenAuswahlControl() {
		editor = UIFactory.getFactory().getDialogFactory()
				.erzeugeAktivitaetenAuswahlDialog();
		editor.addDialogSchliessenListener(this);
	}

	/**
	 * <p>
	 * Dialog anzeigen und auf Reaktion warten
	 * </p>
	 */
	public void showDialog() {
		editor.showDialog();
	}

	/**
	 * {@inheritDoc}
	 */
	// SuppressWarning, da der Commit Event in diesem Fall immer mit Aktivitaet
	// parametriert ist, das aber nicht überprüft werden kann.
	@SuppressWarnings("unchecked")
	public void event(DialogSchliessenEvent evt) {
		if (evt instanceof DialogCommitEvent) {
			Aktivitaet aktivitaet = ((DialogCommitEvent<Aktivitaet>)evt).getBotschaft();
			auswahl(aktivitaet);
		}
	}

	/**
	 * <p>
	 * Wird mit der ermittelten Aktivität aufgerufen, um die Aktion
	 * durchzuführen.
	 * </p>
	 * 
	 * @param aktivitaet
	 *            Ausgewählte Aktivität
	 */
	protected abstract void auswahl(Aktivitaet aktivitaet);
}