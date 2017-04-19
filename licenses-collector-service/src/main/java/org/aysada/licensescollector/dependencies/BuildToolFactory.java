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

import java.util.Arrays;
import java.util.List;

import org.aysada.licensescollector.dependencies.impl.GradleBuildTool;
import org.aysada.licensescollector.dependencies.impl.MavenBuildTool;
import org.aysada.licensescollector.dependencies.impl.NpmBuildTool;

public class BuildToolFactory {

	private List<BuildTool> buildTools = Arrays.asList(new GradleBuildTool(), new MavenBuildTool(), new NpmBuildTool());

	public boolean isBuildFile(String fileName) {
		Object[] foundedTools = buildTools.stream().filter(bt -> bt.isBuildFile(fileName)).toArray();
		return foundedTools.length > 0;
	}

}
