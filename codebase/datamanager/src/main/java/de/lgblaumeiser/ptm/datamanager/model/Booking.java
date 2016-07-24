/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static com.google.common.base.Preconditions.checkState;

import java.time.LocalTime;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.annotation.NonNull;

import com.google.common.base.MoreObjects;

/**
 * This class represents a booking on a day. It is represented by a starting
 * point in time and an ending point in time. An activity defines the action
 * done during that time. The action is a booking number on which work is booked
 * onto.
 */
public class Booking {
    @NonNull
    private final LocalTime starttime;
    private final LocalTime endtime;
    @NonNull
    private final Activity activity;
    @NonNull
    private final String comment;

    public static class BookingBuilder {
	private LocalTime starttime;
	private LocalTime endtime = null;
	private Activity activity;
	@SuppressWarnings("null")
	@NonNull
	private String comment = StringUtils.EMPTY;

	private BookingBuilder(final Booking booking) {
	    starttime = booking.getStarttime();
	    if (booking.hasEndtime()) {
		endtime = booking.getEndtime();
	    }
	    activity = booking.getActivity();
	    comment = booking.getComment();
	}

	private BookingBuilder() {
	    // Nothing to do
	}

	@NonNull
	public BookingBuilder setStarttime(@NonNull final LocalTime starttime) {
	    this.starttime = starttime;
	    return this;
	}

	@NonNull
	public BookingBuilder setEndtime(final LocalTime endtime) {
	    this.endtime = endtime;
	    return this;
	}

	@NonNull
	public BookingBuilder setComment(@NonNull final String comment) {
	    this.comment = comment;
	    return this;
	}

	@NonNull
	public BookingBuilder setActivity(@NonNull final Activity activity) {
	    this.activity = activity;
	    return this;
	}

	@SuppressWarnings("null")
	@NonNull
	public Booking build() {
	    checkData();
	    return new Booking(starttime, endtime, activity, comment);
	}

	private void checkData() {
	    checkState(starttime != null);
	    checkState(activity != null);

	    if (endtime != null) {
		checkState(endtime.isAfter(starttime));
	    }
	}
    }

    /**
     * Creates a new booking builder with no data set.
     *
     * @return A new booking builder
     */
    @NonNull
    public static BookingBuilder newBooking() {
	return new BookingBuilder();
    }

    public BookingBuilder changeBooking() {
	return new BookingBuilder(this);
    }

    private Booking(@NonNull final LocalTime starttime, final LocalTime endtime, @NonNull final Activity activity,
	    @NonNull final String comment) {
	this.starttime = starttime;
	this.endtime = endtime;
	this.activity = activity;
	this.comment = comment;
    }

    /**
     * @return Start time of the booking
     */
    @NonNull
    public LocalTime getStarttime() {
	return starttime;
    }

    /**
     * @return Whether booking already has an end time
     */
    public boolean hasEndtime() {
	return endtime != null;
    }

    /**
     * @return End time of the booking or null if not set
     */
    public LocalTime getEndtime() {
	return endtime;
    }

    /**
     * @return Activity of the booking
     */
    @NonNull
    public Activity getActivity() {
	return activity;
    }

    /**
     * @return Comment of the booking if available, an empty string if no
     *         comment given
     */
    @NonNull
    public String getComment() {
	return comment;
    }

    /**
     * @return Calculates the duration of the booking. Is only allowed if end
     *         time is given
     * @throws IllegalStateException
     *             If no end time is given
     */
    @SuppressWarnings("null")
    public TimeSpan calculateTimeSpan() {
	checkState(hasEndtime());
	return TimeSpan.newTimeSpan(starttime, endtime);
    }

    @Override
    public int hashCode() {
	return java.util.Objects.hash(starttime, endtime, activity, comment);
    }

    @Override
    public boolean equals(final Object obj) {
	if (obj instanceof Booking) {
	    Booking bkg = (Booking) obj;
	    return Objects.equals(starttime, bkg.starttime) && Objects.equals(endtime, bkg.endtime)
		    && Objects.equals(activity, bkg.activity) && Objects.equals(comment, bkg.comment);

	}
	return false;
    }

    @Override
    public String toString() {
	return MoreObjects.toStringHelper(this).omitNullValues().add("Starttime", starttime).add("Endtime", endtime)
		.add("Activity", activity).add("Comment", comment).toString();
    }
}
