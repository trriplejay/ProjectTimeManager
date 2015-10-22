package com.bosch.zeitverwaltung.elemente;

import junit.framework.TestCase;

import com.bosch.zeitverwaltung.elemente.Uhrzeit;
import com.bosch.zeitverwaltung.elemente.Zeitspanne;

public class ZeitspanneTest extends TestCase {
	private Zeitspanne test1 = null;
	private Zeitspanne test2 = null;
	private Zeitspanne test3 = null;
	private Zeitspanne test4 = null;
	private Zeitspanne test5 = null;
	private Zeitspanne test6 = null;

	public final void testEqualsObject() {
		Uhrzeit.setMinutenDelta(MinutenDelta.MinutenDeltaEineMinute);
		test1 = new Zeitspanne(new Uhrzeit(9, 15), new Uhrzeit(9, 30));
		test2 = new Zeitspanne(new Uhrzeit(10, 30), new Uhrzeit(11, 45));
		test3 = new Zeitspanne(new Uhrzeit(9, 15), new Uhrzeit(9, 30));
		assertTrue(test1.equals(test1));
		assertFalse(test1.equals(test2));
		assertTrue(test1.equals(test3));
	}

	public final void testIstUeberschneidungsfrei() {
		Uhrzeit.setMinutenDelta(MinutenDelta.MinutenDeltaEineMinute);
		test1 = new Zeitspanne(new Uhrzeit(9, 15), new Uhrzeit(9, 30));
		test2 = new Zeitspanne(new Uhrzeit(9, 30), new Uhrzeit(11, 45));
		test3 = new Zeitspanne(new Uhrzeit(9, 24), new Uhrzeit(9, 45));
		test4 = new Zeitspanne(new Uhrzeit(9, 0), new Uhrzeit(9, 15));
		test5 = new Zeitspanne(new Uhrzeit(9, 0), new Uhrzeit(9, 21));
		test6 = new Zeitspanne(new Uhrzeit(9, 21), new Uhrzeit(9, 27));
		assertTrue(test1.istUeberschneidungsfrei(test2));
		assertFalse(test1.istUeberschneidungsfrei(test3));
		assertTrue(test1.istUeberschneidungsfrei(test4));
		assertFalse(test1.istUeberschneidungsfrei(test5));
		assertFalse(test1.istUeberschneidungsfrei(test6));
	}

	public final void testBerechneMinutenDifferenz() {
		Uhrzeit.setMinutenDelta(MinutenDelta.MinutenDeltaEineMinute);
		test1 = new Zeitspanne(new Uhrzeit(9, 15), new Uhrzeit(9, 30));
		test2 = new Zeitspanne(new Uhrzeit(10, 30), new Uhrzeit(11, 45));
		test3 = new Zeitspanne(new Uhrzeit(6, 42), new Uhrzeit(21, 30));
		assertEquals(15, test1.berechneMinutenDifferenz());
		assertEquals(75, test2.berechneMinutenDifferenz());
		assertEquals(888, test3.berechneMinutenDifferenz());
		Uhrzeit.setMinutenDelta(MinutenDelta.MinutenDeltaDreiMinuten);
		assertEquals(888, test3.berechneMinutenDifferenz());
		Uhrzeit.setMinutenDelta(MinutenDelta.MinutenDeltaFuenfMinuten);
		assertEquals(890, test3.berechneMinutenDifferenz());
		Uhrzeit.setMinutenDelta(MinutenDelta.MinutenDeltaFuenfzehnMinuten);
		assertEquals(900, test3.berechneMinutenDifferenz());

	}

	public final void testBerechneUeberschneidungsMinuten() {
		Uhrzeit.setMinutenDelta(MinutenDelta.MinutenDeltaEineMinute);
		test1 = new Zeitspanne(new Uhrzeit(9, 15), new Uhrzeit(9, 30));
		test2 = new Zeitspanne(new Uhrzeit(9, 30), new Uhrzeit(11, 45));
		test3 = new Zeitspanne(new Uhrzeit(9, 24), new Uhrzeit(9, 45));
		test4 = new Zeitspanne(new Uhrzeit(9, 0), new Uhrzeit(9, 15));
		test5 = new Zeitspanne(new Uhrzeit(9, 0), new Uhrzeit(9, 21));
		test6 = new Zeitspanne(new Uhrzeit(9, 21), new Uhrzeit(9, 27));
		assertEquals(0, test1.berechneUebereschneidungsMinuten(test2));
		assertEquals(6, test1.berechneUebereschneidungsMinuten(test3));
		assertEquals(15, test4.berechneUebereschneidungsMinuten(test5));
		assertEquals(6, test1.berechneUebereschneidungsMinuten(test6));
		assertEquals(15, test3.berechneUebereschneidungsMinuten(test2));
	}
}
