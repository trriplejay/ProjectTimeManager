/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store.filesystem;

import static java.nio.charset.Charset.defaultCharset;
import static org.apache.commons.io.FileUtils.forceDelete;
import static org.apache.commons.io.FileUtils.listFiles;
import static org.apache.commons.io.FileUtils.moveFile;
import static org.apache.commons.io.FileUtils.write;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.annotation.NonNull;

/**
 * Implementation of the real filesystem access
 */
public class FileSystemAbstractionImpl implements FileSystemAbstraction {
    private static final String TEMP_FILE_ENDING = ".tmpstore";

    @Override
    public void storeToFile(@NonNull final File target, @NonNull final String content) throws IOException {
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

    @SuppressWarnings("null")
    @Override
    public @NonNull String retrieveFromFile(@NonNull final File source) throws IOException {
	return FileUtils.readFileToString(source, defaultCharset());
    }

    @Override
    public boolean dataAvailable(final File source) {
	return source.exists();
    }

}
