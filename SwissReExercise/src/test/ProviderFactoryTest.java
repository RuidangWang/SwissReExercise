package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.jobs.swissre.Provider;
import com.jobs.swissre.ProviderFactory;

public class ProviderFactoryTest {

	ProviderFactory providerFactory = new ProviderFactory();
	
	@Test
	public void testProviderFactory() {
		
		assert(providerFactory != null);
		
	}

	@Test
	public void testCreateProvider() {
		List<Provider> providers = 
				providerFactory.createProvider(ProviderFactory.PROVIDER_NAME_SIMPLE, 5);
		assert(5 == providers.size() );
		assert(providers.get(3) != null );
		
		assert(providers.get(3).getClass().getName() == "com.jobs.swissre.SimpleProvider");
		
		List<Provider> providers2 = 
				providerFactory.createProvider("Notimplemented", 5);
		assert(0 == providers2.size() );
		
	}

}
