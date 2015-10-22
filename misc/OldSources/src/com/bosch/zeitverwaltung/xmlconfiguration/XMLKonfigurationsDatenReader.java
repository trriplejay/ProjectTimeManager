package com.bosch.zeitverwaltung.xmlconfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import org.w3c.dom.Element;

import com.bosch.zeitverwaltung.configuration.KonfigurationsDatenReader;
import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.elemente.AktivitaetsSortierung;
import com.bosch.zeitverwaltung.elemente.MinutenDelta;
import com.bosch.zeitverwaltung.services.DOMReader;
import com.bosch.zeitverwaltung.xmlbasics.XMLBasisReader;
import com.bosch.zeitverwaltung.xmlbasics.XMLBasisWriter;

/**
 * <p>
 * XML-Parser zum Einlesen von Konfigurationsdaten.
 * </p>
 * 
 * @author Lars Geyer
 * @see KonfigurationsDatenReader
 * @see XMLKonfigurationsDatenWriter
 * @see XMLBasisWriter
 */
public class XMLKonfigurationsDatenReader extends KonfigurationsDatenReader {
	/**
	 * <p>
	 * Konstruktor. Er definiert die Konfigurationsdatei
	 * </p>
	 */
	XMLKonfigurationsDatenReader() {
		super();
		setKonfigurationsDatei(XMLKonstanten.Konfiguration);
	}

	/**
	 * <p>
	 * Methode zum Einlesen der Konfigurationsdaten.
	 * </p>
	 * 
	 * @throws IOException
	 *             Bei Dateizugriffsproblemen
	 */
	protected void leseKonfigurationsDaten() throws IOException {
		DOMReader confDatei;

		File configFile = this.getKonfigurationsDatei();
		if (configFile.exists()) {
			confDatei = new DOMReader(configFile);
		} else {
			confDatei = new DOMReader(this.getBasisKonfiguration());
		}

		extrahiereAktivitaeten(confDatei);
		extrahiereMinutenDelta(confDatei);
	}

	/**
	 * <p>
	 * Extrahiere die Aktivitäten aus der XML-Datei
	 * </p>
	 * 
	 * @param configuration
	 *            Das XML-Dokument
	 */
	private void extrahiereAktivitaeten(DOMReader configuration) {
		// Extrahiere das Aktivitäten-Tag
		Collection<Aktivitaet> aktivitaeten = new LinkedList<Aktivitaet>();
		Element aktivitaetenElement = configuration.getElement(null,
				XMLKonstanten.tagAktivitaeten);
		XMLBasisReader helfer = new XMLBasisReader();

		// Gehe durch die Liste der Aktivitäts-Tags und extrahiere die Aktivität
		if (aktivitaetenElement != null) {
			extrahiereAktivitaetenSortierung(configuration, aktivitaetenElement);
			aktivitaeten = helfer.extrahiereAktivitaeten(configuration,
					aktivitaetenElement);
		}

		this.setAktivitaeten(aktivitaeten);
	}

	/**
	 * <p>
	 * Extrahiere aus dem Aktivitäten-Tag die Sortierung, mit der die
	 * Aktivitäten sortiert sein sollen
	 * </p>
	 * 
	 * @param configuration
	 *            Das XML-Dokument
	 * @param aktivitaeten
	 *            Das Aktivitäten-Tag unter dem die Sortierung gespeichert sein
	 *            muss
	 */
	private void extrahiereAktivitaetenSortierung(DOMReader configuration,
			Element aktivitaeten) {
		Element sortierung = configuration.getElement(aktivitaeten,
				XMLKonstanten.tagAktivitaetsSortierung);
		if (sortierung != null) {
			if (configuration.getElement(sortierung,
					XMLKonstanten.tagAktivitaetsSortierungKategorie) != null) {
				this.setSortierung(AktivitaetsSortierung.AktivitaetsSortierungNachKategorie);
			} else if (configuration.getElement(sortierung,
					XMLKonstanten.tagAktivitaetsSortierungName) != null) {
				this.setSortierung(AktivitaetsSortierung.AktivitaetsSortierungNachName);
			} else if (configuration.getElement(sortierung,
					XMLKonstanten.tagAktivitaetsSortierungNummer) != null) {
				this.setSortierung(AktivitaetsSortierung.AktivitaetsSortierungNachBuchungsnummer);
			}
		}
	}

	// /**
	// * <p>
	// * Extrahiere die Spezial-Aktivitäten aus der XML-Datei
	// * </p>
	// *
	// * @param configuration
	// * Das XML-Dokument
	// */
	// private void extrahiereSpezialAktivitaeten(DOMReader configuration) {
	// Map<String, Aktivitaet> aktivitaeten = new HashMap<String, Aktivitaet>();
	// Element spezial = configuration.getElement(null,
	// XMLKonstanten.tagSpezialAktivitaeten);
	// if (spezial != null) {
	// Collection<Element> spezielle = configuration.getElementListe(
	// spezial, XMLKonstanten.tagSpezialAktivitaet);
	// Iterator<Element> iter = spezielle.iterator();
	// while (iter.hasNext()) {
	// Element akt = iter.next();
	// String typ = akt
	// .getAttribute(XMLKonstanten.tagSpezialAktivitaetTyp);
	// String nummer = akt
	// .getAttribute(XMLKonstanten.tagSpezialAktivitaetNummer);
	// Aktivitaet aktivitaet = new Aktivitaet(typ, typ, (!nummer
	// .equals("")) ? nummer : typ, false);
	// aktivitaeten.put(typ, aktivitaet);
	// }
	// }
	// super.setSpezialAktivitaeten(aktivitaeten);
	// }

	/**
	 * <p>
	 * Extrahiere das Minuten-Delta aus der XML-Datei
	 * </p>
	 * 
	 * @param configuration
	 *            Das XML-Dokument
	 */
	private void extrahiereMinutenDelta(DOMReader configuration) {
		Element minutenDelta = configuration.getElement(null,
				XMLKonstanten.tagMinutenDelta);
		if (minutenDelta != null) {
			int delta = Integer.parseInt(minutenDelta
					.getAttribute(XMLKonstanten.tagMinutenDeltaDauer));
			this.setMinutenDelta(MinutenDelta.get(delta));
		}
	}
}
