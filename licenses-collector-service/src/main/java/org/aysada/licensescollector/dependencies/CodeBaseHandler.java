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

import org.aysada.licensescollector.dependencies.model.BuildFile;

public class CodeBaseHandler {

	public static int DEFAULT_SCAN_DEPTH = 3;
	public static int UNLIMITED_SCAN_DEPTH = Integer.MAX_VALUE;

	@Inject
	private BuildFileFactory buildFileFactory;

	@Inject
	private CodeBaseProvider codeBaseProvider;

	public List<BuildFile> lookUpBuildFiles(String remoteUrl, int scanDepth) {
		try {
			File root = codeBaseProvider.getLocalRepositoryRoot(remoteUrl);
			Stream<Path> stream = Files.walk(root.getParentFile().toPath(), scanDepth);
			List<BuildFile> result = stream.filter(f -> buildFileFactory.isBuildFile(f.toFile().getName()))
					.map(p -> buildFileFactory.createBuildFileFrom(root.toPath(), p)).collect(Collectors.toList());
			stream.close();
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
