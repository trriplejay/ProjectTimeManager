/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.annotation.NonNull;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;

/**
 * The implementation of the DayBookings service
 */
class DayBookingsImpl implements DayBookings {
	private final @NonNull LocalDate day;
	private final SortedSet<Booking> bookings = Sets.newTreeSet((A, B) -> A.getStarttime().compareTo(B.getStarttime()));

	/**
	 * Create a DayBooking service implementation
	 *
	 * @param day
	 *            The day for which bookings are stored.
	 */
	DayBookingsImpl(@NonNull final LocalDate day) {
		this.day = day;
	}

	@SuppressWarnings("null")
	@Override
	@NonNull
	public Collection<@NonNull Booking> getBookings() {
		return Collections.unmodifiableCollection(bookings);
	}

	@Override
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
	public Booking addBooking(@NonNull final Activity activity) {
		Preconditions.checkState(CollectionUtils.isNotEmpty(bookings));
		Booking lastBooking = getLastBooking();
		return addBooking(activity, lastBooking.getEndtime());
	}

	@Override
	@NonNull
	public Booking addBooking(@NonNull final Activity activity, @NonNull final LocalTime starttime) {
		Booking lastBooking = getLastBooking();
		if (lastBooking != null) {
			endBooking(lastBooking, starttime);
		}
		Booking newBooking = Booking.newBooking(starttime, activity);
		bookings.add(newBooking);
		return newBooking;
	}

	@Override
	@NonNull
	public Booking endBooking(@NonNull final Booking booking, @NonNull final LocalTime endtime) {
		Preconditions.checkState(bookings.remove(booking));
		Preconditions.checkState(!booking.hasEndtime() || booking.getEndtime().equals(endtime));
		Booking endedBooking = Booking.endBooking(booking, endtime);
		bookings.add(endedBooking);
		return endedBooking;
	}

	@Override
	public void removeBooking(@NonNull final Booking booking) {
		Preconditions.checkState(bookings.contains(booking));
		final Booking previousBooking = getPreviousBooking(booking);
		bookings.remove(booking);
		if (previousBooking != null) {
			bookings.remove(previousBooking);
			Booking newBooking = Booking.newBooking(previousBooking.getStarttime(), previousBooking.getActivity(),
					previousBooking.getComment());
			if (booking.hasEndtime()) {
				newBooking = Booking.endBooking(newBooking, booking.getEndtime());
			}
			bookings.add(newBooking);
		}
	}

	private Booking getPreviousBooking(@NonNull final Booking booking) {
		Set<Booking> previousElements = bookings.headSet(booking);
		if (previousElements.isEmpty()) {
			return null;
		}
		return Iterables.getLast(previousElements);
	}

	@Override
	public @NonNull Booking splitBooking(@NonNull final Booking booking, @NonNull final LocalTime splittime) {
		// TODO Auto-generated method stub
		return booking;
	}

	@Override
	public @NonNull Booking changeBookingTimes(@NonNull final Booking booking, @NonNull final LocalTime starttime,
			@NonNull final LocalTime endtime) {
		// TODO Auto-generated method stub
		return booking;
	}

	@Override
	public @NonNull Booking changeActivity(@NonNull final Booking booking, @NonNull final Activity activity) {
		Preconditions.checkState(bookings.contains(booking));
		Preconditions.checkState(!activity.equals(booking.getActivity()));
		bookings.remove(booking);
		Booking newBooking = Booking.newBooking(booking.getStarttime(), activity, booking.getComment());
		if (booking.hasEndtime()) {
			newBooking = Booking.endBooking(newBooking, booking.getEndtime());
		}
		bookings.add(newBooking);
		return newBooking;
	}

	@Override
	public @NonNull Booking changeComment(@NonNull final Booking booking, @NonNull final String comment) {
		Preconditions.checkState(bookings.contains(booking));
		Preconditions.checkState(StringUtils.isNotBlank(comment));
		Preconditions.checkState(!comment.equals(booking.getComment()));
		bookings.remove(booking);
		Booking newBooking = Booking.newBooking(booking.getStarttime(), booking.getActivity(), comment);
		if (booking.hasEndtime()) {
			newBooking = Booking.endBooking(newBooking, booking.getEndtime());
		}
		bookings.add(newBooking);
		return newBooking;
	}

	@Override
	public @NonNull Booking deleteComment(@NonNull final Booking booking) {
		Preconditions.checkState(bookings.contains(booking));
		Preconditions.checkState(StringUtils.isNotBlank(booking.getComment()));
		bookings.remove(booking);
		Booking newBooking = Booking.newBooking(booking.getStarttime(), booking.getActivity());
		if (booking.hasEndtime()) {
			newBooking = Booking.endBooking(newBooking, booking.getEndtime());
		}
		bookings.add(newBooking);
		return newBooking;
	}

}
