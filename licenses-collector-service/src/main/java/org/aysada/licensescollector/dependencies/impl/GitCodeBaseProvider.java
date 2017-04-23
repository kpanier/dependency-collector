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

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.inject.Inject;

import org.aysada.licensescollector.dependencies.CodeBaseProvider;
import org.aysada.licensescollector.helper.TempDirectoryHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitCodeBaseProvider implements CodeBaseProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(GitCodeBaseProvider.class);

	@Inject
	private TempDirectoryHelper tempDirectoryHelper;

	public File getLocalRepositoryRoot(String remoteUrl) {
		try {
			File prjWs = getLocalWSFor(remoteUrl);
			if (prjWs.exists()) {
				Repository repo = Git.open(prjWs).getRepository();
				Git git = new Git(repo);
				git.fetch().call();
				git.close();
				LOGGER.info("Lcoal git repo for {} updated.", remoteUrl);
				return repo.getDirectory();
			} else {
				prjWs.mkdir();
				Git localRepo = Git.cloneRepository().setDirectory(prjWs).setURI(remoteUrl).setCloneAllBranches(false)
						.call();
				File directory = localRepo.getRepository().getDirectory();
				localRepo.close();
				LOGGER.info("Cloned lcoal git repo for {}.", remoteUrl);
				return directory;
			}
		} catch (IOException | GitAPIException e) {
			LOGGER.error("Error working with git.", e);
		}
		return null;
	}

	protected File getLocalWSFor(String remoteUrl) {
		try {
			String encode = URLEncoder.encode(remoteUrl, "UTF-8");
			return new File(tempDirectoryHelper.getTempDirectory(), encode);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("Can't encode.", e);
		}
		return null;
	}

}
