package com.bosch.zeitverwaltung.auswertung.framework.modell;

import java.util.Collection;

import com.bosch.zeitverwaltung.elemente.Buchung;
import com.bosch.zeitverwaltung.modell.TagesBuchungen;

/**
 * <p>
 * Buchungs-Ermittlung. Bei einer Auswertung müssen Unterbrechungen nud
 * Pausenzeiten beachtet werden. Die Methode dieser Schnittstelle geht durch die
 * Buchungen eines Tages und passt diese entsprechend der Pausenzeiten an. Als
 * Resultat erhält man eine Liste mit Buchungen, aus denen die Pausenzeiten
 * entfernt wurden.
 * </p>
 * 
 * @author Lars Geyer
 */
public interface BuchungsFilter {
	/**
	 * <p>
	 * Geht durch die Liste der Buchungen und gibt eine Liste mit Buchungen
	 * zurück, die nur anrechenbare Zeiten enthalten. Die Uhhrzeiten müssen
	 * nicht mehr mit den realen Buchungen übereinstimmen.
	 * </p>
	 * 
	 * @param buchungen
	 *            Die zu betrachtenden Buchungen
	 * @return Liste mit anrechenbaren Zeiten in Form von Buchungen
	 */
	public Collection<Buchung> berechneArbeitszeiten(TagesBuchungen buchungen);
}