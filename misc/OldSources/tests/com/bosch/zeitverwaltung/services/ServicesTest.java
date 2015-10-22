package com.bosch.zeitverwaltung.services;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ServicesTest {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.bosch.zeitverwaltung.services");

		suite.addTestSuite(ClasspathSucheTest.class);
		suite.addTestSuite(ApplikationsDatenVerzeichnisTest.class);
		suite.addTestSuite(DOMWrapperTest.class);

		return suite;
	}

}
