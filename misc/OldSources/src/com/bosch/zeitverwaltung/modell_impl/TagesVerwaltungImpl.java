package com.bosch.zeitverwaltung.modell_impl;

import com.bosch.zeitverwaltung.elemente.Tag;
import com.bosch.zeitverwaltung.event.EventVerteiler;
import com.bosch.zeitverwaltung.modell.ModellFactory;
import com.bosch.zeitverwaltung.modell.TagesBuchungen;
import com.bosch.zeitverwaltung.modell.TagesVerwaltung;
import com.bosch.zeitverwaltung.modell.event.TagChangedEvent;
import com.bosch.zeitverwaltung.modell.event.TagChangedEventFactory;
import com.bosch.zeitverwaltung.modell.listener.TagChangedListener;

/**
 * <p>
 * Erlaubt die Erzeugung und Verwaltung der aktuellen Buchungsdaten in Form
 * eines <em>TagesBuchungen</em>-Objekts.
 * </p>
 * 
 * @author Lars Geyer
 */
public final class TagesVerwaltungImpl implements TagesVerwaltung {
	private TagesBuchungen aktuellesModell = null;

	private EventVerteiler<TagChangedEvent, TagChangedListener> eventManager =
		new EventVerteiler<TagChangedEvent, TagChangedListener>();

	/**
	 * <p>
	 * Erzeugt ein neues <em>TagesBuchungen</em>-Objekt für den übergebenen
	 * Tag und ersetzt das alte aktuelle Objhekt mit dem neuen.
	 * </p>
	 * 
	 * @param tag
	 *            Tag für den Buchungen angelegt werden sollen.
	 */
	public void neuesModell(Tag tag) {
		ModellFactory buchungsFactory = ModellFactory.getFactory();
		TagesBuchungen neuesModell = buchungsFactory.erzeugeTagesBuchungen(tag);
		aktuellesModell = neuesModell;
		eventManager.event(new TagChangedEventFactory()
				.tagChangedEvent(aktuellesModell));
	}

	/**
	 * <p>
	 * Erlaubt die Abfrage des aktuellen Modells, für das Buchungen editiert
	 * werden.
	 * </p>
	 * 
	 * @return Aktuelles <em>TagesBuchungen</em>-Objekt
	 */
	public TagesBuchungen getAktuellesModell() {
		return aktuellesModell;
	}

	/**
	 * <p>
	 * Setzt das aktuelle <em>TagesBuchungen</em>-Objekt auf ein übergebenes
	 * Objekt
	 * </p>
	 * 
	 * @param buchungen
	 *            Neues aktuelles <em>TagesBuchungen</em>-Objekt
	 */
	public void setNeuesModell(TagesBuchungen buchungen) {
		aktuellesModell = buchungen;
		eventManager.event(new TagChangedEventFactory()
				.tagChangedEvent(aktuellesModell));
	}

	/**
	 * <p>
	 * Fügt einen Listener hinzu, der auf Änderungen des Buchungstages hört.
	 * </p>
	 * 
	 * @param listener
	 *            Neuer Listener
	 */
	public void addTagChangedListener(TagChangedListener listener) {
		eventManager.addEventListener(listener);
	}

	/**
	 * <p>
	 * Entfernt einen Listener, der auf Änderungen des Buchungstages hört.
	 * </p>
	 * 
	 * @param listener
	 *            Zu entfernender Listener
	 */
	public void removeTagChangedListener(TagChangedListener listener) {
		eventManager.delEventListener(listener);
	}
}
