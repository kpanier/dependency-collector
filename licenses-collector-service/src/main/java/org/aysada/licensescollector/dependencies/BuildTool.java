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

import java.util.List;

import org.aysada.licensescollector.dependencies.model.BuildFile;
import org.aysada.licensescollector.dependencies.model.BuildToolType;
import org.aysada.licensescollector.dependencies.model.Dependency;

public interface BuildTool {

	boolean isBuildFile(String fileName);

	BuildToolType getType();

	List<Dependency> getDependencies(BuildFile buildFile);

}
