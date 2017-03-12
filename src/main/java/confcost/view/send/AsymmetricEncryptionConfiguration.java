package confcost.view.send;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.algorithm.AsymmetricEncryption;
import confcost.controller.algorithm.Encryption;
import confcost.model.SendMode;

/**
 * Implements a configuration {@link JPanel} for a generic asymmetric encryption algorithm.
 * 
 * @author Marc Eichler
 *
 */
public class AsymmetricEncryptionConfiguration extends EncryptionConfiguration {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6948378806491625533L;

	/**
	 * The default message length
	 */
	private static final int DEFAULT_MESSAGE_LENGTH = 128;
	
	/**
	 * Message length
	 */
	private JTextField messageLength;
	
	/**
	 * Key length
	 */
	private JComboBox<Integer> keyLength;
	
	/**
	 * Constructor
	 * 
	 * @param encryption	The encryption to configure
	 */
	public AsymmetricEncryptionConfiguration(final @NonNull Class<? extends AsymmetricEncryption> encryption) {
		super(encryption);
			
	}

	/**
	 * @return	the current message length in bit.
	 */
	public int getMessageLength() {
		return Integer.parseInt(messageLength.getText());
	}
	
	/**
	 * @return	the current key length in bit.
	 */
	public int getKeyLength() {
		return (Integer)keyLength.getSelectedItem();
	}

	@Override
	public SendMode getModeInfo(final int iterations, final boolean generateKeyEveryIteration) {
		return new SendMode(null,
				this.encryption, 
				null, 
				this.getKeyLength(), 
				this.getMessageLength(),
				iterations, generateKeyEveryIteration);
	}

	@Override
	protected JPanel createGenericContent() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		
		// Add key length field
		panel.add(new JLabel("Key length"), c);
		keyLength = new JComboBox<Integer>();
		keyLength.setModel(new DefaultComboBoxModel<Integer>(Encryption.getKeyLength(encryption)));
		c.gridx++;
		panel.add(keyLength, c);

		// Add message length field
		c.gridx = 0;
		c.gridy++;
		panel.add(new JLabel("Message length"), c);
		DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getIntegerInstance();
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

	@Override
	protected JPanel createSpecificContent() {
		return null;
	}

}
