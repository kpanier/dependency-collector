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
package org.aysada.licensescollector.dependencies.model;

import java.nio.file.Path;

public class BuildFile {

	private String fileName;
	private Path path;
	private String relativeProjectPath;
	private BuildToolType buildToolType;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public String getRelativeProjectPath() {
		return relativeProjectPath;
	}

	public void setRelativeProjectPath(String relativeProjectPath) {
		this.relativeProjectPath = relativeProjectPath;
	}

	public BuildToolType getBuildToolType() {
		return buildToolType;
	}

	public void setBuildToolType(BuildToolType buildTool) {
		this.buildToolType = buildTool;
	}

}
