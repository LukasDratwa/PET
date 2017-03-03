package confcost.model;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import confcost.controller.encryption.Encryption;
import confcost.controller.ke.KeyExchange;

public class CryptoIteration {
	
	// time needed for the cryptographic operations
	/**
	 * Time to initialize the cryptographic algorithm in micro seconds μs
	 */
	private long initTime;
	
	/**
	 * Time to initialize the cryptographic algorithm on the remote end in micro seconds μs
	 */
	private long remoteInitTime;
	
	/**
	 * Time to encrypt the message in micro seconds μs
	 */
	private long encryptionTime;
	
	/**
	 * Time to decrypt the message in micro seconds μs
	 */
	private long decryptionTime;
	
	// inputs of the cryptographic algorithm
	private final int keyLength;
	private final long messageLength;
	private final @NonNull Class<? extends Encryption> algorithm;
	private final @Nullable Class<? extends KeyExchange> keyExchange;
	
	public CryptoIteration(final @NonNull Class<? extends Encryption> algorithm, 
			final @Nullable Class<? extends KeyExchange> keyExchange, 
			final int keyLength, final long messageLength) {
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
	public final @NonNull Class<? extends Encryption> getAlgorithm() {
		return algorithm;
	}
	public final @Nullable Class<? extends KeyExchange> getKeyExchange() {
		return keyExchange;
	}
	public long getMessageLength() {
		return messageLength;
	}
	
	@Override
	public @NonNull String toString() {
		return Encryption.getName(this.algorithm)+"/"+KeyExchange.getName(this.keyExchange)
		+" ("+this.keyLength+","+this.messageLength+") ";
	}
}
