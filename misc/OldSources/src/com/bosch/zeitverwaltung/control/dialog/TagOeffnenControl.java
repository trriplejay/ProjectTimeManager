package com.bosch.zeitverwaltung.control.dialog;

import java.io.IOException;

import com.bosch.zeitverwaltung.elemente.Tag;
import com.bosch.zeitverwaltung.modell.ModellFactory;
import com.bosch.zeitverwaltung.speicher.BuchungenLaden;
import com.bosch.zeitverwaltung.speicher.SpeicherFactory;
import com.bosch.zeitverwaltung.view.UIFactory;
import com.bosch.zeitverwaltung.view.dialog.BasisDialog;
import com.bosch.zeitverwaltung.view.dialog.TagOeffnenAuswahl;
import com.bosch.zeitverwaltung.view.event.DialogCommitEvent;

/**
 * <p>
 * Das Control wird aufgerufen, sobald der Benutzer einen neuen Tag öffnen
 * möchte. Das Control erzeugt einen entsprechenden Dialog und fragt den
 * gewünschten Tag ab. Anschließend öffnet das Control den Tag, bzw. legt einen
 * neuen Tag an.
 * </p>
 * 
 * @author Lars Geyer
 * @see BasisEditorControl
 * @see TagOeffnenAuswahl
 */
public class TagOeffnenControl extends BasisEditorControl<Tag> {
	private TagOeffnenAuswahl editor;

	/**
	 * <p>
	 * Konstruktor, er initialisiert den Dialog.
	 * </p>
	 * 
	 * @param startTag
	 *            Aktueller Tag. Dieser wird im Dialog selektiert.
	 */
	public TagOeffnenControl(Tag startTag) {
		editor = UIFactory.getFactory().getDialogFactory()
				.erzeugeTagOeffnenAuswahlDialog();
		initialize();
		editor.setStartTag(startTag);
	}

	/**
	 * <p>
	 * Implementierung der <em>BasisEditorControl</em>-Methode
	 * <em>commit</em>. Der Dialog wurde mit einem Änderungsauftrag beendet.
	 * </p>
	 * 
	 * @param evt
	 *            Eventdaten
	 */
	public void commit(DialogCommitEvent<Tag> evt) {
		Tag neuerTag = evt.getBotschaft();
		if (neuerTag != null) {
			BuchungenLaden reader = SpeicherFactory.getFactory()
					.getBuchungsReader();
			try {
				if (reader.existierenBuchungen(neuerTag)) {
					ModellFactory.getFactory().getTagesVerwaltung()
							.setNeuesModell(reader.ladeBuchungen(neuerTag));
				} else {
					ModellFactory.getFactory().getTagesVerwaltung()
							.neuesModell(neuerTag);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <p>
	 * Methode wird vom <em>BasisEditorControl</em> aufgerufen, um Referenz
	 * auf den Editor zu erhalten.
	 * </p>
	 */
	protected BasisDialog getEditor() {
		return editor;
	}
}
