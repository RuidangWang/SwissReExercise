package com.jobs.swissre;
import java.util.List;
import java.util.Random;
/*
 * a RandomInvocator, implements the invocator interface.
 * which gets a provider from the list randomly
 * TODO: need to think about when the provider is excluded by the load balancer
 */
public class RandomInvocator implements Invocator {

	@Override
	public ProviderInstance get(List<ProviderInstance> providerInstanceList) {
		
	  //TODO: should only return the live provider
		if (providerInstanceList == null || providerInstanceList.size() == 0 ) {
			System.out.println("empty provider list!!");
			return null;
		}
		
		Integer idx = (new Random()).nextInt(providerInstanceList.size()-1);
				
		return providerInstanceList.get(idx);
		
		
	}

	
}
