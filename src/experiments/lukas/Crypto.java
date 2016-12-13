package experiments.lukas;

public abstract class Crypto {
	private String pwd, clear, encrypted, decrypted;
	private long encryptionTime, decryptionTime;
	
	public Crypto(String pwd, String clear) {
		this.pwd = pwd;
		this.clear = clear;
		this.encrypted = null;
		this.decrypted = null;
	}
	
	public abstract String encrypt();
	public abstract String decrypt();
	
	public void encryptDecryptAndPrint(String newClearString) {
		this.setClear(newClearString);
		encryptDecryptAndPrint();
	}
	
	public void encryptDecryptAndPrint() {
		encrypt();
		decrypt();
		System.out.println(this);
	}
	
	@Override
	public String toString() {
		return "encryption: " + this.getEncryptionTime() + ", decription: " + this.getDecryptionTime()
			+ ", length clear: " + this.getClear().length();
	}
	
	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}
	
	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	/**
	 * @return the clear
	 */
	public String getClear() {
		return clear;
	}
	
	/**
	 * @param clear the clear to set
	 */
	public void setClear(String clear) {
		this.clear = clear;
	}
	
	/**
	 * @return the encrypted
	 */
	public String getEncrypted() {
		return encrypted;
	}
	
	/**
	 * @param encrypted the encrypted to set
	 */
	public void setEncrypted(String encrypted) {
		this.encrypted = encrypted;
	}
	
	/**
	 * @return the decrypted
	 */
	public String getDecrypted() {
		return decrypted;
	}
	
	/**
	 * @param decrypted the decrypted to set
	 */
	public void setDecrypted(String decrypted) {
		this.decrypted = decrypted;
	}
	
	/**
	 * @return the encryptionTime
	 */
	public long getEncryptionTime() {
		return encryptionTime;
	}
	
	/**
	 * @param encryptionTime the encryptionTime to set
	 */
	public void setEncryptionTime(long encryptionTime) {
		this.encryptionTime = encryptionTime;
	}
	
	/**
	 * @return the decryptionTime
	 */
	public long getDecryptionTime() {
		return decryptionTime;
	}
	
	/**
	 * @param decryptionTime the decryptionTime to set
	 */
	public void setDecryptionTime(long decryptionTime) {
		this.decryptionTime = decryptionTime;
	}
}