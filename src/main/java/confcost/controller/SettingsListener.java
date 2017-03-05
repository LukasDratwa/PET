package confcost.controller;

import confcost.view.send.GeneralSettings;

/**
 * The interface for listeners of settings changes.
 * 
 * @author marc
 *
 */
public interface SettingsListener {

	/**
	 * Callback for when the "Save" button is pressed
	 * 
	 * @param settings	The notifying GlobalSettings
	 */
	public void notifySaveButtonPressed(GeneralSettings settings);
}
