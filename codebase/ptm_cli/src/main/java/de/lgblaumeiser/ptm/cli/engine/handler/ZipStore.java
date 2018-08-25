/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import de.lgblaumeiser.ptm.store.ObjectStore;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipStore<T> implements ObjectStore<T> {
    private ZipOutputStream outputStream;
    private final ObjectMapper jsonUtil = new ObjectMapper();

    private Long currentId = 1L;

    @SuppressWarnings("serial")
    public final Class<T> type = (Class<T>)new TypeToken<T>(getClass()) {
    }.getRawType();

    @Override
    public Collection<T> retrieveAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<T> retrieveById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T store(T object) {
        String extension = type.getTypeName().substring(type.getTypeName().lastIndexOf('.') + 1).toLowerCase();
        String filename = currentId.toString() + "." + extension;
        currentId++;
        try (InputStream inputStream = IOUtils.toInputStream(jsonUtil.writeValueAsString(object), "UTF-8")) {
            outputStream.putNextEntry(new ZipEntry(filename));
            IOUtils.copy(inputStream, outputStream);
            inputStream.close();
            outputStream.closeEntry();
        } catch (IOException e) {
            throw new IllegalStateException();
        }
        return object;
    }

    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException();
    }

    ZipStore setOutputStream(ZipOutputStream outputStream) {
        this.outputStream = outputStream;
        return this;
    }
}
