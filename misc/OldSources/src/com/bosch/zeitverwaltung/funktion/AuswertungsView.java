package com.bosch.zeitverwaltung.funktion;

/**
 * <p>
 * Das Interface definiert die Basisschnittstelle einer Auswertung. Ein View
 * bekommt Verbindung mit seinem Modell mittels der Methode <em>setModell</em>.
 * Die Auswertung wird angezeigt, sobald die Methode <em>showAuswertung</em>
 * aufgerufen wird.
 * </p>
 * 
 * @author Lars Geyer
 * @see AuswertungsModell
 * @see Auswertung
 * 
 * @param <Modell>
 *            Eine Instanz der AuswertungsModell-Schnittstelle, in konkreten
 *            Modellen wird deren Schnittstelle definiert.
 */
public interface AuswertungsView<Modell extends AuswertungsModell> {
	/**
	 * <p>
	 * Stellt die Verbindung zum Auswertungs-Modell her. Die Methode darf nur
	 * einen Link auf das Modell herstellen, die konkreten Modelldaten stehen
	 * hier noch nicht zur Verfügung und dürfen nicht verwendet werden.
	 * </p>
	 * 
	 * @param modell
	 *            Referenz auf das AuswertungsModell-Objekt
	 */
	public void setModell(Modell modell);

	/**
	 * <p>
	 * Diese Methode fordert den View auf, die Auswertung zu triggern und das
	 * Ergebnis darzustellen. Erst bei Aufruf dieser Methode enthält das Modell
	 * die notwendigen Daten, um die Auswertung zu machen und die entsprechenden
	 * Daten zu liefern. Aufbauend auf diesen Daten muss die Methode eine
	 * UI-Komponente erzeugen, die das Ergebnis darstellt.
	 * </p>
	 */
	public void showAuswertung();
}
