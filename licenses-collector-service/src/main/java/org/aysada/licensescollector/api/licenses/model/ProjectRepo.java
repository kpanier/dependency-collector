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
import java.util.Map;

public class ProjectRepo {

	private String url;
	private List<Component> components;
	private Map<String, String> summary;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public Map<String, String> getSummary() {
		return summary;
	}

	public void setSummary(Map<String, String> summary) {
		this.summary = summary;
	}

}
