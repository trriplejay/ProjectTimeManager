package com.bosch.zeitverwaltung.view.dialog;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;

/**
 * <p>
 * Interface zu einer GUI-Komponente, die die Auswahl einer Aktivität
 * ermöglicht.
 * </p>
 * 
 * @author Lars Geyer
 * @see com.bosch.zeitverwaltung.control.dialog.AktivitaetenAuswahlControl
 * @see com.bosch.zeitverwaltung.control.dialog.BuchungsAktivitaetenAuswahlControl
 */
public interface AktivitaetenAuswahl extends BasisDialog {
	/**
	 * <p>
	 * Bestimmt die Aktivität, die zum Anfang der Darstellung markiert sein
	 * soll. Die Methode wird nach <em>setModell</em>, aber vor
	 * <em>showDialog</em> aufgerufen.
	 * </p>
	 * 
	 * @param selektiert
	 *            Zu selektierende Aktivität
	 */
	public void setSelected(Aktivitaet selektiert);
}
