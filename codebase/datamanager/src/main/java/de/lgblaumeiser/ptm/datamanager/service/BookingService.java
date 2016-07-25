/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.eclipse.jdt.annotation.NonNull;

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
     * @return A DayBookings object
     */
    DayBookings createNewDayBookings(@NonNull LocalDate day);

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
    @NonNull
    Booking addBooking(@NonNull DayBookings dayBookings, @NonNull Activity activity);

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
     * @return The created Booking object
     * @throws IllegalStateException
     *             If the starttime is already within a booked time or the
     *             previous booking has already an endtime
     */
    @NonNull
    Booking addBooking(@NonNull DayBookings dayBookings, @NonNull Activity activity, @NonNull LocalTime starttime);

    /**
     * Ends the given booking with the given endtime.
     *
     * @param dayBookings
     *            The day for which the booking should be created
     * @param booking
     *            The booking for which an endtime is added
     * @param endtime
     *            The endtime of the booking
     * @return The created Booking object
     * @throws IllegalStateException
     *             If the last booking has an end or if an booking overlap is
     *             added
     */
    @NonNull
    Booking endBooking(@NonNull DayBookings dayBookings, @NonNull Booking booking, @NonNull LocalTime endtime);

    /**
     * Removes the given booking, the endtime of the previous booking will be
     * set to the endtime of the deleted booking. If the booking is the first,
     * it will simply be deleted.
     *
     * @param dayBookings
     *            The day for which the booking should be created
     * @param booking
     *            The booking to delete
     */
    void removeBooking(@NonNull DayBookings dayBookings, @NonNull Booking booking);

    /**
     * Splits the booking at the splittime into two
     *
     * @param dayBookings
     *            The day for which the booking should be created
     * @param booking
     *            The booking to split
     * @param splittime
     *            The time, where to split the booking
     * @return The first of the create Booking objects
     */
    @NonNull
    Booking splitBooking(@NonNull DayBookings dayBookings, @NonNull Booking booking, @NonNull LocalTime splittime);

    /**
     * Changes the activity of the booking
     *
     * @param dayBookings
     *            The day for which the booking should be created
     * @param booking
     *            The booking to change the activity
     * @param activity
     *            The new activity of the booking
     * @return The changed Booking object
     * @throws IllegalStateException
     *             If booking is unknown or the activity is already set
     */
    @NonNull
    Booking changeActivity(@NonNull DayBookings dayBookings, @NonNull Booking booking, @NonNull Activity activity);

    /**
     * Change start and endtime of the given Booking. Bookings prior or after
     * this booking will be adapted. according their end resp. starttime. If the
     * new times will overlap the previous or next booking completely, the
     * method returns with an error.
     *
     * @param dayBookings
     *            The day for which the booking should be created
     * @param booking
     *            The booking to change
     * @param starttime
     *            The new starttime of the booking
     * @param endtime
     *            The new endtime of the booking
     * @param comment
     *            Optional comment for the booking
     * @return The changed Booking object
     * @throws IllegalStateException
     *             If the booking times overlap or are not in the right order
     */
    @NonNull
    Booking changeBookingTimes(@NonNull DayBookings dayBookings, @NonNull Booking booking, @NonNull LocalTime starttime,
	    @NonNull LocalTime endtime);

    /**
     * Change the comment of the booking
     *
     * @param dayBookings
     *            The day for which the booking should be created
     * @param booking
     *            The booking to change
     * @param comment
     *            The new comment of the booking
     * @return The change booking object
     */
    @NonNull
    Booking changeComment(@NonNull DayBookings dayBookings, @NonNull Booking booking, @NonNull String comment);

    /**
     * Delete the comment of the booking
     *
     * @param dayBookings
     *            The day for which the booking should be created
     * @param booking
     *            The booking to change
     * @return The change booking object
     */
    @NonNull
    Booking deleteComment(@NonNull DayBookings dayBookings, @NonNull Booking booking);

    /**
     * Create booking for day
     */
    @NonNull
    static BookingService createBookingService() {
	return new BookingServiceImpl();
    }
}
