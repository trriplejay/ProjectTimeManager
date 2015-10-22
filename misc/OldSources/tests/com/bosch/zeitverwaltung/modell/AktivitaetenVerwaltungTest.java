package com.bosch.zeitverwaltung.modell;

import java.util.Iterator;

import junit.framework.TestCase;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.elemente.AktivitaetsSortierung;
import com.bosch.zeitverwaltung.modell.AktivitaetenVerwaltung;
import com.bosch.zeitverwaltung.modell.ModellFactory;
import com.bosch.zeitverwaltung.modell_impl.StandardModellFactory;

public class AktivitaetenVerwaltungTest extends TestCase {
	private AktivitaetenVerwaltung testee = null;

	public final void testAktivitaetenVerwaltung() {
		ModellFactory modellFactory = ModellFactory.getFactory();
		if (modellFactory == null) {
			modellFactory = new StandardModellFactory();
		}

		testee = modellFactory.getAktivitaetenVerwaltung();
		testee.setSortierung(AktivitaetsSortierung.AktivitaetsSortierungNachBuchungsnummer);
		Aktivitaet test1 = new Aktivitaet("Aktivitaet1", "Aktivitaet1", "0815",
				false);
		Aktivitaet test2 = new Aktivitaet("Aktivitaet2", "Aktivitaet2", "4711",
				false);
		Aktivitaet test3 = new Aktivitaet("Aktivitaet3", "Aktivitaet3", "3046",
				false);
		Aktivitaet test4 = new Aktivitaet("Aktivitaet27", "Aktivitaet27",
				"9953", false);

		try {
			testee.elementHinzufuegen(test1);
			testee.elementHinzufuegen(test2);
			testee.elementHinzufuegen(test3);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		Iterator<Aktivitaet> iter = testee.elemente().iterator();

		Aktivitaet aktAktivitaet = iter.next();
		assertEquals(test1, aktAktivitaet);

		aktAktivitaet = iter.next();
		assertEquals(test3, aktAktivitaet);

		aktAktivitaet = iter.next();
		assertEquals(test2, aktAktivitaet);

		assertFalse(iter.hasNext());

		testee.starteAenderung();

		testee.elementLoeschen(test2);

		iter = testee.elemente().iterator();

		aktAktivitaet = iter.next();
		assertEquals(test1, aktAktivitaet);

		aktAktivitaet = iter.next();
		assertEquals(test3, aktAktivitaet);

		assertFalse(iter.hasNext());

		testee.restoreAenderung();

		iter = testee.elemente().iterator();

		aktAktivitaet = iter.next();
		assertEquals(test1, aktAktivitaet);

		aktAktivitaet = iter.next();
		assertEquals(test3, aktAktivitaet);
		assertEquals("Aktivitaet3", aktAktivitaet.getAktivitaet());

		aktAktivitaet = iter.next();
		assertEquals(test2, aktAktivitaet);

		assertFalse(iter.hasNext());

		testee.setSortierung(AktivitaetsSortierung.AktivitaetsSortierungNachBuchungsnummer);

		iter = testee.elemente().iterator();

		aktAktivitaet = iter.next();
		assertEquals(test1, aktAktivitaet);

		aktAktivitaet = iter.next();
		assertEquals(test3, aktAktivitaet);

		aktAktivitaet = iter.next();
		assertEquals(test2, aktAktivitaet);

		assertFalse(iter.hasNext());

		testee.starteAenderung();

		testee.elementLoeschen(test2);
		try {
			testee.elementHinzufuegen(test4);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		testee.commitAenderung();

		iter = testee.elemente().iterator();

		aktAktivitaet = iter.next();
		assertEquals(test1, aktAktivitaet);

		aktAktivitaet = iter.next();
		assertEquals(test3, aktAktivitaet);

		aktAktivitaet = iter.next();
		assertEquals(test4, aktAktivitaet);

		assertFalse(iter.hasNext());
	}
}
