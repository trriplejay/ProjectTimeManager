/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.rest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * General rest services of the PTM backend
 */
@RestController
@RequestMapping("/services")
public class ServiceRestController {
	@Autowired
	private ServiceMapper services;

	@RequestMapping(method = RequestMethod.GET, value = "/backup")
	public ResponseEntity<?> backup() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/zip"));
		String outputFilename = "ptm_backup.zip";
		headers.setContentDispositionFormData(outputFilename, outputFilename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		services.backupService().backup(byteOutputStream);
		return ResponseEntity.ok().headers(headers).body(byteOutputStream.toByteArray());
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/restore", headers = "content-type=multipart/*", consumes = "application/zip")
	public ResponseEntity<?> restore(InputStream zipdata) {
		services.backupService().emptyDatabase();
		services.backupService().restore(zipdata);
		return ResponseEntity.ok().build();
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<?> handleException(IllegalStateException e) {
		return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
	}
}
