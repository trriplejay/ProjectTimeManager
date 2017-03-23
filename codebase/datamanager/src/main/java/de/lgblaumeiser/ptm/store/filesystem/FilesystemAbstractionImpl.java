/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.store.filesystem;

import static com.google.common.base.Preconditions.checkState;
import static java.nio.charset.Charset.defaultCharset;
import static org.apache.commons.io.FileUtils.forceDelete;
import static org.apache.commons.io.FileUtils.listFiles;
import static org.apache.commons.io.FileUtils.moveFile;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.apache.commons.io.FileUtils.write;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Implementation of the real filesystem access
 */
public class FilesystemAbstractionImpl implements FilesystemAbstraction {
	private static final String TEMP_FILE_ENDING = ".tmpstore";

	@Override
	public Collection<File> getAllFiles(File folder, String extension) {
		return listFiles(folder, new String[] { extension }, false);
	}

	@Override
	public String retrieveFromFile(final File source) throws IOException {
		checkState(source != null);
		return readFileToString(source, defaultCharset());
	}

	@Override
	public void storeToFile(final File target, final String content) throws IOException {
		checkState(target != null);
		checkState(content != null);
		File tmpFile = new File(target.getParentFile(), target.getName() + TEMP_FILE_ENDING);
		try {
			write(tmpFile, content, defaultCharset());
			if (target.exists()) {
				forceDelete(target);
			}
			moveFile(tmpFile, target);
			for (File currentTmpFile : listFiles(target.getParentFile(), new String[] { TEMP_FILE_ENDING }, true)) {
				forceDelete(currentTmpFile);
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void deleteFile(File target) throws IOException {
		forceDelete(target);
	}

	@Override
	public boolean dataAvailable(final File source) {
		checkState(source != null);
		return source.exists() && source.isFile();
	}

	@Override
	public boolean folderAvailable(File folder, boolean createIfNot) {
		checkState(folder != null);
		if (folder.isDirectory() && folder.exists()) {
			return true;
		}
		if (createIfNot) {
			return folder.mkdir();
		}
		return false;
	}
}
