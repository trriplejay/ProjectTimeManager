package com.bosch.zeitverwaltung.speicher;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.elemente.MinutenDelta;
import com.bosch.zeitverwaltung.elemente.Tag;
import com.bosch.zeitverwaltung.elemente.Uhrzeit;
import com.bosch.zeitverwaltung.modell.AktivitaetenVerwaltung;
import com.bosch.zeitverwaltung.modell.ModellFactory;
import com.bosch.zeitverwaltung.modell.TagesBuchungen;
import com.bosch.zeitverwaltung.modell_impl.StandardModellFactory;
import com.bosch.zeitverwaltung.services.ApplikationsDatenVerzeichnis;
import com.bosch.zeitverwaltung.xmlspeicher.XMLSpeicherFactory;

public class SpeicherBuchungenTest extends TestCase {

	public final void testSpeicher() {
		SpeicherFactory factory = SpeicherFactory.getFactory();
		if (factory == null) {
			factory = new XMLSpeicherFactory();
		}
		ModellFactory modellFactory = ModellFactory.getFactory();
		if (modellFactory == null) {
			modellFactory = new StandardModellFactory();
		}

		Uhrzeit.setMinutenDelta(MinutenDelta.MinutenDeltaEineMinute);

		Tag pruefTag = new Tag(2006, 2, 5);
		TagesBuchungen buchungen = modellFactory
				.erzeugeTagesBuchungen(pruefTag);
		TagesBuchungen gelesen = null;
		Aktivitaet aktivitaet1 = new Aktivitaet("Aktivitaet1", "Aktivitaet1",
				"0815", true);
		Aktivitaet aktivitaet2 = new Aktivitaet("Aktivitaet2", "Aktivitaet2",
				"4711", true);
		AktivitaetenVerwaltung verwaltung = ModellFactory.getFactory()
				.getAktivitaetenVerwaltung();
		try {
			verwaltung.elementHinzufuegen(aktivitaet1);
			verwaltung.elementHinzufuegen(aktivitaet2);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		Uhrzeit test1 = new Uhrzeit(8, 24);
		Uhrzeit test2 = new Uhrzeit(10, 15);
		Uhrzeit test3 = new Uhrzeit(20, 15);
		Uhrzeit test4 = new Uhrzeit(21, 15);

		buchungen.addBuchung(aktivitaet1);
		buchungen.aendereStartzeit(0, test1);
		buchungen.aendereEndezeit(0, test2);
		buchungen.addUnterbrechung();
		buchungen.aendereEndezeit(1, test3);
		buchungen.addBuchung(aktivitaet2);
		buchungen.aendereEndezeit(2, test4);

		BuchungenSpeichern writer = factory.getBuchungsWriter();
		BuchungenLaden reader = factory.getBuchungsReader();

		try {
			writer.speichereBuchungen(buchungen);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(reader.existierenBuchungen(pruefTag));
		try {
			gelesen = reader.ladeBuchungen(pruefTag);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}

		assertEquals(pruefTag, gelesen.getBuchungsTag());
		assertEquals(3, gelesen.getAnzahlBuchungen());
		assertEquals(aktivitaet1, gelesen.getBuchung(0).getAktivitaet());
		assertEquals(test1, gelesen.getBuchung(0).getStartZeit());
		assertEquals(test2, gelesen.getBuchung(0).getEndeZeit());
		assertTrue(gelesen.getBuchung(1).getAktivitaet().getBuchungsNummer()
				.equals("Unterbrechung"));
		assertEquals(test2, gelesen.getBuchung(1).getStartZeit());
		assertEquals(test3, gelesen.getBuchung(1).getEndeZeit());
		assertEquals(aktivitaet2, gelesen.getBuchung(2).getAktivitaet());
		assertEquals(test3, gelesen.getBuchung(2).getStartZeit());
		assertEquals(test4, gelesen.getBuchung(2).getEndeZeit());

		File appdata = new ApplikationsDatenVerzeichnis()
				.getAppdataVerzeichnis();
		File buchungsVz = new File(appdata, "Buchungen");
		File buchungsXML = new File(buchungsVz, "Buchungen_20060205.xml");
		assertTrue(buchungsXML.exists());
		buchungsXML.deleteOnExit();
	}
}
