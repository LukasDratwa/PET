package confcost.model;

import org.eclipse.jdt.annotation.NonNull;

public class CryptoIteration {
	
	// time needed for the cryptographic operations
	/**
	 * Time to initialize the cryptographic algorithm in ms
	 */
	private long initTime;
	
	/**
	 * Time to initialize the cryptographic algorithm on the remote end in ms
	 */
	private long remoteInitTime;
	
	/**
	 * Time to encrypt the message in ms
	 */
	private long encryptionTime;
	
	/**
	 * Time to decrypt the message in ms
	 */
	private long decryptionTime;
	
	// inputs of the cryptographic algorithm
	private final int keyLength;
	private final long messageLength;
	private final @NonNull CProtocol algorithm;
	private final @NonNull KEProtocol keyExchange;
	
	public CryptoIteration(final @NonNull CProtocol algorithm, final @NonNull KEProtocol keyExchange, final int keyLength, final long messageLength) {
		this.algorithm = algorithm;
		this.keyExchange = keyExchange;
		this.keyLength = keyLength;
		this.messageLength = messageLength;
	}

	public void setInitTime(final long initializationTime) {
		this.initTime = initializationTime;
	}

	public void setRemoteInitTime(final long remoteInitTime) {
		this.remoteInitTime = remoteInitTime;
	}

	public void setEncryptionTime(final long encryptionTime) {
		this.encryptionTime = encryptionTime;
	}

	public void setDecryptionTime(final long decryptionTime) {
		this.decryptionTime = decryptionTime;
	}

	public long getInitTime() {
		return initTime;
	}
	public long getRemoteInitTime() {
		return remoteInitTime;
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
	public CProtocol getAlgorithm() {
		return algorithm;
	}
	public KEProtocol getKeyExchange() {
		return keyExchange;
	}
	public long getMessageLength() {
		return messageLength;
	}
	
	@Override
	public @NonNull String toString() {
		return this.algorithm+"/"+this.keyExchange+" ("+this.keyLength+","+this.messageLength+") ";
	}
}
