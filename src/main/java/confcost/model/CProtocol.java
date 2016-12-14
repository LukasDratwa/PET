package confcost.model;

/**
 * Enumerates cryptographic protocols.
 * 
 * @author Marc Eichler
 *
 */
public enum CProtocol {
	RSA;
	
	public String getName() {
		switch (this) {
		case RSA:	return "RSA";
		default:	return null;
		}
	}
}
