/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static de.lgblaumeiser.ptm.util.Utils.assertState;
import static de.lgblaumeiser.ptm.util.Utils.stringHasContent;
import static java.lang.Long.valueOf;

import de.lgblaumeiser.ptm.datamanager.model.internal.ActivityImpl;

/**
 * Data structure for representation of an activity. An activity represents a
 * booking number under which work is done. Each work booking is assigned to an
 * activity and there are reports on hours spent on activities.
 */
public interface Activity {
	static class ActivityBuilder {
		private Long id = valueOf(-1);
		private String activityName;
		private String bookingNumber;
		private boolean hidden = false;

		private ActivityBuilder(final Activity activity) {
			id = activity.getId();
			activityName = activity.getActivityName();
			bookingNumber = activity.getBookingNumber();
			hidden = activity.isHidden();
		}

		private ActivityBuilder() {
			// Nothing to do
		}

		public ActivityBuilder setId(final Long id) {
			this.id = id;
			return this;
		}

		public ActivityBuilder setActivityName(final String activityName) {
			this.activityName = activityName;
			return this;
		}

		public ActivityBuilder setBookingNumber(final String bookingNumber) {
			this.bookingNumber = bookingNumber;
			return this;
		}

		public ActivityBuilder setHidden(final boolean hidden) {
			this.hidden = hidden;
			return this;
		}

		/**
		 * @return An unmodifiable activity representing the data given to the builder,
		 *         Non null, returns with exception if the data is invalid
		 */
		public Activity build() {
			checkData();
			return new ActivityImpl(id, activityName, bookingNumber, hidden);
		}

		private void checkData() {
			assertState(stringHasContent(activityName));
			assertState(stringHasContent(bookingNumber));
		}
	}

	/**
	 * Creates a new activity builder with no data set.
	 *
	 * @return A new activity builder, never null
	 */
	static ActivityBuilder newActivity() {
		return new ActivityBuilder();
	}

	/**
	 * Change an existing activity by providing a builder preset with the activity
	 * data
	 *
	 * @return A new activity builder, never null
	 */
	default ActivityBuilder changeActivity() {
		return new ActivityBuilder(this);
	};

	/**
	 * @return The internal id of the activity. Automatically created by storage
	 *         system
	 */
	Long getId();

	/**
	 * @return Name of the activity. Non null
	 */
	String getActivityName();

	/**
	 * @return Booking number of the activities category. Non null
	 */
	String getBookingNumber();

	/**
	 * @return true, if activity is hidden
	 */
	boolean isHidden();
}
