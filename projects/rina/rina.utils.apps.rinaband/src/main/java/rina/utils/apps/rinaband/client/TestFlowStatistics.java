package rina.utils.apps.rinaband.client;

/**
 * Contains the statistics of a single Flow
 * @author eduardgrasa
 *
 */
public class TestFlowStatistics {
	
	/**
	 * The flow setup time in milliseconds
	 */
	private long flowSetupTimeInMillis = 0;
	
	/**
	 * The flow tearDown time in milliseconds
	 */
	private long flowTearDownTimeInMillis = 0;
	
	/**
	 * The SDUs/second sent
	 */
	private long sentSDUsPerSecond = 0;
	
	/**
	 * The SDUs/second received
	 */
	private long receivedSDUsPerSecond = 0;

	public long getFlowSetupTimeInMillis() {
		return flowSetupTimeInMillis;
	}

	public void setFlowSetupTimeInMillis(long flowSetupTimeInMillis) {
		this.flowSetupTimeInMillis = flowSetupTimeInMillis;
	}
	
	public long getSentSDUsPerSecond() {
		return sentSDUsPerSecond;
	}

	public void setSentSDUsPerSecond(long sentSDUsPerSecond) {
		this.sentSDUsPerSecond = sentSDUsPerSecond;
	}

	public long getReceivedSDUsPerSecond() {
		return receivedSDUsPerSecond;
	}

	public void setReceivedSDUsPerSecond(long receivedSDUsPerSecond) {
		this.receivedSDUsPerSecond = receivedSDUsPerSecond;
	}

	public long getFlowTearDownTimeInMillis() {
		return flowTearDownTimeInMillis;
	}

	public void setFlowTearDownTimeInMillis(long flowTearDownTimeInMillis) {
		this.flowTearDownTimeInMillis = flowTearDownTimeInMillis;
	}
}