package com.bosch.zeitverwaltung.elemente;

import junit.framework.TestCase;

public class BuchungTest extends TestCase {
	public final void testSetAktivitaet() {
		Aktivitaet testAkt = new Aktivitaet("Test3", "Test3", "Test3", true);
		Buchung test1 = new Buchung(new Uhrzeit(9, 25), new Aktivitaet("Test1",
				"Test1", "Test1", true));
		Buchung test2 = new Buchung(new Uhrzeit(9, 25), new Aktivitaet("Test2",
				"Test2", "Test2", true));
		Buchung test3 = new Unterbrechung(new Uhrzeit(9, 25));
		test1.setAktivitaet(testAkt);
		test2.setAktivitaet(testAkt);
		test3.setAktivitaet(testAkt);

		assertEquals(testAkt, test1.getAktivitaet());
		assertEquals(testAkt, test2.getAktivitaet());
		assertNotSame(testAkt, test3.getAktivitaet());
	}

	public final void testBerechneBuchungsdauer() {
		Uhrzeit.setMinutenDelta(MinutenDelta.MinutenDeltaEineMinute);
		Buchung test1 = new Buchung(new Uhrzeit(9, 25), new Aktivitaet("Test1",
				"Test1", "Test1", true));
		Buchung test2 = new Unterbrechung(new Uhrzeit(9, 25));
		test1.setEndeZeit(new Uhrzeit(10, 36));
		test2.setEndeZeit(new Uhrzeit(10, 36));
		assertEquals(71, test1.berechneBuchungsdauer());
		assertEquals(0, test2.berechneBuchungsdauer());
	}
}
