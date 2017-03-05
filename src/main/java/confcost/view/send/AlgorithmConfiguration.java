package confcost.view.send;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.SendButtonListener;
import confcost.controller.encryption.Encryption;
import confcost.model.SendMode;

/**
 * Abstract class to represent panels for possbile algorithm configurations.
 * 
 * <hr>Created on 22.01.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public abstract class AlgorithmConfiguration extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton sendButton;
	private String header;
	
	/**
	 * The encryption to configure
	 */
	protected final Class<? extends Encryption> encryption;
	
	/**
	 * Panel for generic configuration parameters. This will be set by createGenericContent().
	 */
	private JPanel genericInfo;
	
	/**
	 * Panel for algorithm specific configuration parameters. This will be set by createSpecificContent().
	 */
	private JPanel specificInfo;
	
	private List<SendButtonListener> sendButtonListeners = new LinkedList<SendButtonListener>();

	/**
	 * Constructor
	 * 
	 * @param encryption	The encryption to be configured
	 */
	public AlgorithmConfiguration(final @NonNull Class<? extends Encryption> encryption) {
		setLayout(new BorderLayout(0, 0));
		
		this.encryption = encryption;
		this.header = Encryption.getName(encryption);
		if (this.header == null) this.header = "<Unknown encryption>";
		
		sendButton = new JButton("Send");
		this.sendButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				notifySendButtonPressed();
			}
		});
		add(sendButton, BorderLayout.SOUTH);
		
		// Add title
		JLabel title = new JLabel(header);
		title.setFont(new Font("Tahoma", Font.PLAIN, 14));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		add(title, BorderLayout.NORTH);
		
		// Add main config panel
		JPanel content = new JPanel();
		
		content.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		
		c.gridwidth = 2;
		content.add(new JSeparator(), c);
		c.gridwidth = 1;
		c.gridy++;

		this.genericInfo = createGenericContent();
		if (this.genericInfo != null) {
			content.add(this.genericInfo, c);
			c.gridy++;
			c.gridx = 0;
			c.gridwidth = 2;
			content.add(new JSeparator(), c);
			c.gridwidth = 1;
		}
		
		c.gridx++;
		this.specificInfo = createSpecificContent();
		if (this.specificInfo != null) {
			content.add(this.specificInfo, c);
			c.gridy++;
			c.gridx = 0;
			c.gridwidth = 2;
			content.add(new JSeparator(), c);
			c.gridwidth = 1;
		}
		
		// Add spacers
		c.gridx = 0;
		c.gridy++;
		c.weightx = 2;
		c.weighty = 2;
		content.add(new JPanel(), c);
		
		this.add(content, BorderLayout.CENTER);	
	}
	
	/**
	 * Used to create the generic content pane.
	 * 
	 * @return the pane or <code>null</code>
	 */
	protected abstract JPanel createGenericContent();
	
	/**
	 * Used to create the specific content pane.
	 * 
	 * @return the pane or <code>null</code>
	 */
	protected abstract JPanel createSpecificContent();
	
	public abstract SendMode getModeInfo();
	
	protected abstract void initSendClickedListener();

	/**
	 * @return the btnSend
	 */
	public JButton getBtnSend() {
		return sendButton;
	}

	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * Adds a {@link SendButtonListener}
	 * @param listener	the listener
	 */
	public void addSendButtonListener(SendButtonListener listener) {
		this.sendButtonListeners.add(listener);
	}
	
	/**
	 * Notifies all registered {@link SendButtonListener}.
	 */
	public void notifySendButtonPressed() {
		for (SendButtonListener l : sendButtonListeners)
			l.sendButtonClicked(this);
	}
}