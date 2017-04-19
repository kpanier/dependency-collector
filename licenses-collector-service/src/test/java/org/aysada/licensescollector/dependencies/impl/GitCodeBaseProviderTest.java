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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.File;

import org.aysada.licensescollector.helper.TempDirectoryHelper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class GitCodeBaseProviderTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Rule
	public TemporaryFolder tmpFolder = new TemporaryFolder();

	@Mock
	TempDirectoryHelper helper;

	@InjectMocks
	private GitCodeBaseProvider gitCodeBase;

	@Test
	public void testGetWs() throws Exception {
		// given
		when(helper.getTempDirectory()).thenReturn(tmpFolder.newFolder("tmpRoot"));

		// when
		File localWSFor = gitCodeBase.getLocalWSFor("http://github.com/myrepo");

		// then
		assertNotNull(localWSFor);
		assertFalse(localWSFor.exists());
		localWSFor.createNewFile();
		assertTrue(localWSFor.exists());
	}

}
