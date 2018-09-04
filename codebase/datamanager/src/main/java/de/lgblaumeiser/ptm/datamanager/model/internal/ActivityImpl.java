/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.datamanager.model.internal;

import static java.lang.Long.valueOf;
import static java.lang.String.format;
import static java.util.Objects.hash;

import de.lgblaumeiser.ptm.datamanager.model.Activity;

/**
 * Data structure for representation of an activity. An activity represents a
 * booking number under which work is done. Each work booking is assigned to an
 * activity and there are reports on hours spent on activities.
 */
public class ActivityImpl implements Activity {
	private String activityName;
	private String bookingNumber;
	private boolean hidden = false;
	private Long id = valueOf(-1);

	public ActivityImpl(final Long id, final String activityName, final String bookingNumber, final boolean hidden) {
		this.id = id;
		this.activityName = activityName;
		this.bookingNumber = bookingNumber;
		this.hidden = hidden;
	}

	public ActivityImpl() {
		// Only needed for deserialization
	}

	/**
	 * @return The internal id of the activity. Automatically created by storage
	 *         system
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return Name of the activity. Non null
	 */
	public String getActivityName() {
		return activityName;
	}

	/**
	 * @param activityName the activityName to set
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	/**
	 * @return Booking number of the activities category. Non null
	 */
	public String getBookingNumber() {
		return bookingNumber;
	}

	/**
	 * @param bookingNumber the bookingNumber to set
	 */
	public void setBookingNumber(String bookingNumber) {
		this.bookingNumber = bookingNumber;
	}

	/**
	 * @return true, if activity is hidden
	 */
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * @param hidden the hidden to set
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	@Override
	public String toString() {
		return format("Activity: Booking Number: %s, Name: %s, Hidden: %b, Id: %d", bookingNumber, activityName, hidden,
				id);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof ActivityImpl) {
			ActivityImpl act = (ActivityImpl) obj;
			return id == act.id && hidden == act.isHidden() && activityName.equals(act.activityName)
					&& bookingNumber.equals(act.bookingNumber);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return hash(id, activityName, bookingNumber);
	}
}
