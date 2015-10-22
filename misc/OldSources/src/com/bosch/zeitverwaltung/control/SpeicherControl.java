package com.bosch.zeitverwaltung.control;

import java.io.IOException;

import com.bosch.zeitverwaltung.control.dialog.TagOeffnenControl;
import com.bosch.zeitverwaltung.modell.ModellFactory;
import com.bosch.zeitverwaltung.modell.TagesBuchungen;
import com.bosch.zeitverwaltung.speicher.BuchungenLaden;
import com.bosch.zeitverwaltung.speicher.BuchungenSpeichern;
import com.bosch.zeitverwaltung.speicher.SpeicherFactory;
import com.bosch.zeitverwaltung.view.UIFactory;
import com.bosch.zeitverwaltung.view.dialog.NachfragenBox;
import com.bosch.zeitverwaltung.view.event.DateiEvent;
import com.bosch.zeitverwaltung.view.event.DateiOeffnenEvent;
import com.bosch.zeitverwaltung.view.event.DateiSpeichernEvent;
import com.bosch.zeitverwaltung.view.event.DialogCommitEvent;
import com.bosch.zeitverwaltung.view.event.DialogSchliessenEvent;
import com.bosch.zeitverwaltung.view.listener.DateiListener;
import com.bosch.zeitverwaltung.view.listener.DialogSchliessenListener;

/**
 * <p>
 * Dieses Control verwaltet Dateizugriffe. Es bietet die Möglichkeit an, die
 * Buchungen eines anderen Tages zu öffnen, oder die vorhandenen Buchungen zu
 * speichern. Es implementiert das <em>DateiListener</em>-Interface, dass
 * aufgerufen wird, wenn der Benutzer eine Dateioperation anfordert.
 * </p>
 * 
 * @author Lars Geyer
 * @see DateiListener
 * @see InitSchliessenControl
 * @see TagOeffnenControl
 * @see BuchungenLaden
 * @see BuchungenSpeichern
 */
public class SpeicherControl implements DateiListener {
	/**
	 * <p>
	 * Konstruktor, registriert sich als Datei-Listener
	 * </p>
	 */
	public SpeicherControl() {
		UIFactory.getFactory().erzeugeMainWindow().addDateiListener(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void event(DateiEvent evt) {
		if (evt instanceof DateiOeffnenEvent) {
			TagesBuchungen buchungen = ModellFactory.getFactory()
					.getTagesVerwaltung().getAktuellesModell();
			if ((buchungen != null) && buchungen.veraendert()) {
				NachfragenBox dialog = getAbfrageSpeichernDialog();
				dialog.showDialog();
			}
			TagOeffnenControl oeffnenDialog = new TagOeffnenControl(
					ModellFactory.getFactory().getTagesVerwaltung()
							.getAktuellesModell().getBuchungsTag());
			oeffnenDialog.showDialog();
		} else if (evt instanceof DateiSpeichernEvent) {
			try {
				speicherBuchungen();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <p>
	 * Erzeugt einen Dialog, in dem der Benutzer gefragt wird, ob er die
	 * aktuellen Buchungen speichern möchte, bevor er diese löscht.
	 * </p>
	 * 
	 * @return Speichern-Dialog
	 */
	NachfragenBox getAbfrageSpeichernDialog() {
		String text[] = { "Wollen Sie die existierenden",
				"Buchungen speichern?" };

		NachfragenBox dialog = UIFactory.getFactory().getDialogFactory()
				.erzeugeNachfragenDialog();
		dialog.setText(text);
		dialog.setTitel("Buchungen speichern?");
		dialog.setAbbruchOption(true);
		dialog.addDialogSchliessenListener(new DialogSchliessenListener() {
			public void event(DialogSchliessenEvent evt) {
				if (evt instanceof DialogCommitEvent) {
					try {
						speicherBuchungen();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		return dialog;
	}

	/**
	 * <p>
	 * Tatsächliches Speichern der Buchungsdaten
	 * </p>
	 * 
	 * @throws IOException
	 *             Falls beim Dateizugriff ein Fehler aufgetreten ist
	 */
	void speicherBuchungen() throws IOException {
		BuchungenSpeichern writer = SpeicherFactory.getFactory()
				.getBuchungsWriter();
		TagesBuchungen buchungen = ModellFactory.getFactory()
				.getTagesVerwaltung().getAktuellesModell();
		if (buchungen.veraendert()) {
			writer.speichereBuchungen(buchungen);
			buchungen.aenderungenGesichert();
		}
	}
}
