package confcost.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import confcost.model.SendMode;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class AlgorithmConfigurationRC2 extends AlgorithmConfiguration {
	private static final long serialVersionUID = 1629830821L;
	private JTextField textFieldWestMsglength;
	private JComboBox<Integer> comboBoxKeyLength;
	
	public AlgorithmConfigurationRC2(TabSend tabSend, SendMode sendMode) {
			super("RC2", tabSend, sendMode);
			
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
			comboBoxKeyLength.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {128, 192, 256, 512, 1024}));
			panelCenterNorth.add(comboBoxKeyLength);
			comboBoxKeyLength.setSelectedIndex(0);
			
			JButton btnTest = new JButton("Test");
			btnTest.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.out.println("test");
					
				}
				
			});
			panelCenterNorth.add(btnTest);
			
			
			initSendClickedListener();
		}

		@Override
		protected void initSendClickedListener() {
			getBtnSend().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					AlgorithmConfigurationRC2.this.notifySendButtonListeners();
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