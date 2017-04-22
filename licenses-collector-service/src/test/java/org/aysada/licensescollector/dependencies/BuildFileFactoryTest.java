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

import static org.junit.Assert.*;

import java.io.File;

import org.aysada.licensescollector.dependencies.model.BuildFile;
import org.aysada.licensescollector.dependencies.model.BuildToolType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class BuildFileFactoryTest {

	private BuildFileFactory factory;

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Before
	public void setup() {
		factory = new BuildFileFactory();
	}

	@Test
	public void testIsGradleBuildFile() throws Exception {
		// given
		String fileName = "build.gradle";

		// when
		boolean isBuild = factory.isBuildFile(fileName);

		// then
		assertTrue(isBuild);
	}

	@Test
	public void testIsMavenBuildFile() throws Exception {
		// given
		String fileName = "pom.xml";

		// when
		boolean isBuild = factory.isBuildFile(fileName);

		// then
		assertTrue(isBuild);
	}

	@Test
	public void testIsNPMBuildFile() throws Exception {
		// given
		String fileName = "package.json";

		// when
		boolean isBuild = factory.isBuildFile(fileName);

		// then
		assertTrue(isBuild);
	}

	@Test
	public void testNonBuildFile() throws Exception {
		// given
		String javaName = "Foo.java";
		String typeScriptName = "Bar.ts";

		// when
		boolean java = factory.isBuildFile(javaName);
		boolean typescript = factory.isBuildFile(typeScriptName);

		// then
		assertFalse(java);
		assertFalse(typescript);
	}

	@Test
	public void testCreateGradleBuildFile() throws Exception {
		// given
		File buildFile = tempFolder.newFile("build.gradle");

		// when
		BuildFile gradleFile = factory.createBuildFileFrom(tempFolder.getRoot().toPath(), buildFile.toPath());

		// then
		assertEquals(BuildToolType.GRADLE, gradleFile.getBuildToolType());
		assertEquals("build.gradle", gradleFile.getFileName());
		assertEquals("build.gradle", gradleFile.getRelativeProjectPath());
		assertEquals(buildFile.toPath(), gradleFile.getPath());
	}

	@Test
	public void testCreateNPMBuildFile() throws Exception {
		// given
		File folder = tempFolder.newFolder("foo");
		File buildFile = new File(folder, "package.json");
		buildFile.createNewFile();
		
		// when
		BuildFile gradleFile = factory.createBuildFileFrom(tempFolder.getRoot().toPath(), buildFile.toPath());

		// then
		assertEquals(BuildToolType.NPM, gradleFile.getBuildToolType());
		assertEquals("package.json", gradleFile.getFileName());
		assertEquals("foo/package.json", gradleFile.getRelativeProjectPath());
		assertEquals(buildFile.toPath(), gradleFile.getPath());
	}

}
