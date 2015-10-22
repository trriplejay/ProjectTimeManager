package com.bosch.zeitverwaltung.elemente;

import junit.framework.TestCase;

public class AktivitaetTest extends TestCase {
	public final void testUnterbrechungsAktivitaet() {
		UnterbrechungsAktivitaet unterbrechung = new UnterbrechungsAktivitaet();
		assertFalse(unterbrechung.abrechungsRelevant());
		unterbrechung.setEuro(1.0);
		assertFalse(unterbrechung.abrechungsRelevant());
		unterbrechung.setKm(5);
		assertFalse(unterbrechung.abrechungsRelevant());
		unterbrechung.setReisezeit(false);
		assertFalse(unterbrechung.abrechungsRelevant());
	}

	public final void testAbrechungsRelevant() {
		Aktivitaet test1 = new Aktivitaet("Dummy", "Dummy", "Dummy", true);
		Aktivitaet test2 = new Aktivitaet("Dummy", "Dummy", "Dummy", true);
		Aktivitaet test3 = new Aktivitaet("Dummy", "Dummy", "Dummy", true);
		test2.setEuro(4.51);
		test3.setKm(44);

		assertFalse(test1.abrechungsRelevant());
		assertTrue(test2.abrechungsRelevant());
		assertTrue(test3.abrechungsRelevant());
	}

	public final void testGetAbrechnungsTyp() {
		Aktivitaet test1 = new Aktivitaet("Dummy", "Dummy", "Dummy", true);
		Aktivitaet test2 = new Aktivitaet("Dummy", "Dummy", "Dummy", true);
		Aktivitaet test3 = new Aktivitaet("Dummy", "Dummy", "Dummy", true);
		test2.setEuro(4.51);
		test3.setKm(44);

		assertFalse(test1.abrechnungInEuro());
		assertFalse(test1.abrechnungInKm());
		assertTrue(test2.abrechnungInEuro());
		assertFalse(test2.abrechnungInKm());
		assertFalse(test3.abrechnungInEuro());
		assertTrue(test3.abrechnungInKm());
	}

	public final void testGetAbrechnungsInfo() {
		Aktivitaet test1 = new Aktivitaet("Dummy", "Dummy", "Dummy", true);
		Aktivitaet test2 = new Aktivitaet("Dummy", "Dummy", "Dummy", true);
		Aktivitaet test3 = new Aktivitaet("Dummy", "Dummy", "Dummy", true);
		Aktivitaet test4 = new Aktivitaet("Dummy", "Dummy", "Dummy", true);
		Aktivitaet test5 = new Aktivitaet("Dummy", "Dummy", "Dummy", true);
		Aktivitaet test6 = new Aktivitaet("Dummy", "Dummy", "Dummy", true);
		test2.setKm(44);
		test3.setEuro(4.50);
		test4.setEuro(4.51);
		test5.setEuro(4.515);
		test6.setEuro(4.5125);

		assertTrue(test1.getAbrechnungsInfo().equals(""));
		assertTrue(test2.getAbrechnungsInfo().equals("44 km"));
		assertTrue(test3.getAbrechnungsInfo().equals("4,50 €"));
		assertTrue(test4.getAbrechnungsInfo().equals("4,51 €"));
		assertTrue(test5.getAbrechnungsInfo().equals("4,515 €"));
		assertTrue(test6.getAbrechnungsInfo().equals("4,5125 €"));
	}

	public final void testGetAbrechnungsKm() {
		Aktivitaet test1 = new Aktivitaet("Test1", "Test1", "Dummy", true);
		Aktivitaet test2 = new Aktivitaet("Test2", "Test2", "Dummy", true);
		test1.setEuro(4.51);
		test2.setKm(44);

		assertEquals(0, test1.getAbrechnungsKm());
		assertEquals(44, test2.getAbrechnungsKm());
	}
}
