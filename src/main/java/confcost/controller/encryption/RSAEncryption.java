package confcost.controller.encryption;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.RSAKeyGenParameterSpec;

import org.eclipse.jdt.annotation.NonNull;

import confcost.util.HexString;

/**
 * Bouncy Castle RSA Encryption
 * 
 * @author Marc Eichler
 *
 */
public class RSAEncryption extends AsymmetricEncryption {

	public RSAEncryption(@NonNull String provider) {
		super("RSA", provider);
	}

	@Override
	public void generateKeyPair(int keyLength)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
		System.out.println("RSAEncryption::generateKeyPair >> Generating key pair");
		final KeyPairGenerator gen = KeyPairGenerator.getInstance(this.algorithm, this.provider);
		gen.initialize(new RSAKeyGenParameterSpec(keyLength, new BigInteger("3")));
		final KeyPair keyPair = gen.genKeyPair();
		this.publicKey = keyPair.getPublic();
		this.privateKey = keyPair.getPrivate();
		System.out.println("RSAEncryption::generateKeyPair >> Private key: "+new HexString(keyPair.getPrivate().getEncoded()));
		System.out.println("RSAEncryption::generateKeyPair >> Public key: "+new HexString(keyPair.getPublic().getEncoded()));
	}

}
