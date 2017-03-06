package confcost.controller.encryption;

import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.SendMode;

/**
 * Bouncy Castle RC2 Encryption
 * 
 * @author Aziani
 *
 */
public class RC2Encryption extends SymmetricEncryption {
	/**
	 * A printable, unique name of the algorithm
	 */
	public static final @NonNull String NAME = "RC2";

	/**
	 * The supported key length
	 */
	public static final @NonNull Integer[] KEY_LENGTHS = { 64, 128 };
	
	@Override
	public @NonNull String getAlgorithm() {
		return NAME;
	}

	/**
	 * Constructs an {@link RC2Encryption} based on the specified {@link SendMode}
	 * @param mode	The {@link SendMode}
	 */
	public RC2Encryption(final @NonNull SendMode mode) {
		super(mode);
	}
	
	@Override
	public final @NonNull byte[] encrypt(final @NonNull byte @NonNull [] message) throws GeneralSecurityException {
		if (this.key == null) throw new IllegalStateException("No key set!");

		Cipher cipher = Cipher.getInstance(getAlgorithm(), this.provider);
		cipher.init(Cipher.ENCRYPT_MODE, this.key);
		return cipher.doFinal(message);
	}
	
	@Override
	public final @NonNull byte[] decrypt(final @NonNull byte @NonNull [] message) throws GeneralSecurityException {
		if (this.key == null) throw new IllegalStateException("No key set!");

		Cipher cipher = Cipher.getInstance(getAlgorithm(), this.provider);
		cipher.init(Cipher.DECRYPT_MODE, this.key);
		return cipher.doFinal(message);
	}
	
	@Override
	public void generateKey(final int bitLength, final @NonNull byte[] secret) {
		this.key = new SecretKeySpec(this.shortenKey(keyExchange.getKey(), bitLength), getAlgorithm());
	}
	
	private final byte[] shortenKey(final byte[] key, int length) {
		final byte[] shortKey = new byte[length/8];
		
		System.arraycopy(key, 0,  shortKey, 0, Math.min(key.length, shortKey.length));
		
		return shortKey;
	}
}
