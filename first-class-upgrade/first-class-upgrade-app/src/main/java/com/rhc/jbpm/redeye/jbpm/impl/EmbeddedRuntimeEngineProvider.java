package com.rhc.jbpm.redeye.jbpm.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.EnvironmentName;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.internal.runtime.manager.context.EmptyContext;

import com.rhc.jbpm.redeye.jbpm.RuntimeEngineProvider;

@Alternative
@ApplicationScoped
public class EmbeddedRuntimeEngineProvider implements RuntimeEngineProvider {
	RuntimeManager runtimeManager;
	
	@PersistenceUnit(unitName="com.rhc.jbpm.redeye")
	EntityManagerFactory emf;

	public RuntimeEngine getRuntimeEngine() {
		if (runtimeManager == null) {
			synchronized (this) {
				if (runtimeManager == null) {
					runtimeManager = this.getRuntimeManager();
				}
			}
			
		}
		return runtimeManager.getRuntimeEngine(EmptyContext.get());
	}

	private RuntimeManager getRuntimeManager() {
		RuntimeManagerFactory runtimeManagerFactory = RuntimeManagerFactory.Factory
				.get();

		String groupId = "com.rhc.jbpm.redeye";
		String artifactId = "first-class-upgrade-kjar";
		String version = "LATEST";
		
		KieServices kieServices = KieServices.Factory.get();
		ReleaseId releaseId = kieServices.newReleaseId(groupId, artifactId, version);
		KieContainer kieContainer = kieServices.newKieContainer(releaseId);
		
		KieScanner kieScanner = kieServices.newKieScanner(kieContainer);
		kieScanner.start(100L);

		RuntimeEnvironment runtimeEnvironment = RuntimeEnvironmentBuilder.Factory.get().newEmptyBuilder()
				.persistence(true)
				.knowledgeBase(kieContainer.getKieBase())
				.addEnvironmentEntry(EnvironmentName.ENTITY_MANAGER_FACTORY, emf)
				.entityManagerFactory(emf)
				.get();
		return runtimeManagerFactory
				.newPerRequestRuntimeManager(runtimeEnvironment);
	}

}
