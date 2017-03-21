/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store.filesystem;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.nio.charset.Charset.defaultCharset;
import static org.apache.commons.io.FileUtils.forceDelete;
import static org.apache.commons.io.FileUtils.listFiles;
import static org.apache.commons.io.FileUtils.moveFile;
import static org.apache.commons.io.FileUtils.write;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

/**
 * Implementation of the real filesystem access
 */
public class FileSystemAbstractionImpl implements FileSystemAbstraction {
	private static final String TEMP_FILE_ENDING = ".tmpstore";

	@Override
	public void storeToFile(final File target, final String content) throws IOException {
		checkNotNull(target);
		checkNotNull(content);
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
	public Collection<File> getAllFiles(File folder, String extension) {
		return FileUtils.listFiles(folder, new String[] { extension }, false);
	}

	@Override
	public String retrieveFromFile(final File source) throws IOException {
		checkNotNull(source);
		return FileUtils.readFileToString(source, defaultCharset());
	}

	@Override
	public boolean dataAvailable(final File source) {
		checkNotNull(source);
		return source.exists() && source.isFile();
	}
}
