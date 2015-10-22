package com.bosch.zeitverwaltung.factory;

import java.io.IOException;

/**
 * <p>
 * Zentrale Factory der Zeitverwaltung. Sie erzeugt dynamisch die konkreten
 * Factories aus den Klassennamen. Die Klassennamen müssen in
 * Konfigurationsdateien enthalten sein, die mittels der Klasse
 * <em>FactoryKonfiguration</em> ausgelesen werden. Nach der Erzeugung können
 * die Factories über den in den abstrakten Factories definierten Mechanismus
 * zugegriffen werden.
 * </p>
 * 
 * @author Lars Geyer
 * @see FactoryKonfiguration
 * @see com.bosch.zeitverwaltung.modell.ModellFactory
 * @see com.bosch.zeitverwaltung.view.UIFactory
 * @see com.bosch.zeitverwaltung.configuration.KonfigurationsFactory
 * @see com.bosch.zeitverwaltung.speicher.SpeicherFactory
 */
public final class ZeitverwaltungFactory {
	/**
	 * <p>
	 * Der Konstruktor erzeugt für jeden Factory-Typ eine Factory, auf die über
	 * deren statische <em>getFactory</em>-Methode danach zugegriffen werden
	 * kann.
	 * </p>
	 * 
	 * @throws InstantiationException
	 *             Probleme bei der dynamischen Erzeugung
	 * @throws IllegalAccessException
	 *             Probleme bei der dynamischen Erzeugung
	 * @throws ClassNotFoundException
	 *             Probleme bei der dynamischen Erzeugung
	 * @throws IOException
	 *             Probleme beim Zugriff auf die Konfigurationsdateien
	 */
	public ZeitverwaltungFactory() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, IOException {
		FactoryKonfiguration factoryNamen = new FactoryKonfiguration();

		setModellFactoryClass(factoryNamen.getModellFactoryKlassenname());
		setUIFactoryClass(factoryNamen.getUIFactoryKlassenname());
		setConfigurationFactoryClass(factoryNamen
				.getKonfigurationFactoryKlassenname());
		setSpeicherFactoryClass(factoryNamen.getSpeicherFactoryKlassenanme());
	}

	/**
	 * <p>
	 * Erzeuge eine Modell-Factory
	 * </p>
	 * 
	 * @param modellFactoryClass
	 *            Name der Modell-Factory-Klasse
	 * @throws InstantiationException
	 *             Probleme bei der dynamischen Erzeugung
	 * @throws IllegalAccessException
	 *             Probleme bei der dynamischen Erzeugung
	 * @throws ClassNotFoundException
	 *             Probleme bei der dynamischen Erzeugung
	 */
	private void setModellFactoryClass(String modellFactoryClass)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		if (modellFactoryClass != null) {
			Class<?> klasse = getClass().getClassLoader().loadClass(
					modellFactoryClass);
			klasse.newInstance();
		}
	}

	/**
	 * <p>
	 * Erzeuge eine UI-Factory
	 * </p>
	 * 
	 * @param uiFactoryClass
	 *            Name der UI-Factory-Klasse
	 * @throws InstantiationException
	 *             Probleme bei der dynamischen Erzeugung
	 * @throws IllegalAccessException
	 *             Probleme bei der dynamischen Erzeugung
	 * @throws ClassNotFoundException
	 *             Probleme bei der dynamischen Erzeugung
	 */
	private void setUIFactoryClass(String uiFactoryClass)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		if (uiFactoryClass != null) {
			Class<?> klasse = getClass().getClassLoader().loadClass(
					uiFactoryClass);
			klasse.newInstance();
		}
	}

	/**
	 * <p>
	 * Erzeuge eine Konfigurations-Factory
	 * </p>
	 * 
	 * @param configurationFactoryClass
	 *            Name der Konfigurations-Factory-Klasse
	 * @throws InstantiationException
	 *             Probleme bei der dynamischen Erzeugung
	 * @throws IllegalAccessException
	 *             Probleme bei der dynamischen Erzeugung
	 * @throws ClassNotFoundException
	 *             Probleme bei der dynamischen Erzeugung
	 */
	private void setConfigurationFactoryClass(String configurationFactoryClass)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		if (configurationFactoryClass != null) {
			Class<?> klasse = getClass().getClassLoader().loadClass(
					configurationFactoryClass);
			klasse.newInstance();
		}
	}

	/**
	 * <p>
	 * Erzeuge eine Speicher-Factory
	 * </p>
	 * 
	 * @param speicherFactoryClass
	 *            Name der Speicher-Factory-Klasse
	 * @throws InstantiationException
	 *             Probleme bei der dynamischen Erzeugung
	 * @throws IllegalAccessException
	 *             Probleme bei der dynamischen Erzeugung
	 * @throws ClassNotFoundException
	 *             Probleme bei der dynamischen Erzeugung
	 */
	private void setSpeicherFactoryClass(String speicherFactoryClass)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		if (speicherFactoryClass != null) {
			Class<?> klasse = getClass().getClassLoader().loadClass(
					speicherFactoryClass);
			klasse.newInstance();
		}
	}
}
