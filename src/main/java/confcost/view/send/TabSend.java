package confcost.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.swing.AbstractListModel;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.SendButtonListener;
import confcost.model.SendMode;
import net.miginfocom.swing.MigLayout;

/**
 * {@link JPanel} to represent a tab for the send-configurations.
 * 
 * <hr>Created on 22.01.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public class TabSend extends JPanel {
	private static final long serialVersionUID = 1L;
	private AlgorithmConfiguration actualPanelSendConfiguration = null;
	private List<AlgorithmConfiguration> possibleAlgorithmConfigurations  = new ArrayList<AlgorithmConfiguration>();;
	private JTextField textFieldHost;
	private JTextField textFieldPort;
	private JSpinner spinnerIterations;
	
	private String[] getPossibleAlgorithmConfigurationsAsStringArray() {
		String[] result = new String[possibleAlgorithmConfigurations.size()];
		for(int i=0; i<result.length; i++) {
			result[i] = possibleAlgorithmConfigurations.get(i).getHeader();
		}
		return result;
	}
	
	private AlgorithmConfiguration getAlgorithmConfigrationWithHeader(String header) {
		for(AlgorithmConfiguration ac : possibleAlgorithmConfigurations) {
			if(ac.getHeader().equals(header)) {
				return ac;
			}
		}
		return null;
	}
	
	public TabSend(MainFrame mainFrame, Collection<SendMode> modes) {
		for(SendMode mode : modes) {
			switch(mode.messageExchange.getName()) {
				case "RSA":
					possibleAlgorithmConfigurations.add(new AlgorithmConfigurationRSA(this, mode));
					break;
				
				case "AES":
					possibleAlgorithmConfigurations.add(new AlgorithmConfigurationAES(this, mode));
					break;
					
				case "ECIES":
					possibleAlgorithmConfigurations.add(new AlgorithmConfigurationECIES(this, mode));
					break;
					
				case "RC2":
					possibleAlgorithmConfigurations.add(new AlgorithmConfigurationRC2(this, mode));
					break;
			}
		}
		
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
		DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
		decimalFormat.setGroupingUsed(false);
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel panelSendAlgorithms = new JPanel();
		add(panelSendAlgorithms, BorderLayout.WEST);
		panelSendAlgorithms.setLayout(new BorderLayout(0, 0));
		
		JLabel lblAlgorithms = new JLabel("Algorithms:");
		panelSendAlgorithms.add(lblAlgorithms, BorderLayout.NORTH);
		
		JList<String> list = new JList<String>();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AlgorithmConfiguration ac = getAlgorithmConfigrationWithHeader(list.getSelectedValue());
				
				if(ac != null) {
					if(actualPanelSendConfiguration != null) {
						remove(actualPanelSendConfiguration);
						
					}
					
					add(ac, BorderLayout.CENTER);
					actualPanelSendConfiguration = ac;
				}
				
				SwingUtilities.updateComponentTreeUI(TabSend.this);
				mainFrame.pack();
			}
		});
		list.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			
			String[] values = getPossibleAlgorithmConfigurationsAsStringArray();
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		panelSendAlgorithms.add(list, BorderLayout.CENTER);
		
		JPanel panelSendConfigurationGeneral = new JPanel();
		add(panelSendConfigurationGeneral, BorderLayout.EAST);
		panelSendConfigurationGeneral.setLayout(new MigLayout("", "[91px,grow]", "[][30px][grow][grow]"));
		
		JLabel lblGeneralConfiguration = new JLabel("General Configuration");
		panelSendConfigurationGeneral.add(lblGeneralConfiguration, "cell 0 0,alignx left,aligny center");
		
		JPanel panel_1 = new JPanel();
		panelSendConfigurationGeneral.add(panel_1, "cell 0 1,alignx left,aligny top");
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblIterations = new JLabel("Iterations");
		panel_1.add(lblIterations);
		
		SpinnerModel sm = new SpinnerNumberModel(10, 1, 999999, 1);
		spinnerIterations = new JSpinner(sm);
		panel_1.add(spinnerIterations);
		
		JPanel panel_2 = new JPanel();
		panelSendConfigurationGeneral.add(panel_2, "cell 0 2,grow");
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblHost = new JLabel("Host");
		panel_2.add(lblHost);
		
		textFieldHost = new JTextField();
		panel_2.add(textFieldHost);
		textFieldHost.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panelSendConfigurationGeneral.add(panel_3, "cell 0 3,grow");
		
		JLabel lblPort = new JLabel("Port");
		panel_3.add(lblPort);
		
		textFieldPort = new JFormattedTextField(numberFormat);
		panel_3.add(textFieldPort);
		textFieldPort.setColumns(10);
	}
	
	public void addSendButtonListener(final @NonNull SendButtonListener listener) {
		for (AlgorithmConfiguration c : possibleAlgorithmConfigurations) {
			c.addSendButtonListener(listener);
		}
	}

	/**
	 * @return the textFieldHost
	 */
	public JTextField getTextFieldHost() {
		return textFieldHost;
	}

	/**
	 * @param textFieldHost the textFieldHost to set
	 */
	public void setTextFieldHost(JTextField textFieldHost) {
		this.textFieldHost = textFieldHost;
	}

	/**
	 * @return the textFieldPort
	 */
	public JTextField getTextFieldPort() {
		return textFieldPort;
	}

	/**
	 * @param textFieldPort the textFieldPort to set
	 */
	public void setTextFieldPort(JTextField textFieldPort) {
		this.textFieldPort = textFieldPort;
	}

	/**
	 * @return the spinnerIterations
	 */
	public JSpinner getSpinnerIterations() {
		return spinnerIterations;
	}

	/**
	 * @param spinnerIterations the spinnerIterations to set
	 */
	public void setSpinnerIterations(JSpinner spinnerIterations) {
		this.spinnerIterations = spinnerIterations;
	}
}