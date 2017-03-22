/*
 * Copyright 2015, 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static de.lgblaumeiser.ptm.datamanager.model.TimeSpan.newTimeSpan;
import static java.util.Objects.hash;

import java.time.LocalTime;

/**
 * This class represents a booking on a day. It is represented by a starting
 * point in time and an ending point in time. An activity defines the action
 * done during that time. The action is a booking number on which work is booked
 * onto.
 */
public class Booking {
	private final LocalTime starttime;
	private final LocalTime endtime;
	private final Activity activity;

	public static class BookingBuilder {
		private LocalTime starttime;
		private LocalTime endtime = null;
		private Activity activity;

		private BookingBuilder(final Booking booking) {
			starttime = booking.getStarttime();
			if (booking.hasEndtime()) {
				endtime = booking.getEndtime();
			}
			activity = booking.getActivity();
		}

		private BookingBuilder() {
			// Nothing to do
		}

		/**
		 * @param starttime
		 *            Start time for the booking to build
		 * @return The booking build as fluent api, non null
		 */
		public BookingBuilder setStarttime(final LocalTime starttime) {
			checkNotNull(starttime);
			this.starttime = starttime;
			return this;
		}

		/**
		 * @param endtime
		 *            End time for the booking to build
		 * @return The booking build as fluent api, non null
		 */
		public BookingBuilder setEndtime(final LocalTime endtime) {
			this.endtime = endtime;
			return this;
		}

		/**
		 * @param activity
		 *            The activity of the booking to build
		 * @return The booking build as fluent api, non null
		 */
		public BookingBuilder setActivity(final Activity activity) {
			checkNotNull(activity);
			this.activity = activity;
			return this;
		}

		/**
		 * @return An unmodifiable booking representing the data given to the
		 *         builder, Non null, returns with exception if the data is
		 *         invalid
		 */
		public Booking build() {
			checkData();
			return new Booking(starttime, endtime, activity);
		}

		private void checkData() {
			checkNotNull(starttime);
			checkNotNull(activity);

			if (endtime != null) {
				checkState(endtime.isAfter(starttime));
			}
		}
	}

	/**
	 * Creates a new booking builder with no data set.
	 *
	 * @return A new booking builder, never null
	 */
	public static BookingBuilder newBooking() {
		return new BookingBuilder();
	}

	public BookingBuilder changeBooking() {
		return new BookingBuilder(this);
	}

	private Booking(final LocalTime starttime, final LocalTime endtime, final Activity activity) {
		this.starttime = starttime;
		this.endtime = endtime;
		this.activity = activity;
	}

	/**
	 * @return Start time of the booking, never null
	 */
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
	 * @return Activity of the booking, never null
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * @return Calculates the duration of the booking. Is only allowed if end
	 *         time is given
	 * @throws IllegalStateException
	 *             If no end time is given
	 */
	public TimeSpan calculateTimeSpan() {
		checkState(hasEndtime());
		return newTimeSpan(starttime, endtime);
	}

	@Override
	public int hashCode() {
		return hash(starttime, endtime, activity);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Booking) {
			Booking bkg = (Booking) obj;
			return starttime.equals(bkg.starttime) && activity.equals(bkg.activity) && endtime != null
					? endtime.equals(bkg.endtime) : bkg.endtime == null;
		}
		return false;
	}

	@Override
	public String toString() {
		return toStringHelper(this).omitNullValues().add("Starttime", starttime).add("Endtime", endtime)
				.add("Activity", activity).toString();
	}
}
