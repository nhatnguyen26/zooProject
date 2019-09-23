package com.zoo.zooApplication.application.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;

import javax.annotation.Priority;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * This is a temporary mechanism to lock down some sensitive API (write operation)
 * that only internal zoo team can invoke
 */
@ZooMasterAuthentication
@Provider
@Priority(Ordered.LOWEST_PRECEDENCE)
public class ZooMasterAuthFilter implements ContainerRequestFilter {

	@Autowired
	@Value("${APP_MASTER_KEY}")
	private String masterKey;

	private static final String HEADER_NAME = "X-Authorization-Master";

	@Override
	public void filter(ContainerRequestContext requestContext) {
		String masterKeyHeader = requestContext.getHeaderString(HEADER_NAME);
		if (!masterKey.equals(masterKeyHeader)) {
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}
}
