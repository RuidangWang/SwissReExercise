package com.jobs.swissre;
/*
 *  An implementation of Provider
 */

public class SimpleProvider implements Provider {
	
	private int consecutiveHeartBeat;
	private boolean lastLiveState;
	private boolean liveState;
	private int maxParellelRequest;
	
	
	public SimpleProvider() {
		consecutiveHeartBeat = 0;
		maxParellelRequest = 0;
		lastLiveState= false; 
		liveState= false;
	}
	
	public SimpleProvider(int maxParellelRequest) {
		consecutiveHeartBeat = 0;
		this.maxParellelRequest = maxParellelRequest;
		lastLiveState= false;
		liveState= false;
		
	}
	
	@Override
	public void setMaxParallelRequest(int maxParellelRequest) {
		this.maxParellelRequest = maxParellelRequest;
	}

	@Override
	public int getMaxParellelRequest() {
		return maxParellelRequest;
	}

	@Override
	public boolean processRequest(String request, StringBuilder response){
        response.append("Response Data");
		System.out.println("Provider: "+ get() + " processRequest: Req=" + request + ", Rsp=" + response );
	    return true;
	}

	@Override
	//user class name plus object id to identify the provider
	public String get() {
		String id = this.getClass().getName() + String.valueOf( this.hashCode() );
		return id;
	
	}

	@Override
	//function will be called by the load balancer: heartBeatCheck.
	public boolean check() {
		boolean isAlive = checkAlive();
		updateConsecutivHeartBeat();
		System.out.println( "SimpleProvider" + this.get() + ".check(), live ?= " + isAlive);

		return isAlive;
	}

	//decide whether the provider is alive or not. For the moment, return the liveState which can be manually set. 
	private boolean checkAlive() {
		return this.liveState;
		
	}
	
	@Override
	public int getConsecutiveHeartBeat() {
		
		return this.consecutiveHeartBeat;
	}

	// update consecutiveHeartBeat
	// if the state changes, set consecutiveHeartBeat as 1; otherwise just increase it.
	synchronized private void updateConsecutivHeartBeat() {
				
		if (this.liveState == this.lastLiveState) {
			this.consecutiveHeartBeat++;
		}
		else {
			this.consecutiveHeartBeat =1;
		}
		
	}
	
	//set the live State, for testing purpose
	synchronized public void setLiveState(boolean isAlive) {
		System.out.println("The state of the provider "+this.get() + " was set manually. is alive ?=" +isAlive);
		this.lastLiveState = this.liveState;
		this.liveState = isAlive;
	}
	
}
