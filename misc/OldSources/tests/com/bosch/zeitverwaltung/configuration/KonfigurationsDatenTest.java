package com.bosch.zeitverwaltung.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import junit.framework.TestCase;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.elemente.AktivitaetsSortierung;
import com.bosch.zeitverwaltung.elemente.MinutenDelta;
import com.bosch.zeitverwaltung.elemente.Uhrzeit;
import com.bosch.zeitverwaltung.services.ApplikationsDatenVerzeichnis;
import com.bosch.zeitverwaltung.xmlconfiguration.XMLKonfigurationsFactory;

public class KonfigurationsDatenTest extends TestCase {
	public final void testKonfigurationsDatenHandling() {
		KonfigurationsFactory confFactory = KonfigurationsFactory.getFactory();
		if (confFactory == null) {
			confFactory = new XMLKonfigurationsFactory();
		}

		File confDatei = getKonfigurationsFile();
		File confDateiBck = null;
		if (confDatei.exists()) {
			confDateiBck = confDatei;
			File renName = new File(confDatei.getParentFile(),
					"zvw_configuration.xml.bck");
			confDateiBck.renameTo(renName);
		}
		KonfigurationsDatenWriter writer = confFactory
				.getKonfigurationsWriter();
		KonfigurationsDatenReader reader = confFactory
				.getKonfigurationsReader();

		Uhrzeit.setMinutenDelta(MinutenDelta.MinutenDeltaDreiMinuten);
		try {
			reader.leseKonfigurationsDaten();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}

		assertEquals(MinutenDelta.MinutenDeltaDreiMinuten, reader.getMinutenDelta());
		assertTrue(reader.getSortierung() == AktivitaetsSortierung.AktivitaetsSortierungNachKategorie);
		Iterator<Aktivitaet> iter1 = reader.getAktivitaeten().iterator();
		assertTrue(iter1.hasNext());
		assertEquals("Verwaltung", iter1.next().getAktivitaet());
		assertTrue(iter1.hasNext());
		assertEquals("Weiterbildung", iter1.next().getAktivitaet());
		assertFalse(iter1.hasNext());

		writer.setSortierung(reader.getSortierung());
		writer.setAktivitaeten(reader.getAktivitaeten());
		writer.setMinutenDelta(reader.getMinutenDelta());

		try {
			writer.schreibeKonfigurationsDaten();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}

		try {
			reader.leseKonfigurationsDaten();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}

		assertEquals(MinutenDelta.MinutenDeltaDreiMinuten, reader.getMinutenDelta());
		assertEquals(reader.getSortierung(), AktivitaetsSortierung.AktivitaetsSortierungNachKategorie);
		iter1 = reader.getAktivitaeten().iterator();
		assertTrue(iter1.hasNext());
		assertEquals("Verwaltung", iter1.next().getAktivitaet());
		assertTrue(iter1.hasNext());
		assertEquals("Weiterbildung", iter1.next().getAktivitaet());
		assertFalse(iter1.hasNext());

		confDatei.deleteOnExit();
		if (confDateiBck != null) {
			confDateiBck.renameTo(confDatei);
		}
	}

	public File getKonfigurationsFile() {
		File back = null;

		File appdataVerzeichnis = new ApplikationsDatenVerzeichnis()
				.getAppdataVerzeichnis();
		if (appdataVerzeichnis != null) {
			back = new File(appdataVerzeichnis, "zvw_configuration.xml");
		}

		return back;

	}
}
