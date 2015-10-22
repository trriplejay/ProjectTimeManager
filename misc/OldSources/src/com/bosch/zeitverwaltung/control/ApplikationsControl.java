package com.bosch.zeitverwaltung.control;

import java.io.IOException;
import com.bosch.zeitverwaltung.view.MainWindow;
import com.bosch.zeitverwaltung.view.UIFactory;
import com.bosch.zeitverwaltung.view.dialog.AboutInformation;
import com.bosch.zeitverwaltung.view.dialog.UIDialogFactory;
import com.bosch.zeitverwaltung.view.event.AboutEvent;
import com.bosch.zeitverwaltung.view.event.HilfeEvent;
import com.bosch.zeitverwaltung.view.listener.HilfeListener;

/**
 * <p>
 * Hauptcontrol der Anwendung. Es initialisiert die Anwendung und startet die
 * Darstellung des Hauptfensters. Danach legt sich der Hauptthread schlafen, bis
 * der Anwendung-Schließen-Event den Thread wieder aufweckt.
 * </p>
 * 
 * <p>
 * Über das Applikations-Control werden einzelne Controls gestartet, die sich
 * mit den unterschiedlichen Event-Typen beschäftigen. Einen Teil dieser
 * Funktionalität übernimmt das Applikations-Control selbst. Es ist für die
 * Verarbeitung von Hilfe-Events zuständig. Für die anderen Typen von Events
 * gibt es jeweils ein Control:
 * </p>
 * 
 * <p>
 * <ul>
 * <li><em>BuchungsControl</em> für Buchungs-Events</li>
 * <li><em>OptionsControl</em> für Options-Änderungs-Events</li>
 * <li><em>SpeicherControl</em> für Datei-Events</li>
 * <li><em>FunktionsControl</em> für Auswertungs-Events</li>
 * <li><em>InitSchliessenControl</em> für Anwendung-Schließen-Events</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Die Controls werden in der Initialisierung durch das
 * <em>InitSchliessenControl</em> erzeugt. Sie laufen autark und reagieren auf
 * Events, durch die sie auch Einfluss auf den Kontrollfluss bekommen.
 * </p>
 * 
 * @author Lars Geyer
 * @see com.bosch.zeitverwaltung.Zeitverwaltung
 * @see BuchungsControl
 * @see OptionsControl
 * @see SpeicherControl
 * @see com.bosch.zeitverwaltung.funktion.FunktionsControl
 * @see InitSchliessenControl
 * @see HilfeListener
 */
public class ApplikationsControl implements HilfeListener {
	/**
	 * <p>
	 * Initialisierung und Darstellung der Anwendung. Danach wird der
	 * Hauptthread schlafen gelegt.
	 * </p>
	 * 
	 * @throws IOException
	 *             Bei Problemen mit Dateien (IO-, XML-Fehler, etc.)
	 * @throws IllegalArgumentException
	 *             Falls Anwendung bereits läuft (nur eine Instanz erlaubt)
	 */
	public void run() throws IOException, IllegalArgumentException {
		new InitSchliessenControl(this);
		MainWindow hauptFenster = UIFactory.getFactory().erzeugeMainWindow();
		hauptFenster.addHilfeListener(this);
		hauptFenster.starteDarstellung();
		synchronisiere();
	}

	/**
	 * {@inheritDoc}
	 */
	public void event(HilfeEvent evt) {
		if (evt instanceof AboutEvent) {
			UIDialogFactory uiFactory = UIFactory.getFactory().getDialogFactory();
			AboutInformation dialog = uiFactory.erzeugeAboutInformationDialog();
			dialog.showDialog();
		}
	}

	/**
	 * <p>
	 * Warte auf das Ende der Anwendung
	 * </p>
	 */
	private synchronized void synchronisiere() {
		boolean beenden = false;
		while (!beenden) {
			try {
				wait();
				beenden = true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <p>
	 * Wird von der Anwendung-Beenden-Behandlung aufgerufen, um Hauptthread
	 * aufzuwecken.
	 * </p>
	 */
	public synchronized void rendezvous() {
		notifyAll();
	}
}
