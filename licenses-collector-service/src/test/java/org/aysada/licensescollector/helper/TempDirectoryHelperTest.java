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
package org.aysada.licensescollector.helper;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class TempDirectoryHelperTest {

	@Test
	public void testDetTempDir() throws Exception {
		// given
		TempDirectoryHelper helper = new TempDirectoryHelper();

		// when
		File tmpDir = helper.getTempDirectory();

		// then
		assertNotNull(tmpDir);
		assertTrue(tmpDir.exists());
		assertTrue(tmpDir.isDirectory());
	}
}
