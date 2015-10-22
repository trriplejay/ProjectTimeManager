package com.bosch.zeitverwaltung.xmlspeicher;

import junit.framework.Test;
import junit.framework.TestSuite;

public class LockTest {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.bosch.zeitverwaltung.xmlspeicher");

		suite.addTestSuite(LockDateiTest.class);

		return suite;
	}

}
