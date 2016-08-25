/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store.filesystem;

import java.io.File;
import java.io.IOException;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Interface to hide the concrete filesystem access implementation. Improved
 * testability for the business functionality
 */
public interface FileSystemAbstraction {
    static final String TEMP_FILE_ENDING = ".tmpjs";

    void storeToFile(@NonNull File target, @NonNull String content) throws IOException;

    // try {
    // FileUtils.write(tmpFile, json, Charset.defaultCharset());
    // if (targetFile.exists()) {
    // FileUtils.deleteQuietly(targetFile);
    // }
    // FileUtils.moveFile(tmpFile, targetFile);
    // } catch (IOException e) {
    // throw new IllegalStateException(e);
    // }
}
