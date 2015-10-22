package com.bosch.zeitverwaltung.modell;

/**
 * <p>
 * Interface zur Verwaltung von Änderungen an einem Managementobjekt. Das
 * Managementobjekt muss diese Schnittstelle implementieren. Danach kann eine
 * Speicherfunktion eine Speicherungen nur dann veranlassen, wenn sich etwas an
 * den Daten verändert hat.
 * </p>
 * 
 * @author Lars Geyer
 */
public interface AenderungsManager {

	/**
	 * <p>
	 * Wurde das Modell verändert?
	 * </p>
	 * 
	 * @return Das Modell wurde verändert
	 */
	public boolean veraendert();

	/**
	 * <p>
	 * Eine Sicherung der Daten wurde vorgenommen, erst nach einer Änderung ist
	 * eine Sicherung wieder notwendig.
	 * </p>
	 */
	public void aenderungenGesichert();
}