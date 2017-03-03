package confcost.controller;

import confcost.view.send.TabSend;

/**
 * Listener class to listen to listen to {@link View} events.
 * 
 * @author Marc Eichler
 *
 */
public interface ViewListener {
	public void notifyCryptoPassSelected(TabSend tab);
}
