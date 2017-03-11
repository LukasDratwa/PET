package confcost.view.send;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.algorithm.Algorithm;
import confcost.controller.algorithm.Encryption;
import confcost.controller.algorithm.RSAEncryption;
import confcost.controller.algorithm.Signature;
import confcost.controller.ke.KeyExchange;
import confcost.model.Model;
import confcost.model.SendMode;

/**
 * Generic panel for {@link Signature} algorithms.
 * 
 * Marc Eichler
 */
public class SignatureConfiguration extends AlgorithmConfiguration {
	private static final long serialVersionUID = 1L;

	/**
	 * The default message length
	 */
	private static final int DEFAULT_MESSAGE_LENGTH = 128;
	
	/**
	 * The encryption to configure
	 */
	protected final Class<? extends Signature> signature;
	
	/**
	 * Panel for generic configuration parameters. This will be set by createGenericContent().
	 */
	private JPanel genericInfo;

	/**
	 * Panel for encryption selection and encryption specific parameters. This will be created by createEncryptionPanel().
	 */
	private JPanel encryptionPanel;

	/**
	 * Panel for algorithm specific configuration parameters. This will be set by createSpecificContent().
	 */
	private JPanel specificInfo;
	
	/**
	 * A drop down menu of available key exchange protocols
	 */
	private JComboBox<EncryptionWrapper> encryptions;
	
	/**
	 * Message length
	 */
	private JTextField messageLength;
	
	/**
	 * Constructor
	 * 
	 * @param encryption	The encryption to be configured
	 */
	public SignatureConfiguration(final @NonNull Class<? extends Signature> signature, final @NonNull Model model) {
		super(signature);

		this.signature = signature;
		
		this.encryptions = new JComboBox<>();
		for (Class<? extends Encryption> e : Signature.getEncryptions(this.signature)) {
			encryptions.addItem(new EncryptionWrapper(e));
		}

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

		c.gridy++;
		this.encryptionPanel = createEncryptionPanel();
		if (this.genericInfo != null) {
			content.add(this.encryptionPanel, c);
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
	protected JPanel createGenericContent() {

		JPanel panel = new JPanel();
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		
		// Add message length field
		c.gridy++;
		panel.add(new JLabel("Message length"), c);
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
		DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
		decimalFormat.setGroupingUsed(false);
		messageLength = new JFormattedTextField(decimalFormat);
		messageLength.setText(""+DEFAULT_MESSAGE_LENGTH);
		c.gridx++;
		panel.add(messageLength, c);

		// Add spacer
		c.gridx = 0;
		c.gridy++;
		c.weightx = 2;
		c.gridwidth = 2;
		panel.add(new JPanel(), c);

		return panel;
	}
	
	protected JPanel createEncryptionPanel() {
		JPanel panel = new JPanel();

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		
		// Add encryption combo box
		panel.add(new JLabel("Encryption"), c);
		c.gridx++;
		panel.add(this.encryptions, c);

		// Add spacer
		c.gridx = 0;
		c.gridy++;
		c.weightx = 2;
		c.gridwidth = 2;
		panel.add(new JPanel(), c);
		
		return panel;
	}
	
	/**
	 * Used to create the specific content pane.
	 * 
	 * @return the pane or <code>null</code>
	 */
	protected JPanel createSpecificContent() {
		return null;
	}

	private final @NonNull Class<? extends Encryption> getSelectedEncryption() {
		System.out.println("DEBUG: USING RSA");
		return RSAEncryption.class;
	}
	
	private final @NonNull Class<? extends KeyExchange> getSelectedKeyExchange() {
		System.out.println("DEBUG: NO KEY EXCHANGE");
		return null;
	}
	
	private final @NonNull int getKeyLength() {
		System.out.println("DEBUG: KEY LENGTH = "+512);
		return 512;
	}
	
	private final @NonNull int getMessageLength() {
		System.out.println("DEBUG: MSG LENGTH = "+512);
		return 512;
	}

	@Override
	public SendMode getModeInfo(int iterations, boolean generateKeyEveryIteration) {
		return new SendMode(this.signature, 
				getSelectedEncryption(), getSelectedKeyExchange(), 
				getKeyLength(), getMessageLength(), 
				iterations, generateKeyEveryIteration);
	}
	
	/**
	 * Wrapper for {@link Encryption}s for use in {@link JComboBox}
	 * @author Marc Eichler
	 */
	class EncryptionWrapper {
		private final @NonNull Class<? extends Encryption> encryption;
		public EncryptionWrapper(final @NonNull Class<? extends Encryption> encryption) {
			this.encryption = encryption;
		}
		public final String toString() {
			return Algorithm.getName(this.encryption);
		}
		public final @NonNull Class<? extends Encryption> get() {
			return this.encryption;
		}
	}
}