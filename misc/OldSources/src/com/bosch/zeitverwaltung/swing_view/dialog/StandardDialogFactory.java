package com.bosch.zeitverwaltung.swing_view.dialog;

import com.bosch.zeitverwaltung.swing_view.MainWindowImpl;
import com.bosch.zeitverwaltung.view.dialog.AboutInformation;
import com.bosch.zeitverwaltung.view.dialog.AktivitaetenAuswahl;
import com.bosch.zeitverwaltung.view.dialog.AktivitaetenEditor;
import com.bosch.zeitverwaltung.view.dialog.AktivitaetsSortierungsAuswahl;
import com.bosch.zeitverwaltung.view.dialog.MinutenDeltaAuswahl;
import com.bosch.zeitverwaltung.view.dialog.NachfragenBox;
import com.bosch.zeitverwaltung.view.dialog.TagOeffnenAuswahl;
import com.bosch.zeitverwaltung.view.dialog.UIDialogFactory;

/**
 * @author gel2fr
 * 
 */
public class StandardDialogFactory extends UIDialogFactory {
	private MainWindowImpl hauptFenster;

	/**
	 * <p>
	 * Konstruktor, wird von der Standard-GUI-Factory aufgerufen.
	 * </p>
	 * 
	 * @param hauptFenster
	 */
	public StandardDialogFactory(MainWindowImpl hauptFenster) {
		this.hauptFenster = hauptFenster;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AboutInformation erzeugeAboutInformationDialog() {
		AboutInformation back = null;

		if (hauptFenster != null) {
			back = new AboutInformationImpl(hauptFenster);
		}

		return back;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AktivitaetenAuswahl erzeugeAktivitaetenAuswahlDialog() {
		AktivitaetenAuswahl back = null;

		if (hauptFenster != null) {
			back = new AktivitaetenAuswahlImpl(hauptFenster);
		}

		return back;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AktivitaetenEditor erzeugeAktivitaetenEditor() {
		AktivitaetenEditor back = null;

		if (hauptFenster != null) {
			back = new AktivitaetenEditorImpl(hauptFenster);
		}

		return back;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AktivitaetsSortierungsAuswahl erzeugeAktivitaetsSortierungsAuswahl() {
		return hauptFenster.getAktivitaetsSortierungsAuswahl();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MinutenDeltaAuswahl erzeugeMinutenDeltaAuswahl() {
		return hauptFenster.getMinutenDeltaAuswahl();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NachfragenBox erzeugeNachfragenDialog() {
		NachfragenBox back = null;

		if (hauptFenster != null) {
			back = new NachfragenBoxImpl(hauptFenster);
		}

		return back;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TagOeffnenAuswahl erzeugeTagOeffnenAuswahlDialog() {
		TagOeffnenAuswahl back = null;

		if (hauptFenster != null) {
			back = new TagOeffnenAuswahlImpl(hauptFenster);
		}

		return back;
	}

}
