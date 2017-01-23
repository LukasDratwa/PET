package confcost.model;

public class CryptoIteration {
	
	// time needed for the cryptographic operations
	private long initializationTime;
	private long encryptionTime;
	private long decryptionTime;
	
	// inputs of the cryptographic algorithm
	private int keyLength;
	private long messageLength;
	private String algoName;
	
	public CryptoIteration(long initializationTime, long encryptionTime, long decryptionTime, int keyLength,String algoName ,long messageLength) {
		this.initializationTime = initializationTime;
		this.encryptionTime = encryptionTime;
		this.decryptionTime = decryptionTime;
		this.keyLength = keyLength;
		this.algoName = algoName;
		this.messageLength = messageLength;
	}
	
	public long getInitializationTime() {
		return initializationTime;
	}
	public long getEncryptionTime() {
		return encryptionTime;
	}
	public long getDecryptionTime() {
		return decryptionTime;
	}
	public int getKeyLength() {
		return keyLength;
	}
	public String getAlgoName() {
		return algoName;
	}
	public long getMessageLength() {
		return messageLength;
	}
}
