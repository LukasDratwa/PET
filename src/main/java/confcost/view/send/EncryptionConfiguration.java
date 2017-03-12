package confcost.view.send;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JSeparator;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import confcost.controller.algorithm.Encryption;

/**
 * Abstract class to represent panels for possbile algorithm configurations.
 * 
 * <hr>Created on 22.01.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public abstract class EncryptionConfiguration extends AlgorithmConfiguration {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The encryption to configure
	 */
	protected final @NonNull Class<? extends Encryption> encryption;
	
	/**
	 * Panel for generic configuration parameters. This will be set by createGenericContent().
	 */
	private JPanel genericInfo;
	
	/**
	 * Panel for algorithm specific configuration parameters. This will be set by createSpecificContent().
	 */
	private JPanel specificInfo;
	
	/**
	 * Constructor
	 * 
	 * @param encryption	The encryption to be configured
	 */
	public EncryptionConfiguration(final @NonNull Class<? extends Encryption> encryption) {
		super(encryption);
		
		this.encryption = encryption;

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
	protected abstract @Nullable JPanel createGenericContent();
	
	/**
	 * Used to create the specific content pane.
	 * 
	 * @return the pane or <code>null</code>
	 */
	protected abstract @Nullable JPanel createSpecificContent();
	
}