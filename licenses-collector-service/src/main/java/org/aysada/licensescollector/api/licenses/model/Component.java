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
package org.aysada.licensescollector.api.licenses.model;

import java.util.List;

public class Component {

	private String buildTool;
	private String license;
	private List<Component> components;

	public String getBuildTool() {
		return buildTool;
	}

	public void setBuildTool(String buildTool) {
		this.buildTool = buildTool;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

}
