package confcost.controller;

import confcost.view.send.AlgorithmConfiguration;

/**
 * A listener class for the "Send" button.
 * 
 * @author Marc Eichler
 *
 */
public interface SendButtonListener {
	/**
	 * Callback for when the "Send" button was clicked.
	 * 
	 * @param ac	The AlgorithmConfiguration
	 */
	public void sendButtonClicked(AlgorithmConfiguration ac);
}
