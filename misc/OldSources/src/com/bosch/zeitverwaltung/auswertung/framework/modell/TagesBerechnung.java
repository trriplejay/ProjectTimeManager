package com.bosch.zeitverwaltung.auswertung.framework.modell;

import com.bosch.zeitverwaltung.modell.TagesBuchungen;

/**
 * <p>
 * Wertet die Buchungen eines Tages aus.
 * </p>
 * 
 * @author Lars Geyer
 */
public class TagesBerechnung extends BuchungsBerechnung {
	/**
	 * <p>
	 * Abgeleitete Modelle rufen diese Methode auf, um die
	 * <em>TagesBuchungen</em> zu setzen, für die Berechnungen durchgeführt
	 * werden sollen
	 * </p>
	 * 
	 * @param buchungen
	 *            <em>TagesBuchungen</em>-Objekt
	 * @param filterKlasse
	 *            Name der Filterklasse, um nicht anrechenbare Zeiten
	 *            herauszufiltern
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	protected void setBuchungen(TagesBuchungen buchungen, String filterKlasse)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		BuchungsFilter filter = new FilterLader().erzeugeFilter(filterKlasse);
		setBuchungen(filter.berechneArbeitszeiten(buchungen));
	}
}
