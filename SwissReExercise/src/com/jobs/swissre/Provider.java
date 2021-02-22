package com.jobs.swissre;

/*
 * Interface Provider
 */
public interface Provider {
	
	//get the unique identifier of the provider instance
	public String get();  
		
	//whether or not the service provider alive
	public boolean check();
	
	public boolean processRequest(String request, StringBuilder response);
	
	//how many consecutive heart beats which has the same status, alive or not
	public int getConsecutiveHeartBeat();
	
	//set maximal parallel request number 
	public void setMaxParallelRequest( int maxParellelRequest);
	
	//get maximal parallel request number
	public int getMaxParellelRequest();
	
	//set the live state manually, for testing and controlling
	public void setLiveState(boolean isAlive) ;
	
	

}
