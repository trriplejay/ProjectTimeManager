/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static de.lgblaumeiser.ptm.util.Utils.assertState;
import static de.lgblaumeiser.ptm.util.Utils.emptyString;
import static java.lang.Long.valueOf;

import java.time.LocalDate;
import java.time.LocalTime;

import de.lgblaumeiser.ptm.datamanager.model.internal.BookingImpl;

/**
 * This class represents a booking on a day. It is represented by a starting
 * point in time and an ending point in time. An activity defines the action
 * done during that time. The action is a booking number on which work is booked
 * onto.
 */
public interface Booking {
	static class BookingBuilder {
		private Long id = valueOf(-1);
		private LocalDate bookingday;
		private LocalTime starttime;
		private LocalTime endtime = null;
		private Long activity;
		private String user;
		private String comment = emptyString();

		private BookingBuilder(final Booking booking) {
			id = booking.getId();
			bookingday = booking.getBookingday();
			user = booking.getUser();
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

		/**
		 * @param bookingDay Day for the booking
		 * @return The booking build as fluent api, non null
		 */
		public BookingBuilder setBookingday(final LocalDate bookingDay) {
			this.bookingday = bookingDay;
			return this;
		}

		/**
		 * @param starttime Start time for the booking to build
		 * @return The booking build as fluent api, non null
		 */
		public BookingBuilder setStarttime(final LocalTime starttime) {
			this.starttime = starttime;
			return this;
		}

		/**
		 * @param endtime End time for the booking to build
		 * @return The booking build as fluent api, non null
		 */
		public BookingBuilder setEndtime(final LocalTime endtime) {
			this.endtime = endtime;
			return this;
		}

		/**
		 * @param activity The if of the activity of the booking to build
		 * @return The booking build as fluent api, non null
		 */
		public BookingBuilder setActivity(final Long activity) {
			this.activity = activity;
			return this;
		}

		/**
		 * @param user The user of the booking to build
		 * @return The booking build as fluent api, non null
		 */
		public BookingBuilder setUser(final String user) {
			this.user = user;
			return this;
		}

		/**
		 * @param comment A comment of the booking to build
		 * @return The booking build as fluent api, non null
		 */
		public BookingBuilder setComment(final String comment) {
			this.comment = comment;
			return this;
		}

		/**
		 * @return An unmodifiable booking representing the data given to the builder,
		 *         Non null, returns with exception if the data is invalid
		 */
		public Booking build() {
			checkData();
			return new BookingImpl(id, bookingday, user, starttime, endtime, activity, comment);
		}

		private void checkData() {
			assertState(bookingday != null);
			assertState(starttime != null);
			assertState(activity != null);
			assertState(user != null);
			assertState(comment != null);

			if (endtime != null) {
				assertState(endtime.isAfter(starttime));
			}
		}
	}

	/**
	 * Creates a new booking builder with no data set.
	 *
	 * @return A new booking builder, never null
	 */
	static BookingBuilder newBooking() {
		return new BookingBuilder();
	}

	/**
	 * Change an existing booking by providing a builder preset with the booking
	 * data
	 *
	 * @return A new booking builder, never null
	 */
	default BookingBuilder changeBooking() {
		return new BookingBuilder(this);
	}

	/**
	 * 
	 * @return Bookingday of the booking, never null
	 */
	public LocalDate getBookingday();

	/**
	 * @return Start time of the booking, never null
	 */
	public LocalTime getStarttime();

	/**
	 * @return Whether booking already has an end time
	 */
	public boolean hasEndtime();

	/**
	 * @return End time of the booking or null if not set
	 */
	public LocalTime getEndtime();

	/**
	 * @return Activity of the booking, never null
	 */
	public Long getActivity();

	/**
	 * @return User for whom booking was made, never null
	 */
	public String getUser();

	/**
	 * @return A comment if available, an empty string of not, never null
	 */
	public String getComment();

	/**
	 * @return The internal id of the booking. Automatically created by storage
	 *         system
	 */
	public Long getId();
}
