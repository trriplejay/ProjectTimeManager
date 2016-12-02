/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Iterables.getLast;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.unmodifiableList;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Model class to store all bookings of a day
 */
public class DayBookings {
	private final LocalDate day;
	private final List<Booking> bookings = newArrayList();

	/**
	 * Create a new day bookings object
	 *
	 * @param day
	 *            The day for which the object should be created
	 * @return A new instance, never null
	 */
	public static DayBookings newDay(final LocalDate day) {
		checkNotNull(day);
		return new DayBookings(day);
	}

	/**
	 * Create a DayBooking object for a day
	 *
	 * @param day
	 *            The day for which bookings are stored.
	 */
	private DayBookings(final LocalDate day) {
		this.day = day;
	}

	/**
	 * @return Return all bookings stored in an unmodifiable list, never null
	 */
	public List<Booking> getBookings() {
		return unmodifiableList(bookings);
	}

	/**
	 * @return The booking entered last or null if no booking is stored
	 */
	public Booking getLastBooking() {
		return getLast(bookings, null);
	}

	/**
	 * @return Return the for which bookings are stored, never null
	 */
	public LocalDate getDay() {
		return day;
	}

	/**
	 * Add a booking into the list. No validation is done.
	 *
	 * @param booking
	 *            The booking to add
	 * @return The object itself to add fluently, never null
	 * @throws IllegalStateException
	 *             If the last booking is still open ended
	 */
	public DayBookings addBooking(final Booking booking) {
		checkNotNull(booking);
		Booking last = getLastBooking();
		checkState(last == null || last.hasEndtime());
		bookings.add(booking);
		return this;
	}

	/**
	 * Remove a booking from the list. No validation is done.
	 *
	 * @param booking
	 *            The booking to remove
	 * @return The object itself to change fluently, never null
	 */
	public DayBookings removeBooking(final Booking booking) {
		checkNotNull(booking);
		bookings.remove(booking);
		return this;
	}

	/**
	 * Replace a booking in the list. Add is only done if the oldBooking could
	 * be removed
	 *
	 * @param oldBooking
	 *            The booking to remove
	 * @param newBooking
	 *            The booking to add
	 * @return The object itself to change fluently, never null
	 * @throws IllegalStateException
	 *             If the oldBooking could not be removed
	 */
	public DayBookings replaceBooking(final Booking oldBooking, final Booking newBooking) {
		checkNotNull(oldBooking);
		checkNotNull(newBooking);
		int index = bookings.indexOf(oldBooking);
		checkState(index >= 0);
		if (!newBooking.hasEndtime()) {
			checkState(index == bookings.size() - 1);
		}
		bookings.remove(oldBooking);
		bookings.add(index, newBooking);
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(day, bookings);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof DayBookings) {
			DayBookings other = (DayBookings) obj;
			return Objects.equals(day, other.day) && Objects.equals(bookings, other.bookings);
		}
		return super.equals(false);
	}

}
