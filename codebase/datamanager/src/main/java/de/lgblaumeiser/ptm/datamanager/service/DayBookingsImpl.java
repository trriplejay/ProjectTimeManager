/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.eclipse.jdt.annotation.NonNull;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
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

    @Override
    @NonNull
    public List<Booking> getBookings() {
	return Collections.unmodifiableList(bookings);
    }

    @Override
    @NonNull
    public Booking getLastBooking() {
	return Iterables.getLast(bookings, null);
    }

    @Override
    @NonNull
    public LocalDate getDay() {
	return day;
    }

    @Override
    @NonNull
    public Booking addBooking(@NonNull final Activity activity, final String comment) {
	Preconditions.checkState(CollectionUtils.isNotEmpty(bookings));
	Booking lastBooking = getLastBooking();
	return addBooking(activity, lastBooking.getEndtime(), comment);
    }

    @Override
    @NonNull
    public Booking addBooking(@NonNull final Activity activity, @NonNull final LocalTime starttime,
	    final String comment) {
	Booking lastBooking = getLastBooking();
	if (lastBooking != null) {
	    endBooking(lastBooking, starttime);
	}
	Booking newBooking = Booking.newBooking(starttime, activity, comment);
	bookings.add(newBooking);
	return newBooking;
    }

    @Override
    @NonNull
    public Booking endBooking(final Booking booking, final LocalTime endtime) {
	Preconditions.checkState(bookings.remove(booking));
	Preconditions.checkState(!booking.hasEndtime() || booking.getEndtime().equals(endtime));
	Booking endedBooking = Booking.endBooking(booking, endtime);
	bookings.add(endedBooking);
	return endedBooking;
    }

    @Override
    @NonNull
    public Booking refactorBooking(@NonNull final Booking booking, @NonNull final LocalTime starttime,
	    @NonNull final LocalTime endtime, final String comment) {
	// TODO Auto-generated method stub
	return null;
    }

    private void checkTime(final LocalTime starttime) {
	// TODO Auto-generated method stub

    }

}
