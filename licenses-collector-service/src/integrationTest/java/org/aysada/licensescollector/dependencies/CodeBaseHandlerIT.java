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

import java.util.List;

import org.aysada.licensescollector.dependencies.impl.GitCodeBaseProvider;
import org.aysada.licensescollector.dependencies.model.BuildFile;
import org.aysada.licensescollector.dependencies.util.TempDirectoryBean;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class CodeBaseHandlerIT {

	@Rule
	public WeldInitiator weld;

	@Rule
	public TemporaryFolder tempFolder;

	public CodeBaseHandlerIT() {
		tempFolder = new TemporaryFolder();
		weld = WeldInitiator.from(GitCodeBaseProvider.class, BuildFileFactory.class, CodeBaseHandler.class)
				.addBeans(TempDirectoryBean.createTemporyDirectoryHelperBean(tempFolder)).build();
	}

	@Test
	public void testLookUpBuildFileFromPublicRemoteRepo() throws Exception {
		// given
		String remoteUrl = "https://github.com/kpanier/licenses-collector.git";
		CodeBaseHandler codeBase = weld.select(CodeBaseHandler.class).get();

		// when
		List<BuildFile> buildFiles = codeBase.lookUpBuildFiles(remoteUrl, CodeBaseHandler.DEFAULT_SCAN_DEPTH);

		// then
		assertNotNull(buildFiles);
		assertEquals(1, buildFiles.size());
		assertEquals("build.gradle", buildFiles.get(0).getFileName());
	}

}
