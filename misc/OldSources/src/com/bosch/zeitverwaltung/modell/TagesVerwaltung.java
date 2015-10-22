package com.bosch.zeitverwaltung.modell;

import com.bosch.zeitverwaltung.elemente.Tag;
import com.bosch.zeitverwaltung.modell.listener.TagChangedListener;

/**
 * <p>
 * Erlaubt die Erzeugung und Verwaltung der aktuellen Buchungsdaten in Form
 * eines <em>TagesBuchungen</em>-Objekts.
 * </p>
 * 
 * @author Lars Geyer
 */
public interface TagesVerwaltung {
	/**
	 * <p>
	 * Erzeugt ein neues <em>TagesBuchungen</em>-Objekt f�r den �bergebenen
	 * Tag und ersetzt das alte aktuelle Objhekt mit dem neuen.
	 * </p>
	 * 
	 * @param tag
	 *            Tag f�r den Buchungen angelegt werden sollen.
	 */
	public void neuesModell(Tag tag);

	/**
	 * <p>
	 * Erlaubt die Abfrage des aktuellen Modells, f�r das Buchungen editiert
	 * werden.
	 * </p>
	 * 
	 * @return Aktuelles <em>TagesBuchungen</em>-Objekt
	 */
	public TagesBuchungen getAktuellesModell();

	/**
	 * <p>
	 * Setzt das aktuelle <em>TagesBuchungen</em>-Objekt auf ein �bergebenes
	 * Objekt
	 * </p>
	 * 
	 * @param buchungen
	 *            Neues aktuelles <em>TagesBuchungen</em>-Objekt
	 */
	public void setNeuesModell(TagesBuchungen buchungen);

	/**
	 * <p>
	 * F�gt einen Listener hinzu, der auf �nderungen des Buchungstages h�rt.
	 * </p>
	 * 
	 * @param listener
	 *            Neuer Listener
	 */
	public void addTagChangedListener(TagChangedListener listener);

	/**
	 * <p>
	 * Entfernt einen Listener, der auf �nderungen des Buchungstages h�rt.
	 * </p>
	 * 
	 * @param listener
	 *            Zu entfernender Listener
	 */
	public void removeTagChangedListener(TagChangedListener listener);
}