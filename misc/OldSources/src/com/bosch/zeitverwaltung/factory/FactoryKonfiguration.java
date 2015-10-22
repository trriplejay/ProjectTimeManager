package com.bosch.zeitverwaltung.factory;

import java.io.IOException;
import java.util.Iterator;

import org.w3c.dom.Element;

import com.bosch.zeitverwaltung.services.ClasspathSuche;
import com.bosch.zeitverwaltung.services.DOMReader;

/**
 * <p>
 * Klasse ist dafür zuständig, die Klassennamen der konkreten Factories aus den
 * Konfigurationsdateien zu ermitteln.
 * </p>
 * 
 * <p>
 * Es gibt vier Typen von Factories, die mittels vier String-Konstanten
 * unterschieden werden können. Vier Methoden geben jeweils den Namen eines
 * Factory-Typs zurück.
 * </p>
 * 
 * @author Lars Geyer
 * @see ZeitverwaltungFactory
 */
final class FactoryKonfiguration {
	// Die vier Factory-Typen, in den Konfigurationsdateien werden diese
	// Konstanten zur Typisierung
	// verwendet.
	private static final String modellFactoryName = "ModellFactory";

	private static final String uiFactoryName = "UIFactory";

	private static final String configurationFactoryName = "KonfigurationsFactory";

	private static final String speicherFactoryName = "SpeicherFactory";

	// Name einer Konfigurationsdatei. Er ist typischerweise im Classpath im
	// Package der realisierenden
	// Factory-Klasse enthalten.
	private static final String factoryConfName = "zeitverwaltungfactory.xml";

	// Liste aller Factory-Klassennamen
	private String modellFactoryKlasse = null;

	private String uiFactoryKlasse = null;

	private String configurationFactoryKlasse = null;

	private String speicherFactoryKlasse = null;

	// Lazy Initialisierung, Daten werden geladen, sobald erster Klassennamen
	// abgefragt wird
	private boolean initialisiert = false;

	/**
	 * <p>
	 * Gibt den Klassennamen der Modell-Factory zurück
	 * </p>
	 * 
	 * @return Klassennamen der Modell-Factory
	 * @throws IOException
	 *             Bei Dateizugriffproblemen
	 */
	String getModellFactoryKlassenname() throws IOException {
		if (!initialisiert) {
			ladeDaten();
		}

		return modellFactoryKlasse;
	}

	/**
	 * <p>
	 * Gibt den Klassennamen der UI-Factory zurück
	 * </p>
	 * 
	 * @return Klassennamen der UI-Factory
	 * @throws IOException
	 *             Bei Dateizugriffproblemen
	 */
	String getUIFactoryKlassenname() throws IOException {
		if (!initialisiert) {
			ladeDaten();
		}

		return uiFactoryKlasse;
	}

	/**
	 * <p>
	 * Gibt den Klassennamen der Konfigurations-Factory zurück
	 * </p>
	 * 
	 * @return Klassennamen der Konfigurations-Factory
	 * @throws IOException
	 *             Bei Dateizugriffproblemen
	 */
	String getKonfigurationFactoryKlassenname() throws IOException {
		if (!initialisiert) {
			ladeDaten();
		}

		return configurationFactoryKlasse;
	}

	/**
	 * <p>
	 * Gibt den Klassennamen der Speicher-Factory zurück
	 * </p>
	 * 
	 * @return Klassennamen der Speicher-Factory
	 * @throws IOException
	 *             Bei Dateizugriffproblemen
	 */
	String getSpeicherFactoryKlassenanme() throws IOException {
		if (!initialisiert) {
			ladeDaten();
		}

		return speicherFactoryKlasse;
	}

	/**
	 * <p>
	 * Sucht nach Konfigurationsdateien im Classpath. Danach liest es diese aus
	 * und speichert die Klassennamen der Factories.
	 * </p>
	 * 
	 * @throws IOException
	 *             Bei Dateizugriffsproblemen
	 */
	private void ladeDaten() throws IOException {
		ClasspathSuche factoryDateien = new ClasspathSuche(factoryConfName);

		while (factoryDateien.hasFiles()) {
			DOMReader aktDatei = new DOMReader(factoryDateien.nextInputStream());
			Iterator<Element> iter = aktDatei.getElementListe(null, "factory")
					.iterator();
			while (iter.hasNext()) {
				Element aktElement = iter.next();
				Element typElement = aktDatei.getElement(aktElement, "typ");
				Element klassenElement = aktDatei.getElement(aktElement,
						"klasse");

				String name = typElement.getTextContent();
				String klasse = klassenElement.getTextContent();

				if (name.equals(modellFactoryName)) {
					modellFactoryKlasse = klasse;
				} else if (name.equals(uiFactoryName)) {
					uiFactoryKlasse = klasse;
				} else if (name.equals(configurationFactoryName)) {
					configurationFactoryKlasse = klasse;
				} else if (name.equals(speicherFactoryName)) {
					speicherFactoryKlasse = klasse;
				}
			}
		}
	}
}
