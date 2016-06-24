/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;

/**
 * Class which offers the services needed for entering the bookings of a day
 */
public interface DayBookings {
    /**
     * @return All stored bookings of the day
     */
    List<Booking> getBookings();

    /**
     * @return The day for which the bookings are stored
     */
    LocalDate getDay();

    /**
     * Add a booking which starts at the time the last booking ended
     *
     * @param activity
     *            The activity of the booking
     * @return The created Booking object
     * @throws IllegalStateException
     *             If this is the first booking or the last booking has no
     *             defined ending
     */
    Booking addBooking(Activity activity);

    /**
     * Add a booking at the corresponding starttime. If the last booking has no
     * ended this booking ends the previous booking at the given starttime.
     *
     * @param activity
     *            The activity of the booking
     * @param starttime
     *            The starttime of the booking
     * @return The created Booking object
     * @throws IllegalStateException
     *             If the starttime is already within a booked time
     */
    Booking addBooking(Activity activity, LocalTime starttime);

    /**
     * Ends the given booking with the given endtime.
     *
     * @param booking
     *            The booking for which an endtime is added
     * @param endtime
     *            The endtime of the booking
     * @return The created Booking object
     * @throws IllegalStateException
     *             If the last booking has an end or if an booking overlap is
     *             added
     */
    Booking endBooking(Booking booking, LocalTime endtime);

    /**
     * Change start and endtime of the given Booking
     *
     * @param booking
     *            The booking to change
     * @param starttime
     *            The new starttime of the booking
     * @param endtime
     *            The new endtime of the booking
     * @return The changed Booking object
     * @throws IllegalStateException
     *             If the booking times overlap or are not in the right order
     */
    Booking refactorBooking(Booking booking, LocalTime starttime, LocalTime endtime);

    /**
     * Create booking for day
     */
    static DayBookings createDayBookings(final LocalDate date) {
	return new DayBookingsImpl(date);
    }
}
