package com.bosch.zeitverwaltung.modell;

/**
 * <p>
 * Dieses Interface erlaubt die Durchführung von Änderungen an einer Liste von
 * Elementen in einem Transkationsmodus, d.h. die Änderungen sind zurücknehmbar.
 * Dies kann z.B. von einem GUI verwendet werden, um einen Änderungsdialog zu
 * haben, der einen Abbruch-Button zur Verfügung stellt.
 * </p>
 * 
 * @author Lars Geyer
 */
public interface AenderungsTransaktion {
	/**
	 * <p>
	 * Starten der Transaktion, das Modell muss den aktuellen Zustand
	 * sicherstellen, um ihn wiederherstellen zu können.
	 * </p>
	 */
	public void starteAenderung();

	/**
	 * <p>
	 * Änderungen wurden abgeschlossen und sollen übernommen werden.
	 * </p>
	 */
	public void commitAenderung();

	/**
	 * <p>
	 * Der Änderungsvorgang wurde abgebrochen, der alte Zustand soll
	 * wiederhergestellt werden.
	 * </p>
	 */
	public void restoreAenderung();
}
