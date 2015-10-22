package com.bosch.zeitverwaltung.modell;

import java.util.Date;

import junit.framework.TestCase;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.elemente.MinutenDelta;
import com.bosch.zeitverwaltung.elemente.Tag;
import com.bosch.zeitverwaltung.elemente.Uhrzeit;
import com.bosch.zeitverwaltung.modell_impl.StandardModellFactory;

public class TagesBuchungenTest extends TestCase {
	public final void testAddBuchung() {
		ModellFactory modellFactory = ModellFactory.getFactory();
		if (modellFactory == null) {
			modellFactory = new StandardModellFactory();
		}
		TagesBuchungen testee = modellFactory.erzeugeTagesBuchungen(new Tag(
				new Date()));
		Uhrzeit.setMinutenDelta(MinutenDelta.MinutenDeltaEineMinute);

		Aktivitaet aktivitaet1 = new Aktivitaet("Aktivitaet1", "Aktivitaet1",
				"0815", false);
		Aktivitaet aktivitaet2 = new Aktivitaet("Aktivitaet2", "Aktivitaet1",
				"4711", false);

		Uhrzeit test1 = new Uhrzeit(6, 24);
		Uhrzeit test2 = new Uhrzeit(20, 15);
		Uhrzeit test3 = new Uhrzeit(21, 15);
		Uhrzeit test4 = new Uhrzeit(10, 15);
		Uhrzeit test5 = new Uhrzeit(22, 15);

		assertFalse(testee.veraendert());

		testee.addBuchung(aktivitaet1);
		testee.aendereStartzeit(0, test1);
		assertEquals(1, testee.getAnzahlBuchungen());
		assertEquals("06:24", testee.getBuchung(0).getStartZeit().toString());
		assertNull(testee.getBuchung(0).getEndeZeit());
		assertEquals("Aktivitaet1", testee.getBuchung(0).getAktivitaet()
				.getAktivitaet());

		assertTrue(testee.veraendert());
		testee.aenderungenGesichert();
		assertFalse(testee.veraendert());

		testee.aendereEndezeit(0, test2);
		testee.addBuchung(aktivitaet2);
		assertEquals(2, testee.getAnzahlBuchungen());
		assertEquals("06:24", testee.getBuchung(0).getStartZeit().toString());
		assertEquals("20:15", testee.getBuchung(0).getEndeZeit().toString());
		assertEquals("Aktivitaet1", testee.getBuchung(0).getAktivitaet()
				.getAktivitaet());
		assertEquals("20:15", testee.getBuchung(1).getStartZeit().toString());
		assertNull(testee.getBuchung(1).getEndeZeit());
		assertEquals("Aktivitaet2", testee.getBuchung(1).getAktivitaet()
				.getAktivitaet());

		assertTrue(testee.veraendert());

		testee.aendereEndezeit(1, test3);
		testee.addBuchung(aktivitaet2);
		assertEquals(3, testee.getAnzahlBuchungen());
		assertEquals("06:24", testee.getBuchung(0).getStartZeit().toString());
		assertEquals("20:15", testee.getBuchung(0).getEndeZeit().toString());
		assertEquals("Aktivitaet1", testee.getBuchung(0).getAktivitaet()
				.getAktivitaet());
		assertEquals("20:15", testee.getBuchung(1).getStartZeit().toString());
		assertEquals("21:15", testee.getBuchung(1).getEndeZeit().toString());
		assertEquals("Aktivitaet2", testee.getBuchung(1).getAktivitaet()
				.getAktivitaet());
		assertEquals("21:15", testee.getBuchung(2).getStartZeit().toString());
		assertNull(testee.getBuchung(2).getEndeZeit());
		assertEquals("Aktivitaet2", testee.getBuchung(2).getAktivitaet()
				.getAktivitaet());

		testee.addBuchung(aktivitaet1);
		assertEquals(3, testee.getAnzahlBuchungen());
		assertEquals("06:24", testee.getBuchung(0).getStartZeit().toString());
		assertEquals("20:15", testee.getBuchung(0).getEndeZeit().toString());
		assertEquals("Aktivitaet1", testee.getBuchung(0).getAktivitaet()
				.getAktivitaet());
		assertEquals("20:15", testee.getBuchung(1).getStartZeit().toString());
		assertEquals("21:15", testee.getBuchung(1).getEndeZeit().toString());
		assertEquals("Aktivitaet2", testee.getBuchung(1).getAktivitaet()
				.getAktivitaet());
		assertEquals("21:15", testee.getBuchung(2).getStartZeit().toString());
		assertNull(testee.getBuchung(2).getEndeZeit());
		assertEquals("Aktivitaet2", testee.getBuchung(2).getAktivitaet()
				.getAktivitaet());

		testee.aendereEndezeit(1, test4);
		assertEquals(3, testee.getAnzahlBuchungen());
		assertEquals("06:24", testee.getBuchung(0).getStartZeit().toString());
		assertEquals("20:15", testee.getBuchung(0).getEndeZeit().toString());
		assertEquals("Aktivitaet1", testee.getBuchung(0).getAktivitaet()
				.getAktivitaet());
		assertEquals("20:15", testee.getBuchung(1).getStartZeit().toString());
		assertEquals("21:15", testee.getBuchung(1).getEndeZeit().toString());
		assertEquals("Aktivitaet2", testee.getBuchung(1).getAktivitaet()
				.getAktivitaet());
		assertEquals("21:15", testee.getBuchung(2).getStartZeit().toString());
		assertNull(testee.getBuchung(2).getEndeZeit());
		assertEquals("Aktivitaet2", testee.getBuchung(2).getAktivitaet()
				.getAktivitaet());

		testee.aendereEndezeit(2, test5);
		assertEquals(3, testee.getAnzahlBuchungen());
		assertEquals("06:24", testee.getBuchung(0).getStartZeit().toString());
		assertEquals("20:15", testee.getBuchung(0).getEndeZeit().toString());
		assertEquals("Aktivitaet1", testee.getBuchung(0).getAktivitaet()
				.getAktivitaet());
		assertEquals("20:15", testee.getBuchung(1).getStartZeit().toString());
		assertEquals("21:15", testee.getBuchung(1).getEndeZeit().toString());
		assertEquals("Aktivitaet2", testee.getBuchung(1).getAktivitaet()
				.getAktivitaet());
		assertEquals("21:15", testee.getBuchung(2).getStartZeit().toString());
		assertEquals("22:15", testee.getBuchung(2).getEndeZeit().toString());
		assertEquals("Aktivitaet2", testee.getBuchung(2).getAktivitaet()
				.getAktivitaet());

		testee.aendereStartzeit(1, test4);
		assertEquals(3, testee.getAnzahlBuchungen());
		assertEquals("06:24", testee.getBuchung(0).getStartZeit().toString());
		assertEquals("10:15", testee.getBuchung(0).getEndeZeit().toString());
		assertEquals("Aktivitaet1", testee.getBuchung(0).getAktivitaet()
				.getAktivitaet());
		assertEquals("10:15", testee.getBuchung(1).getStartZeit().toString());
		assertEquals("21:15", testee.getBuchung(1).getEndeZeit().toString());
		assertEquals("Aktivitaet2", testee.getBuchung(1).getAktivitaet()
				.getAktivitaet());
		assertEquals("21:15", testee.getBuchung(2).getStartZeit().toString());
		assertEquals("22:15", testee.getBuchung(2).getEndeZeit().toString());
		assertEquals("Aktivitaet2", testee.getBuchung(2).getAktivitaet()
				.getAktivitaet());

		testee.aendereAktivitaet(1, aktivitaet1);
		assertEquals(3, testee.getAnzahlBuchungen());
		assertEquals("06:24", testee.getBuchung(0).getStartZeit().toString());
		assertEquals("10:15", testee.getBuchung(0).getEndeZeit().toString());
		assertEquals("Aktivitaet1", testee.getBuchung(0).getAktivitaet()
				.getAktivitaet());
		assertEquals("10:15", testee.getBuchung(1).getStartZeit().toString());
		assertEquals("21:15", testee.getBuchung(1).getEndeZeit().toString());
		assertEquals("Aktivitaet1", testee.getBuchung(1).getAktivitaet()
				.getAktivitaet());
		assertEquals("21:15", testee.getBuchung(2).getStartZeit().toString());
		assertEquals("22:15", testee.getBuchung(2).getEndeZeit().toString());
		assertEquals("Aktivitaet2", testee.getBuchung(2).getAktivitaet()
				.getAktivitaet());

		testee.loescheBuchung(testee.getBuchung(0));
		testee.loescheBuchung(testee.getBuchung(0));
		testee.loescheBuchung(testee.getBuchung(0));
		testee.addBuchung(aktivitaet1);
		testee.aendereStartzeit(0, test1);
		testee.beendeBuchung();
		assertEquals(1, testee.getAnzahlBuchungen());
		assertEquals("06:24", testee.getBuchung(0).getStartZeit().toString());
		assertNotNull(testee.getBuchung(0).getEndeZeit());
		assertEquals("Aktivitaet1", testee.getBuchung(0).getAktivitaet()
				.getAktivitaet());

		testee.aendereEndezeit(0, test4);
		testee.addUnterbrechung();
		assertEquals(2, testee.getAnzahlBuchungen());
		assertEquals("06:24", testee.getBuchung(0).getStartZeit().toString());
		assertEquals("10:15", testee.getBuchung(0).getEndeZeit().toString());
		assertEquals("Aktivitaet1", testee.getBuchung(0).getAktivitaet()
				.getAktivitaet());
		assertEquals("10:15", testee.getBuchung(1).getStartZeit().toString());
		assertNull(testee.getBuchung(1).getEndeZeit());
		assertEquals("Unterbrechung", testee.getBuchung(1).getAktivitaet()
				.getAktivitaet());

		testee.aendereEndezeit(1, test2);
		assertEquals(2, testee.getAnzahlBuchungen());
		assertEquals("06:24", testee.getBuchung(0).getStartZeit().toString());
		assertEquals("10:15", testee.getBuchung(0).getEndeZeit().toString());
		assertEquals("Aktivitaet1", testee.getBuchung(0).getAktivitaet()
				.getAktivitaet());
		assertEquals("10:15", testee.getBuchung(1).getStartZeit().toString());
		assertEquals("20:15", testee.getBuchung(1).getEndeZeit().toString());
		assertEquals("Unterbrechung", testee.getBuchung(1).getAktivitaet()
				.getAktivitaet());
	}
}
