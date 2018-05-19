/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.rest;

import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Utils to do rest calls on the rest api
 */
public class RestUtils {
	private static final int TIMEOUT = 5 * 1000; // 5 times 1000 msec

	private final CloseableHttpClient clientConnector;
	private final String baseUrl;

	private final ObjectMapper jsonMapper;

	private final Properties applicationProps;

	/**
	 * Post a call to the rest api. Expects that a numerical id is returned as
	 * part of the creation call.
	 * 
	 * @param apiName
	 *            Name of the api
	 * @param bodyData
	 *            Body of the post data, this is a flat map that is converted
	 *            into a flat json
	 * @return The Id of the created or manipulated object
	 */
	public Long post(String apiName, Map<String, String> bodyData) {
		try {
			final HttpPost request = new HttpPost(baseUrl + apiName);
			StringEntity bodyJson = new StringEntity(jsonMapper.writeValueAsString(bodyData));
			bodyJson.setContentType("application/json");
			bodyJson.setContentEncoding("UTF-8");
			request.setEntity(bodyJson);
			HttpResponse response = clientConnector.execute(request);
			checkState(
					response.getStatusLine().getStatusCode() == 201 || response.getStatusLine().getStatusCode() == 200,
					"Cannot access server properly, Status " + response.getStatusLine() + ", URI: " + apiName);
			String uri = apiName;
			if (response.getStatusLine().getStatusCode() == 201) {
				uri = response.getHeaders("Location")[0].getValue();
			}
			return Long.parseLong(uri.substring(uri.lastIndexOf("/") + 1));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

	}

	/**
	 * Returns an element or an array of elements depending on the returnClass
	 * 
	 * @param apiName
	 *            The api name of the get call
	 * @param returnClass
	 *            The class object of a result type
	 * @return The found element or array of elements
	 */
	public <T> T get(String apiName, Class<T> returnClass) {
		try {
			final HttpGet request = new HttpGet(baseUrl + apiName);
			HttpResponse response = clientConnector.execute(request);
			checkState(response.getStatusLine().getStatusCode() == 200,
					"Cannot access server properly, Status " + response.getStatusLine() + ", URI: " + apiName);
			return jsonMapper.readValue(new InputStreamReader(response.getEntity().getContent()), returnClass);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

	}

	/**
	 * Delete an entity via a rest call
	 * 
	 * @param apiName
	 *            The api name for the deletion
	 */
	public void delete(String apiName) {
		try {
			final String requestString = baseUrl + apiName;
			final HttpDelete request = new HttpDelete(requestString);
			HttpResponse response = clientConnector.execute(request);
			checkState(response.getStatusLine().getStatusCode() == 200,
					"Cannot access server properly, Status " + response.getStatusLine() + ", URI: " + requestString);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Constructor, creates the HTTP Client object to execute http rest requests
	 */
	public RestUtils() {
		final RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(TIMEOUT)
				.setConnectionRequestTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).build();
		clientConnector = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
		applicationProps = loadAppProps();
		String host = getProperty("ptm.host");
		String port = getProperty("ptm.port");
		baseUrl = "http://" + host + ":" + port;
		jsonMapper = new ObjectMapper();
		jsonMapper.registerModule(new JavaTimeModule());
	}

	private Properties loadAppProps() {
		Properties applicationProps = new Properties();
		try (InputStream in = getClass().getResourceAsStream("rest.properties")) {
			applicationProps.load(in);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return applicationProps;
	}

	private String getProperty(String key) {
		String prop = System.getenv(key);
		prop = (prop == null) ? System.getProperty(key) : prop;
		prop = (prop == null) ? applicationProps.getProperty(key) : prop;
		checkState(prop != null);
		return prop;
	}
}
