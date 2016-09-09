/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.time.LocalTime;
import java.util.Objects;

import com.google.common.base.MoreObjects;

/**
 * Class for storing a time span, i.e., a span of time defined by a start time
 * and an end time. The class gives calculations on the time span. This is a
 * transient type that is only used for calculation. It is not persisted.
 */
public final class TimeSpan {
    private final LocalTime starttime;
    private final LocalTime endtime;

    /**
     * Create a new time span object after a consistency check.
     *
     * @param starttime
     *            Start time of the time span
     * @param endtime
     *            End time of the time span
     * @return The time span representation for the given data never null
     * @throws IllegalArgumentException
     *             If the start time is after the end time. Time spans can only
     *             be defined within a day.
     */
    static TimeSpan newTimeSpan(final LocalTime starttime, final LocalTime endtime) {
	checkNotNull(starttime);
	checkNotNull(endtime);
	checkState(endtime.isAfter(starttime));
	return new TimeSpan(starttime, endtime);
    }

    private TimeSpan(final LocalTime starttime, final LocalTime endtime) {
	this.starttime = starttime;
	this.endtime = endtime;
    }

    /**
     * @return Start time of the time span, never null
     */
    public LocalTime getStarttime() {
	return starttime;
    }

    /**
     * @return End time of the time span, never null
     */
    public LocalTime getEndtime() {
	return endtime;
    }

    @Override
    public int hashCode() {
	return Objects.hash(starttime, endtime);
    }

    @Override
    public boolean equals(final Object obj) {
	if (obj instanceof TimeSpan) {
	    TimeSpan ts = (TimeSpan) obj;
	    return Objects.equals(starttime, ts.starttime) && Objects.equals(endtime, ts.endtime);
	}
	return false;
    }

    @Override
    public String toString() {
	return MoreObjects.toStringHelper(this).add("Start time", starttime).add("End time", endtime).toString();
    }

    // /**
    // * <p>
    // * Vergleicht zwei Zeitspannen bez�glich �berschneidungsfreiheit
    // * </p>
    // *
    // * @param vergleich
    // * Vergleichszeitspanne
    // *
    // * @return Beide Zeitspannen sind �berschneidungsfrei
    // */
    // public boolean istUeberschneidungsfrei(TimeSpan vergleich) {
    // boolean back = true;
    //
    // if (startZeit.compareTo(vergleich.getStartZeit()) < 0) {
    // if (endeZeit.compareTo(vergleich.getStartZeit()) > 0) {
    // back = false;
    // }
    // } else if (startZeit.compareTo(vergleich.getStartZeit()) > 0) {
    // if (startZeit.compareTo(vergleich.getEndeZeit()) < 0) {
    // back = false;
    // }
    // } else if (startZeit.compareTo(vergleich.getStartZeit()) == 0) {
    // back = false;
    // }
    //
    // return back;
    // }
    //
    // /**
    // * <p>
    // * Berechnet den Abstand zwischen Ende- und Anfangszeitpunkt in Minuten
    // * </p>
    // *
    // * @return Minuten
    // */
    // public int berechneMinutenDifferenz() {
    // int minuten = 0;
    //
    // if (endeZeit != null) {
    // if (startZeit.getStunde() == endeZeit.getStunde()) {
    // minuten = endeZeit.getMinuten() - startZeit.getMinuten();
    // } else {
    // minuten = endeZeit.getMinuten() + 60 - startZeit.getMinuten();
    // minuten += (endeZeit.getStunde() - startZeit.getStunde() - 1) * 60;
    // }
    // } else {
    // throw new IllegalArgumentException("Keine Endezeit");
    // }
    //
    // return minuten;
    // }
    //
    // /**
    // * <p>
    // * Berechnet die �berschneidungszeit zweier Zeitspannen.
    // * </p>
    // *
    // * @param vergleich
    // * Vergleichszeitspanne
    // * @return Minuten
    // */
    // public int berechneUebereschneidungsMinuten(TimeSpan vergleich) {
    // int back = 0;
    //
    // if (!istUeberschneidungsfrei(vergleich)) {
    // if (startZeit.compareTo(vergleich.getStartZeit()) < 0) {
    // if (endeZeit.compareTo(vergleich.getEndeZeit()) < 0) {
    // back = new TimeSpan(vergleich.getStartZeit(), endeZeit)
    // .berechneMinutenDifferenz();
    // } else {
    // back = vergleich.berechneMinutenDifferenz();
    // }
    // } else {
    // if (endeZeit.compareTo(vergleich.getEndeZeit()) < 0) {
    // back = berechneMinutenDifferenz();
    // } else {
    // back = new TimeSpan(startZeit, vergleich.getEndeZeit())
    // .berechneMinutenDifferenz();
    // }
    // }
    // }
    //
    // return back;
    // }
}
