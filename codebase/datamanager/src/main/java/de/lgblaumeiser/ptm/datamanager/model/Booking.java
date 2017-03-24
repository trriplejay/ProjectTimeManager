/*
 * Copyright 2015, 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkState;
import static de.lgblaumeiser.ptm.datamanager.model.TimeSpan.newTimeSpan;
import static java.lang.Long.valueOf;
import static java.util.Objects.hash;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This class represents a booking on a day. It is represented by a starting
 * point in time and an ending point in time. An activity defines the action
 * done during that time. The action is a booking number on which work is booked
 * onto.
 */
public class Booking {
	private final LocalDate bookingday;
	private final LocalTime starttime;
	private final LocalTime endtime;
	private final Activity activity;
	private Long id;

	public static class BookingBuilder {
		private Long id = valueOf(-1);
		private LocalDate bookingday;
		private LocalTime starttime;
		private LocalTime endtime = null;
		private Activity activity;

		private BookingBuilder(final Booking booking) {
			id = booking.getId();
			bookingday = booking.getBookingday();
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
		 * @param bookingDay
		 *            Day for the booking
		 * @return The booking build as fluent api, non null
		 */
		public BookingBuilder setBookingday(LocalDate bookingDay) {
			this.bookingday = bookingDay;
			return this;
		}

		/**
		 * @param starttime
		 *            Start time for the booking to build
		 * @return The booking build as fluent api, non null
		 */
		public BookingBuilder setStarttime(final LocalTime starttime) {
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
			return new Booking(id, bookingday, starttime, endtime, activity);
		}

		private void checkData() {
			checkState(bookingday != null);
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
	 * @return A new booking builder, never null
	 */
	public static BookingBuilder newBooking() {
		return new BookingBuilder();
	}

	public BookingBuilder changeBooking() {
		return new BookingBuilder(this);
	}

	private Booking(final Long id, final LocalDate bookingday, final LocalTime starttime, final LocalTime endtime,
			final Activity activity) {
		this.id = id;
		this.bookingday = bookingday;
		this.starttime = starttime;
		this.endtime = endtime;
		this.activity = activity;
	}

	public LocalDate getBookingday() {
		return bookingday;
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
	 * @return The internal id of the booking. Automatically created by storage
	 *         system
	 */
	public Long getId() {
		return id;
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
		return hash(id, bookingday, starttime, endtime, activity);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Booking) {
			Booking bkg = (Booking) obj;
			return id == bkg.id && bookingday.equals(bkg.bookingday) && starttime.equals(bkg.starttime)
					&& activity.equals(bkg.activity) && endtime != null ? endtime.equals(bkg.endtime)
							: bkg.endtime == null;
		}
		return false;
	}

	@Override
	public String toString() {
		return toStringHelper(this).omitNullValues().add("Bookingday", bookingday).add("Starttime", starttime)
				.add("Endtime", endtime).add("Activity", activity).add("Id", id).toString();
	}
}
