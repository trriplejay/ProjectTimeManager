package com.bosch.zeitverwaltung.xmlspeicher;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.Element;

import com.bosch.zeitverwaltung.elemente.Buchung;
import com.bosch.zeitverwaltung.modell.TagesBuchungen;
import com.bosch.zeitverwaltung.services.DOMWriter;
import com.bosch.zeitverwaltung.services.DateiKopieren;
import com.bosch.zeitverwaltung.speicher.BuchungenSpeichern;
import com.bosch.zeitverwaltung.xmlbasics.XMLBasisWriter;

/**
 * <p>
 * Schnittstelle Speichern von Buchungsdaten
 * </p>
 * 
 * @author Lars Geyer
 * @see BuchungenSpeichern
 * @see XMLBuchungsReader
 * @see XMLBasisWriter
 */
public class XMLBuchungsWriter implements BuchungenSpeichern {
	/**
	 * <p>
	 * Konstruktor, er stellt sicher, dass nur eine Instanz der Zeitverwaltung
	 * läuft.
	 * </p>
	 */
	XMLBuchungsWriter() throws IllegalArgumentException {
		new XMLDienste().pruefeLockDatei();
	}

	/**
	 * <p>
	 * Methode zum Speichern der Buchungen eines Tages-Modells in einer
	 * XML-Datei
	 * </p>
	 * 
	 * @param buchungen
	 *            Modell der Buchungen des zu speichernden Tages
	 */
	public void speichereBuchungen(TagesBuchungen buchungen) throws IOException {
		XMLDienste helfer = new XMLDienste();
		File datenSpeicher = helfer.getDatenVerzeichnis();
		String dateiname = helfer.erzeugeDateiname(buchungen.getBuchungsTag());

		File datei = new File(datenSpeicher, dateiname);
		File backup = new File(datenSpeicher, dateiname + ".bck");

		if (datei.exists()) {
			new DateiKopieren().kopieren(datei, backup);
		}

		try {
			DOMWriter writer = new DOMWriter(datei, XMLDienste.tagWurzel);

			for (int i = 0; i < buchungen.getAnzahlBuchungen(); i++) {
				erzeugeBuchungsElement(writer, buchungen.getBuchung(i));
			}

			writer.speichereDatei();
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new IOException(e.getLocalizedMessage());
		}
	}

	/**
	 * <p>
	 * Erzeuge ein Buchung-Tag für eine Buchung.
	 * </p>
	 * 
	 * @param writer
	 *            Das XML-Dokument
	 * @param buchung
	 *            Die zu speichernde Buchung
	 */
	private void erzeugeBuchungsElement(DOMWriter writer, Buchung buchung) {
		Element buchungsElement = writer.neuesElement(null,
				XMLDienste.tagBuchung);
		XMLBasisWriter helfer = new XMLBasisWriter();

		helfer.erzeugeAktivitaetsElement(writer, buchungsElement, buchung
				.getAktivitaet());
		helfer.erzeugeStartzeitElement(writer, buchungsElement, buchung
				.getStartZeit());
		if (buchung.getEndeZeit() != null) {
			helfer.erzeugeEndezeitElement(writer, buchungsElement, buchung
					.getEndeZeit());
		}
		if (!buchung.getKommentar().equals("")) {
			Element temp = writer.neuesElement(buchungsElement,
					XMLDienste.tagKommentar);
			temp.setTextContent(buchung.getKommentar());
		}
	}
}
