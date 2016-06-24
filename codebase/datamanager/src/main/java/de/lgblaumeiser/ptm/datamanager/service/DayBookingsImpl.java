/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;

/**
 * The implementation of the DayBookings service
 */
class DayBookingsImpl implements DayBookings {
    private final LocalDate day;
    private final List<Booking> bookings = Lists.newArrayList();

    /**
     * Create a DayBooking service implementation
     *
     * @param day
     *            The day for which bookings are stored.
     */
    DayBookingsImpl(final LocalDate day) {
	this.day = day;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.lgblaumeiser.ptm.datamanager.service.DayBookings#getBookings()
     */
    @Override
    public List<Booking> getBookings() {
	return Collections.unmodifiableList(bookings);
    }

    /*
     * (non-Javadoc)
     *
     * @see de.lgblaumeiser.ptm.datamanager.service.DayBookings#getDay()
     */
    @Override
    public LocalDate getDay() {
	return day;
    }

    @Override
    public Booking addBooking(final Activity activity) {
	Preconditions.checkState(CollectionUtils.isNotEmpty(bookings));
	// Booking lastBooking = Iterables.getLast(bookings);
	// Preconditions.checkState(lastBooking.hasEndtime());
	// return addBooking(activity, lastBooking.getEndtime());
	return null;
    }

    @Override
    public Booking addBooking(final Activity activity, final LocalTime starttime) {
	// checkTime(starttime);
	// Booking lastBooking = Iterables.getLast(bookings);
	// if (!lastBooking.hasEndtime()) {
	// endBooking(lastBooking, starttime);
	// }
	Booking newBooking = Booking.newBooking(starttime, activity);
	bookings.add(newBooking);
	return newBooking;
    }

    @Override
    public Booking endBooking(final Booking booking, final LocalTime endtime) {
	Preconditions.checkState(bookings.remove(booking));
	Booking endedBooking = Booking.endBooking(booking, endtime);
	bookings.add(endedBooking);
	return endedBooking;
    }

    @Override
    public Booking refactorBooking(final Booking booking, final LocalTime starttime, final LocalTime endtime) {
	// TODO Auto-generated method stub
	return null;
    }

    private void checkTime(final LocalTime starttime) {
	// TODO Auto-generated method stub

    }

}
