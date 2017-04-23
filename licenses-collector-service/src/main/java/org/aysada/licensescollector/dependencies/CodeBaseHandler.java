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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.aysada.licensescollector.dependencies.model.BuildFile;
import org.aysada.licensescollector.dependencies.model.Dependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeBaseHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(CodeBaseHandler.class);

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
			LOGGER.info("Scan for build file in {}", root.getParentFile().toPath());
			List<BuildFile> result = stream.filter(f -> buildFileFactory.isBuildFile(f.toFile().getName()))
					.map(p -> buildFileFactory.createBuildFileFrom(root.toPath(), p)).collect(Collectors.toList());
			stream.close();
			return result;
		} catch (IOException e) {
			LOGGER.error("Error scanning for buildfiles.", e);
		}
		return null;
	}

	public List<Dependency> getDependecies(List<BuildFile> buildFiles) {
		List<Dependency> result = new ArrayList<Dependency>();
		for (BuildFile buildFile : buildFiles) {
			BuildTool buildTool = buildFileFactory.getBuildToolFor(buildFile);
			result.addAll(buildTool.getDependencies(buildFile));
		}
		return result;
	}
}
