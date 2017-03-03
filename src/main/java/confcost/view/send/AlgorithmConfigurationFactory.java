package confcost.view.send;

import confcost.controller.encryption.AsymmetricEncryption;
import confcost.controller.encryption.Encryption;
import confcost.controller.encryption.SymmetricEncryption;
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
	 * @param encryption	The encryption
	 * @param model	The model
	 * @return	The configuration panel
	 */
	@SuppressWarnings("unchecked")
	public static AlgorithmConfiguration create(Class<? extends Encryption> encryption, final Model model) {
		// Encryption is asymmetric
		if (AsymmetricEncryption.class.isAssignableFrom(encryption))
			return new AsymmetricAlgorithmConfiguration((Class<? extends AsymmetricEncryption>)encryption);
		
		// Encryption is symmetric
		else if (SymmetricEncryption.class.isAssignableFrom(encryption)) {
			return new SymmetricAlgorithmConfiguration((Class<? extends SymmetricEncryption>)encryption, model);
		} 
		
		// Unknown encryption type
		else {
			throw new IllegalStateException("Encryption is neither symmetric nor asymmetric: "+encryption+"!");
		}
	}
	
}
