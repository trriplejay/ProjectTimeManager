package com.bosch.zeitverwaltung.view.dialog;

import com.bosch.zeitverwaltung.elemente.Tag;

/**
 * <p>
 * Interface zum Dialog, der es erlaubt einen Tag auszuwählen, dessen Buchungen
 * dann im Hauptfenster dargestellt werden sollen. Auf den <em>BasisDialog</em>
 * wird zurückgegriffen, um die Dialog- Schließen Funktionalität zu verwenden.
 * </p>
 * 
 * @author Lars Geyer
 * @see com.bosch.zeitverwaltung.control.dialog.TagOeffnenControl
 * @see BasisDialog
 */
public interface TagOeffnenAuswahl extends BasisDialog {
	/**
	 * <p>
	 * Diese Methode wird vor <em>showDialog</em> aufgerufen und weißt den
	 * Dialog an, den übergebenen Tag initial zu selektieren.
	 * </p>
	 * 
	 * @param startTag
	 *            Initial zu selektierender Tag
	 */
	public void setStartTag(Tag startTag);
}
