package confcost.view.send;

import confcost.controller.algorithm.Algorithm;
import confcost.controller.algorithm.AsymmetricEncryption;
import confcost.controller.algorithm.Encryption;
import confcost.controller.algorithm.Signature;
import confcost.controller.algorithm.SymmetricEncryption;
import confcost.model.Model;

/**
 * Factory class for creating {@link AlgorithmConfiguration}s
 * 
 * @author Marc Eichler
 *
 */
public class AlgorithmConfigurationFactory {

	/**
	 * Factory method for creating a config panel for the specified asymmetric encryption.
	 * 
	 * If no suitable configuration panel was found, a generic configuration panel is returned.
	 * 
	 * @param algorithm	The encryption
	 * @param model	The model
	 * @return	The configuration panel
	 */
	@SuppressWarnings("unchecked")
	public static AlgorithmConfiguration create(Class<? extends Algorithm> algorithm, final Model model) {
		System.out.println("Creating panel for "+algorithm);
		if (Encryption.class.isAssignableFrom(algorithm)) {
			// Encryption is asymmetric
			if (AsymmetricEncryption.class.isAssignableFrom(algorithm))
				return new AsymmetricEncryptionConfiguration((Class<? extends AsymmetricEncryption>)algorithm);
			// Encryption is symmetric
			else if (SymmetricEncryption.class.isAssignableFrom(algorithm)) {
				return new SymmetricEncryptionConfiguration((Class<? extends SymmetricEncryption>)algorithm, model);
			} 
			// Unknown encryption type
			else {
				throw new IllegalArgumentException("Encryption is neither symmetric nor asymmetric: "+algorithm+"!");
			}
		} else if (Signature.class.isAssignableFrom(algorithm)) {
			return new SignatureConfiguration((Class<? extends Signature>)algorithm, model);
		} else {
			throw new IllegalArgumentException("Algorithm is neither Signature nor Encryption!");
		}
	}	
}
