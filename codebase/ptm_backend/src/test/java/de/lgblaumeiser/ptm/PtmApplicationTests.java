/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.lgblaumeiser.ptm.rest.ActivityRestController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PtmApplicationTests {
	@Autowired
	private ActivityRestController activityController;

	@Test
	public void contextLoads() {
		assertThat(activityController).isNotNull();
	}

}
