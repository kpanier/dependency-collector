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

import org.aysada.licensescollector.dependencies.BuildTool;
import org.junit.Test;

public class GradleBuildToolTest {
	
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
	
}
