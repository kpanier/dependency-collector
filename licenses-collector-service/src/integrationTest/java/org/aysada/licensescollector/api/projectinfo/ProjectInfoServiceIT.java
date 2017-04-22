/*******************************************************************************
 * Copyright (c) 2017 Karsten Panier and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Karsten Panier - initial API and implementation
 *******************************************************************************/
package org.aysada.licensescollector.api.projectinfo;

import static org.junit.Assert.assertEquals;

import java.net.URLEncoder;

import javax.ws.rs.client.Client;

import org.aysada.licensescollector.AppConfiguration;
import org.aysada.licensescollector.LicensesCollectorApplication;
import org.aysada.licensescollector.api.projectinfo.model.ProjectBuildFile;
import org.aysada.licensescollector.api.projectinfo.model.ProjectInfo;
import org.glassfish.jersey.client.ClientProperties;
import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;

public class ProjectInfoServiceIT {

	private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("test-example.yml");

	@ClassRule
	public static final DropwizardAppRule<AppConfiguration> RULE = new DropwizardAppRule<>(
			LicensesCollectorApplication.class, CONFIG_PATH);

	@Test
	public void testGetProjectInfo() throws Exception {
		// given
		String projectRepo = "https://github.com/kpanier/licenses-collector.git";
		String serverUrl = "http://localhost:" + RULE.getLocalPort() + "/api/project/"
				+ URLEncoder.encode(projectRepo, "UTF-8");

		Client client = new JerseyClientBuilder(RULE.getEnvironment()).withProperty(ClientProperties.READ_TIMEOUT, 8000)
				.build("test client");

		// when
		ProjectInfo projectInfo = client.target(serverUrl).request().get(ProjectInfo.class);

		// then
		assertEquals(projectRepo, projectInfo.getUrl());
		ProjectBuildFile projectBuildFile = projectInfo.getBuildFiles().get(0);
		assertEquals("GRADLE", projectBuildFile.getBuildToolType());
		assertEquals("../licenses-collector-service/build.gradle", projectBuildFile.getRelativeProjectPath());
	}

}
