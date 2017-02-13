package confcost.controller.encryption;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import org.eclipse.jdt.annotation.NonNull;

import confcost.util.HexString;

public class ECIESEncryption extends AsymmetricEncryption{

	public ECIESEncryption(@NonNull String provider) {
		super("ECIES", provider);
	}

	@Override
	public void generateKeyPair(int keyLength)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
		System.out.println("ECIESEncryption::generateKeyPair >> Generating key pair");
		ECGenParameterSpec brainpoolP256R1 = new ECGenParameterSpec(
	            "brainpoolP256R1");
		final KeyPairGenerator gen = KeyPairGenerator.getInstance(this.algorithm, this.provider);
		gen.initialize(brainpoolP256R1);
		final KeyPair keyPair = gen.genKeyPair();
		this.publicKey = keyPair.getPublic();
		this.privateKey = keyPair.getPrivate();
		System.out.println("ECIESEncryption::generateKeyPair >> Private key: "+new HexString(keyPair.getPrivate().getEncoded()));
		System.out.println("ECIESEncryption::generateKeyPair >> Public key: "+new HexString(keyPair.getPublic().getEncoded()));
	}
	
	@Override
	public void setPublicKey(byte[] bytes) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException {
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(bytes);
		this.publicKey = KeyFactory.getInstance("EC", this.provider).generatePublic(pubKeySpec);
		System.out.println("GAE::setPublicKey >> Setting PubKey" + new HexString(bytes));
	}
}
