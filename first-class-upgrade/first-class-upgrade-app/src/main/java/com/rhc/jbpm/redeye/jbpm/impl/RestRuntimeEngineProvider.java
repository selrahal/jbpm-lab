package com.rhc.jbpm.redeye.jbpm.impl;

import java.net.MalformedURLException;
import java.net.URL;

import javax.enterprise.inject.Alternative;

import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.remote.client.api.RemoteRestRuntimeEngineFactory;
import org.kie.services.client.api.RemoteRuntimeEngineFactory;

import com.rhc.jbpm.redeye.jbpm.RuntimeEngineProvider;
import com.rhc.jbpm.redeye.model.Person;

@Alternative
public class RestRuntimeEngineProvider implements RuntimeEngineProvider {
	RemoteRestRuntimeEngineFactory factory;
	
	public RestRuntimeEngineProvider() {
		//Configuration specifying our local BPMS instance and Deployment
		URL deploymentUrl = null;
		try {
			deploymentUrl = new URL("http://localhost:8080/business-central");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String user = "bpmsAdmin";
		String password = "abcd1234!";
		String groupId = "com.rhc.jbpm.redeye";
		String artifactId = "first-class-upgrade-kjar";
		String version = "LATEST";
		String kieBase = "";
		String kieSession = "";
		
		
		//Get the KieSession
		StringBuilder deploymentIdBuilder = new StringBuilder();
		deploymentIdBuilder.append(groupId);
		deploymentIdBuilder.append(':');
		deploymentIdBuilder.append(artifactId);
		deploymentIdBuilder.append(':');
		deploymentIdBuilder.append(version);
		
		if (kieBase != null && !kieBase.isEmpty() &&
				kieSession != null && !kieSession.isEmpty()) {
			deploymentIdBuilder.append(':');
			deploymentIdBuilder.append(kieBase);
			deploymentIdBuilder.append(':');
			deploymentIdBuilder.append(kieSession);
		}
		String deploymentId = deploymentIdBuilder.toString();
		factory = RemoteRuntimeEngineFactory.newRestBuilder()
				.addUserName(user)
				.addPassword(password)
				.addDeploymentId(deploymentId)
				.addUrl(deploymentUrl)
				.addExtraJaxbClasses(Person.class)
				.buildFactory();
	}

	public RuntimeEngine getRuntimeEngine() {
		return factory.newRuntimeEngine();
	}
}
