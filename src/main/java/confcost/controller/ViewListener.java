package confcost.controller;

import confcost.view.SendModeView;
import confcost.view.View;
import confcost.view2.TabSend;

/**
 * Listener class to listen to listen to {@link View} events.
 * 
 * @author Marc Eichler
 *
 */
public interface ViewListener {
	public void notifyClosing(View view);

	public void notifyCryptoPassSelected(TabSend tab);

	public void notifySendButtonPressed(SendModeView sendModeView);
}
