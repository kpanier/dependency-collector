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
package org.aysada.licensescollector.api.projectinfo;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.aysada.licensescollector.api.projectinfo.model.ProjectBuildFile;
import org.aysada.licensescollector.api.projectinfo.model.ProjectInfo;
import org.aysada.licensescollector.dependencies.CodeBaseHandler;
import org.aysada.licensescollector.dependencies.model.BuildFile;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class ProjectInfoEndPoint implements ProjectInfoService {

	@Inject
	private CodeBaseHandler codeBaseHandler;
	private MapperFacade mapper;
	
	@PostConstruct
	public void init() {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().mappingContextFactory(new MappingContext.Factory())
				.mapNulls(false).build();
		mapper = mapperFactory.getMapperFacade();
	}
	
	@Override
	public Response getProjectInformations(String repoUrl) {
		ProjectInfo projectInfo = createProjectInformationsFor(repoUrl);
		return Response.ok(projectInfo).build();
	}

	public ProjectInfo createProjectInformationsFor(String repoUrl) {
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setUrl(repoUrl);
		List<BuildFile> buildFiles = codeBaseHandler.lookUpBuildFiles(repoUrl, CodeBaseHandler.DEFAULT_SCAN_DEPTH);
		List<ProjectBuildFile> buildInfos = mapper.mapAsList(buildFiles, ProjectBuildFile.class);
		projectInfo.setBuildFiles(buildInfos);
		return projectInfo;
	}

}
