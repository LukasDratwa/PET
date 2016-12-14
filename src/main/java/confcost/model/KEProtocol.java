
package confcost.model;

/**
 * Enumerates key exchange protocols.
 * 
 * @author Marc Eichler
 *
 */
public enum KEProtocol {
	DiffieHellman;
	
	public String getName() {
		switch (this) {
		case DiffieHellman:	return "DH";
		default:	return null;
		}
	}
}
