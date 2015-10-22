package com.bosch.zeitverwaltung.xmlconfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.w3c.dom.Element;

import com.bosch.zeitverwaltung.configuration.KonfigurationsDatenWriter;
import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.elemente.AktivitaetsSortierung;
import com.bosch.zeitverwaltung.services.DOMWriter;
import com.bosch.zeitverwaltung.xmlbasics.XMLBasisWriter;

/**
 * <p>
 * Speicherung von Konfigurationsdaten in XML-Datei
 * </p>
 * 
 * @author Lars Geyer
 * @see KonfigurationsDatenWriter
 * @see XMLKonfigurationsDatenReader
 * @see XMLBasisWriter
 */
public class XMLKonfigurationsDatenWriter extends KonfigurationsDatenWriter {
	/**
	 * <p>
	 * Konstruktor, definiert die Konfigurationsdatei
	 * </p>
	 */
	XMLKonfigurationsDatenWriter() {
		super();
		setKonfigurationsDatei(XMLKonstanten.Konfiguration);
	}

	/**
	 * <p>
	 * Methode zum Schreiben der Konfigurationsdaten
	 * </p>
	 * 
	 * @throws IOException
	 *             Bei Dateizugriffsproblemen
	 */
	public void schreibeKonfigurationsDaten() throws IOException {
		File outputDatei = getKonfigurationsDatei();
		if (outputDatei.exists()) {
			outputDatei.delete();
		}

		DOMWriter confDatei = new DOMWriter(outputDatei,
				XMLKonstanten.tagWurzel);
		erzeugeAktivitaetenElement(confDatei);
		erzeugeMinutenDeltaElement(confDatei);
		confDatei.speichereDatei();
	}

	/**
	 * <p>
	 * Erzeuge das Aktivitäten-Tag
	 * </p>
	 * 
	 * @param configuration
	 *            Das XML-Dokument
	 */
	private void erzeugeAktivitaetenElement(DOMWriter configuration) {
		XMLBasisWriter helfer = new XMLBasisWriter();
		Element aktivitaeten = configuration.neuesElement(null,
				XMLKonstanten.tagAktivitaeten);
		erzeugeSortierungsElement(configuration, aktivitaeten);
		Iterator<Aktivitaet> iter = this.getAktivitaeten().iterator();
		while (iter.hasNext()) {
			helfer.erzeugeAktivitaetsElement(configuration, aktivitaeten, iter
					.next());
		}
	}

	/**
	 * <p>
	 * Erzeuge das Sortierungs-Tag
	 * </p>
	 * 
	 * @param configuration
	 *            Das XML-Dokument
	 * @param vater
	 *            Das Aktivitäten-Tag
	 */
	private void erzeugeSortierungsElement(DOMWriter configuration,
			Element vater) {
		Element sortierung = configuration.neuesElement(vater,
				XMLKonstanten.tagAktivitaetsSortierung);
		if (this.getSortierung() == AktivitaetsSortierung.AktivitaetsSortierungNachKategorie) {
			configuration.neuesElement(sortierung,
					XMLKonstanten.tagAktivitaetsSortierungKategorie);
		} else if (this.getSortierung() == AktivitaetsSortierung.AktivitaetsSortierungNachName) {
			configuration.neuesElement(sortierung,
					XMLKonstanten.tagAktivitaetsSortierungName);
		} else if (this.getSortierung() == AktivitaetsSortierung.AktivitaetsSortierungNachBuchungsnummer) {
			configuration.neuesElement(sortierung,
					XMLKonstanten.tagAktivitaetsSortierungNummer);
		}
	}

	// /**
	// * <p>
	// * Erzeugt die Spezial-Aktivitaets-Informationen
	// * </p>
	// *
	// * @param configuration
	// * Das XML-Dokument
	// */
	// private void erzeugeSpezialAktivitaetenElement(DOMWriter configuration) {
	// Element spezielle = configuration.neuesElement(null,
	// XMLKonstanten.tagSpezialAktivitaeten);
	// Iterator<Aktivitaet> iter = this.getSpezialAktivitaeten().values()
	// .iterator();
	// while (iter.hasNext()) {
	// erzeugeSpezialAktivitaetElement(configuration, spezielle, iter
	// .next());
	// }
	// }
	//
	// /**
	// * <p>
	// * Erzeugt ein Spezial-Aktivitaets-Tag
	// * </p>
	// *
	// * @param configuration
	// * Das XML-Dokument
	// * @param vater
	// * Das Vater-Tag, unter dem das Element eingehängt werden soll
	// * @param aktivitaet
	// * Die zu speichernde Aktivität
	// */
	// private void erzeugeSpezialAktivitaetElement(DOMWriter configuration,
	// Element vater, Aktivitaet aktivitaet) {
	// Element spezielle = configuration.neuesElement(vater,
	// XMLKonstanten.tagSpezialAktivitaet);
	// spezielle.setAttribute(XMLKonstanten.tagSpezialAktivitaetTyp,
	// aktivitaet.getAktivitaet());
	// if (!aktivitaet.getBuchungsNummer().equals(aktivitaet.getAktivitaet())) {
	// spezielle.setAttribute(XMLKonstanten.tagSpezialAktivitaetNummer,
	// aktivitaet.getBuchungsNummer());
	// }
	//
	// }

	/**
	 * <p>
	 * Erzeuge Minuten-Delta-Tag
	 * </p>
	 * 
	 * @param configuration
	 *            Das XML-Dokument
	 */
	private void erzeugeMinutenDeltaElement(DOMWriter configuration) {
		Element delta = configuration.neuesElement(null,
				XMLKonstanten.tagMinutenDelta);
		delta.setAttribute(XMLKonstanten.tagMinutenDeltaDauer, Integer
				.toString(this.getMinutenDelta().getMinutenDelta()));
	}
}
