package com.bosch.zeitverwaltung.view;

import com.bosch.zeitverwaltung.view.listener.BeendenListener;
import com.bosch.zeitverwaltung.view.listener.BuchungsListener;
import com.bosch.zeitverwaltung.view.listener.DateiListener;
import com.bosch.zeitverwaltung.view.listener.HilfeListener;
import com.bosch.zeitverwaltung.view.listener.OptionenListener;

/**
 * <p>
 * Schnittstelle zum Hauptfenster. Diese Dienste benötigt die Anwendung von
 * einer Hauptfensterimplementierung. Ein GUI muss die hier definierte
 * Schnittstelle implementieren.
 * </p>
 * 
 * <p>
 * Eine implementierende Klasse muss auf <em>AktivitaetenChangedEvent</em>-,
 * <em>BuchungenChangedEvent</em>-, DeltaChangedEvent</em>- und <em>TagChangedEvent</em>-Objekte
 * reagieren, um die wichtigen Änderungen mitzubekommen.
 * </p>
 * 
 * @author Lars Geyer
 */
public interface MainWindow {

	/**
	 * <p>
	 * Anmeldung eines Listeners, der Aufforderungen zu Optionsänderungen
	 * verarbeitet.
	 * </p>
	 * 
	 * @param listener
	 *            Neuer Listener
	 */
	public void addOptionenListener(OptionenListener listener);

	/**
	 * <p>
	 * Abmeldung eines Listeners, der Aufforderungen zu Optionsänderungen
	 * verarbeitet.
	 * </p>
	 * 
	 * @param listener
	 *            Listener
	 */
	public void removeOptionenListener(OptionenListener listener);

	/**
	 * <p>
	 * Anmeldung eines Listeners, der Hilfeanforderungen verarbeitet.
	 * </p>
	 * 
	 * @param listener
	 *            Neuer Listener
	 */
	public void addHilfeListener(HilfeListener listener);

	/**
	 * <p>
	 * Abmeldung eines Listeners, der Hilfeanforderungen verarbeitet.
	 * </p>
	 * 
	 * @param listener
	 *            Listener
	 */
	public void removeHilfeListener(HilfeListener listener);

	/**
	 * <p>
	 * Anmeldung eines Listeners, der Buchungsanforderungen verarbeitet.
	 * </p>
	 * 
	 * @param listener
	 *            Neuer Listener
	 */
	public void addBuchungsListener(BuchungsListener listener);

	/**
	 * <p>
	 * Abmeldung eines Listeners, der Buchungsanforderungen verarbeitet.
	 * </p>
	 * 
	 * @param listener
	 *            Listener
	 */
	public void removeBuchungsListener(BuchungsListener listener);

	/**
	 * <p>
	 * Anmeldung eines Listeners, der Anforderungen zu Dateioperationen
	 * verarbeitet.
	 * </p>
	 * 
	 * @param listener
	 *            Neuer Listener
	 */
	public void addDateiListener(DateiListener listener);

	/**
	 * <p>
	 * Abmeldung eines Listeners, der Anforderungen zu Dateioperationen
	 * verarbeitet.
	 * </p>
	 * 
	 * @param listener
	 *            Listener
	 */
	public void removeDateiListener(DateiListener listener);

	/**
	 * <p>
	 * Anmeldung eines Listeners, der die Anforderung zum Beenden der Anwendung
	 * verarbeitet
	 * </p>
	 * 
	 * @param listener
	 *            Neuer Listener
	 */
	public void addBeendenListener(BeendenListener listener);

	/**
	 * <p>
	 * Abmeldung eines Listeners, der die Anforderung zum Beenden der Anwendung
	 * verarbeitet
	 * </p>
	 * 
	 * @param listener
	 *            Listener
	 */
	public void removeBeendenListener(BeendenListener listener);

	/**
	 * <p>
	 * Schnittstelle, um Auswertungsfunktionen am GUI anzumelden und sie damit
	 * auswählbar zu machen
	 * </p>
	 * 
	 * @return Link auf die Anmeldeschnittstelle für Auswertungsfunktionen
	 */
	public FunktionsTrigger getFunktionsTrigger();

	/**
	 * <p>
	 * Nach der Initialisierung wird diese Funktion dazu verwendet, das
	 * Hauptfenster auf den Bildschirm zu bringen.
	 * </p>
	 */
	public void starteDarstellung();
}