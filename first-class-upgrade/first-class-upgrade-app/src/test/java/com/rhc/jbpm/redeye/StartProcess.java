package com.rhc.jbpm.redeye;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.remote.client.api.RemoteRestRuntimeEngineFactory;
import org.kie.services.client.api.RemoteRuntimeEngineFactory;
import org.kie.services.client.api.command.RemoteRuntimeEngine;

import com.rhc.jbpm.redeye.model.Person;

public class StartProcess {
	
	@Test
	public void startProcessTest() throws MalformedURLException {
		//Configuration specifying our local BPMS instance and Deployment
		URL deploymentUrl = new URL("http://localhost:8080/business-central");
		String user = "bpmsAdmin";
		String password = "abcd1234!";
		String groupId = "com.rhc.jbpm";
		String artifactId = "redeye-kjar";
		String version = "1.0.0-SNAPSHOT";
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
		RemoteRestRuntimeEngineFactory factory = RemoteRuntimeEngineFactory.newRestBuilder().addUserName(user).addPassword(password).addDeploymentId(deploymentId).addUrl(deploymentUrl).buildFactory();

		RemoteRuntimeEngine engine = factory.newRuntimeEngine();
		KieSession ksession = engine.getKieSession();
		
		//Prepare data
		Person person = new Person();
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("pilot", person);
		
		//Start process
		ProcessInstance processInstance = ksession.startProcess("redeye-kjar.simple", params);
		
		//Test
		Assert.assertNotNull(processInstance);
		System.out.println(processInstance);
	}
}
