package confcost.controller.encryption;

import java.security.GeneralSecurityException;

import javax.crypto.Cipher;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.SendMode;

/**
 * Bouncy Castle AES Encryption
 * 
 * @author Marc Eichler
 *
 */
public class AESEncryption extends SymmetricEncryption {
	public static final @NonNull String NAME = "AES";
	public static final @NonNull String PROVIDER = "BC";
	
	@Override
	public @NonNull String getAlgorithm() {
		return NAME;
	}
	
	/**
	 * Constructor
	 * @param provider	The security provider
	 * @param keyExchange	The key exchange protocol
	 */
	public AESEncryption(final @NonNull SendMode sendMode) {
		super(sendMode);
	}
	
	@Override
	public @NonNull byte[] encrypt(@NonNull byte @NonNull [] message) throws GeneralSecurityException {
		if (this.key == null) throw new IllegalStateException("No key set!");

		Cipher cipher = Cipher.getInstance(NAME, this.provider);
		cipher.init(Cipher.ENCRYPT_MODE, this.key);
		return cipher.doFinal(message);
	}
	
	@Override
	public @NonNull byte[] decrypt(@NonNull byte @NonNull [] message) throws GeneralSecurityException {
		if (this.key == null) throw new IllegalStateException("No key set!");

		Cipher cipher = Cipher.getInstance(NAME, this.provider);
		cipher.init(Cipher.DECRYPT_MODE, this.key);
		return cipher.doFinal(message);
	}
}
