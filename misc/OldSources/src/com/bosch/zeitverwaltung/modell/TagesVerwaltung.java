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
	 * Erzeugt ein neues <em>TagesBuchungen</em>-Objekt für den übergebenen
	 * Tag und ersetzt das alte aktuelle Objhekt mit dem neuen.
	 * </p>
	 * 
	 * @param tag
	 *            Tag für den Buchungen angelegt werden sollen.
	 */
	public void neuesModell(Tag tag);

	/**
	 * <p>
	 * Erlaubt die Abfrage des aktuellen Modells, für das Buchungen editiert
	 * werden.
	 * </p>
	 * 
	 * @return Aktuelles <em>TagesBuchungen</em>-Objekt
	 */
	public TagesBuchungen getAktuellesModell();

	/**
	 * <p>
	 * Setzt das aktuelle <em>TagesBuchungen</em>-Objekt auf ein übergebenes
	 * Objekt
	 * </p>
	 * 
	 * @param buchungen
	 *            Neues aktuelles <em>TagesBuchungen</em>-Objekt
	 */
	public void setNeuesModell(TagesBuchungen buchungen);

	/**
	 * <p>
	 * Fügt einen Listener hinzu, der auf Änderungen des Buchungstages hört.
	 * </p>
	 * 
	 * @param listener
	 *            Neuer Listener
	 */
	public void addTagChangedListener(TagChangedListener listener);

	/**
	 * <p>
	 * Entfernt einen Listener, der auf Änderungen des Buchungstages hört.
	 * </p>
	 * 
	 * @param listener
	 *            Zu entfernender Listener
	 */
	public void removeTagChangedListener(TagChangedListener listener);
}