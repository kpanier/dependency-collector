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

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.aysada.licensescollector.api.licenses.model.Component;
import org.aysada.licensescollector.api.licenses.model.ProjectRepo;
import org.aysada.licensescollector.dependencies.CodeBaseHandler;
import org.aysada.licensescollector.dependencies.model.BuildFile;
import org.aysada.licensescollector.dependencies.model.Dependency;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class LicensesCollectorEndPoint implements LicensesCollectorService {

	@Inject
	private CodeBaseHandler codeBaseHandler;
	private MapperFacade mapper;

	@PostConstruct
	public void init() {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().mappingContextFactory(new MappingContext.Factory())
				.mapNulls(false).build();
		mapper = mapperFactory.getMapperFacade();}

	@Override
	public Response getLicenses(String repoUrl) {
		List<BuildFile> buildFiles = codeBaseHandler.lookUpBuildFiles(repoUrl, CodeBaseHandler.DEFAULT_SCAN_DEPTH);
		List<Dependency> dependecies = codeBaseHandler.getDependecies(buildFiles);
		List<Component> list = mapper.mapAsList(dependecies, Component.class);
		ProjectRepo projectRepo = new ProjectRepo();
		projectRepo.setComponents(list);
		return  Response.ok(projectRepo).build();
	}

}
