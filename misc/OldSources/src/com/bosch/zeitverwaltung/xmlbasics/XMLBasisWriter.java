package com.bosch.zeitverwaltung.xmlbasics;

import org.w3c.dom.Element;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.elemente.Uhrzeit;
import com.bosch.zeitverwaltung.elemente.UnterbrechungsAktivitaet;
import com.bosch.zeitverwaltung.services.DOMWriter;

/**
 * <p>
 * Enthaelt die Generierung von Basis-XML-Tags, die sowohl in den
 * XML-Konfigurationen als auch in der XML-Buchungsspeicherung eingesetzt
 * werden.
 * </p>
 * 
 * @author Lars Geyer
 * @see XMLBasisKonstanten
 * @see XMLBasisReader
 * @see com.bosch.zeitverwaltung.xmlconfiguration.XMLKonfigurationsDatenWriter
 * @see com.bosch.zeitverwaltung.xmlspeicher.XMLBuchungsWriter
 */
public class XMLBasisWriter {
	/**
	 * <p>
	 * Erzeuge ein Aktivitäts-Tag
	 * </p>
	 * 
	 * @param document
	 *            Das XML-Dokument
	 * @param vater
	 *            Das Aktivitäten-Tag
	 * @param aktivitaet
	 *            Die zu speichernde Aktivität
	 */
	public void erzeugeAktivitaetsElement(DOMWriter document, Element vater,
			Aktivitaet aktivitaet) {
		Element aktivitaetsElement = document.neuesElement(vater,
				XMLBasisKonstanten.tagAktivitaet);

		Element temp = document.neuesElement(aktivitaetsElement,
				XMLBasisKonstanten.tagAktivitaetsName);
		if (aktivitaet instanceof UnterbrechungsAktivitaet) {
			temp.setTextContent(XMLBasisKonstanten.tagAktivitaetUnterbrechung);
		} else {
			temp.setTextContent(aktivitaet.getAktivitaet());
		}
		temp = document.neuesElement(aktivitaetsElement,
				XMLBasisKonstanten.tagAktivitaetsNummer);
		temp.setTextContent(aktivitaet.getBuchungsNummer());
		temp = document.neuesElement(aktivitaetsElement,
				XMLBasisKonstanten.tagAktivitaetsKategorie);
		temp.setTextContent(aktivitaet.getKategorie());
		if (aktivitaet.istProjektAktivitaet()) {
			temp = document.neuesElement(aktivitaetsElement,
					XMLBasisKonstanten.tagProjektAktivitaet);
		}

		if (aktivitaet.istReisezeit()) {
			if (aktivitaet.istAktiveReisezeit()) {
				temp = document.neuesElement(aktivitaetsElement,
						XMLBasisKonstanten.tagReisezeitAktiv);
			} else {
				temp = document.neuesElement(aktivitaetsElement,
						XMLBasisKonstanten.tagReisezeitPassiv);
			}
		}
		if (aktivitaet.abrechungsRelevant()) {
			temp = document.neuesElement(aktivitaetsElement,
					XMLBasisKonstanten.tagAktivitaetsAbrechnung);
			if (aktivitaet.abrechnungInKm()) {
				temp.setAttribute(
						XMLBasisKonstanten.tagAktivitaetsAbrechnungKM, Integer
								.toString(aktivitaet.getAbrechnungsKm()));
			} else {
				temp.setAttribute(
						XMLBasisKonstanten.tagAktivitaetsAbrechnungEuro, Double
								.toString(aktivitaet.getAbrechnungsBetrag()));
			}
		}
	}

	/**
	 * <p>
	 * Erzeugen eines Startzeit-Tags aus einer Uhrzeit
	 * </p>
	 * 
	 * @param document
	 *            Das XML-Dokument
	 * @param vater
	 *            Das XML-Element, unter dem die Startzeit gespeichert werden
	 *            soll.
	 * @param zeit
	 *            Die Uhrzeit, die gespeichert werden soll
	 */
	public void erzeugeStartzeitElement(DOMWriter document, Element vater,
			Uhrzeit zeit) {
		erzeugeZeitpunktElement(document, vater,
				XMLBasisKonstanten.tagStartzeit, zeit);
	}

	/**
	 * <p>
	 * Erzeugen eines Endezeit-Tags aus einer Uhrzeit
	 * </p>
	 * 
	 * @param document
	 *            Das XML-Dokument
	 * @param vater
	 *            Das XML-Element, unter dem die Endezeit gespeichert werden
	 *            soll.
	 * @param zeit
	 *            Die Uhrzeit, die gespeichert werden soll
	 */
	public void erzeugeEndezeitElement(DOMWriter document, Element vater,
			Uhrzeit zeit) {
		erzeugeZeitpunktElement(document, vater,
				XMLBasisKonstanten.tagEndezeit, zeit);
	}

	/**
	 * <p>
	 * Eigentliche Erzeugung des Start-/Endezeit-Tags
	 * </p>
	 * 
	 * @param document
	 *            Das XML-Dokument
	 * @param vater
	 *            Das XML-Element, unter dem der Zeitpunkt gespeichert werden
	 *            soll
	 * @param tagname
	 *            Entweder <em>tagStartzeit</em> oder <em>tagEndezeit</em>
	 * @param zeit
	 *            Die Uhrzeit, die gespeichert werden soll
	 */
	private void erzeugeZeitpunktElement(DOMWriter document, Element vater,
			String tagname, Uhrzeit zeit) {
		Element zeitpunkt = document.neuesElement(vater, tagname);
		erzeugeUhrzeitElement(document, zeitpunkt, zeit);
	}

	/**
	 * <p>
	 * Erzeugen des Uhrzeit-Tags unterhalb des Start-/Endezeit-Tags
	 * </p>
	 * 
	 * @param document
	 *            Das XML-Dokument
	 * @param vater
	 *            Das Start-/Endezeit-Element, unter dem die Uhrzeit gespeichert
	 *            werden soll
	 * @param zeit
	 *            Die Uhrzeit, die gespeichert werden soll
	 */
	private void erzeugeUhrzeitElement(DOMWriter document, Element vater,
			Uhrzeit zeit) {
		Element uhrzeit = document.neuesElement(vater,
				XMLBasisKonstanten.tagUhrzeit);

		uhrzeit.setAttribute(XMLBasisKonstanten.tagUhrzeitStunde, Integer
				.toString(zeit.getStunde()));
		uhrzeit.setAttribute(XMLBasisKonstanten.tagUhrzeitMinute, Integer
				.toString(zeit.getMinuten()));
	}
}
