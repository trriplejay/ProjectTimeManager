package com.bosch.zeitverwaltung.auswertung.framework.modell;

import com.bosch.zeitverwaltung.elemente.Uhrzeit;
import com.bosch.zeitverwaltung.elemente.Zeitspanne;

public final class FilterBoschStgt extends StarrePausenFilter {
	public FilterBoschStgt() {
		super();
		this.setPause(new Zeitspanne(new Uhrzeit(9, 0), new Uhrzeit(9, 15)));
		this.setPause(new Zeitspanne(new Uhrzeit(12, 0), new Uhrzeit(12, 30)));
	}
}
