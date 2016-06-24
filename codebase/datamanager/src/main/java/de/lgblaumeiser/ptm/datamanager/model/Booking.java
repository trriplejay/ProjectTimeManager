/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import java.util.Objects;
import java.time.LocalTime;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.annotation.NonNull;
import com.google.common.base.Preconditions;
import com.google.common.base.MoreObjects;

/**
 * This class represents a booking on a day. It is represented by a starting point
 * in time and an ending point in time. An activity defines the action done during
 * that time. The action is a booking number on which work is booked onto.  
 */
public class Booking {
	@NonNull
	private final LocalTime starttime;
	private final LocalTime endtime;
	@NonNull
	private final Activity activity;
	private final String comment;

	/**
	 * Creates a new booking based on a start time and an activity.
	 * A booking with an end time can only be created by an booking and an end time.
	 * 
	 * @param starttime The start time of the booking
	 * @param activity The end time of the booking 
	 * @return A Booking object representing the booking
	 */
	@NonNull
	public static Booking newBooking(@NonNull LocalTime starttime, @NonNull Activity activity) {
		return new Booking(starttime, null, activity, null);
	}
	
	/**
	 * Creates a new booking based on a start time, an activity and a comment.
	 * A booking with an end time can only be created by an booking and an end time.
	 * 
	 * @param starttime The start time of the booking
	 * @param activity The end time of the booking 
	 * @param comment A comment for the booking
	 * @return A Booking object representing the booking
	 */
	@NonNull
	public static Booking newBooking(@NonNull LocalTime starttime, @NonNull Activity activity, @NonNull String comment) {
		return new Booking (starttime, null, activity, comment);
	}
	
	/**
	 * Creates a booking with an end time based on a previous booking.
	 * The original booking remains unchanged. If the given booking already has an end time,
	 * this will be overwritten. If the end time is earlier than the start time, an
	 * IllegalStateException will be thrown. Bookings over a day border are not allowed.
	 * 
	 * @param booking The booking with the start time given
	 * @param endtime The end time for the new booking
	 * @return A new booking object with the original data but the end time set
	 * @throws IllegalStateException If the end time is earlier than the start time
	 */
	@NonNull
	public static Booking endBooking(@NonNull Booking booking, @NonNull LocalTime endtime) {
		Preconditions.checkArgument(endtime.isAfter(booking.starttime));
		return new Booking (booking.starttime, endtime, booking.activity, booking.comment);
	}
	
	private Booking(LocalTime starttime, LocalTime endtime, Activity activity, String comment) {
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
	 * @return End time of the booking
	 * @throws IllegalStateException If the booking has not an end time
	 */
	@NonNull
	public LocalTime getEndtime() {
		Preconditions.checkState(hasEndtime());
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
	 * @return Comment of the booking if available, an empty string if no comment given
	 */
	@NonNull
	public String getComment() {
		return (comment != null) ? comment : StringUtils.EMPTY;
	}

	/**
	 * @return Calculates the duration of the booking. Is only allowed if end time is given
	 * @throws IllegalStateException If no end time is given
	 */
	public TimeSpan calculateTimeSpan() {
		Preconditions.checkState(hasEndtime());
		return TimeSpan.newTimeSpan(starttime, endtime);
	}

	
	@Override
	public int hashCode() {
		return java.util.Objects.hash(starttime, endtime, activity, comment);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Booking) {
			Booking bkg = (Booking)obj;
			return Objects.equals(starttime, bkg.starttime) &&
					Objects.equals(endtime, bkg.endtime) &&
					Objects.equals(activity, bkg.activity) &&
					Objects.equals(comment, bkg.comment);
			
		}
		return false;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			       .omitNullValues()
			       .add("Starttime", starttime)
			       .add("Endtime", endtime)
			       .add("Activity", activity)
			       .add("Comment", comment)
			       .toString();
	}
}
