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
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.List;

import javax.enterprise.inject.spi.Bean;

import org.aysada.licensescollector.dependencies.model.BuildFile;
import org.jboss.weld.junit4.MockBean;
import org.jboss.weld.junit4.WeldInitiator;
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

	private Bean<?> createBeanConfig() {
		codeBaseProvider = mock(CodeBaseProvider.class);
		return MockBean.builder().types(CodeBaseProvider.class).creating(codeBaseProvider).build();
	}

}
