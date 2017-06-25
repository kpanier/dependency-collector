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

import com.codahale.metrics.health.HealthCheckRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.jetty.setup.ServletEnvironment;
import io.dropwizard.setup.Environment;
import io.swagger.jaxrs.listing.ApiListingResource;

public class LicensesCollectorApplicationTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Mock
	private Environment env;

	@Mock
	private JerseyEnvironment jersey;

	@Mock
	private ServletEnvironment servlet;

	@Mock
	private HealthCheckRegistry healthChecks;

	@Mock
	private ObjectMapper objectMapper;

	@Test
	public void testRunWithCorrectServerConfig() throws Exception {
		// given
		LicensesCollectorApplication application = new LicensesCollectorApplication();
		when(env.jersey()).thenReturn(jersey);
		when(env.servlets()).thenReturn(servlet);
		when(env.healthChecks()).thenReturn(healthChecks);
		when(env.getObjectMapper()).thenReturn(objectMapper);

		
		// when
		application.run(new AppConfiguration(), env);

		// then
		// swagger docu service
		verify(jersey).register(any(ApiListingResource.class));
		// weld CDI
		verify(servlet).addServletListeners(any(Listener.class));
	}

}
