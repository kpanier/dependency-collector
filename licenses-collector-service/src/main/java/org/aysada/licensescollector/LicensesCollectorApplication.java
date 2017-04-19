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
package org.aysada.licensescollector;

import org.glassfish.jersey.server.ServerProperties;
import org.jboss.weld.environment.servlet.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zapodot.hystrix.bundle.HystrixBundle;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.swagger.jaxrs.listing.ApiListingResource;

public class LicensesCollectorApplication extends Application<AppConfiguration> {

	public static final Logger LOGGER = LoggerFactory.getLogger(LicensesCollectorApplication.class);

	public static void main(final String[] args) throws Exception {
		new LicensesCollectorApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<AppConfiguration> bootstrap) {
		bootstrap.addBundle(HystrixBundle.withDefaultSettings());
	}

	@Override
	public void run(final AppConfiguration configuration, final Environment environment) throws Exception {
		environment.jersey().register(new ApiListingResource());
		environment.jersey().enable(ServerProperties.LOCATION_HEADER_RELATIVE_URI_RESOLUTION_DISABLED);

		environment.servlets().addServletListeners(new Listener());
	}
	
}