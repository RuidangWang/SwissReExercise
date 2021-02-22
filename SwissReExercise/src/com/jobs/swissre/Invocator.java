package com.jobs.swissre;
import java.util.List;

/*
 * interface of Invocation which decides which provider instance should be called.
 */
//access the list of provider
public interface Invocator {
	
	public ProviderInstance get(List<ProviderInstance> providerInstanceList);
	
	
}
