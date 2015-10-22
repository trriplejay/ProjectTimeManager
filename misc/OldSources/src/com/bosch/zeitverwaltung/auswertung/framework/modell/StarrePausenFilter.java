package com.bosch.zeitverwaltung.auswertung.framework.modell;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import com.bosch.zeitverwaltung.elemente.Buchung;
import com.bosch.zeitverwaltung.elemente.Uhrzeit;
import com.bosch.zeitverwaltung.elemente.Unterbrechung;
import com.bosch.zeitverwaltung.elemente.Zeitspanne;
import com.bosch.zeitverwaltung.modell.TagesBuchungen;

/**
 * <p>
 * Filtert Arbeitszeiten mit starren Pausen, z.B. 9-9:15 Uhr und 12-12:30 Uhr.
 * </p>
 * 
 * @author Lars Geyer
 */
public class StarrePausenFilter implements BuchungsFilter {
	private Collection<Zeitspanne> pausenZeiten = new LinkedList<Zeitspanne>();

	/**
	 * <p>
	 * Abgeleitete Klassen definieren die Pausen und teilen sie über diese
	 * Methode mit.
	 * </p>
	 * 
	 * @param pause
	 *            Neue Pause
	 */
	protected void setPause(Zeitspanne pause) {
		pausenZeiten.add(pause);
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Buchung> berechneArbeitszeiten(TagesBuchungen buchungen) {
		Collection<Buchung> back = new LinkedList<Buchung>();

		Buchung aktBuchung;
		Zeitspanne aktZeitspanne;
		for (int index = 0; index < buchungen.getAnzahlBuchungen(); index++) {
			aktBuchung = buchungen.getBuchung(index);
			if (aktBuchung.getEndeZeit() != null) {
				aktZeitspanne = new Zeitspanne(aktBuchung.getStartZeit(),
						aktBuchung.getEndeZeit());
				if (!(aktBuchung instanceof Unterbrechung)) {
					if (enthaeltPause(aktZeitspanne)) {
						int minuten = berechnePausenMinuten(aktZeitspanne);
						Uhrzeit endezeit = aktBuchung.getEndeZeit();
						int endemin = endezeit.getRohMinuten();
						endemin = endemin - minuten;
						int stunde = endezeit.getStunde();
						while (endemin < 0) {
							stunde--;
							endemin += 60;
						}
						Buchung temp = new Buchung(aktBuchung.getStartZeit(),
								aktBuchung.getAktivitaet());
						temp.setEndeZeit(new Uhrzeit(stunde, endemin));
						temp.setKommentar(aktBuchung.getKommentar());
						back.add(temp);
					} else {
						back.add(aktBuchung);
					}
				}
			}
		}

		return back;
	}

	/**
	 * <p>
	 * Prüft, ob Zeitspanne eine Pause enthält.
	 * </p>
	 * 
	 * @param zeitspanne
	 *            Zu prüfende Zeitspanne
	 * 
	 * @return Zeitspanne enthält Pause
	 */
	private boolean enthaeltPause(Zeitspanne zeitspanne) {
		return (ermittlePause(zeitspanne).size() > 0);
	}

	/**
	 * <p>
	 * Berechnet die Anzahl an Minuten, welche innerhalb der Zeitspanne
	 * Pausenminuten sind.
	 * </p>
	 * 
	 * @param zeitspanne
	 *            Betrachtete Zeitspanne
	 * 
	 * @return Überschneidungsminuten mit Pausen
	 */
	private int berechnePausenMinuten(Zeitspanne zeitspanne) {
		int back = 0;
		Iterator<Zeitspanne> iter = ermittlePause(zeitspanne).iterator();
		Zeitspanne aktPause = null;

		while (iter.hasNext()) {
			aktPause = iter.next();
			back += zeitspanne.berechneUebereschneidungsMinuten(aktPause);
		}

		return back;
	}

	/**
	 * <p>
	 * Ermittle alle Pausen, die eine Überschneidung mit der Zeitspanne haben.
	 * </p>
	 * 
	 * @param zeitspanne
	 *            Betrachtete Zeitspanne
	 * 
	 * @return Liste mit Pausen, die eine Überschneidung haben
	 */
	private Collection<Zeitspanne> ermittlePause(Zeitspanne zeitspanne) {
		Collection<Zeitspanne> back = new LinkedList<Zeitspanne>();

		Iterator<Zeitspanne> iter = pausenZeiten.iterator();
		Zeitspanne aktPause;

		while (iter.hasNext()) {
			aktPause = iter.next();

			if (!zeitspanne.istUeberschneidungsfrei(aktPause)) {
				back.add(aktPause);
			}
		}

		return back;
	}
}
