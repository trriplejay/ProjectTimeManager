/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.rest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

/**
 * Rest proxy service for accessing the infrastructure services of the ptm rest
 * service api
 */
public class RestInfrastructureServices extends RestBaseService {
	/**
	 * Backup method which receives the back uped objects as zipped input stream
	 * which is simply routed to the file given.
	 * 
	 * @param backupFile The file object to store the backup data
	 */
	public void backup(File backupFile) {
		try (FileOutputStream dataSink = new FileOutputStream(backupFile)) {
			InputStream recevicedData = getRestUtils().get("/services/backup");
			IOUtils.copy(recevicedData, dataSink);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Restore method which sends the back up data as zipped output stream to the server
	 * 
	 * @param backupFile The file which stores the data
	 */
	public void restore(File backupFile) {
		try (FileInputStream dataSource = new FileInputStream(backupFile);
				ByteArrayOutputStream sendData = new ByteArrayOutputStream()) {
			IOUtils.copy(dataSource, sendData);
			getRestUtils().put("/services/restore", sendData.toByteArray());
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}