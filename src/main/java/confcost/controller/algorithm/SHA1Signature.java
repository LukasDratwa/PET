package confcost.controller.algorithm;

import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.SendMode;

/**
 * SHA-1
 * @author Marc Eichler
 *
 */
public class SHA1Signature extends Signature {

	public final static @NonNull String NAME = "SHA1";
	
	/**
	 * The available {@link Encryption}s
	 */
	public final static @NonNull Collection<Class<? extends Encryption>> ENCRYPTIONS;
	static { ENCRYPTIONS = new LinkedList<Class<? extends Encryption>>(); 
		ENCRYPTIONS.add(RSAEncryption.class);
	}
	
	public SHA1Signature(@NonNull SendMode mode) throws ReflectiveOperationException {
		super(mode);
	}
	
	/**
	 * @return	the name
	 */
	public String getName() {
		return NAME + "with"+ encryption.getName();
	}

	@Override
	public @NonNull byte[] sign(@NonNull byte[] data) throws GeneralSecurityException {
		java.security.Signature sig = java.security.Signature.getInstance(getName(), this.provider);
		
		if (encryption instanceof AsymmetricEncryption) {
			sig.initSign(((AsymmetricEncryption) encryption).getPrivateKey());
		}
    	sig.update(data);
    	byte[] signature = sig.sign();
    	
		return signature;
	}

	@Override
	public boolean verify(final @NonNull byte[] data, final @NonNull byte[] signature) throws GeneralSecurityException {
		java.security.Signature sig = java.security.Signature.getInstance(getName(), this.provider);
		if (encryption instanceof AsymmetricEncryption) {
			sig.initVerify(((AsymmetricEncryption) encryption).getPublicKey());
		}
		sig.update(data);
		return sig.verify(signature);
	}

}
