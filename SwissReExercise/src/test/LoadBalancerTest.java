package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jobs.swissre.LoadBalancer;
import com.jobs.swissre.Provider;
import com.jobs.swissre.ProviderFactory;
import com.jobs.swissre.RoundRobinInvocator;

public class LoadBalancerTest {

	LoadBalancer defaultLoadBalancer = new LoadBalancer();
	
	/*LoadBalancerTest(){
		ProviderFactory providerFactory = new ProviderFactory();
		List<Provider> providerList = providerFactory.createProvider("SIMPLE", 5);
	    
		defaultLoadBalancer.register(providerList);
	}*/
	
	@Before
	public void buildupProviders()
	{
		ProviderFactory providerFactory = new ProviderFactory();
		List<Provider> providerList = providerFactory.createProvider("SIMPLE", 5);
		   
		defaultLoadBalancer.register(providerList);
	}
	
	@Test
	public void testLoadBalancer() {
		assert(defaultLoadBalancer != null);
	}

	
	@Test
	public void testRegister() {
			
		ProviderFactory providerFactory = new ProviderFactory();		
		List<Provider> providerList = providerFactory.createProvider("SIMPLE", 6);
		defaultLoadBalancer.register(providerList);
		
		providerList = providerFactory.createProvider("SIMPLE", 2);
		defaultLoadBalancer.register(providerList);
		
			
	}

	@Test
	public void testGet() {
		Provider provider = defaultLoadBalancer.get();
		assert (provider != null) ; 
		
	}

	@Test
	public void testIncludeProvider() {

			
		defaultLoadBalancer.excludeProvider(defaultLoadBalancer.get());
	
		defaultLoadBalancer.includeProvider(defaultLoadBalancer.get());
	}

	/* @Test
	public void testExcludeProvider() {
		fail("Not yet implemented");
	} */

	@Test
	public void testProcessRequest() {
		
		StringBuilder response = new StringBuilder();
		assert( defaultLoadBalancer.processRequest(new String("Request Data Test"), response ) );
		assert(response.toString().equals(new String("Response Data")));
		
	}

	@Test
	public void testHeartBeatCheck() {
		LoadBalancer myLoadBalancer = new LoadBalancer();
		RoundRobinInvocator rrInvocator = new RoundRobinInvocator();
		myLoadBalancer.setInvocator(rrInvocator);
		
		ProviderFactory providerFactory = new ProviderFactory();
		List<Provider> providerList = providerFactory.createProvider("SIMPLE", 10);
					
		//register the provider list to loadBalancer
		myLoadBalancer.register(providerList);
		
		myLoadBalancer.heartBeatCheck();
		
		//manually set the live state
		for (int i = 0; i<20; i++) {
			Provider provider = myLoadBalancer.get();
			provider.setLiveState( i%2==0);
			
		}
	}

}
