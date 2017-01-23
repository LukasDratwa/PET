package confcost.controller.ke;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyAgreement;
import javax.crypto.spec.DHParameterSpec;

import org.eclipse.jdt.annotation.NonNull;

import confcost.network.Frame;
import confcost.util.HexString;

/**
 * Implements a Diffie-Hellman key exchange protocol.
 * 
 * @author Marc Eichler
 *
 */
public class DiffieHellmanKeyExchange extends KeyExchange {
	
	private int keyLength = 0;
	
	private byte[] secretKey;

	@Override
	public void send(@NonNull Socket socket) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, IOException, InvalidKeySpecException, InvalidParameterSpecException {
		if (keyLength <= 0) throw new IllegalStateException("No key length defined!");

	    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	    
		// Send key length
		new DataOutputStream(socket.getOutputStream()).writeInt(keyLength);

		System.out.println("DiffieHellmanKeyExchange::send(Socket) >> Generating Key Pair");

		DHParameterSpec spec = this.generateSpec();
		localKeyPair = this.generateKeys(spec);

		System.out.println("DiffieHellmanKeyExchange::send(Socket) >> PrivateKey: "+new HexString(localKeyPair.getPrivate().getEncoded()));
		System.out.println("DiffieHellmanKeyExchange::send(Socket) >> Public Key: "+new HexString(localKeyPair.getPublic().getEncoded()));

		KeyAgreement keyAgree = KeyAgreement.getInstance("DH", "BC");
		keyAgree.init(localKeyPair.getPrivate());
		
		// Send public key
		new Frame(localKeyPair.getPublic().getEncoded()).write(socket);
		
		// Receive public key
		EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Frame.get(socket).data);
		remotePubKey = KeyFactory.getInstance("DH").generatePublic(pubKeySpec);
		System.out.println("DiffieHellmanKeyExchange::send(Socket) >> Remote Public Key: "+new HexString(remotePubKey.getEncoded()));
	
		keyAgree.doPhase(remotePubKey, true);
		secretKey = keyAgree.generateSecret();
	}

	@Override
	public void receive(@NonNull Socket socket) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, IOException, InvalidKeySpecException, InvalidParameterSpecException {
	    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	    
		// Receive key length
		this.keyLength = new DataInputStream(socket.getInputStream()).readInt();
		System.out.println("DiffieHellmanKeyExchange::receive(Socket) >> Key length is "+keyLength+" bit");

		System.out.println("DiffieHellmanKeyExchange::receive(Socket) >> Generating Key Pair");
		
		DHParameterSpec spec = this.generateSpec();
		KeyPair localPair = this.generateKeys(spec);
	
		System.out.println("DiffieHellmanKeyExchange::receive(Socket) >> PrivateKey: "+new HexString(localPair.getPrivate().getEncoded()));
		System.out.println("DiffieHellmanKeyExchange::receive(Socket) >> Public Key: "+new HexString(localPair.getPublic().getEncoded()));

		KeyAgreement keyAgree = KeyAgreement.getInstance("DH", "BC");
		keyAgree.init(localPair.getPrivate());
		
		// Send public key
		new Frame(localPair.getPublic().getEncoded()).write(socket);
		
		// Receive public key
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Frame.get(socket).data);
		PublicKey remotePubKey = KeyFactory.getInstance("DH").generatePublic(pubKeySpec);
		System.out.println("DiffieHellmanKeyExchange::receive(Socket) >> Remote Public Key: "+new HexString(remotePubKey.getEncoded()));
	
		keyAgree.doPhase(remotePubKey, true);
		
		keyAgree.generateSecret();
		secretKey = keyAgree.generateSecret();
	}
	
	@Override
	public void setKeyLength(int bit) {
		if (bit <= 0) throw new IllegalArgumentException("Invalid bit length: "+bit);
		this.keyLength = bit;
	}
	
	public byte[] getKey() {
		return this.secretKey;
	}
	
	private DHParameterSpec generateSpec() throws NoSuchAlgorithmException, InvalidParameterSpecException {
		AlgorithmParameterGenerator paramGen = AlgorithmParameterGenerator.getInstance("DH");
		paramGen.init(this.keyLength, new SecureRandom());
		AlgorithmParameters params = paramGen.generateParameters();
		DHParameterSpec spec = params.getParameterSpec(DHParameterSpec.class);
		
		return spec;
	}
	
	private KeyPair generateKeys(DHParameterSpec spec) throws NoSuchAlgorithmException {
        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
        keyPairGenerator.initialize(this.keyLength, new SecureRandom());

        final KeyPair keyPair = keyPairGenerator.generateKeyPair();
        
        return keyPair;
	}
}
