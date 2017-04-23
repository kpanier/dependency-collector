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
package org.aysada.licensescollector.dependencies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.inject.spi.Bean;

import org.aysada.licensescollector.dependencies.model.BuildFile;
import org.aysada.licensescollector.dependencies.model.BuildToolType;
import org.aysada.licensescollector.dependencies.model.Dependency;
import org.jboss.weld.junit4.MockBean;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class CodeBaseHandlerTest {

	@Rule
	public WeldInitiator weld = WeldInitiator.from(CodeBaseHandler.class, BuildFileFactory.class)
			.addBeans(createBeanConfig()).build();
	
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	private CodeBaseProvider codeBaseProvider;
	private CodeBaseHandler codeBaseHandler;

	@Test
	public void testLookUpBuildFiles() throws Exception {
		// given
		codeBaseHandler = weld.select(CodeBaseHandler.class).get();
		File prjRoot = tempFolder.newFolder("fooPrj");
		new File(prjRoot, "pom.xml").createNewFile();
		new File(prjRoot, "settings.xml").createNewFile();
		File subDir = new File(prjRoot, "subDir");
		subDir.mkdir();
		new File(subDir, "build.gradle").createNewFile();
		new File(subDir, "HelloWorld.java").createNewFile();
		when(codeBaseProvider.getLocalRepositoryRoot(any())).thenReturn(prjRoot);

		// when
		List<BuildFile> buildFiles = codeBaseHandler.lookUpBuildFiles("foo", 3);

		// then
		assertNotNull(buildFiles);
		assertEquals(2, buildFiles.size());
	}

	@Test
	@Ignore
	public void testGetDependecies() throws Exception {
		// given
		BuildFileFactory fac = mock(BuildFileFactory.class);
		weld = WeldInitiator.from(CodeBaseHandler.class)
				.addBeans(MockBean.builder().types(BuildFileFactory.class).creating(fac).build()).build();
		codeBaseHandler = weld.select(CodeBaseHandler.class).get();
		BuildFile file = new BuildFile();
		file.setBuildToolType(BuildToolType.GRADLE);
		BuildTool gradleTool = mock(BuildTool.class);
		when(fac.getBuildToolFor(file)).thenReturn(gradleTool);
		Dependency dependency = new Dependency();
		when(gradleTool.getDependencies(file)).thenReturn(Arrays.asList(new Dependency[] { dependency }));

		// when
		List<Dependency> dependecies = codeBaseHandler.getDependecies(Arrays.asList(new BuildFile[] { file }));

		// then
		assertNotNull(dependecies);
		assertFalse(dependecies.isEmpty());
	}

	private Bean<?> createBeanConfig() {
		codeBaseProvider = mock(CodeBaseProvider.class);
		return MockBean.builder().types(CodeBaseProvider.class).creating(codeBaseProvider).build();
	}

}
