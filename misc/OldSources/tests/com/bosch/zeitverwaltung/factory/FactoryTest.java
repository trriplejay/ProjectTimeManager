package com.bosch.zeitverwaltung.factory;

import junit.framework.Test;
import junit.framework.TestSuite;

public class FactoryTest {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.bosch.zeitverwaltung.factory");

		suite.addTestSuite(FactoryKonfigurationTest.class);

		return suite;
	}

}
