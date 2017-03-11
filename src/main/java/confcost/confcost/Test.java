package confcost.confcost;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.spec.RSAKeyGenParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class Test {
	static final String DIGEST_SHA1 = "SHA256withRSA";
    static final String BC_PROVIDER = "BC";

    public static void main(String[] args) throws Exception {
    	Security.addProvider(new BouncyCastleProvider());
    	
    	String data = "ASDF";
    	
    	Signature sig = Signature.getInstance(DIGEST_SHA1, BC_PROVIDER);

		int keyLength = 512;
		KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA", "BC");
		gen.initialize(new RSAKeyGenParameterSpec(keyLength, new BigInteger("3")));
		final KeyPair keyPair = gen.genKeyPair();
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
    	
    	sig.initSign(privateKey);
    	sig.update(data.getBytes());
    	byte[] signature = sig.sign();

    	sig.initVerify(publicKey);
    	sig.update(data.getBytes());
    	System.out.println("Verify: "+sig.verify(signature));
    }

}
