/*
 * Copyright 2015, 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.rest;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Maps.newHashMap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;

/**
 * Utils to do rest calls on the rest api
 */
public class RestUtils {

	private static final int TIMEOUT = 5 * 1000; // 5 times 1000 msec

	private final CloseableHttpClient clientConnector;
	private final String baseUrl;

	private final Gson gsonUtil = new Gson();

	private final Properties applicationProps;

	/**
	 * Send a post request to the configured server
	 * 
	 * @param apiName
	 *            The relative path of the api
	 * @param params
	 *            a list of key value pairs to be used in a json request body
	 * @return The id of the object returned
	 */
	public String post(final String apiName, final String... params) {
		try {
			final HttpPost request = new HttpPost(baseUrl + apiName);
			Map<String, String> bodyData = newHashMap();
			for (int i = 0; i < params.length; i += 2) {
				bodyData.put(params[i], params[i + 1]);
			}
			StringEntity bodyJson = new StringEntity(gsonUtil.toJson(bodyData));
			request.setEntity(bodyJson);
			HttpResponse response = clientConnector.execute(request);
			checkState(response.getStatusLine().getStatusCode() == 201,
					"Cannot access server properly, Status " + response.getStatusLine() + ", URI: " + apiName);
			String uri = IOUtils.toString(response.getEntity().getContent(),
					response.getEntity().getContentEncoding().getValue());
			return uri.substring(uri.lastIndexOf("/") + 1);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Execute a get query using the given apiName using the internal http
	 * connection
	 *
	 * @param apiName
	 *            The rest request to execute, starts with a '/'
	 * @param classOfT
	 *            The class of the return type
	 * @return A collection of retrieved objects
	 * @throws IOException
	 *             If the connection went wrong or if not a Status ok has been
	 *             returned
	 */
	public <T> T get(final String apiName, final Class<T> classOfT) {
		final HttpGet request = new HttpGet(baseUrl + apiName);
		try {
			HttpResponse response = clientConnector.execute(request);
			checkState(response.getStatusLine().getStatusCode() == 200,
					"Cannot access server properly, Status " + response.getStatusLine() + ", URI: " + apiName);
			return gsonUtil.fromJson(new InputStreamReader(response.getEntity().getContent()), classOfT);
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
	}

	private Properties loadAppProps() {
		Properties applicationProps = new Properties();
		try (InputStream in = getClass().getResourceAsStream("application.properties")) {
			applicationProps.load(in);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return applicationProps;
	}

	private String getProperty(String key) {
		String prop = System.getenv(key);
		prop = (prop == null) ? System.getProperty(key) : prop;
		prop = (prop == null) ? applicationProps.getProperty("ptm.host") : prop;
		checkState(prop != null);
		return prop;
	}
}
