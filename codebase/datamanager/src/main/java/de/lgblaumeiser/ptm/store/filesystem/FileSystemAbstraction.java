/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.store.filesystem;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Interface to hide the concrete filesystem access implementation. Improved
 * testability for the business functionality
 */
public interface FileSystemAbstraction {
	/**
	 * @param The
	 *            folder in which files are searched
	 * @param extension
	 *            The extension of the files searched
	 * @return A list of files with the given extension stored in the given
	 *         folder
	 */
	Collection<File> getAllFiles(File folder, String extension);

	/**
	 * @param source
	 *            File to read
	 * @return Content of file as string, might be empty
	 * @throws IOException
	 *             If access fails
	 */
	String retrieveFromFile(File source) throws IOException;

	/**
	 * @param target
	 *            File reference in which to store content
	 * @param content
	 *            Content to store
	 * @throws IOException
	 *             If access fails
	 */
	void storeToFile(File target, String content) throws IOException;

	/**
	 * @param target
	 *            The file to delete
	 */
	void deleteFile(File target) throws IOException;

	/**
	 * @param source
	 *            File to check whether it exists
	 * @return True, if file really exists
	 */
	boolean dataAvailable(File source);

	/**
	 * @param store
	 *            Store folder to check whether it exists
	 * @param createIfNot
	 *            Create the folder if it does not
	 * @return True, if the folder exists now
	 */
	boolean folderAvailable(File store, boolean createIfNot);
}