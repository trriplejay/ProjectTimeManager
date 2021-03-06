/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.store;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.model.internal.ActivityImpl;
import de.lgblaumeiser.ptm.datamanager.model.internal.BookingImpl;
import de.lgblaumeiser.ptm.store.filesystem.FileStore;
import de.lgblaumeiser.ptm.store.filesystem.FilesystemAbstraction;
import de.lgblaumeiser.ptm.store.filesystem.FilesystemAbstractionImpl;

/**
 * A provider for a FileStore instance for a dedicated model class
 */
public class FileStoreProvider {
	private FilesystemAbstraction filesystemAbstraction = new FilesystemAbstractionImpl();
	private FileStore<Activity> activityFileStore = new FileStore<Activity>(filesystemAbstraction) {
		@Override
		protected Class<? extends Activity> getImplType() {
			return ActivityImpl.class;
		}

		@Override
		protected Class<Activity> getType() {
			return Activity.class;
		}
	};
	private FileStore<Booking> bookingFileStore = new FileStore<Booking>(filesystemAbstraction) {
		@Override
		protected Class<? extends Booking> getImplType() {
			return BookingImpl.class;
		}

		@Override
		protected Class<Booking> getType() {
			return Booking.class;
		}
	};

	public FileStore<Activity> getActivityFileStore() {
		return activityFileStore;
	}

	public FileStore<Booking> getBookingFileStore() {
		return bookingFileStore;
	}

	public StoreBackupRestore<Activity> getActivityBackupRestore() {
		return activityFileStore;
	}

	public StoreBackupRestore<Booking> getBookingBackupRestore() {
		return bookingFileStore;
	}

	public ZipBackupRestore getZipBackupRestore() {
		return new ZipBackupRestore(activityFileStore, bookingFileStore);
	}
}
