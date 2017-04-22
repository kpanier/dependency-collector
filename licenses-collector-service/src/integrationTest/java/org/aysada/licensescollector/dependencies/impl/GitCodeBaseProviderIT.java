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

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.aysada.licensescollector.dependencies.BuildFileFactory;
import org.aysada.licensescollector.dependencies.util.TempDirectoryBean;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class GitCodeBaseProviderIT {

	@Rule
	public WeldInitiator weld;

	@Rule
	public TemporaryFolder tempFolder;

	public GitCodeBaseProviderIT() {
		tempFolder = new TemporaryFolder();
		weld = WeldInitiator.from(GitCodeBaseProvider.class, BuildFileFactory.class)
				.addBeans(TempDirectoryBean.createTemporyDirectoryHelperBean(tempFolder)).build();
	}

	@Test
	public void testGetLocalRepositoryRootWithEmptyLocalDir() throws Exception {
		// given
		String remoteUrl = "https://github.com/kpanier/licenses-collector.git";
		GitCodeBaseProvider gitCodeBase = weld.select(GitCodeBaseProvider.class).get();

		// when
		File localRepositoryRoot = gitCodeBase.getLocalRepositoryRoot(remoteUrl);

		// then
		assertTrue(localRepositoryRoot.exists());
	}

	@Test
	public void testGetLocalRepositoryRootWithExistingClone() throws Exception {
		// given
		String remoteUrl = "https://github.com/kpanier/licenses-collector.git";
		GitCodeBaseProvider gitCodeBase = weld.select(GitCodeBaseProvider.class).get();
		assertTrue(gitCodeBase.getLocalRepositoryRoot(remoteUrl).exists());

		// when
		File localRepositoryRoot = gitCodeBase.getLocalRepositoryRoot(remoteUrl);

		// then
		// TODO Test Update
		assertTrue(localRepositoryRoot.exists());
	}

}
