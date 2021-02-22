package com.jobs.swissre;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Predicate;
/*
 * LoadBalancer Class, maintains a list of providers,
 * forward the processRequest to one provider through an invocator 
 */
public class LoadBalancer {
	final int MAX_PROVIDER_NUMBER = 10;
	final int HEART_BEAT_CHECK_SECONDS = 3;
	
	//store all registered Providers
	private List<ProviderInstance> providerList;

	private Invocator invocator;
	volatile long  parellelRequestCount;  //TODO, not really parallel request count, just request count
	
	public Invocator getInvocator() {
		return invocator;
	}

	public void setInvocator(Invocator invocator) {
		this.invocator = invocator;
	}

	//Default constructor, use RandomInvocator
	public LoadBalancer() {
		providerList  = new ArrayList<ProviderInstance>();  
		invocator = new RandomInvocator(); 
		parellelRequestCount=0;
		
	}

	//Constructor with an invocator as parameter
	public LoadBalancer(Invocator invocator) {
		providerList = new ArrayList<ProviderInstance>();
		this.invocator = invocator;  
	}
	
	//register the serviceProvider list to the load balancer
	//can only register the maximal number
	public void register(List<Provider> providers) {
		if (this.providerList.size() >= MAX_PROVIDER_NUMBER) {
			System.out.println("The maximal provider instance number " + MAX_PROVIDER_NUMBER + " reached, cannot add more provider instance");
			return;
		}
		else
		{
			int startIdx = providerList.size();
			for (int i = 0; i< (MAX_PROVIDER_NUMBER-startIdx ) && i<providers.size(); i++) {
				//TODO: default include the provider into the list.
				this.providerList.add(new ProviderInstance(providers.get(i), true) );  
				System.out.println("A provider: " + providers.get(i).get() +" was registered.");
			}
		}
		
	}

	// return a Provider
	public Provider get() {
		//get a provider
		ProviderInstance providerInstance = invocator.get(this.providerList); 
		if (providerInstance != null ) {
			return providerInstance.getInstance();
		}
		else
			return null;
		
	}
	
	
	//include specific provider type or instance??
	public void includeProvider(Provider provider ) {
		for (ProviderInstance providerInstance : providerList) {
			if (providerInstance.getInstance().equals(provider) ) {
				System.out.println("A provider " + providerInstance.getInstance().get() +" is included");
				providerInstance.setInclusive(true);
			}
		}
		
	}
	
	//remove a service provide instance
	public void excludeProvider(Provider provider) {
		for (ProviderInstance providerInstance : providerList) {
			if (providerInstance.getInstance().equals(provider) ) {
				System.out.println("A provider " + providerInstance.getInstance().get() +" is excluded");
				providerInstance.setInclusive(false);
			}
		}
	}
	
	//processRequest, then forward the request to a provider
	public boolean processRequest(String request, StringBuilder reponse) {
		if (isMaxCapacity()) {
			System.out.println("Reached the maximal capacity, could not process furthur requests.");
			return false;
		}
		
		//get a provider
		ProviderInstance pi = invocator.get(this.providerList);
		if (pi.isInclusive()) {
			this.parellelRequestCount++;
			System.out.println("processRequest: forward the request to the provider: "+ pi.getInstance().get());
			return pi.getInstance().processRequest(request, reponse);
		}
		else
		{
			//TODO:??
			System.out.println("!!!the provider is excluded!!! TODO: looking for next available provider");
			return false;
		}
		
	}
	
	//heatBeatCheck, check the state of the provider in every HEAT_BEAT_CHECK_SECONDS
	//if it is not alive, exclude from the load balancer
	//if it is 2 times consecutive alive again, include it into the load balancer
	public void heartBeatCheck() {
		Timer timer = new Timer();
		int begin = 0;
		int timeInterval = HEART_BEAT_CHECK_SECONDS*1000;

		timer.scheduleAtFixedRate(new TimerTask() {
		    @Override
		    public void run() {
		        checkExclude();
		        checkInclude();
		    }
		}, begin, timeInterval);
	}
	

	// whether or not the maximal capacity of the cluster is reached
	private boolean isMaxCapacity() {
		long maxParellelRequest = 0;
		for (ProviderInstance pi: providerList) {
			if (pi.isInclusive()) {
				maxParellelRequest += pi.getInstance().getMaxParellelRequest(); 
			}
		}
		
		if (parellelRequestCount >= maxParellelRequest ) return true;
		else
			 return false;
	}
	
	
	// if a provider's heart beat return "not alive", exclude it
	private void checkExclude() {
		  for ( ProviderInstance pi : providerList ) {
			if (pi.isInclusive()) {
				//the instance is not inclusive
				if ( !pi.getInstance().check() ) {
					pi.setInclusive(false);
					System.out.println("LoadBalancer: the provider " + pi.getInstance().get()+ "is excluded.");
				}
			}
			
		}
	}
	
	
	//include the provider again when it is two consecutive heart beat live
	private void checkInclude() {
		for ( ProviderInstance pi : providerList ) {
			if (!pi.isInclusive()) {
				//the instance is not inclusive
				if (pi.getInstance().check() && pi.getInstance().getConsecutiveHeartBeat()>=2 ) {
					pi.setInclusive(true);
					System.out.println("LoadBalancer: the provider " + pi.getInstance().get()+ "is included");

				}
			}
			
		}
	}
	
	
}
