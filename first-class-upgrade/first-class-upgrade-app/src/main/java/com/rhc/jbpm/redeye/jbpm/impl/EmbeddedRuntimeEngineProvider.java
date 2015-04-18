package com.rhc.jbpm.redeye.jbpm.impl;

import javax.enterprise.inject.Alternative;

import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.internal.runtime.manager.context.EmptyContext;

import com.rhc.jbpm.redeye.jbpm.RuntimeEngineProvider;

@Alternative
public class EmbeddedRuntimeEngineProvider implements RuntimeEngineProvider {
	RuntimeManager runtimeManager;

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
		String version = "1.0.0";

		//Incomplete, still need to set up persistence
		RuntimeEnvironment runtimeEnvironment = RuntimeEnvironmentBuilder.Factory
				.get().newDefaultBuilder(groupId, artifactId, version).get();
		return runtimeManagerFactory
				.newPerRequestRuntimeManager(runtimeEnvironment);
	}

}
