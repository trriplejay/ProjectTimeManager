/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import com.beust.jcommander.ParameterException;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.Assert.assertTrue;

public class BackupRestoreTest extends AbstractHandlerTest {
    private static final String BACKUP_COMMAND = "backup";
    private static final String RESTORE_COMMAND = "backup";

    @Test
    public void testBackup() throws IOException {
        File tempfile = File.createTempFile("test_backup", ".zip");
//        commandline.runCommand(BACKUP_COMMAND, "-z", tempfile.toString());
//        ZipInputStream inputs = new ZipInputStream(new FileInputStream(tempfile));
//        String content1 = getContentForNextZipEntry(inputs);
//        assertTrue(content1.contains(ACTIVITY1NAME));
//        assertTrue(content1.contains(ACTIVITY1NUMBER));
//        String content2 = getContentForNextZipEntry(inputs);
//        assertTrue(content2.contains(ACTIVITY2NAME));
//        assertTrue(content2.contains(ACTIVITY2NUMBER));
//        String content3 = getContentForNextZipEntry(inputs);
//        assertTrue(content3.contains(USER));
//        assertTrue(content3.contains(ACTIVITY1NAME));
//        assertTrue(content3.contains(ACTIVITY1NUMBER));
//        FileUtils.forceDelete(tempfile);
    }

    private String getContentForNextZipEntry(ZipInputStream inputs) throws IOException {
        byte[] buffer = new byte[1024];
        int read = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipEntry current = inputs.getNextEntry();
        while ((read = inputs.read(buffer, 0, buffer.length)) > 0) {
            baos.write(buffer, 0, read);
        }
        return baos.toString("UTF-8");
    }

    @Test(expected = ParameterException.class)
    public void testWithoutZipfile() {
        commandline.runCommand(BACKUP_COMMAND);
    }
}