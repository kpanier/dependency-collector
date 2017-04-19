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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

public class CodeBaseHandler {

	int DEFAULT_SCAN_DEPTH = 3;
	int UNLIMITED_SCAN_DEPTH = Integer.MAX_VALUE;

	@Inject
	private BuildToolFactory buildToolFactory;
	
	@Inject 
	private CodeBaseProvider codeBaseProvider;

	public List<Path> lookUpBuildFiles(String remoteUrl, int scanDepth) {
		try {
			File root = codeBaseProvider.getLocalRepositoryRoot(remoteUrl);
			Stream<Path> stream = Files.walk(root.toPath(), scanDepth);
			List<Path> result = stream.filter(f -> buildToolFactory.isBuildFile(f.toFile().getName()))
					.collect(Collectors.toList());
			stream.close();
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
