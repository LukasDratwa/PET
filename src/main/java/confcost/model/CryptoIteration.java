package confcost.model;

public class CryptoIteration {
	
	private long encryptionTime;
	private long decryptionTime;
	private long initializationTime;
	private int keyLength;
	
	public CryptoIteration(long encryptionTime, long decryptionTime, long initializationTime, int keyLength) {
		this.encryptionTime = encryptionTime;
		this.decryptionTime = decryptionTime;
		this.initializationTime = initializationTime;
		this.keyLength = keyLength;
	}
	
	public long getEncryptionTime() {
		return encryptionTime;
	}
	
	public long getDecryptionTime() {
		return decryptionTime;
	}
	
	public long getInitializationTime() {
		return initializationTime;
	}
	
	public int getKeyLength() {
		return keyLength;
	}
}
