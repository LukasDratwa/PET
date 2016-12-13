package experiments.lukas;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class CryptoJasypt extends Crypto {
	public CryptoJasypt(String pwd, String clear) {
		super(pwd, clear);
	}

	@Override
	public String encrypt() {
		long timestamp_before = System.currentTimeMillis();
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(getPwd());
		encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
		setEncrypted(encryptor.encrypt(getClear()));
		
		setEncryptionTime(System.currentTimeMillis() - timestamp_before);
		return getEncrypted();
	}

	@Override
	public String decrypt() {
		long timestamp_before = System.currentTimeMillis();
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(getPwd());
		encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
		setDecrypted(encryptor.decrypt(getEncrypted()));
		
		setDecryptionTime(System.currentTimeMillis() - timestamp_before);
		return getDecrypted();
	}
}