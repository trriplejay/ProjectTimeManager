/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.store;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.Maps;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;

/**
 * A implementation of a backup/restore mechanism using StoreBackupRestore. The implementation is using zip streams for input and output. 
 */
public class ZipBackupRestore {
	private final StoreBackupRestore<Activity> activityStorage;
	private final StoreBackupRestore<Booking> bookingStorage;
	
	/**
	 * Make a backup to the provided output stream as a zip file content
	 * 
	 * @param outputStream The output stream to which the backup is provided
	 */
	public void backup (OutputStream outputStream) {
		try (ZipOutputStream zipstream = new ZipOutputStream(outputStream)) {
			activityStorage.backup((name, stream) -> createZipEntry(zipstream, name, stream));
			bookingStorage.backup((name, stream) -> createZipEntry(zipstream, name, stream));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private void createZipEntry(ZipOutputStream zipstream, String name, InputStream stream) throws IOException {
		zipstream.putNextEntry(new ZipEntry(name));
		IOUtils.copy(stream, zipstream);
		zipstream.closeEntry();
	}
	
	/**
	 * Restore from a backup provided as a zip file content
	 * 
	 * @param inputStream The input stream in which the zipped backup is provided
	 */
	public void restore(InputStream inputStream) {
		Map<String, String> activityMap = Maps.newHashMap();
		Map<String, String> bookingMap = Maps.newHashMap();
		try (ZipInputStream zipstream = new ZipInputStream(inputStream)) {
			ZipEntry currentEntry;
			while ((currentEntry = zipstream.getNextEntry()) != null) {
				String key = currentEntry.getName();
				String data = extractFromZip(zipstream);
				zipstream.closeEntry();
				if (key.endsWith("activity")) {
					activityMap.put(key, data);
				}
				else if (key.endsWith("booking")) {
					bookingMap.put(key, data);
				}
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		activityStorage.restore(activityMap);
		bookingStorage.restore(bookingMap);
	}
	
	private String extractFromZip(ZipInputStream inputStream) {
		try (ByteArrayOutputStream stringOutputSink = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[1024];
			int read = 0;
			while ((read = inputStream.read(buffer, 0, buffer.length)) > 0) {
				stringOutputSink.write(buffer, 0, read);
			}
			return stringOutputSink.toString("UTF-8");
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Empty the database to prepare e.g. restoring of a backup
	 */
	public void emptyDatabase() {
		activityStorage.delete();
		bookingStorage.delete();
	}
	
	/**
	 * Standard constructor used to provide dependencies
	 * 
	 * @param activityStorage
	 * @param bookingStorage
	 */
	public ZipBackupRestore(StoreBackupRestore<Activity> activityStorage, StoreBackupRestore<Booking> bookingStorage) {
		this.activityStorage = activityStorage;
		this.bookingStorage = bookingStorage;
	}
}
