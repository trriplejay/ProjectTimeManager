package com.bosch.zeitverwaltung.elemente;

import java.text.ParseException;

import junit.framework.TestCase;

import com.bosch.zeitverwaltung.elemente.Uhrzeit;

public class UhrzeitTest extends TestCase {
	private Uhrzeit test1 = null;
	private Uhrzeit test2 = null;
	private Uhrzeit test3 = null;

	protected void setUp() throws Exception {
		super.setUp();
		Uhrzeit.setMinutenDelta(MinutenDelta.MinutenDeltaEineMinute);
		test1 = new Uhrzeit(9, 24);
		test2 = new Uhrzeit(9, 48);
		test3 = new Uhrzeit(10, 15);
	}

	public final void testIstFrueher() {
		assertTrue(test1.compareTo(test2) < 0);
		assertTrue(test1.compareTo(test3) < 0);
		assertFalse(test2.compareTo(test1) < 0);
		assertFalse(test3.compareTo(test1) < 0);
		assertFalse(test1.compareTo(test1) < 0);
	}

	public final void testIstFrueherOderGleich() {
		assertTrue(test1.compareTo(test2) <= 0);
		assertTrue(test1.compareTo(test3) <= 0);
		assertFalse(test2.compareTo(test1) <= 0);
		assertFalse(test3.compareTo(test1) <= 0);
		assertTrue(test1.compareTo(test1) <= 0);
	}

	public final void testIstSpaeter() {
		assertTrue(test2.compareTo(test1) > 0);
		assertTrue(test3.compareTo(test1) > 0);
		assertFalse(test1.compareTo(test2) > 0);
		assertFalse(test1.compareTo(test3) > 0);
		assertFalse(test1.compareTo(test1) > 0);
	}

	public final void testIstSpaeterOderGleich() {
		assertTrue(test2.compareTo(test1) >= 0);
		assertTrue(test3.compareTo(test1) >= 0);
		assertFalse(test1.compareTo(test2) >= 0);
		assertFalse(test1.compareTo(test3) >= 0);
		assertTrue(test1.compareTo(test1) >= 0);
	}

	public final void testIstGleich() {
		assertFalse(test2.compareTo(test1) == 0);
		assertFalse(test3.compareTo(test1) == 0);
		assertFalse(test1.compareTo(test2) == 0);
		assertFalse(test1.compareTo(test3) == 0);
		assertTrue(test1.compareTo(test1) == 0);
	}

	public final void testParseZeit() {
		String tester1 = "09:24";
		String tester2 = "9:24";
		String tester3 = "09.24";
		String tester4 = "07:1509:24";
		String tester5 = "arbitrary";
		String tester6 = "foo:bar";

		try {
			assertTrue(test1.compareTo(new Uhrzeit(tester1)) == 0);
			assertTrue(test1.compareTo(new Uhrzeit(tester2)) == 0);
			assertTrue(test1.compareTo(new Uhrzeit(tester3)) == 0);
		} catch (ParseException e) {
			fail(e.getMessage());
		}
		try {
			new Uhrzeit(tester4);
			fail("Exception missing");
		} catch (ParseException e) {
		}
		try {
			new Uhrzeit(tester5);
			fail("Exception missing");
		} catch (ParseException e) {
		}
		try {
			new Uhrzeit(tester6);
			fail("Exception missing");
		} catch (ParseException e) {
		}
	}

	public final void testDelta() {
		Uhrzeit test = new Uhrzeit(9, 23);
		Uhrzeit.setMinutenDelta(MinutenDelta.MinutenDeltaEineMinute);
		assertEquals(9, test.getStunde());
		assertEquals(23, test.getMinuten());
		Uhrzeit.setMinutenDelta(MinutenDelta.MinutenDeltaDreiMinuten);
		assertEquals(9, test.getStunde());
		assertEquals(21, test.getMinuten());
		Uhrzeit.setMinutenDelta(MinutenDelta.MinutenDeltaFuenfMinuten);
		assertEquals(9, test.getStunde());
		assertEquals(20, test.getMinuten());
		Uhrzeit.setMinutenDelta(MinutenDelta.MinutenDeltaFuenfzehnMinuten);
		assertEquals(9, test.getStunde());
		assertEquals(15, test.getMinuten());
	}
}
