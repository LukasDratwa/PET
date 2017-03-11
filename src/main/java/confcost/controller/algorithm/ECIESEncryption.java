package confcost.controller.algorithm;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.SendMode;
import confcost.util.HexString;

public class ECIESEncryption extends AsymmetricEncryption{
	/**
	 * A printable, unique name of the algorithm
	 */
	public static final @NonNull String NAME = "ECIES";

	/**
	 * The supported key length
	 */
	public static final @NonNull Integer[] KEY_LENGTHS = { 128, 192, 256 };
	
	@Override
	public @NonNull String getName() {
		return NAME;
	}
	
	/**
	 * Constructs a new {@link ECIESEncryption} based on the specified {@link SendMode}.
	 * @param mode	The {@link SendMode}
	 */
	public ECIESEncryption(final @NonNull SendMode mode) {
		super(mode);
		removeCryptographyRestrictions();
	}

	@Override
	public void generateKeyPair(int keyLength)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
		System.out.println("ECIESEncryption::generateKeyPair >> Generating key pair");
		ECGenParameterSpec brainpoolP256R1 = new ECGenParameterSpec(
	            "brainpoolP256R1");
		final KeyPairGenerator gen = KeyPairGenerator.getInstance(getName(), this.provider);
		gen.initialize(brainpoolP256R1);
		final KeyPair keyPair = gen.genKeyPair();
		this.publicKey = keyPair.getPublic();
		this.privateKey = keyPair.getPrivate();
		System.out.println("ECIESEncryption::generateKeyPair >> Private key: "+new HexString(keyPair.getPrivate().getEncoded()));
		System.out.println("ECIESEncryption::generateKeyPair >> Public key: "+new HexString(keyPair.getPublic().getEncoded()));
	}
	
	@Override
	public void setPublicKey(byte[] bytes) throws IOException {
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(bytes);
		try {
			this.publicKey = KeyFactory.getInstance("EC", this.provider).generatePublic(pubKeySpec);
		} catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchProviderException e) {
			e.printStackTrace();
		}
		System.out.println("GAE::setPublicKey >> Setting PubKey" + new HexString(bytes));
	}
	
	private static void removeCryptographyRestrictions() {
        if (!("Java(TM) SE Runtime Environment".equals(System.getProperty("java.runtime.name")))) {
            return;
        }
        try {
            final Class<?> jceSecurity = Class.forName("javax.crypto.JceSecurity");
            final Class<?> cryptoPermissions = Class.forName("javax.crypto.CryptoPermissions");
            final Class<?> cryptoAllPermission = Class.forName("javax.crypto.CryptoAllPermission");

            final Field isRestrictedField = jceSecurity.getDeclaredField("isRestricted");
            isRestrictedField.setAccessible(true);
            final Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(isRestrictedField, isRestrictedField.getModifiers() & ~Modifier.FINAL);
            isRestrictedField.set(null, false);

            final Field defaultPolicyField = jceSecurity.getDeclaredField("defaultPolicy");
            defaultPolicyField.setAccessible(true);
            final PermissionCollection defaultPolicy = (PermissionCollection) defaultPolicyField.get(null);

            final Field perms = cryptoPermissions.getDeclaredField("perms");
            perms.setAccessible(true);
            ((Map<?, ?>) perms.get(defaultPolicy)).clear();
            
            final Field instance = cryptoAllPermission.getDeclaredField("INSTANCE");
            instance.setAccessible(true);
            defaultPolicy.add((Permission) instance.get(null));
        } catch (final Exception e) {
        	e.printStackTrace();
        }
    }
}
