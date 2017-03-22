/*
 * Copyright 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.rest;

import static java.lang.System.setProperty;

import org.springframework.stereotype.Component;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.store.ObjectStore;
import de.lgblaumeiser.ptm.store.filesystem.FileStore;
import de.lgblaumeiser.ptm.store.filesystem.FileSystemAbstractionImpl;

/**
 * Small bean that creates and configures the services needed by the Rest
 * interface
 */
@Component
public class ServiceMapper {
	private ObjectStore<Activity> activityStore;

	public ServiceMapper() {
		setProperty("filestore.folder", "ptm");
		activityStore = new FileStore<Activity>() {
		}.setFilesystemAccess(new FileSystemAbstractionImpl());
	}

	public ObjectStore<Activity> activityStore() {
		return activityStore;
	}
}
