package com.bosch.zeitverwaltung.elemente;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * Repräsentiert einen Tag. Verbirgt die Unschönheit der <em>Calendar</em>-Klasse
 * </p>
 * 
 * @author Lars Geyer
 */
public final class Tag implements Comparable<Tag> {
	int jahr;
	int monat;
	int tag;

	/**
	 * <p>
	 * Erzeugt einen Tag aus den Angaben zu Jahr, Monat und Tag.
	 * </p>
	 * 
	 * @param jahr
	 *            Jahr in dem Tag liegt
	 * @param monat
	 *            Monat in dem Tag liegt
	 * @param tag
	 *            Tag des Monats
	 */
	public Tag(int jahr, int monat, int tag) {
		if (!istKorrekterTag(jahr, monat, tag)) {
			throw new IllegalArgumentException("Keine korrekte Tagangabe");
		}
		this.jahr = jahr;
		this.monat = monat;
		this.tag = tag;
	}

	/**
	 * <p>
	 * Erzeugt einen Tag aus einem <em>Date</em>-Objekt
	 * </p>
	 * 
	 * @param datum
	 *            <em>Date</em>-Objekt
	 */
	public Tag(Date datum) {
		Calendar temp = Calendar.getInstance();
		temp.setTime(datum);
		jahr = temp.get(Calendar.YEAR);
		monat = temp.get(Calendar.MONTH) + 1;
		tag = temp.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * <p>
	 * Ausgabe des Jahres für den dargestellten Tag
	 * </p>
	 * 
	 * @return Jahr
	 */
	public int getJahr() {
		return jahr;
	}

	/**
	 * <p>
	 * Ausgabe des Monats für den dargestellten Tag
	 * </p>
	 * 
	 * @return Monat
	 */
	public int getMonat() {
		return monat;
	}

	/**
	 * <p>
	 * Ausgabe des Tages im Monat für den dargestellten Tag
	 * </p>
	 * 
	 * @return Tag
	 */
	public int getTag() {
		return tag;
	}

	/**
	 * <p>
	 * Ausgabe des dargestellten Tages als <em>Date</em>-Objekt
	 * </p>
	 * 
	 * @return <em>Date</em>-Objekt
	 */
	public Date getDate() {
		Calendar temp = Calendar.getInstance();
		temp.set(Calendar.YEAR, jahr);
		temp.set(Calendar.MONTH, monat - 1);
		temp.set(Calendar.DAY_OF_MONTH, tag);
		return temp.getTime();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object obj) {
		boolean back = false;

		if ((obj != null) && (obj instanceof Tag)) {
			back = (compareTo((Tag) obj) == 0);
		}

		return back;
	}

	/**
	 * {@inheritDoc}
	 */
	public int compareTo(Tag vergleich) {
		int back;

		if (jahr != vergleich.getJahr()) {
			back = (jahr < vergleich.getJahr()) ? -1 : 1;
		} else if (monat != vergleich.getMonat()) {
			back = (monat < vergleich.getMonat()) ? -1 : 1;
		} else if (tag != vergleich.getTag()) {
			back = (tag < vergleich.getTag()) ? -1 : 1;
		} else {
			back = 0;
		}

		return back;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		Calendar temp = Calendar.getInstance();
		temp.set(Calendar.YEAR, jahr);
		temp.set(Calendar.MONTH, monat - 1);
		temp.set(Calendar.DAY_OF_MONTH, tag);
		return DateFormat.getDateInstance(DateFormat.MEDIUM).format(
				temp.getTime());
	}

	/**
	 * <p>
	 * Konsistentprüfung, um Gültigkeit einer Tagesangabe zu überprüfen.
	 * </p>
	 * 
	 * @param jahr
	 *            Jahr in dem Tag liegen soll
	 * @param monat
	 *            Monat in dem Tag liegen soll
	 * @param tag
	 *            Tag des Monats
	 * 
	 * @return Tagangabe ist korrekt
	 */
	private boolean istKorrekterTag(int jahr, int monat, int tag) {
		boolean back = true;

		if ((monat < 1) || (monat > 12)) {
			back = false;
		}

		int tagmax = 0;
		switch (monat) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			tagmax = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			tagmax = 30;
			break;
		case 2:
			tagmax = ((jahr % 4) == 0) ? 29 : 28;
			break;
		}

		if ((tag < 1) || (tag > tagmax)) {
			back = false;
		}

		return back;
	}
}
