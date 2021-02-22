package com.jobs.swissre;
import java.util.List;
/*
 * a RoundRobinInvocator, return the next provider as sequential
 */
public class RoundRobinInvocator implements Invocator {

	private Integer nextInvocatedProvider;
	
	public RoundRobinInvocator() {
		nextInvocatedProvider = 0;
	}
	
	@Override
	public ProviderInstance get(List<ProviderInstance> providerInstanceList) {
		
		//TODO?? get only live provider 
		if (nextInvocatedProvider == (providerInstanceList.size()-1) ) 
			nextInvocatedProvider =0;
		else
			nextInvocatedProvider++;
		
		return providerInstanceList.get(nextInvocatedProvider);
	}

}
