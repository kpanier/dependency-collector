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

public class GitCodeBaseProvider implements CodeBaseProvider {

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
				return repo.getDirectory();
			} else {
				prjWs.mkdir();
				Git localRepo = Git.cloneRepository().setDirectory(prjWs).setURI(remoteUrl).setCloneAllBranches(false)
						.call();
				File directory = localRepo.getRepository().getDirectory();
				localRepo.close();
				return directory;
			}
		} catch (IOException | GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected File getLocalWSFor(String remoteUrl) {
		try {
			String encode = URLEncoder.encode(remoteUrl, "UTF-8");
			return new File(tempDirectoryHelper.getTempDirectory(), encode);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
