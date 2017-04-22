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
package org.aysada.licensescollector.dependencies.util;

import java.io.File;

import javax.enterprise.inject.spi.Bean;

import org.aysada.licensescollector.helper.TempDirectoryHelper;
import org.jboss.weld.junit4.MockBean;
import org.junit.rules.TemporaryFolder;

public class TempDirectoryBean {

	public static Bean<?> createTemporyDirectoryHelperBean(TemporaryFolder tempFolder) {
		return MockBean.builder().types(TempDirectoryHelper.class).creating(getTestTempDirectoryHelper(tempFolder))
				.build();
	}

	private static TempDirectoryHelper getTestTempDirectoryHelper(TemporaryFolder tempFolder) {
		return new TempDirectoryHelper() {
			@Override
			public File getTempDirectory() {
				return tempFolder.getRoot();
			}
		};
	}

}
