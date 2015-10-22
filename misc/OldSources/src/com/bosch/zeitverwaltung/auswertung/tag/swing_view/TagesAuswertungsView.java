package com.bosch.zeitverwaltung.auswertung.tag.swing_view;

import com.bosch.zeitverwaltung.auswertung.framework.swing_view.SimplerAuswertungsDialog;
import com.bosch.zeitverwaltung.auswertung.tag.modell.TagesAuswertungsModell;
import com.bosch.zeitverwaltung.funktion.AuswertungsView;

/**
 * <p>
 * Dialog zur Darstellung einer Tages-Auswertung von Buchungen
 * </p>
 * 
 * @author Lars Geyer
 * @see SimplerAuswertungsDialog
 * @see AuswertungsView
 * @see TagesAuswertungsModell
 */
public class TagesAuswertungsView extends SimplerAuswertungsDialog implements
		AuswertungsView<TagesAuswertungsModell> {
	private static final long serialVersionUID = 1L;

	private TagesAuswertungsModell modell = null;

	/**
	 * <p>
	 * Stellt die Verbindung zum Auswertungs-Modell her.
	 * </p>
	 * 
	 * @param modell
	 *            Referenz auf das AuswertungsModell-Objekt
	 */
	public void setModell(TagesAuswertungsModell modell) {
		this.modell = modell;
	}

	/**
	 * <p>
	 * Diese Methode fordert den View auf, die Auswertung zu triggern und das
	 * Ergebnis darzustellen.
	 * </p>
	 */
	public void showAuswertung() {
		setTitle("Auswertungen für " + modell.getBuchungsTag());
		try {
			modell.modelleLaden();
			datenVerbinden(modell);
			setVisible(true);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
