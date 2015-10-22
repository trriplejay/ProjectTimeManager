package com.bosch.zeitverwaltung.elemente;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ElementeTest {
	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.bosch.zeitverwaltung.elemente");

		suite.addTestSuite(UhrzeitTest.class);
		suite.addTestSuite(ZeitspanneTest.class);
		suite.addTestSuite(BuchungTest.class);
		suite.addTestSuite(AktivitaetTest.class);
		suite.addTestSuite(TagTest.class);

		return suite;
	}
}
