package com.bosch.zeitverwaltung.auswertung.monat.taetigkeiten.modell;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.bosch.zeitverwaltung.auswertung.framework.modell.ListenBerechnung;
import com.bosch.zeitverwaltung.elemente.Buchung;
import com.bosch.zeitverwaltung.elemente.Tag;
import com.bosch.zeitverwaltung.funktion.AuswertungsModell;
import com.bosch.zeitverwaltung.modell.TagesBuchungen;
import com.bosch.zeitverwaltung.speicher.BuchungenLaden;
import com.bosch.zeitverwaltung.speicher.SpeicherFactory;

/**
 * <p>
 * Modell zur Auswertung von Taetigkeiten eines Monats
 * </p>
 * 
 * @author Lars Geyer
 * @see ListenBerechnung
 * @see AuswertungsModell
 */
public class TaetigkeitenAuswertungsModell extends ListenBerechnung implements
		AuswertungsModell {
	/**
	 * <p>
	 * Diese Methode konfiguriert das Modell. Sie muss vor einer Auswertung
	 * aufgerufen werden. Die Parameter spezifizieren den Abrechnugsmonat, für
	 * den die Auswertung erfolgen soll.
	 * </p>
	 * 
	 * @param monat
	 *            Abrechnungsmonat
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public void setMonat(Tag monat) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		List<TagesBuchungen> buchungsListe = new LinkedList<TagesBuchungen>();

		BuchungenLaden lader = SpeicherFactory.getFactory().getBuchungsReader();
		for (int tag = 1; tag < 32; tag++) {
			Tag aktTag;
			try {
				aktTag = new Tag(monat.getJahr(), monat.getMonat(), tag);
				if (lader.existierenBuchungen(aktTag)) {
					try {
						TagesBuchungen aktModell = lader.ladeBuchungen(aktTag);
						if (pruefeTag(aktModell)) {
							buchungsListe.add(aktModell);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (IllegalArgumentException e1) {
			}
		}

		setBuchungen(buchungsListe,
				"com.bosch.zeitverwaltung.auswertung.framework.modell.FilterBoschStgt");
	}

	private boolean pruefeTag(TagesBuchungen buchungen) {
		boolean back = true;

		for (int index = 0; index < buchungen.getAnzahlBuchungen(); index++) {
			Buchung akt = buchungen.getBuchung(index);
			if (akt.getEndeZeit() == null) {
				back = false;
				break;
			}
		}
		return back;
	}
}
