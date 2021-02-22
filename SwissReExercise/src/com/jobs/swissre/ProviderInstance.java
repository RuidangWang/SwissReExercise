package com.jobs.swissre;

/*
 * a data structure to manage the provider instance
 */

public class ProviderInstance {

	//keep the instance of a provider
	private Provider instance;
	
	//whether or not the provider is included by the load balancer
	volatile private boolean inclusive;

	
	public ProviderInstance( Provider instance) {
		this.instance= instance;
		this.inclusive = true;
		
	}
	public ProviderInstance( Provider instance, boolean inclusive) {
		this.instance= instance;
		this.inclusive = inclusive;	
	}
		
	public Provider getInstance() {
		return instance;
	}
	public void setInstance(Provider instance) {
		this.instance = instance;
	}


	public boolean isInclusive() {
		return inclusive;
	}


	public void setInclusive(boolean inclusive) {
		this.inclusive = inclusive;
	}
	
	
	
}
