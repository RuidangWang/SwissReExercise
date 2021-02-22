package com.jobs.swissre;
import java.util.List;

public class AppMain {
	
	private static LoadBalancer myLoadBalancer;
	private static void runWithRandomInvocator() {
		 myLoadBalancer = new LoadBalancer();
		
		//providerManager create instance of provider
		ProviderFactory providerFactory = new ProviderFactory();
		List<Provider> providerList = providerFactory.createProvider("SIMPLE", 5);
				
		//register the provider list to loadBalancer
		myLoadBalancer.register(providerList);
		
		//loadBalancer processRequst
		processRequests();
		
		
	}

	private static void processRequests()
	{

		//loadBalancer processRequst
		for (int i=0; i<10; i++) {
			StringBuilder response = new StringBuilder();
			myLoadBalancer.processRequest(new String("request"), response);
		}
	}
	
	public static void main(String[] args) {
		
		try {
			runWithRandomInvocator();
			
			//set a new invocator
			Invocator roundRobinInvocator = new RoundRobinInvocator();
			myLoadBalancer.setInvocator(roundRobinInvocator);
			
			processRequests();
			
			myLoadBalancer.heartBeatCheck();
			
			for (int i=0; i<100;  i++) {
				myLoadBalancer.get().setLiveState( i %5 != 0 );
				processRequests();
				Thread.sleep(myLoadBalancer.HEART_BEAT_CHECK_SECONDS*1000);
			}
		}catch(InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
		
		
   }

}
