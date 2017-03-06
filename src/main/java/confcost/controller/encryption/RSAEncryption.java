package confcost.controller.encryption;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.RSAKeyGenParameterSpec;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.SendMode;
import confcost.util.HexString;

/**
 * Bouncy Castle RSA Encryption
 * 
 * @author Marc Eichler
 *
 */
public class RSAEncryption extends AsymmetricEncryption {
	/**
	 * A printable, unique name of the algorithm
	 */
	public static final @NonNull String NAME = "RSA";

	/**
	 * The supported key length
	 */
	public static final @NonNull Integer[] KEY_LENGTHS = { 128, 256, 512, 1024, 2048, 4096, 8192 };
	
	@Override
	public @NonNull String getAlgorithm() {
		return NAME;
	}

	/**
	 * Constructs a new {@link RSAEncryption} based on the specified {@link SendMode}
	 * @param mode	The {@link SendMode}
	 */
	public RSAEncryption(final @NonNull SendMode mode) {
		super(mode);
	}

	@Override
	public void generateKeyPair(int keyLength)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
		System.out.println("RSAEncryption::generateKeyPair >> Generating key pair ("+keyLength+" bit)");
		final KeyPairGenerator gen = KeyPairGenerator.getInstance(getAlgorithm(), this.provider);
		gen.initialize(new RSAKeyGenParameterSpec(keyLength, new BigInteger("3")));
		final KeyPair keyPair = gen.genKeyPair();
		this.publicKey = keyPair.getPublic();
		this.privateKey = keyPair.getPrivate();
		System.out.println("RSAEncryption::generateKeyPair >> Private key: "+new HexString(keyPair.getPrivate().getEncoded()));
		System.out.println("RSAEncryption::generateKeyPair >> Public key: "+new HexString(keyPair.getPublic().getEncoded()));
	}

}
