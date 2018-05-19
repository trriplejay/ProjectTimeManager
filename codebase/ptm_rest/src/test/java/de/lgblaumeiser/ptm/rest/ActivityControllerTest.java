/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.rest;

import static com.google.common.io.Files.createTempDir;
import static java.lang.System.setProperty;
import static org.apache.commons.io.FileUtils.forceDelete;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test the activity rest controller
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ActivityControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private File tempFolder;

	@Before
	public void before() {
		tempFolder = createTempDir();
		String tempStorage = new File(tempFolder, ".ptm").getAbsolutePath();
		setProperty("filestore.folder", tempStorage);
	}

	@After
	public void after() throws IOException {
		forceDelete(tempFolder);
	}

	@Test
	public void testWithInitialSetupNoActivities() throws Exception {
		mockMvc.perform(get("/activities")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("[]")));
	}

	@Test
	public void testRoundtripCreateAndRetrieveActivity() throws Exception {
		ActivityRestController.ActivityBody data = new ActivityRestController.ActivityBody();
		data.name = "MyTestActivity";
		data.id = "0815";
		mockMvc.perform(post("/activities").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(data))).andDo(print()).andExpect(status().isCreated());

		mockMvc.perform(get("/activities")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("MyTestActivity")))
				.andExpect(content().string(containsString("0815")));

		mockMvc.perform(get("/activities/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("MyTestActivity")))
				.andExpect(content().string(containsString("0815")));

		mockMvc.perform(delete("/activities/1")).andDo(print()).andExpect(status().isOk());

		mockMvc.perform(get("/activities")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("[]")));

	}
}
