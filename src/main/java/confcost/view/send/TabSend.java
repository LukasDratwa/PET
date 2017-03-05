package confcost.view.send;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.SendButtonListener;
import confcost.controller.SettingsListener;
import confcost.controller.encryption.Encryption;
import confcost.model.Model;

/**
 * {@link JPanel} to represent a tab for the send-configurations.
 * 
 * <hr>Created on 22.01.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public class TabSend extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * The {@link JTabbedPane} containing all algorithm configurations
	 */
	private JTabbedPane algoSelectionPane;
	
	private List<AlgorithmConfiguration> algorithmConfigurations  = new ArrayList<AlgorithmConfiguration>();

	/**
	 * The {@link GeneralSettings}
	 */
	private GeneralSettings settings;
	
	/**
	 * Constructor
	 * @param model	The main model
	 */
	public TabSend(Model model) {
		setLayout(new BorderLayout(0, 0));
		
		this.algoSelectionPane = createAlgorithmSelectionPane(model);
		this.add(this.algoSelectionPane, BorderLayout.CENTER);
		
		// Add the settings panel
		this.settings = new GeneralSettings();
		this.add(this.settings, BorderLayout.EAST);		
	}
	
	private JTabbedPane createAlgorithmSelectionPane(final Model model) {
		JTabbedPane pane = new JTabbedPane();
		
		for (Class<? extends Encryption> encryption : model.getEncryptions()) {
			AlgorithmConfiguration config = AlgorithmConfigurationFactory.create(encryption, model);
			algorithmConfigurations.add(config);
			pane.addTab(config.getHeader(), config);
		}
		if (pane.getTabCount() == 0) throw new IllegalStateException("No encryption methods could be found!");
		
		pane.setTabPlacement(JTabbedPane.LEFT);
		
		return pane;
	}
	
	/**
	 * Returns the currently selected {@link AlgorithmConfiguration}, or <code>null</code>.
	 * 
	 * @return	the config
	 */
	public AlgorithmConfiguration getCurrentConfig() {
		return (AlgorithmConfiguration) this.algoSelectionPane.getComponentAt(this.algoSelectionPane.getSelectedIndex());
	}

	/**
	 * Sets the host to the specified value.
	 * @param host	The new host
	 */
	public void setHost(final @NonNull String host) {
		settings.setHost(host);
	}

	/**
	 * Sets the port to the specified value.
	 * @param port 	The new port
	 */
	public void setPort(int port) {
		settings.setPort(port);
	}
	
	/**
	 * Sets the number of iterations.
	 * @param iterations	The iterations
	 */
	public void setIterations(int iterations) {
		settings.setIterations(iterations);
	}

	/**
	 * Sets whether or not key exchange should be performed every iteration
	 * @param b	The value
	 */
	public void setKeyExchangeEveryIteration(boolean b) {
		this.settings.setKeyExchangeEveryIteration(b);
	}
	
	/**
	 * Adds a new {@link SendButtonListener} to be notified.
	 * @param listener	The listener
	 */
	public void addSendButtonListener(final @NonNull SendButtonListener listener) {
		for (AlgorithmConfiguration c : algorithmConfigurations) {
			c.addSendButtonListener(listener);
		}
	}

	/**
	 * Adds a new {@link SettingsListener} to be notified.
	 * @param listener	The listener
	 */
	public void addSettingsListener(SettingsListener listener) {
		this.settings.addSettingsListener(listener);
	}
}