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

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.aysada.licensescollector.dependencies.impl.GradleBuildTool;
import org.aysada.licensescollector.dependencies.impl.MavenBuildTool;
import org.aysada.licensescollector.dependencies.impl.NpmBuildTool;
import org.aysada.licensescollector.dependencies.model.BuildFile;

public class BuildFileFactory {

	private List<BuildTool> buildTools = Arrays.asList(new GradleBuildTool(), new MavenBuildTool(), new NpmBuildTool());

	public boolean isBuildFile(String fileName) {
		Object[] foundedTools = buildTools.stream().filter(bt -> bt.isBuildFile(fileName)).toArray();
		return foundedTools.length > 0;
	}

	public BuildFile createBuildFileFrom(Path root, Path path) {
		BuildFile result = new BuildFile();
		result.setBuildToolType(
				buildTools.stream().filter(bt -> bt.isBuildFile(path.toFile().getName())).findFirst().get().getType());
		result.setPath(path);
		result.setRelativeProjectPath(root.relativize(path).toString());
		result.setFileName(path.toFile().getName());
		return result;
	}

	public BuildTool getBuildToolFor(BuildFile buildFile) {
		return buildTools.stream().filter(t -> t.getType().equals(buildFile.getBuildToolType())).findFirst().get();
	}

}
