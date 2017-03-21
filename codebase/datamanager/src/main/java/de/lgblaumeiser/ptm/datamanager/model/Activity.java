/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static com.google.common.base.Preconditions.checkState;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.MoreObjects;

/**
 * Data structure for representation of an activity. An activity represents a
 * booking number under which work is done. Each work booking is assigned to an
 * activity and there are reports on hours spent on activities.
 */
public class Activity {
	private final String activityName;
	private final String bookingNumber;
	private Long id = Long.valueOf(-1);

	/**
	 * Create new line activity
	 *
	 * @param activityName
	 *            Name of the new activity
	 * @param bookingNumber
	 *            booking number of the new activity
	 * @return The freshly created activity. Non null since inability of
	 *         creating the activity results in runtime exception
	 */
	public static Activity newActivity(final String activityName, final String bookingNumber) {
		checkState(StringUtils.isNotBlank(activityName));
		checkState(StringUtils.isNotBlank(bookingNumber));
		return new Activity(activityName, bookingNumber);
	}

	private Activity(final String activityName, final String bookingNumber) {
		this.activityName = activityName;
		this.bookingNumber = bookingNumber;
	}

	public Long getId() {
		return id;
	}

	/**
	 * @return Name of the activity. Non null
	 */
	public String getActivityName() {
		return activityName;
	}

	/**
	 * @return Booking number of the activities category. Non null
	 */
	public String getBookingNumber() {
		return bookingNumber;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("Booking Number", bookingNumber).add("Activity", activityName)
				.toString();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Activity) {
			Activity act = (Activity) obj;
			return Objects.equals(activityName, act.getActivityName())
					&& Objects.equals(bookingNumber, act.getBookingNumber());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(activityName, bookingNumber);
	}
}
