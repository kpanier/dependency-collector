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
package org.aysada.licensescollector.api.licenses;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.aysada.licensescollector.api.licenses.model.ProjectRepo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api
@Path("licenses")
public interface LicensesCollectorService {

	@Path("{repoUrl}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "get Licenses Information", notes = "get a list of all used Licenses in this source repo.", response = ProjectRepo.class)
	public Response getLicenses(
			@ApiParam(value = "Url to the project source code control repository.", required = true) @PathParam("repoUrl") String repoUrl);

}
