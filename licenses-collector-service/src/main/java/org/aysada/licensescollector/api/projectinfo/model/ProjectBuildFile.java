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
package org.aysada.licensescollector.api.projectinfo.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public class ProjectBuildFile {

	private String relativeProjectPath;
	private String buildToolType;

	public String getRelativeProjectPath() {
		return relativeProjectPath;
	}

	public void setRelativeProjectPath(String relativeProjectPath) {
		this.relativeProjectPath = relativeProjectPath;
	}

	public String getBuildToolType() {
		return buildToolType;
	}

	public void setBuildToolType(String buildToolType) {
		this.buildToolType = buildToolType;
	}

}
