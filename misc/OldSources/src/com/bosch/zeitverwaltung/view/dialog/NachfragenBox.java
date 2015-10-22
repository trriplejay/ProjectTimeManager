package com.bosch.zeitverwaltung.view.dialog;

/**
 * <p>
 * Interface zu einer Abfrage-Box, mit deren Hilfe dem Benutzer Fragen gestellt
 * werden können. Diese Box wird zum Beispiel zur Abfrage verwendet, ob der
 * Benutzer Daten speichern möchte, bevor er die Anwendung schließt.
 * </p>
 * 
 * @author Lars Geyer
 */
public interface NachfragenBox extends BasisDialog {
	/**
	 * <p>
	 * Setzt das Textproperty. Muss vor dem showDialog gesetzt werden.
	 * </p>
	 * 
	 * @param text
	 *            Text, der in der Dialogbox dargestellt werden soll.
	 */
	public void setText(String[] text);

	/**
	 * <p>
	 * Setzt das Titelproperty. Muss vor dem showDialog gesetzt werden.
	 * </p>
	 * 
	 * @param titel
	 *            Titel der Dialogbox
	 */
	public void setTitel(String titel);

	/**
	 * <p>
	 * Legt fest, ob Abfrage einen Abbruch-Button enthalten soll (<em>true</em>)
	 * oder nicht (<em>false</em>). Default ist <em>false</em>.
	 * </p>
	 * 
	 * @param abbruchOption
	 *            Fragen können mit oder ohne der Möglichkeit des Abbruches
	 *            gestellt werden. Wird hier <em>true</em> übergeben, so wird
	 *            die Abbruch-Option präsentiert.
	 */
	public void setAbbruchOption(boolean abbruchOption);
}
