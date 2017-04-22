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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.spi.Bean;

import org.aysada.licensescollector.api.projectinfo.model.ProjectBuildFile;
import org.aysada.licensescollector.api.projectinfo.model.ProjectInfo;
import org.aysada.licensescollector.dependencies.CodeBaseHandler;
import org.aysada.licensescollector.dependencies.model.BuildFile;
import org.aysada.licensescollector.dependencies.model.BuildToolType;
import org.jboss.weld.junit4.MockBean;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.Rule;
import org.junit.Test;

public class ProjectInfoEndPointTest {

	@Rule
	public WeldInitiator weld = WeldInitiator.from(ProjectInfoEndPoint.class).addBeans(createBeanConfig()).build();

	private ProjectInfoEndPoint projectInfoEndPoint;

	private CodeBaseHandler codeBaseHandler;

	@Test
	public void testCreateProjectInformationsForEmptyFiles() throws Exception {
		// given
		projectInfoEndPoint = weld.select(ProjectInfoEndPoint.class).get();
		List<BuildFile> list = new ArrayList<BuildFile>();
		when(codeBaseHandler.lookUpBuildFiles("foo", CodeBaseHandler.DEFAULT_SCAN_DEPTH)).thenReturn(list);

		// whenH
		ProjectInfo projectInfo = projectInfoEndPoint.createProjectInformationsFor("foo");

		// then
		assertNotNull(projectInfo);
		assertEquals("foo", projectInfo.getUrl());
		assertTrue(projectInfo.getBuildFiles().isEmpty());
	}

	@Test
	public void testCreateProjectInformationsForProjectWithBuildFile() throws Exception {
		// given

		projectInfoEndPoint = weld.select(ProjectInfoEndPoint.class).get();
		List<BuildFile> list = new ArrayList<BuildFile>();
		BuildFile bf = new BuildFile();
		bf.setBuildToolType(BuildToolType.GRADLE);
		bf.setRelativeProjectPath("/service/build.gradle");
		list.add(bf);
		when(codeBaseHandler.lookUpBuildFiles("prj", CodeBaseHandler.DEFAULT_SCAN_DEPTH)).thenReturn(list);

		// when
		ProjectInfo projectInfo = projectInfoEndPoint.createProjectInformationsFor("prj");

		// then
		assertNotNull(projectInfo);
		assertEquals("prj", projectInfo.getUrl());
		assertFalse(projectInfo.getBuildFiles().isEmpty());
		ProjectBuildFile projectBuildFile = projectInfo.getBuildFiles().get(0);
		assertEquals("GRADLE", projectBuildFile.getBuildToolType());
		assertEquals("/service/build.gradle", projectBuildFile.getRelativeProjectPath());
	}

	private Bean<?> createBeanConfig() {
		codeBaseHandler = mock(CodeBaseHandler.class);
		return MockBean.builder().types(CodeBaseHandler.class).creating(codeBaseHandler).build();
	}

}
