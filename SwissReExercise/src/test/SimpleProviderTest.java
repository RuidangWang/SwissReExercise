package test;
import com.jobs.swissre.SimpleProvider;
import static org.junit.Assert.*;

import org.junit.Test;

public class SimpleProviderTest {
	
	SimpleProvider sp1= new SimpleProvider();
	SimpleProvider sp_requestNum = new SimpleProvider(5);
	
	

	@Test
	public void testSimpleProvider() {
		assert(sp1.getMaxParellelRequest()==0);
		assert(sp1.getConsecutiveHeartBeat() == 0);
	}

	@Test
	public void testSimpleProviderInt() {
		assert(sp_requestNum.getMaxParellelRequest() == 5);
	}

	@Test
	public void testSetMaxParallelRequest() {
		sp_requestNum.setMaxParallelRequest(6);
		assert(sp_requestNum.getMaxParellelRequest() == 6);
	}

	@Test
	public void testGetMaxParellelRequest() {
		assert(sp1.getMaxParellelRequest() == 0);
		assert(sp_requestNum.getMaxParellelRequest() ==5 );
		assert(sp1.getConsecutiveHeartBeat() == 0);
		
	}

	@Test
	public void testProcessRequest() {
		StringBuilder response = new StringBuilder();
		assert( sp1.processRequest(new String("Request Data Test"), response ) );
		assert(response.toString().equals(new String("Response Data")));
		assert(sp1.getConsecutiveHeartBeat() == 0);
	}

	@Test
	public void testGet() {
		String id = sp1.getClass().getName() + String.valueOf( sp1.hashCode());
		
		assert(id.equals(sp1.get()) );
		assert(sp1.getConsecutiveHeartBeat() == 0);
	}

	@Test
	public void testCheck_ConsecutiveHeartBeat() {
	 /*	sp1.setLiveState(false);
		assert(!sp1.check());
		assert(sp1.getConsecutiveHeartBeat() == 2);
		*/
		
		SimpleProvider sp2 = new SimpleProvider();
		sp2.setLiveState(false);
		assert(sp2.getConsecutiveHeartBeat() == 0);
		assert(!sp2.check());
		assert(sp2.getConsecutiveHeartBeat() == 1);
		
		sp1.setLiveState(true);
		assert (sp1.check());
		assert(sp1.getConsecutiveHeartBeat() == 1);
		
		sp1.setLiveState(true);
		assert (sp1.check());
		assert(sp1.getConsecutiveHeartBeat() == 2);
		
		sp1.setLiveState(true);
		assert (sp1.check());
		assert(sp1.getConsecutiveHeartBeat() == 3);
		
		sp1.setLiveState(false);
		assert (!sp1.check());
		assert(sp1.getConsecutiveHeartBeat() == 1);
		
	}

	

	

}
