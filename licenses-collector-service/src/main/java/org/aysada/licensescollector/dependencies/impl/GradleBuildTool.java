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

import org.aysada.licensescollector.dependencies.BuildTool;

public class GradleBuildTool implements BuildTool {

	@Override
	public boolean isBuildFile(String fileName) {
		if (fileName.equals("build.gradle")) {
			return true;
		}
		return false;
	}

}
