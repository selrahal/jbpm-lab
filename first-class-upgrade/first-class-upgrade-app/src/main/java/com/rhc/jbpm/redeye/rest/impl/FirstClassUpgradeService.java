package com.rhc.jbpm.redeye.rest.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rhc.jbpm.redeye.jbpm.RuntimeEngineProvider;
import com.rhc.jbpm.redeye.model.Person;
import com.rhc.jbpm.redeye.rest.endpoint.FirstClassUpgradeEndpoint;

public class FirstClassUpgradeService implements FirstClassUpgradeEndpoint {
	private static final Logger LOG = LoggerFactory.getLogger(FirstClassUpgradeService.class);
	
	@Inject
	RuntimeEngineProvider runtimeEngineProvider;
	
	public Response createFirstClassRequest(Person person) {
		LOG.info("REST request recieved for " + person);
		RuntimeEngine runtimeEngine = runtimeEngineProvider.getRuntimeEngine();
		KieSession kieSession = runtimeEngine.getKieSession();
		
		Map<String, Object> input = new HashMap<String, Object>(1);
		input.put("requestor", person);
		
		kieSession.startProcess("FirstClassUpgrades.FirstClassUpgrade", input);
		
		return Response.ok("Request for first class upgrade for passenger " + person + " initiated!").build();
	}
}
