package experiments.lukas;

import com.chilkatsoft.CkCrypt2;

public class CryptoChillkat extends Crypto {
	private String algorithm, chiperMode, encodingMode;
	private int keyLength;
	
	public CryptoChillkat(String pwd, String clear) {
		super(pwd, clear);
		
		this.setAlgorithm("aes");
		this.setChiperMode("cbc");
		this.setKeyLength(256);
		this.setEncodingMode("hex");
	}
	
	@Override
	public String encrypt() {
		long timestamp_before = System.currentTimeMillis();
		CkCrypt2 crypt = new CkCrypt2();
		
		boolean success = crypt.UnlockComponent("Anything for 30-day trial");
	    if (success != true) {
	        System.out.println(crypt.lastErrorText());
	        return null;
	    }

	    //  AES is also known as Rijndael.
	    crypt.put_CryptAlgorithm(getAlgorithm());

	    //  CipherMode may be "ecb", "cbc", "ofb", "cfb", "gcm", etc.
	    //  Note: Check the online reference documentation to see the Chilkat versions
	    //  when certain cipher modes were introduced.
	    crypt.put_CipherMode(getChiperMode());

	    //  KeyLength may be 128, 192, 256
	    crypt.put_KeyLength(getKeyLength());

	    //  The padding scheme determines the contents of the bytes
	    //  that are added to pad the result to a multiple of the
	    //  encryption algorithm's block size.  AES has a block
	    //  size of 16 bytes, so encrypted output is always
	    //  a multiple of 16.
	    crypt.put_PaddingScheme(0);

	    //  EncodingMode specifies the encoding of the output for
	    //  encryption, and the input for decryption.
	    //  It may be "hex", "url", "base64", or "quoted-printable".
	    crypt.put_EncodingMode(getEncodingMode());

	    //  An initialization vector is required if using CBC mode.
	    //  ECB mode does not use an IV.
	    //  The length of the IV is equal to the algorithm's block size.
	    //  It is NOT equal to the length of the key.
	    String ivHex = "000102030405060708090A0B0C0D0E0F";
	    crypt.SetEncodedIV(ivHex, "hex");

	    //  The secret key must equal the size of the key.  For
	    //  256-bit encryption, the binary secret key is 32 bytes.
	    //  For 128-bit encryption, the binary secret key is 16 bytes.
	    crypt.SetEncodedKey(getPwd(), getEncodingMode());

	    //  Encrypt a string...
	    //  The input string is 44 ANSI characters (i.e. 44 bytes), so
	    //  the output should be 48 bytes (a multiple of 16).
	    //  Because the output is a hex string, it should
	    //  be 96 characters long (2 chars per byte).
	    setEncrypted(crypt.encryptStringENC(getClear()));
	    
	    setEncryptionTime(System.currentTimeMillis() - timestamp_before);
	    return getEncrypted();
	}

	@Override
	public String decrypt() {
		long timestamp_before = System.currentTimeMillis();
		CkCrypt2 crypt = new CkCrypt2();
		
		boolean success = crypt.UnlockComponent("Anything for 30-day trial");
	    if (success != true) {
	        System.out.println(crypt.lastErrorText());
	        return null;
	    }
		
	    //  AES is also known as Rijndael.
	    crypt.put_CryptAlgorithm(getAlgorithm());

	    //  CipherMode may be "ecb", "cbc", "ofb", "cfb", "gcm", etc.
	    //  Note: Check the online reference documentation to see the Chilkat versions
	    //  when certain cipher modes were introduced.
	    crypt.put_CipherMode(getChiperMode());

	    //  KeyLength may be 128, 192, 256
	    crypt.put_KeyLength(getKeyLength());

	    //  The padding scheme determines the contents of the bytes
	    //  that are added to pad the result to a multiple of the
	    //  encryption algorithm's block size.  AES has a block
	    //  size of 16 bytes, so encrypted output is always
	    //  a multiple of 16.
	    crypt.put_PaddingScheme(0);

	    //  EncodingMode specifies the encoding of the output for
	    //  encryption, and the input for decryption.
	    //  It may be "hex", "url", "base64", or "quoted-printable".
	    crypt.put_EncodingMode(getEncodingMode());

	    //  An initialization vector is required if using CBC mode.
	    //  ECB mode does not use an IV.
	    //  The length of the IV is equal to the algorithm's block size.
	    //  It is NOT equal to the length of the key.
	    String ivHex = "000102030405060708090A0B0C0D0E0F";
	    crypt.SetEncodedIV(ivHex, "hex");

	    //  The secret key must equal the size of the key.  For
	    //  256-bit encryption, the binary secret key is 32 bytes.
	    //  For 128-bit encryption, the binary secret key is 16 bytes.
	    crypt.SetEncodedKey(getPwd(), getEncodingMode());
		setDecrypted(crypt.decryptStringENC(getEncrypted()));
		
		setDecryptionTime(System.currentTimeMillis() - timestamp_before);
		return getDecrypted();
	}

	/**
	 * @return the algorithm
	 */
	public String getAlgorithm() {
		return algorithm;
	}

	/**
	 * @param algorithm the algorithm to set
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * @return the chiperMode
	 */
	public String getChiperMode() {
		return chiperMode;
	}

	/**
	 * @param chiperMode the chiperMode to set
	 */
	public void setChiperMode(String chiperMode) {
		this.chiperMode = chiperMode;
	}

	/**
	 * @return the encodingMode
	 */
	public String getEncodingMode() {
		return encodingMode;
	}

	/**
	 * @param encodingMode the encodingMode to set
	 */
	public void setEncodingMode(String encodingMode) {
		this.encodingMode = encodingMode;
	}

	/**
	 * @return the keyLength
	 */
	public int getKeyLength() {
		return keyLength;
	}

	/**
	 * @param keyLength the keyLength to set
	 */
	public void setKeyLength(int keyLength) {
		this.keyLength = keyLength;
	}
}