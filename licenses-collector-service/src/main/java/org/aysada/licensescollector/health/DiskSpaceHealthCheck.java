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
package org.aysada.licensescollector.health;

import com.codahale.metrics.health.HealthCheck;
import com.google.common.io.Files;

public class DiskSpaceHealthCheck extends HealthCheck {

	@Override
	protected Result check() throws Exception {
		if(Files.createTempDir().getFreeSpace()<100000) {
			return Result.unhealthy("Not enough space.");
		}
		return Result.healthy();
	}

}
