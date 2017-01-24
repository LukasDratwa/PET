package confcost.view2;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import confcost.model.SendMode;

/**
 * Class to represent the possible configurations for the algorithm RSA.
 * 
 * <hr>Created on 22.01.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public class AlgorithmConfigurationRSA extends AlgorithmConfiguration {
	private static final long serialVersionUID = 1L;
	private JTextField textFieldWestMsglength;
	private JComboBox<Integer> comboBoxKeyLength;

	public AlgorithmConfigurationRSA(MainFrame mainFrame, TabSend tabSend, SendMode sendMode) {
		super("RSA", mainFrame, tabSend, sendMode);
		
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
		DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
		decimalFormat.setGroupingUsed(false);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panelWest = new JPanel();
		panel.add(panelWest, BorderLayout.WEST);
		panelWest.setLayout(new BorderLayout(0, 0));
		
		JPanel panelWestNorth = new JPanel();
		panelWest.add(panelWestNorth, BorderLayout.NORTH);
		
		JLabel lblWestMsglength = new JLabel("Nachrichtenlaenge");
		panelWestNorth.add(lblWestMsglength);
		
		textFieldWestMsglength = new JFormattedTextField(numberFormat);
		panelWestNorth.add(textFieldWestMsglength);
		textFieldWestMsglength.setColumns(10);
		
		JPanel panelCenter = new JPanel();
		panel.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCenterNorth = new JPanel();
		panelCenter.add(panelCenterNorth, BorderLayout.NORTH);
		
		JLabel lblCenterKeylength = new JLabel("Schluessellaenge");
		panelCenterNorth.add(lblCenterKeylength);
		
		comboBoxKeyLength = new JComboBox<Integer>();
		comboBoxKeyLength.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {128, 256, 512, 1024, 2048}));
		panelCenterNorth.add(comboBoxKeyLength);
		comboBoxKeyLength.setSelectedIndex(0);
		
		
		initSendClickedListener();
	}

	@Override
	protected void initSendClickedListener() {
		getBtnSend().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AlgorithmConfigurationRSA.this.notifySendButtonListeners();
			}
		});
	}

	/**
	 * @return the textFieldWestMsglength
	 */
	public JTextField getTextFieldWestMsglength() {
		return textFieldWestMsglength;
	}

	/**
	 * @param textFieldWestMsglength the textFieldWestMsglength to set
	 */
	public void setTextFieldWestMsglength(JTextField textFieldWestMsglength) {
		this.textFieldWestMsglength = textFieldWestMsglength;
	}

	/**
	 * @return the comboBoxKeyLength
	 */
	public JComboBox<Integer> getComboBoxKeyLength() {
		return comboBoxKeyLength;
	}

	/**
	 * @param comboBoxKeyLength the comboBoxKeyLength to set
	 */
	public void setComboBoxKeyLength(JComboBox<Integer> comboBoxKeyLength) {
		this.comboBoxKeyLength = comboBoxKeyLength;
	}
}