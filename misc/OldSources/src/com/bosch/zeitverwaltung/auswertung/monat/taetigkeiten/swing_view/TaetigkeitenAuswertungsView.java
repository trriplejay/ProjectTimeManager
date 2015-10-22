package com.bosch.zeitverwaltung.auswertung.monat.taetigkeiten.swing_view;

import com.bosch.zeitverwaltung.auswertung.framework.swing_view.SimplerAuswertungsDialog;
import com.bosch.zeitverwaltung.auswertung.monat.taetigkeiten.modell.TaetigkeitenAuswertungsModell;
import com.bosch.zeitverwaltung.elemente.Tag;
import com.bosch.zeitverwaltung.funktion.AuswertungsView;
import com.bosch.zeitverwaltung.modell.ModellFactory;

/**
 * <p>
 * Dialog zur Darstellung der Buchungen eines Monats.
 * </p>
 * 
 * @author Lars Geyer
 * @see SimplerAuswertungsDialog
 * @see AuswertungsView
 * @see TaetigkeitenAuswertungsModell
 */
public class TaetigkeitenAuswertungsView extends SimplerAuswertungsDialog implements
		AuswertungsView<TaetigkeitenAuswertungsModell> {
	private static final long serialVersionUID = 1L;

	private TaetigkeitenAuswertungsModell modell = null;

	/**
	 * <p>
	 * Stellt die Verbindung zum Auswertungs-Modell her.
	 * </p>
	 * 
	 * @param modell
	 *            Referenz auf das AuswertungsModell-Objekt
	 */
	public void setModell(TaetigkeitenAuswertungsModell modell) {
		this.modell = modell;
	}

	/**
	 * <p>
	 * Diese Methode fordert den View auf, die Auswertung zu triggern und das
	 * Ergebnis darzustellen.
	 * </p>
	 */
	public void showAuswertung() {
		Tag aktTag = ModellFactory.getFactory().getTagesVerwaltung()
				.getAktuellesModell().getBuchungsTag();
		setTitle("Monatsauswertung für "
				+ String.format("%1$02d", aktTag.getMonat()) + "/"
				+ aktTag.getJahr());
		try {
			modell.setMonat(aktTag);
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
