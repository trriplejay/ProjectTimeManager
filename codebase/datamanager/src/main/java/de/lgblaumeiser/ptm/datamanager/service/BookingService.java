/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.service;

import java.time.LocalDate;
import java.time.LocalTime;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;

/**
 * Class which offers the services needed for entering the bookings of a day
 */
public interface BookingService {
    /**
     * Create a new day bookings object for a day
     *
     * @param day
     *            The day for which bookings should be stored
     * @return A DayBookings object, never null
     */

    DayBookings createNewDayBookings(LocalDate day);

    /**
     * Add a booking which starts at the time the last booking ended
     *
     * @param activity
     *            The activity of the booking
     * @return The created Booking object, never null
     * @throws IllegalStateException
     *             If this is the first booking or the last booking has no
     *             defined ending
     */
    Booking addBooking(DayBookings dayBookings, Activity activity);

    /**
     * Add a booking at the corresponding starttime. If the last bogoking has no
     * ended this booking ends the previous booking at the given starttime.
     *
     * @param dayBookings
     *            The day for which the booking should be created
     * @param activity
     *            The activity of the booking
     * @param starttime
     *            The starttime of the booking
     * @return The created Booking object, never null
     * @throws IllegalStateException
     *             If the starttime is already within a booked time or the
     *             previous booking has already an endtime
     */
    Booking addBooking(DayBookings dayBookings, Activity activity, LocalTime starttime);

    /**
     * Ends the given booking with the given endtime.
     *
     * @param dayBookings
     *            The day for which the booking should be created
     * @param booking
     *            The booking for which an endtime is added
     * @param endtime
     *            The endtime of the booking
     * @return The created Booking object, never null
     * @throws IllegalStateException
     *             If a booking overlap is added
     */
    Booking endBooking(DayBookings dayBookings, Booking booking, LocalTime endtime);

    /**
     * Removes the given booking, might create a gap in the days bookings, since no other booking is changed.
     *
     * @param dayBookings
     *            The day for which the booking should be created
     * @param booking
     *            The booking to delete
     */
    void removeBooking(DayBookings dayBookings, Booking booking);
}
