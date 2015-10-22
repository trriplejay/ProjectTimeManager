package com.bosch.zeitverwaltung.services;

import java.io.File;

/**
 * <p>
 * Hilfsklasse zur Ermittlung des Verzeichnisses, in das Daten geschrieben
 * werden können. Per Default ist dies ein Unterverezeichnis des
 * Anwendungsdaten-Verzeichnis von Windows.
 * </p>
 * 
 * @author Lars Geyer
 */
public final class ApplikationsDatenVerzeichnis {
	/**
	 * <p>
	 * Name des Unterverzeichnis, in dem die Zeitverwaltungsdaten gespeichert
	 * werden sollen
	 * </p>
	 */
	private static final String configurationVerzeichnis = "BoschZeitverwaltung";

	/**
	 * <p>
	 * Gibt ein <em>File</em>-Handle auf das Verzeichnis zurück, in dem
	 * Anwendungsdaten gespeichert werden sollen.
	 * </p>
	 * 
	 * @return <em>File</em>-Handle auf das Anwendungsdaten-Verzeichnis
	 */
	public File getAppdataVerzeichnis() {
		File back = null;
		String appdataName = System.getenv("APPDATA");
		if (appdataName != null) {
			File appdata = new File(appdataName);
			if (appdata.exists()) {
				back = new File(appdata, configurationVerzeichnis);
			}
		}

		if (back == null) {
			String userHome = System.getProperty("user.home");
			File appdata1 = new File(userHome, "Application Data");
			File appdata2 = new File(userHome, "Anwendungsdaten");
			if (appdata1.exists()) {
				back = new File(appdata1, configurationVerzeichnis);
			} else if (appdata2.exists()) {
				back = new File(appdata2, configurationVerzeichnis);
			}
		}

		if (back != null) {
			if (!back.exists()) {
				back.mkdir();
			}
		}

		return back;
	}
}
