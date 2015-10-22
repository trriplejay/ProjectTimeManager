package com.bosch.zeitverwaltung;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.bosch.zeitverwaltung.elemente.AktivitaetTest;
import com.bosch.zeitverwaltung.elemente.TagTest;
import com.bosch.zeitverwaltung.elemente.BuchungTest;
import com.bosch.zeitverwaltung.elemente.UhrzeitTest;
import com.bosch.zeitverwaltung.elemente.ZeitspanneTest;
import com.bosch.zeitverwaltung.modell.AktivitaetenVerwaltungTest;
import com.bosch.zeitverwaltung.modell.TagesBuchungenTest;
import com.bosch.zeitverwaltung.services.ApplikationsDatenVerzeichnisTest;
import com.bosch.zeitverwaltung.services.ClasspathSucheTest;
import com.bosch.zeitverwaltung.services.DOMWrapperTest;
import com.bosch.zeitverwaltung.xmlspeicher.LockDateiTest;

public class AlleTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for com.bosch.zeitverwaltung");

		suite.addTestSuite(UhrzeitTest.class);
		suite.addTestSuite(ZeitspanneTest.class);
		suite.addTestSuite(BuchungTest.class);
		suite.addTestSuite(AktivitaetTest.class);
		suite.addTestSuite(TagTest.class);

		suite.addTestSuite(AktivitaetenVerwaltungTest.class);
		suite.addTestSuite(TagesBuchungenTest.class);

		suite.addTestSuite(ClasspathSucheTest.class);
		suite.addTestSuite(ApplikationsDatenVerzeichnisTest.class);
		suite.addTestSuite(DOMWrapperTest.class);

		suite.addTestSuite(LockDateiTest.class);

		return suite;
	}

}
