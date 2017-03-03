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

import confcost.controller.encryption.AsymmetricEncryption;
import confcost.model.Model;
import confcost.model.SendMode;

/**
 * Implements a configuration {@link JPanel} for a generic asymmetric encryption algorithm.
 * 
 * @author Marc Eichler
 *
 */
public class AsymmetricAlgorithmConfiguration extends AlgorithmConfiguration {

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
	public AsymmetricAlgorithmConfiguration(final @NonNull Class<? extends AsymmetricEncryption> encryption) {
		super(encryption);
			
	}

	/**
	 * Returns the current message length
	 * @return
	 */
	public int getMessageLength() {
		return Integer.parseInt(messageLength.getText());
	}
	
	public int getKeyLength() {
		return (Integer)keyLength.getSelectedItem();
	}

	@Override
	public SendMode getModeInfo() {
		return new SendMode(this.encryption, 
				null, 
				this.getKeyLength(), 
				this.getMessageLength());
	}

	@Override
	protected void initSendClickedListener() {
		// TODO Auto-generated method stub

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
		keyLength.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {128, 256, 512, 1024, 2048}));
		c.gridx++;
		panel.add(keyLength, c);

		// Add message length field
		c.gridx = 0;
		c.gridy++;
		panel.add(new JLabel("Message length"), c);
		DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getIntegerInstance();
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
