package confcost.controller.encryption;

import java.security.GeneralSecurityException;

import javax.crypto.Cipher;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.ke.KeyExchange;

/**
 * Bouncy Castle AES Encryption
 * 
 * @author Marc Eichler
 *
 */
public class AESEncryption extends SymmetricEncryption {

	public AESEncryption(final @NonNull String provider, final @NonNull KeyExchange keyExchange) {
		super("AES", provider, keyExchange);
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
}
