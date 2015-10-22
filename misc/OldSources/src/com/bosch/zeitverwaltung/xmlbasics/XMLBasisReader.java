package com.bosch.zeitverwaltung.xmlbasics;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.w3c.dom.Element;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.elemente.Uhrzeit;
import com.bosch.zeitverwaltung.elemente.UnterbrechungsAktivitaet;
import com.bosch.zeitverwaltung.services.DOMReader;

/**
 * <p>
 * Enthaelt das Parsen Basis-XML-Tags, die sowohl in den XML-Konfigurationen als
 * auch in der XML-Buchungsspeicherung eingesetzt werden.
 * </p>
 * 
 * @author Lars Geyer
 * @see XMLBasisKonstanten
 * @see XMLBasisWriter
 * @see com.bosch.zeitverwaltung.xmlconfiguration.XMLKonfigurationsDatenReader
 * @see com.bosch.zeitverwaltung.xmlspeicher.XMLBuchungsReader
 */
public class XMLBasisReader {
	/**
	 * <p>
	 * Extrahiere eine Aktivität aus einem Aktivitäts-Tag
	 * </p>
	 * 
	 * @param document
	 *            Das XML-Dokument
	 * @param vater
	 *            Das Vater-Tag der Aktivitäts-Tags
	 * @return Die am Vater-Tag hängenden Aktivitäts-Tags
	 */
	public Collection<Aktivitaet> extrahiereAktivitaeten(DOMReader document,
			Element vater) {
		Collection<Aktivitaet> aktivitaeten = new LinkedList<Aktivitaet>();

		Iterator<Element> iter = document.getElementListe(vater,
				XMLBasisKonstanten.tagAktivitaet).iterator();

		while (iter.hasNext()) {
			Element aktivitaetsElement = iter.next();
			Aktivitaet aktivitaet = extrahiereAktivitaet(document,
					aktivitaetsElement);
			aktivitaeten.add(aktivitaet);
		}

		return aktivitaeten;
	}

	/**
	 * <p>
	 * Methode zur Extraktion eines Aktivitäts-Tags
	 * </p>
	 * 
	 * @param document
	 *            Das XML-Dokument
	 * @param aktivitaet
	 *            Das Aktivitäts-Tag
	 * @return Das entsprechende <em>Aktivitaet</em>-Objekt
	 */
	private Aktivitaet extrahiereAktivitaet(DOMReader document,
			Element aktivitaet) {
		Aktivitaet back = null;

		String name = document.getElement(aktivitaet,
				XMLBasisKonstanten.tagAktivitaetsName).getTextContent();
		String nummer = document.getElement(aktivitaet,
				XMLBasisKonstanten.tagAktivitaetsNummer).getTextContent();
		String kategorie = document.getElement(aktivitaet,
				XMLBasisKonstanten.tagAktivitaetsKategorie).getTextContent();
		boolean projektAktivitaet = false;
		if (document.getElement(aktivitaet,
				XMLBasisKonstanten.tagProjektAktivitaet) != null) {
			projektAktivitaet = true;
		}

		if (name.equals(XMLBasisKonstanten.tagAktivitaetUnterbrechung)) {
			back = new UnterbrechungsAktivitaet();
		} else {
			back = new Aktivitaet(name, kategorie, nummer, projektAktivitaet);

			Element temp = document.getElement(aktivitaet,
					XMLBasisKonstanten.tagAktivitaetsAbrechnung);
			if (temp != null) {
				String abrechnung = temp
						.getAttribute(XMLBasisKonstanten.tagAktivitaetsAbrechnungKM);
				if (!abrechnung.equals("")) {
					back.setKm(Integer.parseInt(abrechnung));
				} else {
					abrechnung = temp
							.getAttribute(XMLBasisKonstanten.tagAktivitaetsAbrechnungEuro);
					back.setEuro(Double.parseDouble(abrechnung));
				}
			} else {
				if (document.getElement(aktivitaet,
						XMLBasisKonstanten.tagReisezeitAktiv) != null) {
					back.setReisezeit(true);
				} else if (document.getElement(aktivitaet,
						XMLBasisKonstanten.tagReisezeitPassiv) != null) {
					back.setReisezeit(false);
				}
			}
		}

		return back;
	}

	/**
	 * <p>
	 * Methode extrahiert aus einem XML-Dokument ein am Element
	 * <em>zeitspanne</em> hängedes <em>Startzeit</em>-Tag und gibt die
	 * darin gespeicherte Uhrzeit zurück.
	 * </p>
	 * 
	 * @param document
	 *            Das XML-Dokument
	 * @param zeitspanne
	 *            Das XML-Tag, unter dem die Startzeit gespeichert ist
	 * @return Die der Startzeit entsprechende Uhrzeit
	 */
	public Uhrzeit extrahiereStartzeit(DOMReader document, Element zeitspanne) {
		return extrahiereZeitpunkt(document, zeitspanne,
				XMLBasisKonstanten.tagStartzeit);
	}

	/**
	 * <p>
	 * Methode extrahiert aus einem XML-Dokument ein am Element
	 * <em>zeitspanne</em> hängedes <em>Endezeit</em>-Tag und gibt die
	 * darin gespeicherte Uhrzeit zurück.
	 * </p>
	 * 
	 * @param document
	 *            Das XML-Dokument
	 * @param zeitspanne
	 *            Das XML-Tag, unter dem die Endezeit gespeichert ist
	 * @return Die der Endezeit entsprechende Uhrzeit
	 */
	public Uhrzeit extrahiereEndezeit(DOMReader document, Element zeitspanne) {
		return extrahiereZeitpunkt(document, zeitspanne,
				XMLBasisKonstanten.tagEndezeit);
	}

	/**
	 * <p>
	 * Eigentliche Extraktion einer Start-/Endezeit
	 * </p>
	 * 
	 * @param document
	 *            Das XML-Dokument
	 * @param zeitspanne
	 *            Das XML-Tag, unter dem die Zeit gespeichert ist
	 * @param tagname
	 *            <em>tagStartzeit</em> oder <em>tagEndezeit</em>, je nach
	 *            Verwendung
	 * @return Die dem Zeitpunkt entsprechende Uhrzeit
	 */
	private Uhrzeit extrahiereZeitpunkt(DOMReader document, Element zeitspanne,
			String tagname) {
		Uhrzeit back = null;
		Element zeitpunkt = document.getElement(zeitspanne, tagname);
		if (zeitpunkt != null) {
			back = extrahiereUhrzeit(document, zeitpunkt);
		}
		return back;
	}

	/**
	 * <p>
	 * Extraktion des Uhrzeit-Tags unterhalb des Start- oder Endezeit-Tags, d.h.
	 * lesen der eigentlichen Uhrzeit
	 * </p>
	 * 
	 * @param document
	 *            Das XML-Dokument
	 * @param zeitspanne
	 *            Das Start-/Endezeit-Tag, unter dem die Uhrzeit gespeichert ist
	 * @return Die dem Zeitpunkt entsprechende Uhrzeit
	 */
	private Uhrzeit extrahiereUhrzeit(DOMReader document, Element zeitpunkt) {
		Element uhrzeit = document.getElement(zeitpunkt,
				XMLBasisKonstanten.tagUhrzeit);

		int stunde = Integer.parseInt(uhrzeit
				.getAttribute(XMLBasisKonstanten.tagUhrzeitStunde));
		int minute = Integer.parseInt(uhrzeit
				.getAttribute(XMLBasisKonstanten.tagUhrzeitMinute));

		return new Uhrzeit(stunde, minute);
	}
}
