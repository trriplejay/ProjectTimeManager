/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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

    public Duration getLengthInMinutes() {
	return Duration.ofMinutes(ChronoUnit.MINUTES.between(starttime, endtime));
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
}
