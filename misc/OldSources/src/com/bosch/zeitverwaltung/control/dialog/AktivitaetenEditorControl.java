package com.bosch.zeitverwaltung.control.dialog;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.modell.AktivitaetenVerwaltung;
import com.bosch.zeitverwaltung.modell.ModellFactory;
import com.bosch.zeitverwaltung.view.UIFactory;
import com.bosch.zeitverwaltung.view.dialog.AktivitaetenEditor;

/**
 * <p>
 * Controller für Aktivitäten-Editoren
 * </p>
 * 
 * @author Lars Geyer
 */
public class AktivitaetenEditorControl extends ListEditorControl<Aktivitaet> {
	private AktivitaetenVerwaltung modell;
	private AktivitaetenEditor editor;

	/**
	 * <p>
	 * Erzeugt und initialisiert den Dialog
	 * </p>
	 */
	public AktivitaetenEditorControl() {
		ModellFactory modellFactory = ModellFactory.getFactory();
		modell = modellFactory.getAktivitaetenVerwaltung();
		UIFactory uiFactory = UIFactory.getFactory();
		editor = uiFactory.getDialogFactory().erzeugeAktivitaetenEditor();
		initialize();
	}

	/**
	 * {@inheritDoc}
	 */
	protected AktivitaetenEditor getEditor() {
		return editor;
	}

	/**
	 * {@inheritDoc}
	 */
	protected AktivitaetenVerwaltung getModell() {
		return modell;
	}
}
