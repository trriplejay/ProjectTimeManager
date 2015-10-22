package com.bosch.zeitverwaltung.factory;

import java.io.IOException;

import junit.framework.TestCase;

public class FactoryKonfigurationTest extends TestCase {

	public final void testFactoryKonfiguration() {
		try {
			FactoryKonfiguration testee = new FactoryKonfiguration();
			assertEquals(
					"com.bosch.zeitverwaltung.modell_impl.StandardModellFactory",
					testee.getModellFactoryKlassenname());
			assertEquals(
					"com.bosch.zeitverwaltung.swing_view.StandardUIFactory",
					testee.getUIFactoryKlassenname());
			assertEquals(
					"com.bosch.zeitverwaltung.xmlconfiguration.XMLKonfigurationsFactory",
					testee.getKonfigurationFactoryKlassenname());
			assertEquals(
					"com.bosch.zeitverwaltung.xmlspeicher.XMLSpeicherFactory",
					testee.getSpeicherFactoryKlassenanme());
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}

}
