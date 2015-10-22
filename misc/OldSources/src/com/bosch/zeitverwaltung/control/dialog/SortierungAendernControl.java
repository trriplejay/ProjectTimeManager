package com.bosch.zeitverwaltung.control.dialog;

import com.bosch.zeitverwaltung.elemente.AktivitaetsSortierung;
import com.bosch.zeitverwaltung.modell.ModellFactory;
import com.bosch.zeitverwaltung.view.UIFactory;
import com.bosch.zeitverwaltung.view.dialog.AktivitaetsSortierungsAuswahl;
import com.bosch.zeitverwaltung.view.dialog.BasisDialog;
import com.bosch.zeitverwaltung.view.event.DialogCommitEvent;

/**
 * <p>
 * Überwacht Änderungen an der Aktivitäten-Sortierung, nachdem Aufforderung zur
 * Optionsänderung erfolgte
 * </p>
 * 
 * @author Lars Geyer
 * @see AktivitaetsSortierungsAuswahl
 * @see com.bosch.zeitverwaltung.modell.AktivitätsSortierungsManager
 * @see com.bosch.zeitverwaltung.view.event.DialogSchliessenEvent
 */
public class SortierungAendernControl extends
		BasisEditorControl<AktivitaetsSortierung> {
	private AktivitaetsSortierungsAuswahl editor;

	/**
	 * <p>
	 * Konstruktor, er erzeugt und initialisiert den Editor.
	 * </p>
	 */
	public SortierungAendernControl() {
		editor = UIFactory.getFactory().getDialogFactory()
				.erzeugeAktivitaetsSortierungsAuswahl();
		initialize();
	}

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
	protected void commit(DialogCommitEvent<AktivitaetsSortierung> evt) {
		AktivitaetsSortierung sortierung = evt.getBotschaft();
		ModellFactory.getFactory().getAktivitaetenVerwaltung().setSortierung(
				sortierung);
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
	protected BasisDialog getEditor() {
		return editor;
	}
}
