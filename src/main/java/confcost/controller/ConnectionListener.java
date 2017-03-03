package confcost.controller;

import confcost.model.Connection;

/**
 * An interface of a listener for connection changes.
 * 
 * @author Marc Eichler
 *
 */
public interface ConnectionListener {
	/**
	 * Callback method for when a {@link Connection} has changed
	 * @param connection
	 */
	public void notifyConnectionChanged(Connection connection);
}
