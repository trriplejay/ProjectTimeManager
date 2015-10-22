package com.bosch.zeitverwaltung.xmlspeicher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.w3c.dom.Element;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.elemente.Tag;
import com.bosch.zeitverwaltung.elemente.Uhrzeit;
import com.bosch.zeitverwaltung.elemente.UnterbrechungsAktivitaet;
import com.bosch.zeitverwaltung.modell.ModellFactory;
import com.bosch.zeitverwaltung.modell.TagesBuchungen;
import com.bosch.zeitverwaltung.services.DOMReader;
import com.bosch.zeitverwaltung.speicher.BuchungenLaden;
import com.bosch.zeitverwaltung.xmlbasics.XMLBasisReader;
import com.bosch.zeitverwaltung.xmlbasics.XMLBasisWriter;

/**
 * <p>
 * Schnittstelle zum Einlesen und Verwalten von Buchungsdaten
 * </p>
 * 
 * @author Lars Geyer
 * @see BuchungenLaden
 * @see XMLBuchungsWriter
 * @see XMLBasisWriter
 */
public class XMLBuchungsReader implements BuchungenLaden {
	/**
	 * <p>
	 * Konstruktor, er stellt sicher, dass nur eine Instanz der Zeitverwaltung
	 * läuft.
	 * </p>
	 */
	XMLBuchungsReader() {
		new XMLDienste().pruefeLockDatei();
	}

	/**
	 * <p>
	 * Methode überprüft, ob für den übergebenen Tag bereits Buchungen
	 * gespeichert wurden.
	 * </p>
	 * 
	 * @param tag
	 *            Tag, für den erfragt wird, ob Buchungen gespeichert sind
	 * @return true, falls Buchungen für den übergebenen Tag gespeichert sind
	 */
	public boolean existierenBuchungen(Tag tag) {
		XMLDienste helfer = new XMLDienste();
		File datenSpeicher = helfer.getDatenVerzeichnis();
		String dateiname = helfer.erzeugeDateiname(tag);

		File datei = new File(datenSpeicher, dateiname);
		return datei.exists();
	}

	/**
	 * <p>
	 * Methode zum Einlesen der Buchungen eines Tages.
	 * </p>
	 * 
	 * @param tag
	 *            Tag, für den die Buchungen eingelesen werden sollen
	 * @return Ein TagesBuchungen-Modell mit den Buchungen des Tages
	 * @throws IOException
	 *             Falls Datei nicht existiert oder aus anderen Gründen nicht
	 *             darauf zugegriffen werden kann.
	 */
	public TagesBuchungen ladeBuchungen(Tag tag) throws IOException {
		TagesBuchungen buchungsModell = null;

		XMLDienste helfer = new XMLDienste();
		File datenSpeicher = helfer.getDatenVerzeichnis();
		String dateiname = helfer.erzeugeDateiname(tag);

		File datei = new File(datenSpeicher, dateiname);
		if (!datei.exists()) {
			throw new FileNotFoundException("Datei existiert nicht");
		}

		DOMReader reader = new DOMReader(datei);
		buchungsModell = ModellFactory.getFactory().erzeugeTagesBuchungen(tag);
		extrahiereBuchungen(reader, buchungsModell);

		return buchungsModell;
	}

	/**
	 * <p>
	 * Extrahiert den Buchungen-Tag der gespeicherten Daten. Die darin
	 * gespeicherten Buchungen werden ausgelesen und die entsprechenden
	 * <em>Buchung</em>-Objekte (inkl. Unterbrechungen) werden erzeugt und in
	 * einer Liste gesammelt.
	 * </p>
	 * 
	 * @param reader
	 *            Das XML-Dokument
	 * @param buchungsModell
	 *            Modell zum Speichern der Buchungen
	 */
	private void extrahiereBuchungen(DOMReader reader, TagesBuchungen buchungsModell) {
		Iterator<Element> iter = reader.getElementListe(null,
				XMLDienste.tagBuchung).iterator();
		while (iter.hasNext()) {
			extrahiereBuchung(reader, buchungsModell, iter.next());
		}
		buchungsModell.aenderungenGesichert();
	}

	/**
	 * <p>
	 * Extrahiere ein Buchung-Tag und erzeuge die dazu passende Buchung (<em>TagesEreignis</em>-Objekt).
	 * </p>
	 * 
	 * @param reader
	 *            Das XML-Dokument
	 * @param buchungsModell
	 *            Senke für Buchungen
	 * @param buchung
	 *            Der zu extrahierende Buchungs-Tag
	 * @return Das dem Tag entsprechende Buchungs-Objekt
	 */
	private void extrahiereBuchung(DOMReader reader,
			TagesBuchungen buchungsModell, Element buchung) {
		XMLBasisReader helfer = new XMLBasisReader();

		Aktivitaet aktivitaet = helfer.extrahiereAktivitaeten(reader, buchung)
				.iterator().next();

		int index = 0;
		if (aktivitaet instanceof UnterbrechungsAktivitaet) {
			index = buchungsModell.addUnterbrechung();
		}
		else {
			index = buchungsModell.addBuchung(aktivitaet);			
		}
		Uhrzeit startzeit = helfer.extrahiereStartzeit(reader, buchung);
		buchungsModell.aendereStartzeit(index, startzeit);
		Uhrzeit endezeit = helfer.extrahiereEndezeit(reader, buchung);
		if (endezeit != null) {
			buchungsModell.aendereEndezeit(index, endezeit);
		}
		Element temp = reader.getElement(buchung, XMLDienste.tagKommentar);
		if (temp != null) {
			buchungsModell.aendereKommentar(index, temp.getTextContent());
		}
	}
}
