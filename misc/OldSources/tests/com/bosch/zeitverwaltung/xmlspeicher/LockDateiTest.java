package com.bosch.zeitverwaltung.xmlspeicher;

import java.io.File;

import junit.framework.TestCase;

public class LockDateiTest extends TestCase {

	public final void testPruefeLockDatei() {
		XMLDienste testee = new XMLDienste();
		File lock = getLockDatei();
		if (lock.exists()) {
			System.err.println("lock existiert");
			lock.delete();
		}

		try {
			testee.pruefeLockDatei();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail();
		}
		try {
			testee.pruefeLockDatei();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail();
		}
	}

	public static File getLockDatei() {
		File back = new XMLDienste().getDatenVerzeichnis();
		back = new File(back, "locked.txt");
		return back;
	}

}
