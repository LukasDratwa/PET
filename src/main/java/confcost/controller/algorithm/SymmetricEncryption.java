package confcost.controller.algorithm;

import java.lang.reflect.Constructor;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import confcost.controller.ke.KeyExchange;
import confcost.model.SendMode;

/**
 * Provides the interface for symmetric encryption methods.
 * 
 * @author Marc Eichler
 *
 */
public abstract class SymmetricEncryption extends Encryption {
	/**
	 * The {@link KeyExchange}
	 */
	protected @NonNull KeyExchange keyExchange;
	
	/**
	 * The {@link SecretKey}
	 */
	protected @Nullable SecretKey secretKey;
	
	/**
	 * Create a new {@link AsymmetricEncryption} with the specified key exchange protocol
	 * 
	 * @param mode	The {@link SendMode}
	 */
	public SymmetricEncryption(final @NonNull SendMode mode) {
		super(mode);
		
		// Initialize KeyExchange
		Constructor<? extends KeyExchange> c;
		try {
			c = mode.keyExchange.getConstructor((Class<?>[])null);
			keyExchange = c.newInstance((Object[])null);
		} catch (ReflectiveOperationException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the {@link KeyExchange}
	 */
	public final @NonNull KeyExchange getKeyExchange() {
		return this.keyExchange;
	}
	
	/**
	 * @return the {@link SecretKey}
	 */
	public final @NonNull SecretKey getSecretKey() {
		return this.secretKey;
	}
	
	/**
	 * Generates an appropriate key based on the specified length and secret.
	 * @param bitLength	The length of the key in bit
	 * @param secret	The secret
	 * @throws InvalidKeySpecException	If the secret is an invalid key
	 * @throws NoSuchProviderException	If the provider was not found 	
	 * @throws NoSuchAlgorithmException 	If the algorithm was not found
	 */
	public void generateKey(final int bitLength, final @NonNull byte[] secret) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException {
		SecretKeySpec spec = new SecretKeySpec(shortenKey(keyExchange.getKey(), bitLength), this.getName());
		this.secretKey = spec;
	}

	
	/**
	 * Returns a the specified key, shortened to <code>length</code> bit.
	 * @param key	The key
	 * @param length	The length in bit
	 * @return	The shortened key
	 */
	private byte[] shortenKey(final @NonNull byte[] key, int length) {
		final byte[] shortKey = new byte[length/8];
		
		System.arraycopy(key, 0,  shortKey, 0, shortKey.length);
		
		return shortKey;
	}
}
