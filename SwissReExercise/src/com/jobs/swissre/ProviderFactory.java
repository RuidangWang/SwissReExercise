package com.jobs.swissre;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
 *  Provider Factory, to create a provider
 */
public class ProviderFactory {

	public final static String PROVIDER_NAME_SIMPLE = "SIMPLE";
	final static int MAX_PARELLEL_REQUEST = 1000; 
		
	public ProviderFactory() {
	}
	
	//create instanceNumber Instance of the Provider with the providerName
	//@providerName: the name of the provider
	//@InstanceNumber: the number of instance of the provider 
	public List<Provider> createProvider(String providerName, Integer instanceNumber) {
		List<Provider> providerList = new ArrayList<Provider>();
		
		for (int i=0; i<instanceNumber; i++) {
			if (providerName == PROVIDER_NAME_SIMPLE )  {
				Provider provider = new SimpleProvider(MAX_PARELLEL_REQUEST);
				providerList.add(provider);
			}
			else {
				System.out.println("do not support the provider " + providerName + "use SimpleServiceProvider");  //??
			}
		}
		
		return providerList;
	}
	
}
