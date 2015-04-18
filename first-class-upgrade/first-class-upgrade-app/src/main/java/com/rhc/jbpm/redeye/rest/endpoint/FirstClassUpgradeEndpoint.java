package com.rhc.jbpm.redeye.rest.endpoint;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.rhc.jbpm.redeye.model.Person;

@Path(value="/firstclass")
public interface FirstClassUpgradeEndpoint {
	@POST
	public Response createFirstClassRequest(Person name);
}
