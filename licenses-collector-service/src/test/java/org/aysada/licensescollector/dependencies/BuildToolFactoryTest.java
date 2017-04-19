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

import org.junit.Before;
import org.junit.Test;

public class BuildToolFactoryTest {

	private BuildToolFactory factory;

	@Before
	public void setup() {
		factory = new BuildToolFactory();
	}
	
	@Test
	public void testIsGradleBuildFile() throws Exception {
		// given
		String fileName="build.gradle";
		
		// when
		boolean isBuild = factory.isBuildFile(fileName);
		
		// then
		assertTrue(isBuild);
	}

	@Test
	public void testIsMavenBuildFile() throws Exception {
		// given
		String fileName="pom.xml";
		
		// when
		boolean isBuild = factory.isBuildFile(fileName);
		
		// then
		assertTrue(isBuild);
	}
	
	@Test
	public void testIsNPMBuildFile() throws Exception {
		// given
		String fileName="package.json";
		
		// when
		boolean isBuild = factory.isBuildFile(fileName);
		
		// then
		assertTrue(isBuild);
	}

	@Test
	public void testNonBuildFile() throws Exception {
		// given
		String javaName="Foo.java";
		String typeScriptName="Bar.ts";
		
		// when
		boolean java = factory.isBuildFile(javaName);
		boolean typescript = factory.isBuildFile(typeScriptName);
		
		// then
		assertFalse(java);
		assertFalse(typescript);
	}

}
