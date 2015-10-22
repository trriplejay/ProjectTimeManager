package com.bosch.zeitverwaltung.control;

import com.bosch.zeitverwaltung.control.dialog.AktivitaetenAuswahlControl;
import com.bosch.zeitverwaltung.control.dialog.BuchungsAktivitaetenAuswahlControl;
import com.bosch.zeitverwaltung.modell.ModellFactory;
import com.bosch.zeitverwaltung.view.UIFactory;
import com.bosch.zeitverwaltung.view.event.BuchungBeendenEvent;
import com.bosch.zeitverwaltung.view.event.BuchungLoeschenEvent;
import com.bosch.zeitverwaltung.view.event.BuchungsEvent;
import com.bosch.zeitverwaltung.view.event.NeueBuchungEvent;
import com.bosch.zeitverwaltung.view.event.NeueBuchungMitAktivitaetEvent;
import com.bosch.zeitverwaltung.view.event.NeueUnterbrechungEvent;
import com.bosch.zeitverwaltung.view.listener.BuchungsListener;

/**
 * <p>
 * Dieses Control ist mit der Verwaltung von Buchungs-Events beschäftigt. Es
 * nimmt diese entgegen und führt entweder die Änderung der Modelle oder es
 * startet ein Dialog-Control, welches weitere Angaben abfragt, um die Änderung
 * vornehmen zu können.
 * </p>
 * 
 * @author Lars Geyer
 * @see InitSchliessenControl
 * @see BuchungsListener
 */
public class BuchungsControl implements BuchungsListener {
	/**
	 * <p>
	 * Konstruktor, registriert sich als Buchungs-Listener
	 * </p>
	 */
	public BuchungsControl() {
		UIFactory.getFactory().erzeugeMainWindow().addBuchungsListener(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void event(BuchungsEvent evt) {
		if (evt instanceof NeueBuchungEvent) {
			AktivitaetenAuswahlControl dialog = new BuchungsAktivitaetenAuswahlControl();
			dialog.showDialog();
		} else if (evt instanceof NeueBuchungMitAktivitaetEvent) {
			ModellFactory.getFactory().getTagesVerwaltung()
					.getAktuellesModell().addBuchung(
							((NeueBuchungMitAktivitaetEvent) evt)
									.getAktivitaet());
		} else if (evt instanceof NeueUnterbrechungEvent) {
			ModellFactory.getFactory().getTagesVerwaltung()
					.getAktuellesModell().addUnterbrechung();
		} else if (evt instanceof BuchungBeendenEvent) {
			ModellFactory.getFactory().getTagesVerwaltung()
					.getAktuellesModell().beendeBuchung();
		} else if (evt instanceof BuchungLoeschenEvent) {
			ModellFactory.getFactory().getTagesVerwaltung()
					.getAktuellesModell().loescheBuchung(
							((BuchungLoeschenEvent) evt).getBuchung());
		}
	}
}
