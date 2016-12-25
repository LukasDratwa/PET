package confcost.model;

public class CryptoIteration {
	
	private long executionTime;
	private long initializationTime;
	private int keyLength;
	
	public CryptoIteration(long executionTime, long initializationTime, int keyLength) {
		this.executionTime = executionTime;
		this.initializationTime = initializationTime;
	}
	
	public long getExecutionTime() {
		return executionTime;
	}
	
	public long getInitializationTime() {
		return initializationTime;
	}
	
	public int getKeyLength() {
		return keyLength;
	}
}
