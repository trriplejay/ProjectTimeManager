/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.store;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * A backup service for the ObjectStore
 */
public interface StoreBackupRestore<T> {
	/**
	 * Handler interface, sending each file to be handled as input stream
	 */
	public interface StoreBackupStreamHandler {
		void streamForBackup(String filename, InputStream contentStream) throws IOException;
	}

	/**
	 * Make a backup of all existing objects of the type, let a handler do the transfer to the
	 * backup medium. The caller has to transfer the input stream into the backup target by providing
	 * the backupTarget implementation
	 * 
	 * @param backupTarget The handler that transfers the content into the backup target
	 */
	void backup(StoreBackupStreamHandler backupTarget);
	
	/**
	 * Restore the objects from the map, works only if no objects of the type exist
	 * 
	 * @param filenameToContentMap A map of content indexed by the filename of the content
	 */
	void restore(Map<String, String> filenameToContentMap);
	
	/**
	 * Deletes all objects of the type
	 */
	void delete();
}
