package confcost.controller;

import confcost.view.SendModeView;
import confcost.view.View;

/**
 * Listener class to listen to listen to {@link View} events.
 * 
 * @author Marc Eichler
 *
 */
public interface ViewListener {
	public void notifyClosing(View view);

	public void notifySendButtonPressed(SendModeView sendModeView);
}
