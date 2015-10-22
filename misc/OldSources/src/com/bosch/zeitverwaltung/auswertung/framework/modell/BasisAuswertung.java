package com.bosch.zeitverwaltung.auswertung.framework.modell;

/**
 * <p>
 * Diese Klasse enthält Hilfsfunktionen, die von abgeleiteten Auswertungsklassen
 * benutzt werden, um Auswertungsdaten Bildschirmtauglich darzustellen.
 * </p>
 * 
 * @author Lars Geyer
 * @see BuchungsAuswertung
 * @see Zusammenfassung
 */
public class BasisAuswertung {
	/**
	 * <p>
	 * Erzeugt aus einer Minutenangabe einen String, der eine
	 * Stundenrepräsentation der Minuten enthält.
	 * </p>
	 * 
	 * @param minuten
	 *            Die darzustellenden Minuten
	 * @return Stundenangabe in Stringform
	 */
	protected String erzeugeStundenString(final int minuten) {
		final StringBuffer back = new StringBuffer();

		final int stunden = minuten / 60;
		final int rest = (minuten % 60) * 100 / 60;

		back.append(stunden);
		back.append(',');
		back.append(String.format("%1$02d", rest));
		back.append(" h");

		return back.toString();
	}

	/**
	 * <p>
	 * Erzeugt aus einer Promilleangabe einen String, der die Promille in einer
	 * Prozentangabe enthält.
	 * </p>
	 * 
	 * @param promille
	 *            Die darzustellenden Promille
	 * @return Prozentwert in Stringform
	 */
	protected String erzeugeProzentString(final int promille) {
		return String.format("%1$1.1f", promille / 10.0) + " %";
	}
}