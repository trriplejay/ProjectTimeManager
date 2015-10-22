package com.bosch.zeitverwaltung.elemente;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * <p>
 * Repräsentiert eine unveränderbare Uhrzeit. Klasse verbirgt die Unschönheiten
 * der <em>Calendar</em>-Klasse
 * </p>
 * 
 * @author Lars Geyer
 */
public final class Uhrzeit implements Comparable<Uhrzeit> {
	private static MinutenDelta minutenDelta;

	/**
	 * <p>
	 * Setzt ein Minutendelta, mit dem die Minuten gefiltert werden, um
	 * Abrechnungszeiträume darstellen zu können.
	 * </p>
	 * 
	 * @param neuesMinutenDelta
	 *            Neuer Abrechnungszeitraum
	 */
	public static void setMinutenDelta(MinutenDelta neuesMinutenDelta) {
		minutenDelta = neuesMinutenDelta;
	}

	/**
	 * <p>
	 * Gibt den aktuellen Abrechnungszeitraum zurück
	 * </p>
	 * 
	 * @return Abrechnungszeitraum
	 */
	public static MinutenDelta getMinutenDelta() {
		return minutenDelta;
	}

	private int stunde;
	private int minute;

	/**
	 * <p>
	 * Erzeugt eine neue Uhrzeit aus der Angabe von Stunde und Minute, z.B.
	 * 09:53 Uhr. Die Angaben beziehen sich auf die Tagesstunden und die
	 * Minutenangabe innerhalb der Stunde.
	 * </p>
	 * 
	 * @param stunde
	 *            Tages-Stunde
	 * @param minute
	 *            Stunden-Minuten
	 */
	public Uhrzeit(int stunde, int minute) {
		if (!istKorrekteUhrzeit(stunde, minute)) {
			throw new IllegalArgumentException("Keine korrekte Zeitangabe");
		}
		this.stunde = stunde;
		this.minute = minute;
	}

	/**
	 * <p>
	 * Erzeugt eine neue Uhrzeit aus einem <em>Date</em>-Objekt
	 * </p>
	 * 
	 * @param zeit
	 *            <em>Date</em>-Objekt
	 */
	public Uhrzeit(Date zeit) {
		Calendar temp = Calendar.getInstance();
		temp.setTime(zeit);
		stunde = temp.get(Calendar.HOUR_OF_DAY);
		minute = temp.get(Calendar.MINUTE);
	}

	/**
	 * <p>
	 * Parser, um eine Uhrzeut aus einem String zu erzeugen.
	 * </p>
	 * 
	 * @param zeitString
	 *            String mit einer Uhrzeit
	 * @throws ParseException
	 *             Falls keine korrekte Uhrzeit erzeugt werden konnte.
	 */
	public Uhrzeit(String zeitString) throws ParseException {
		StringTokenizer stz = new StringTokenizer(zeitString, ",");

		if (stz.countTokens() != 2) {
			stz = new StringTokenizer(zeitString, ":");
			if (stz.countTokens() != 2) {
				stz = new StringTokenizer(zeitString, ".");
				if (stz.countTokens() != 2) {
					throw new ParseException("Keine korrekte Zeitangabe", 0);
				}
			}
		}

		try {
			stunde = Integer.parseInt(stz.nextToken());
			minute = Integer.parseInt(stz.nextToken());
		} catch (NumberFormatException e) {
			throw new ParseException("Keine korrekte Zeitangabe", 0);
		}

		if (!istKorrekteUhrzeit(stunde, minute)) {
			throw new ParseException("Keine korrekte Zeitangabe", 0);
		}
	}

	/**
	 * <p>
	 * Ausgabe der Stundenangabe
	 * </p>
	 * 
	 * @return Tages-Stunde
	 */
	public int getStunde() {
		return stunde;
	}

	/**
	 * <p>
	 * Ausgabe der gefilterten Minuten, d.h. die Minutenangabe wurde mittels des
	 * Minutendeltas gefiltert.
	 * </p>
	 * 
	 * @return Minuten einer Stunde
	 */
	public int getMinuten() {
		return (minute / minutenDelta.getMinutenDelta())
				* minutenDelta.getMinutenDelta();
	}

	/**
	 * <p>
	 * Ausgabe der ungefilterten Minuten, es erfolgt keine Filterung durch das
	 * Minutendelta.
	 * </p>
	 * 
	 * @return Minuten einer Stunde
	 */
	public int getRohMinuten() {
		return minute;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		Calendar temp = Calendar.getInstance();
		temp.set(Calendar.HOUR_OF_DAY, getStunde());
		temp.set(Calendar.MINUTE, getMinuten());
		return DateFormat.getTimeInstance(DateFormat.SHORT).format(
				temp.getTime());
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object obj) {
		boolean back = false;

		if (obj instanceof Uhrzeit) {
			Uhrzeit zeit = (Uhrzeit) obj;

			if ((zeit.getStunde() == stunde) && (zeit.getMinuten() == minute)) {
				back = true;
			}
		}

		return back;
	}

	/**
	 * {@inheritDoc}
	 */
	public int compareTo(Uhrzeit vergleich) {
		int back = 0;

		if (stunde != vergleich.getStunde()) {
			back = (stunde < vergleich.getStunde()) ? -1 : 1;
		} else if (minute != vergleich.getRohMinuten()) {
			back = (minute < vergleich.getRohMinuten()) ? -1 : 1;
		}

		return back;
	}

	/**
	 * <p>
	 * Konsistenzcheck, der die Korrektheit einer Zeitangabe überprüft. Wird im
	 * Konstruktor verwendet.
	 * </p>
	 * 
	 * @param stunde
	 *            Zu überprüfende Stundenangabe
	 * @param minute
	 *            Zu überprüfende Minutenangabe
	 * @return Ist korrekte Zeitangabe
	 */
	private boolean istKorrekteUhrzeit(int stunde, int minute) {
		boolean back = false;

		if ((stunde >= 0) && (stunde < 24)) {
			if ((minute >= 0) && (minute < 60)) {
				back = true;
			}
		}

		return back;
	}
}
