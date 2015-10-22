package com.bosch.zeitverwaltung.control.dialog;

import com.bosch.zeitverwaltung.elemente.MinutenDelta;
import com.bosch.zeitverwaltung.modell.ModellFactory;
import com.bosch.zeitverwaltung.view.UIFactory;
import com.bosch.zeitverwaltung.view.dialog.BasisDialog;
import com.bosch.zeitverwaltung.view.dialog.MinutenDeltaAuswahl;
import com.bosch.zeitverwaltung.view.event.DialogCommitEvent;

/**
 * <p>
 * Überwacht Änderungen am Minuten-Delta, nachdem Aufforderung zur
 * Optionsänderung erfolgte
 * </p>
 * 
 * @author Lars Geyer
 * @see MinutenDeltaAuswahl
 * @see com.bosch.zeitverwaltung.modell.MinutenDeltaManager
 * @see com.bosch.zeitverwaltung.view.event.DialogSchliessenEvent
 */
public class MinutenDeltaControl extends BasisEditorControl<MinutenDelta> {
	private MinutenDeltaAuswahl editor;

	/**
	 * <p>
	 * Konstruktor, er erzeugt und initialisiert den Editor.
	 * </p>
	 */
	public MinutenDeltaControl() {
		editor = UIFactory.getFactory().getDialogFactory()
				.erzeugeMinutenDeltaAuswahl();
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
	protected void commit(DialogCommitEvent<MinutenDelta> evt) {
		MinutenDelta minutenDelta = evt.getBotschaft();
		ModellFactory.getFactory().getMinutenDelta().setMinutenDelta(
				minutenDelta);
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
