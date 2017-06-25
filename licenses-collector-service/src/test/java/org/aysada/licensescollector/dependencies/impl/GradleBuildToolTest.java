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
package org.aysada.licensescollector.dependencies.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.aysada.licensescollector.dependencies.BuildTool;
import org.aysada.licensescollector.dependencies.model.BuildFile;
import org.aysada.licensescollector.dependencies.model.Dependency;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class GradleBuildToolTest {

	@Rule
	public TemporaryFolder tmpFolder = new TemporaryFolder();

	@Test
	public void testIsGradleBuildFile() throws Exception {
		// given
		BuildTool gradleBuildTool = new GradleBuildTool();

		// when
		boolean validBuildFile = gradleBuildTool.isBuildFile("build.gradle");
		boolean javaFile = gradleBuildTool.isBuildFile("Foo.java");
		boolean mavenBuild = gradleBuildTool.isBuildFile("pom.xml");

		// then
		assertTrue(validBuildFile);
		assertFalse(javaFile);
		assertFalse(mavenBuild);
	}

	@Test
	public void testGetDependiencies() throws Exception {
		// given
		BuildTool gradleBuildTool = new GradleBuildTool();
		BuildFile buildFile = new BuildFile();
		File newFile = tmpFolder.newFile("build.gradle");
		String content = "apply plugin: 'java'\nrepositories {\n   mavenCentral()\n}\n"
				+ "dependencies { \n compile group: 'junit', name: 'junit', version: '4.12' \n "
				+ "compile group: 'javax', name: 'javaee-api', version: '7.0' \n}";
		Files.write(newFile.toPath(), content.getBytes());
		buildFile.setPath(newFile.toPath());

		// when
		List<Dependency> dependencies = gradleBuildTool.getDependencies(buildFile);

		// then
		assertNotNull(dependencies);
		System.err.println(dependencies);
	}

	@Test
	public void testExtractDependencyFromOutput() throws Exception {
		// given
		GradleBuildTool gradleBuildTool = new GradleBuildTool();
		Path simpleDependencyOutput = new File(
				getClass().getClassLoader().getResource("gradle_simple_dependency_output.txt").toURI()).toPath();
		String output = new String(Files.readAllBytes(simpleDependencyOutput));

		// when
		List<Dependency> list = gradleBuildTool.extractDependencyFromOutput(output);

		// then
		assertNotNull(list);
	}
	
	@Test
	public void testIsInRuntimeClassPath() throws Exception {
		// given
		GradleBuildTool gradleBuildTool = new GradleBuildTool();
		
		// when & then
		assertTrue(gradleBuildTool.isInRuntimeClassPath("runtimeClasspath", false));
		assertTrue(gradleBuildTool.isInRuntimeClassPath("|    \\--- org.hamcrest:hamcrest-core:1.3", true));
		assertTrue(gradleBuildTool.isInRuntimeClassPath("\\--- javax:javaee-api:7.0", true));
		assertFalse(gradleBuildTool.isInRuntimeClassPath(" ", true));
		assertFalse(gradleBuildTool.isInRuntimeClassPath("Foo ", false));
	}
	
	@Test
	public void testCreateDependencyFromPlusLine() throws Exception {
		// given
		GradleBuildTool gradleBuildTool = new GradleBuildTool();
		String line = "+--- io.dropwizard:dropwizard-core:1.1.0";

		// when
		Dependency dependency = gradleBuildTool.createDependencyFrom(line);
		
		// then
		assertEquals("io.dropwizard",dependency.getGroup());
		assertEquals("dropwizard-core",dependency.getName());
		assertEquals("1.1.0",dependency.getVersion());
	}
	
	@Test
	public void testCreateDependencyFromSubLine() throws Exception {
		// given
		GradleBuildTool gradleBuildTool = new GradleBuildTool();
		String line = "|    |    |    \\--- com.fasterxml.jackson.core:jackson-core:2.8.7";

		// when
		Dependency dependency = gradleBuildTool.createDependencyFrom(line);
		
		// then
		assertEquals("com.fasterxml.jackson.core",dependency.getGroup());
		assertEquals("jackson-core",dependency.getName());
		assertEquals("2.8.7",dependency.getVersion());
	}

	@Test
	public void testCreateDependencyFromLineWithVersionSwitch() throws Exception {
		// given
		GradleBuildTool gradleBuildTool = new GradleBuildTool();
		String line = "|    |    |    +--- com.fasterxml.jackson.core:jackson-annotations:2.8.0 -> 2.8.4";

		// when
		Dependency dependency = gradleBuildTool.createDependencyFrom(line);
		
		// then
		assertEquals("com.fasterxml.jackson.core",dependency.getGroup());
		assertEquals("jackson-annotations",dependency.getName());
		assertEquals("2.8.4",dependency.getVersion());
	}

}
