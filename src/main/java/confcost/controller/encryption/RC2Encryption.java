package confcost.controller.encryption;

import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.eclipse.jdt.annotation.NonNull;
import confcost.controller.ke.KeyExchange;

/**
 * Bouncy Castle RC2 Encryption
 * 
 * @author Aziani
 *
 */
public class RC2Encryption extends SymmetricEncryption {

	public RC2Encryption(final @NonNull String provider, final @NonNull KeyExchange keyExchange) {
		super("RC2", provider, keyExchange);
	}
	
	@Override
	public @NonNull byte[] encrypt(@NonNull byte @NonNull [] message) throws GeneralSecurityException {
		if (this.key == null) throw new IllegalStateException("No key set!");

		Cipher cipher = Cipher.getInstance(this.algorithm, this.provider);
		cipher.init(Cipher.ENCRYPT_MODE, this.key);
		return cipher.doFinal(message);
	}
	
	@Override
	public @NonNull byte[] decrypt(@NonNull byte @NonNull [] message) throws GeneralSecurityException {
		if (this.key == null) throw new IllegalStateException("No key set!");

		Cipher cipher = Cipher.getInstance(this.algorithm, this.provider);
		cipher.init(Cipher.DECRYPT_MODE, this.key);
		return cipher.doFinal(message);
	}
	
	@Override
	public void generateKey(final int bitLength, final @NonNull byte[] secret) {
		this.key = new SecretKeySpec(this.shortenKey(keyExchange.getKey(), bitLength), this.algorithm);
	}
	
	private byte[] shortenKey(final byte[] key, int length) {
		final byte[] shortKey = new byte[length/8];
		
		System.arraycopy(key, 0,  shortKey, 0, Math.min(key.length, shortKey.length));
		
		return shortKey;
	}
}
