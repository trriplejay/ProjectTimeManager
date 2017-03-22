/*
 * Copyright 2015, 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.Long.valueOf;
import static java.util.Objects.hash;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Data structure for representation of an activity. An activity represents a
 * booking number under which work is done. Each work booking is assigned to an
 * activity and there are reports on hours spent on activities.
 */
public class Activity {
	private final String activityName;
	private final String bookingNumber;
	private Long id = valueOf(-1);

	/**
	 * Create new activity
	 *
	 * @param activityName
	 *            Name of the new activity
	 * @param bookingNumber
	 *            booking number of the new activity
	 * @return The freshly created activity. Non null since inability of
	 *         creating the activity results in runtime exception
	 */
	public static Activity newActivity(final String activityName, final String bookingNumber) {
		checkState(isNotBlank(activityName));
		checkState(isNotBlank(bookingNumber));
		return new Activity(activityName, bookingNumber);
	}

	private Activity(final String activityName, final String bookingNumber) {
		this.activityName = activityName;
		this.bookingNumber = bookingNumber;
	}

	/**
	 * @return The internal id of the activity. Automatically created by storage
	 *         system
	 */
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
		return toStringHelper(this).add("Booking Number", bookingNumber).add("Activity", activityName).toString();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Activity) {
			Activity act = (Activity) obj;
			return id == act.id && activityName.equals(act.activityName) && bookingNumber.equals(act.bookingNumber);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return hash(id, activityName, bookingNumber);
	}
}
