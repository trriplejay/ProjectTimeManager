package com.bosch.zeitverwaltung.control;

import java.io.IOException;
import java.util.Date;

import com.bosch.zeitverwaltung.configuration.KonfigurationsFactory;
import com.bosch.zeitverwaltung.elemente.Tag;
import com.bosch.zeitverwaltung.funktion.FunktionsControl;
import com.bosch.zeitverwaltung.modell.AenderungsManager;
import com.bosch.zeitverwaltung.modell.ModellFactory;
import com.bosch.zeitverwaltung.modell.TagesVerwaltung;
import com.bosch.zeitverwaltung.speicher.BuchungenLaden;
import com.bosch.zeitverwaltung.speicher.SpeicherFactory;
import com.bosch.zeitverwaltung.view.UIFactory;
import com.bosch.zeitverwaltung.view.dialog.NachfragenBox;
import com.bosch.zeitverwaltung.view.event.BeendenEvent;
import com.bosch.zeitverwaltung.view.listener.BeendenListener;

/**
 * <p>
 * Dieses Control ist für die Initialisierung und die Beendigung der Anwendung
 * verantwortlich. Während der Initialisierung erzeugt es in der richtigen
 * Reihenfolge die für bestimmte Events zuständigen Controls.
 * </p>
 * 
 * @author Lars Geyer
 * @see ApplikationsControl
 * @see BeendenListener
 */
public class InitSchliessenControl implements BeendenListener {
	private ApplikationsControl hauptControl = null;
	private SpeicherControl speicherHandler = null;

	/**
	 * <p>
	 * Konstruktor, er initialisiert die Anwendung
	 * </p>
	 * 
	 * @param hauptControl
	 *            Link auf Haupt-Control, wird zum Beenden der Anwendung
	 *            benötigt.
	 * @throws IOException
	 *             Bei Problemen mit Dateien (IO-, XML-Fehler, etc.)
	 * @throws IllegalArgumentException
	 *             Falls Anwendung bereits läuft (nur eine Instanz erlaubt)
	 */
	public InitSchliessenControl(ApplikationsControl hauptControl)
			throws IOException, IllegalArgumentException {
		this.hauptControl = hauptControl;
		speicherHandler = new SpeicherControl();
		new BuchungsControl();
		new OptionsControl();
		new FunktionsControl();
		// KonfigurationsManager erwartet, dass alle Modelle erzeugt wurden
		KonfigurationsFactory.getFactory().getKonfigurationsReader()
				.ladeKonfigurationsDaten();
		UIFactory.getFactory().erzeugeMainWindow().addBeendenListener(this);
		initialisiereTag();
	}

	/**
	 * <p>
	 * Initialisierung des Tages, d.h. anlegen eines Tages-Buchungsmodells und
	 * einlesen von Buchungen sofern vorhanden.
	 * </p>
	 */
	private void initialisiereTag() {
		TagesVerwaltung tagesVerwaltung = ModellFactory.getFactory()
				.getTagesVerwaltung();
		BuchungenLaden reader = SpeicherFactory.getFactory()
				.getBuchungsReader();
		Tag initTag = initialisierungsTag();
		if (reader.existierenBuchungen(initialisierungsTag())) {
			try {
				tagesVerwaltung.setNeuesModell(reader.ladeBuchungen(initTag));
			} catch (IOException e) {
				e.printStackTrace();
				tagesVerwaltung.neuesModell(initTag);
			}
		} else {
			tagesVerwaltung.neuesModell(initTag);
		}
	}

	/**
	 * <p>
	 * Methode definiert den Start-Tag, der beim Starten der Anwendung angezeigt
	 * werden soll
	 * </p>
	 * 
	 * @return Tag, der beim Start der Anwendung geöffnet werden soll
	 */
	private Tag initialisierungsTag() {
		return new Tag(new Date());
	}

	/**
	 * {@inheritDoc}
	 */
	public void event(BeendenEvent evt) {
		AenderungsManager buchungen = ModellFactory.getFactory()
				.getTagesVerwaltung().getAktuellesModell();
		if (buchungen.veraendert()) {
			NachfragenBox dialog = speicherHandler.getAbfrageSpeichernDialog();
			dialog.showDialog();
		}
		try {
			KonfigurationsFactory.getFactory().getKonfigurationsWriter()
					.speicherKonfigurationsDaten();
		} catch (IOException e) {
			e.printStackTrace();
		}
		hauptControl.rendezvous();
	}
}
