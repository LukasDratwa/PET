package confcost.view.status;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.MatteBorder;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.ConnectionListener;
import confcost.model.Connection;
import confcost.model.Connection.Status;

public class ConnectionPanel extends JPanel implements ConnectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3029747330127915913L;

	/**
	 * The {@link Connection} to display
	 */
	final @NonNull Connection connection;
	
	/**
	 * The status label
	 */
	@NonNull JLabel status;
	
	@NonNull JLabel progress;
	
	@NonNull JProgressBar progressBar;
	
	@NonNull JLabel error;
	
	public ConnectionPanel(final @NonNull Connection connection) {
		this.setLayout(new GridBagLayout());

		this.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
		
		this.connection = connection;
		connection.addConnectionListener(this);
		
		this.initialize();
	}
	
	/**
	 * Initializes the display
	 */
	public void initialize() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 0;
		
		// Add type, name
		c.gridwidth = 2;
		this.add(new JLabel(connection.getType()+" - "+connection.getHost()), c);
		
		// Add encryption, key exchange
		c.gridy++;
		c.gridx = 0;
		this.add(new JLabel(this.connection.getMode().toString()), c);

		// Add status label
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy++;
		c.ipadx = 5;
		this.status = new JLabel();
		this.add(status, c);
		c.ipadx = 0;
		
		// Add progress and progress bar
		c.gridx++;
		c.anchor = GridBagConstraints.LINE_END;
		this.progress = new JLabel();
		this.add(progress, c);

		c.anchor = GridBagConstraints.CENTER;
		c.gridy++;
		c.gridx = 0;
		c.gridwidth = 2;
		this.progressBar = new JProgressBar(0, connection.getMode().iterations);
		this.add(this.progressBar, c);
		
		// Add error label
		c.gridy++;
		c.anchor = GridBagConstraints.WEST;
		this.error = new JLabel();
		this.add(this.error, c);
		
		// Add padding
		c.weightx = 2;
		c.gridy++;
		c.gridwidth = 3;
		this.add(new JPanel(), c);
		
		// Fill in dynamic values
		this.update();
	}
	
	/**
	 * Updates the display
	 */
	public void update() {
		this.status.setText(""+connection.getStatus());
		this.progress.setText(connection.getCurrentIteration()+"/"+connection.getMode().iterations);

		this.progressBar.setValue(connection.getCurrentIteration());
		this.progressBar.setStringPainted(true);
		
		if (connection.getStatus() == Status.ERROR) error.setText(connection.getError().toString());
	}

	@Override
	public void notifyConnectionChanged(Connection connection) {
		this.update();
	}
}
