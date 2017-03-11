package confcost.model;


import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.algorithm.Encryption;
import confcost.controller.algorithm.Signature;
import confcost.controller.ke.KeyExchange;

public class CryptoIteration {
	
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

	private final int iteration;

	private final @NonNull CryptoPass pass;
	
	/**
	 * Creates a new {@link CryptoIteration}
	 * 
	 * @param iteration	The current iteration
	 * @param mode	The {@link SendMode}
	 */
	public CryptoIteration(final int iteration, final @NonNull CryptoPass pass) {
		this.iteration = iteration;
		this.pass = pass;
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
	
	public final @NonNull CryptoPass getPass() {
		return this.pass;
	}
	
	@Override
	public @NonNull String toString() {
		final @NonNull SendMode mode = this.pass.getSendMode();
		StringBuilder ret = new StringBuilder("["+(iteration+1)+"] ");
		if (mode.signature != null) {
			ret.append(Signature.getName(mode.signature) + " with ");
		} 
		
		ret.append(Encryption.getName(mode.encryption));
		
		if (mode.keyExchange != null) {
			ret.append("/"+KeyExchange.getName(mode.keyExchange));
		}
		
		return ret.toString();
	}
}
