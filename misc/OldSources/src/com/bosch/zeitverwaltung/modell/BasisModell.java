package com.bosch.zeitverwaltung.modell;

import java.util.Collection;

/**
 * <p>
 * Basis-Modell. Es hat einige grundlegende Funktionalitäten, die in mehreren
 * Modellen zum Einsatz kommen.
 * </p>
 * 
 * @author Lars Geyer
 * @see AktivitaetenVerwaltung
 * @see BuchungsFilter
 * 
 * @param <Element>
 *            Objekttyp, der mittels des Interface verwaltet wird
 */
public interface BasisModell<Element> extends AenderungsTransaktion {
	/**
	 * <p>
	 * Methode erlaubt den Zugriff auf die verwalteten Elemente
	 * </p>
	 * 
	 * @return Liste der verwaltenten Elemente
	 */
	public Collection<Element> elemente();

	/**
	 * <p>
	 * Hinzufügen eines neuen Elements in die Liste
	 * </p>
	 * 
	 * @param neuesElement
	 *            Einzufügendes Element
	 * @return Index des Elements in der Liste
	 * @throws Exception
	 *             Falls die Überprüfung des neuen Elements fehl schlägt
	 */
	public int elementHinzufuegen(Element neuesElement) throws Exception;

	/**
	 * <p>
	 * Löschen des übergebenen Elements aus der Liste
	 * </p>
	 * 
	 * @param zuLoeschendes
	 *            Zu löschendes Element
	 */
	public void elementLoeschen(Element zuLoeschendes);
}
