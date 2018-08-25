/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.zip.ZipOutputStream;

/**
 * Command to backup all activities and bookings into zip file
 *
 */
@Parameters(commandDescription="backup all bookings and activities into zip file")
public class Backup extends AbstractCommandHandler {
    @Parameter(names = {"-z", "--zipfile"}, description = "Name of zipfile to store the data", required = true)
    private String zipfileName;

    @Override
    public void handleCommand() {
        getLogger().log("Download activities ...");
        Collection<Activity> activities = getServices().getActivityStore().retrieveAll();
        getLogger().log("Download bookings ...");
        Collection<Booking> bookings = getServices().getBookingsStore().retrieveAll();
        getLogger().log("Store data to zipfile ...");
        try {
            ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(zipfileName));
            ZipStore<Activity> actStore = new ZipStore<Activity>() {}.setOutputStream(outputStream);
            activities.forEach(actStore::store);
            ZipStore<Booking> bookStore = new ZipStore<Booking>() {}.setOutputStream(outputStream);
            bookings.forEach(bookStore::store);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
        getLogger().log("... done");
    }
}
