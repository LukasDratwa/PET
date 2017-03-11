package confcost.view.send;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import confcost.controller.algorithm.Encryption;
import confcost.controller.algorithm.SymmetricEncryption;
import confcost.controller.ke.KeyExchange;
import confcost.model.Model;
import confcost.model.SendMode;

/**
 * Implements a configuration {@link JPanel} for a generic symmetric encryption algorithm.
 * 
 * @author Marc Eichler
 *
 */
public class SymmetricEncryptionConfiguration extends EncryptionConfiguration {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6780497349986492603L;

	/**
	 * The default message length
	 */
	private static final int DEFAULT_MESSAGE_LENGTH = 128;
	
	/**
	 * A drop down menu of available key exchange protocols
	 */
	private JComboBox<KeyExchangeWrapper> keyExchanges;
	
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
	 * @param encryption	The encryption to configure
	 * @param model			The main {@link Model}
	 */
	public SymmetricEncryptionConfiguration(Class<? extends SymmetricEncryption> encryption, final Model model) {
		super(encryption);
		
		keyExchanges = new JComboBox<>();
		
		for (Class<? extends KeyExchange> ke : model.getKeyExchanges()) {
			keyExchanges.addItem(new KeyExchangeWrapper(ke));
		}
	}

	/**
	 * Returns the selected {@link KeyExchange}.
	 * @return	the key exchange protocol
	 */
	public Class<? extends KeyExchange> getSelectedKeyExchange() {
		return ((KeyExchangeWrapper)this.keyExchanges.getSelectedItem()).ke;
	}

	/**
	 * @return	the current key length in bit.
	 */
	public int getKeyLength() {
		return (Integer)keyLength.getSelectedItem();
	}

	/**
	 * @return	the current message length
	 */
	public int getMessageLength() {
		return Integer.parseInt(messageLength.getText());
	}

	@Override
	public SendMode getModeInfo(final int iterations, final boolean generateKeyEveryIteration) {
		return new SendMode(null,
				this.encryption, 
				this.getSelectedKeyExchange(), 
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
		
		// Add key exchange field
		panel.add(new JLabel("Key exchange protocol"), c);
		c.gridx++;
		this.keyExchanges =  new JComboBox<>();
		panel.add(this.keyExchanges, c);
		c.gridy++;
		c.gridx = 0;

		// Add key length field
		panel.add(new JLabel("Key length"), c);
		keyLength = new JComboBox<Integer>();
		keyLength.setModel(new DefaultComboBoxModel<Integer>(Encryption.getKeyLength(this.encryption)));
		c.gridx++;
		panel.add(keyLength, c);
		
		// Add message length field
		c.gridx = 0;
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

	@Override
	protected JPanel createSpecificContent() {
		return null;
	}
	
	/**
	 * Wraps a {@link KeyExchange} for use in a {@link JComboBox}
	 * @author Marc Eichler
	 *
	 */
	class KeyExchangeWrapper {
		public final Class<? extends KeyExchange> ke;
		private final String name;
		KeyExchangeWrapper(final Class<? extends KeyExchange> ke) {
			this.ke = ke;
			this.name = KeyExchange.getName(ke);
		}
		
		public String toString() {
			return this.name;
		}
	}
}
