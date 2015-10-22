package com.bosch.zeitverwaltung;

import java.io.IOException;

import com.bosch.zeitverwaltung.control.ApplikationsControl;
import com.bosch.zeitverwaltung.factory.ZeitverwaltungFactory;

/**
 * <p>
 * Dies ist die Startklasse der Zeitverwaltungs-Anwendung. Diese Klasse ist für
 * die Erzeugung der Basis-Factories zuständig. Danach gibt sie die Kontrolle an
 * <em>ApplikationsControl</em>. Die Erzeugung der Basis-Factories erfolgt
 * durch die Erzeugung einer <em>ZeitverwaltungFactory</em>. Die Klasse
 * erwartet, dass sie die Kontrolle zurückerhält, sobald die Anwendung beendet
 * werden soll.
 * </p>
 * 
 * @author Lars Geyer
 * @see ApplikationsControl
 * @see ZeitverwaltungFactory
 */
public class Zeitverwaltung {
	/**
	 * <p>
	 * Diese Funktion wird beim Start der Anwendung aufgerufen. Sie wird niemals
	 * verlassen, da an ihrem Ende mittels <em>System.exit</em> die Anwendung
	 * beendet wird. Die Funktion fängt Factory- sowie die Anwendungsexception
	 * <em>IOException</em> ab und beendet die Anwendung mit einer
	 * Fehlermeldung.
	 * </p>
	 */
	public void run() {
		try {
			new ZeitverwaltungFactory();
			new ApplikationsControl().run();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (InstantiationException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * <p>
	 * Main-Funktion. Sie erwartet keinerlei Parameter und ruft die Methode
	 * <em>run</em> auf.
	 * </p>
	 * 
	 * @param args
	 *            ohne Bedeutung
	 */
	public static void main(String[] args) {
		Zeitverwaltung meineApplikation = new Zeitverwaltung();
		meineApplikation.run();
	}
}
