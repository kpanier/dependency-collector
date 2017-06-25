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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.aysada.licensescollector.dependencies.BuildTool;
import org.aysada.licensescollector.dependencies.model.BuildFile;
import org.aysada.licensescollector.dependencies.model.BuildToolType;
import org.aysada.licensescollector.dependencies.model.Dependency;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

public class GradleBuildTool implements BuildTool {

	@Override
	public boolean isBuildFile(String fileName) {
		if (fileName.equals("build.gradle")) {
			return true;
		}
		return false;
	}

	@Override
	public BuildToolType getType() {
		return BuildToolType.GRADLE;
	}

	@Override
	public List<Dependency> getDependencies(BuildFile buildFile) {
		GradleConnector connector = GradleConnector.newConnector();
		ProjectConnection connect = connector.forProjectDirectory(buildFile.getPath().toFile().getParentFile())
				.connect();
		BuildLauncher buildLauncher = connect.newBuild();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		buildLauncher.setStandardOutput(outputStream);
		buildLauncher.forTasks("dependencies");
		buildLauncher.run();
		connect.close();
		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return extractDependencyFromOutput(outputStream.toString());
	}

	public List<Dependency> extractDependencyFromOutput(String output) {
		ArrayList<Dependency> result = new ArrayList<Dependency>();
		String[] lines = output.split("\n");
		boolean inRuntimeClassPath = false;
		for (String line : lines) {
			inRuntimeClassPath = isInRuntimeClassPath(line, inRuntimeClassPath);
			if (inRuntimeClassPath && !line.startsWith("runtimeClasspath")) {
				if (line.startsWith("+")) {
					result.add(createDependencyFrom(line));
				}
			}
		}
		return result;
	}

	protected Dependency createDependencyFrom(String line) {
		Dependency dependency = new Dependency();
		String[] strings = line.substring(line.lastIndexOf("---") + 3).trim().split(":");
		dependency.setGroup(strings[0]);
		dependency.setName(strings[1]);
		if (strings[2].contains("->")) {
			dependency.setVersion(strings[2].split("->")[1].trim());
		} else {
			dependency.setVersion(strings[2]);
		}
		return dependency;
	}

	protected boolean isInRuntimeClassPath(String line, boolean inRuntimeClassPath) {
		if (line.startsWith("runtimeClasspath")) {
			return true;
		}
		String trimmedLine = line.trim();
		boolean depTreeElement = trimmedLine.startsWith("|") || trimmedLine.startsWith("+")
				|| trimmedLine.startsWith("\\");
		if (inRuntimeClassPath && !depTreeElement && trimmedLine.equals("")) {
			return false;
		}
		return inRuntimeClassPath;
	}

}
