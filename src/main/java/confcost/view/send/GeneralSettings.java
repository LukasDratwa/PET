package confcost.view.send;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.SettingsListener;

/**
 * The panel containing the general application settings.
 * 
 * @author Marc Eichler
 *
 */
public class GeneralSettings extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4522404087318324605L;

	/**
	 * The iteration spinner
	 */
	private JSpinner iterations;
	
	/**
	 * The key exchange optimization checkbox
	 */
	private JCheckBox keyExchangeEveryTime;
	
	/**
	 * The host {@link JTextField}
	 */
	private JTextField host;
	
	/**
	 * The save button
	 */
	private JButton saveButton;
	
	/**
	 * The port {@link JTextField}
	 */
	private JTextField port;
	
	/**
	 * The registered {@link SettingsListener}
	 */
	private List<SettingsListener> listeners;
	
	public GeneralSettings() {
		this.listeners = new LinkedList<>();
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// Add iteration spinner
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		this.add(new JLabel("Iterations: "), c);
		this.iterations = new JSpinner(new SpinnerNumberModel(100, 1, Integer.MAX_VALUE, 1));
		c.gridx++;
		this.add(iterations, c);
		c.gridx = 0;
		
		// Add key exchange checkbox
		this.keyExchangeEveryTime = new JCheckBox("Key exchange every iteration?");
		c.gridy++;
		c.gridwidth = 2;
		this.add(keyExchangeEveryTime, c);

		// Add separator
		c.gridy++;
		c.gridwidth = 2;
		this.add(new JSeparator(), c);

		// Add Host field
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy++;
		this.add(new JLabel("Host"), c);
		this.host = new JTextField();
		c.gridx++;
		this.add(host, c);
		
		// Add Port field
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy++;
		this.add(new JLabel("Port"), c);
		this.port = new JTextField();
		c.gridx++;
		this.add(port, c);
		
		// Add separator
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy++;
		this.add(new JSeparator(), c);
		
		c.gridy++;
		this.saveButton = new JButton("Save");
		this.saveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GeneralSettings.this.notifySaveButtonPressed();
			}
		});
		this.add(saveButton, c);

		// Add padding panel
		c.gridy++;
		c.gridwidth = 2;
		c.weighty = 2;
		this.add(new JPanel(), c);
		
		this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
	}

	/**
	 * @return the textFieldHost
	 */
	public @NonNull String getHost() {
		return host.getText();
	}

	/**
	 * Sets the host.
	 * @param host the host 
	 */
	public void setHost(@NonNull String host) {
		this.host.setText(host);
	}

	/**
	 * @return the textFieldPort
	 */
	public int getPort() {
		return Integer.parseInt(port.getText());
	}

	/**
	 * Sets the port.
	 * @param port the port 
	 */
	public void setPort(int port) {
		if (port < 0) throw new IllegalStateException("Port is negative: "+port+"!");
		this.port.setText(""+port);
	}

	/**
	 * Returns the set number of iterations
	 * @return the iterations
	 */
	public int getIterations() {
		return (int) iterations.getModel().getValue();
	}

	/**
	 * Sets the number of iterations.
	 * @param iterations	The iterations
	 */
	public void setIterations(int iterations) {
		this.iterations.getModel().setValue(iterations);
	}

	/**
	 * @return whether or not key exchange should be performed every iteration
	 */
	public boolean getKeyExchangeEveryIteration() {
		return keyExchangeEveryTime.isSelected();
	}

	/**
	 * Sets whether or not key exchange should be performed every iteration
	 * @param b	The value
	 */
	public void setKeyExchangeEveryIteration(boolean b) {
		this.keyExchangeEveryTime.setSelected(b);
	}
	
	/**
	 * Adds a new {@link SettingsListener} to be notified of changes.
	 * 
	 * @param listener	The listener
	 */
	public void addSettingsListener(SettingsListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Notifies all {@link SettingsListener} of the "Save" button being pressed.
	 */
	private void notifySaveButtonPressed() {
		for (SettingsListener l : listeners)
			l.notifySaveButtonPressed(this);
	}
}
