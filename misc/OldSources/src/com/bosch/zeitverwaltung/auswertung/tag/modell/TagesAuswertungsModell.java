package com.bosch.zeitverwaltung.auswertung.tag.modell;

import com.bosch.zeitverwaltung.auswertung.framework.modell.ListenBerechnung;
import com.bosch.zeitverwaltung.auswertung.framework.modell.TagesBerechnung;
import com.bosch.zeitverwaltung.elemente.Tag;
import com.bosch.zeitverwaltung.funktion.AuswertungsModell;
import com.bosch.zeitverwaltung.modell.ModellFactory;

/**
 * <p>
 * Modell für die Auswertung eines Tages
 * </p>
 * 
 * @author Lars Geyer
 * @see ListenBerechnung
 * @see com.bosch.zeitverwaltung.auswertung.framework.modell.BuchungsBerechnung
 */
public class TagesAuswertungsModell extends TagesBerechnung implements
		AuswertungsModell {
	/**
	 * <p>
	 * Methode bereitet die Buchungen vor, die danach ausgewertet werden können.
	 * Diese Methode ist vor allen Auswertungs-Methoden aufzurufen.
	 * </p>
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public void modelleLaden() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		setBuchungen(ModellFactory.getFactory().getTagesVerwaltung()
				.getAktuellesModell(),
				"com.bosch.zeitverwaltung.auswertung.framework.modell.FilterBoschStgt");
	}

	/**
	 * <p>
	 * Gibt den Buchungstag zurück, für den die Auswertung erfolgen soll
	 * </p>
	 * 
	 * @return Buchungstag
	 */
	public Tag getBuchungsTag() {
		return ModellFactory.getFactory().getTagesVerwaltung()
				.getAktuellesModell().getBuchungsTag();
	}
}
