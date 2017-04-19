package org.aysada.licensescollector;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.jboss.weld.environment.servlet.Listener;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.jetty.setup.ServletEnvironment;
import io.dropwizard.setup.Environment;
import io.swagger.jaxrs.listing.ApiListingResource;

public class LicensesCollectorApplicationTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Mock
	Environment env;

	@Mock
	JerseyEnvironment jersey;

	@Mock
	ServletEnvironment servlet;

	@Test
	public void testRunWithCorrectServerConfig() throws Exception {
		// given
		LicensesCollectorApplication application = new LicensesCollectorApplication();
		when(env.jersey()).thenReturn(jersey);
		when(env.servlets()).thenReturn(servlet);

		// when
		application.run(new AppConfiguration(), env);

		// then
		// swagger docu service
		verify(jersey).register(any(ApiListingResource.class));
		// weld CDI
		verify(servlet).addServletListeners(any(Listener.class));
	}

}
