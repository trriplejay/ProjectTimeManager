package com.bosch.zeitverwaltung.elemente;

/**
 * <p>
 * Repräsentiert eine unveränderbare Zeitspanne.
 * </p>
 * 
 * @author Lars Geyer
 */
public final class Zeitspanne {
	private Uhrzeit startZeit;
	private Uhrzeit endeZeit;

	/**
	 * <p>
	 * Konstruktor, einzige Möglichkeit die Zeitspanne zu definieren.
	 * </p>
	 * 
	 * @param startZeit
	 *            Startzeitpunkt
	 * @param endeZeit
	 *            Endezeitpunkt
	 */
	public Zeitspanne(Uhrzeit startZeit, Uhrzeit endeZeit) {
		if (endeZeit != null) {
			pruefeZeitspanne(startZeit, endeZeit);
		}
		this.startZeit = startZeit;
		this.endeZeit = endeZeit;
	}

	/**
	 * <p>
	 * Gibt Anfangszeit der Zeitspanne zurück.
	 * </p>
	 * 
	 * @return Anfangszeit
	 */
	public Uhrzeit getStartZeit() {
		return startZeit;
	}

	/**
	 * <p>
	 * Gibt Endezeit der Zeitspanne zurück.
	 * </p>
	 * 
	 * @return Endezeit
	 */
	public Uhrzeit getEndeZeit() {
		return endeZeit;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object obj) {
		boolean back = false;

		if (obj instanceof Zeitspanne) {
			Zeitspanne pruefling = (Zeitspanne) obj;
			if (startZeit.compareTo(pruefling.getStartZeit()) == 0) {
				if (endeZeit == null) {
					if (pruefling.getEndeZeit() == null) {
						back = true;
					}
				} else if (endeZeit.compareTo(pruefling.getEndeZeit()) == 0) {
					back = true;
				}
			}
		}

		return back;
	}

	/**
	 * <p>
	 * Vergleicht zwei Zeitspannen bezüglich Überschneidungsfreiheit
	 * </p>
	 * 
	 * @param vergleich
	 *            Vergleichszeitspanne
	 * 
	 * @return Beide Zeitspannen sind überschneidungsfrei
	 */
	public boolean istUeberschneidungsfrei(Zeitspanne vergleich) {
		boolean back = true;

		if (startZeit.compareTo(vergleich.getStartZeit()) < 0) {
			if (endeZeit.compareTo(vergleich.getStartZeit()) > 0) {
				back = false;
			}
		} else if (startZeit.compareTo(vergleich.getStartZeit()) > 0) {
			if (startZeit.compareTo(vergleich.getEndeZeit()) < 0) {
				back = false;
			}
		} else if (startZeit.compareTo(vergleich.getStartZeit()) == 0) {
			back = false;
		}

		return back;
	}

	/**
	 * <p>
	 * Berechnet den Abstand zwischen Ende- und Anfangszeitpunkt in Minuten
	 * </p>
	 * 
	 * @return Minuten
	 */
	public int berechneMinutenDifferenz() {
		int minuten = 0;

		if (endeZeit != null) {
			if (startZeit.getStunde() == endeZeit.getStunde()) {
				minuten = endeZeit.getMinuten() - startZeit.getMinuten();
			} else {
				minuten = endeZeit.getMinuten() + 60 - startZeit.getMinuten();
				minuten += (endeZeit.getStunde() - startZeit.getStunde() - 1) * 60;
			}
		} else {
			throw new IllegalArgumentException("Keine Endezeit");
		}

		return minuten;
	}

	/**
	 * <p>
	 * Berechnet die Überschneidungszeit zweier Zeitspannen.
	 * </p>
	 * 
	 * @param vergleich
	 *            Vergleichszeitspanne
	 * @return Minuten
	 */
	public int berechneUebereschneidungsMinuten(Zeitspanne vergleich) {
		int back = 0;

		if (!istUeberschneidungsfrei(vergleich)) {
			if (startZeit.compareTo(vergleich.getStartZeit()) < 0) {
				if (endeZeit.compareTo(vergleich.getEndeZeit()) < 0) {
					back = new Zeitspanne(vergleich.getStartZeit(), endeZeit)
							.berechneMinutenDifferenz();
				} else {
					back = vergleich.berechneMinutenDifferenz();
				}
			} else {
				if (endeZeit.compareTo(vergleich.getEndeZeit()) < 0) {
					back = berechneMinutenDifferenz();
				} else {
					back = new Zeitspanne(startZeit, vergleich.getEndeZeit())
							.berechneMinutenDifferenz();
				}
			}
		}

		return back;
	}

	/**
	 * <p>
	 * Prüft die semantische Korrektheit einer Zeitspanne
	 * </p>
	 * 
	 * @param startZeit
	 *            Anfangszeit der Zeitspanne
	 * @param endeZeit
	 *            Endezeit der Zeitspanne
	 */
	private void pruefeZeitspanne(Uhrzeit startZeit, Uhrzeit endeZeit) {
		if (startZeit.compareTo(endeZeit) >= 0) {
			throw new IllegalArgumentException(
					"Startzeit muss vor Endezeit sein");
		}
	}
}
