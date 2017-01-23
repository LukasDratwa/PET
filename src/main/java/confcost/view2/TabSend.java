package confcost.view2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
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
	private List<AlgorithmConfiguration> possibleAlgorithmConfigurations;

	private void initPossibleAlgorithmConfigurations(MainFrame mainFrame) {
		possibleAlgorithmConfigurations = new ArrayList<AlgorithmConfiguration>();
		
		possibleAlgorithmConfigurations.add(new AlgorithmConfigurationRSA(mainFrame));
		possibleAlgorithmConfigurations.add(new AlgorithmConfigurationTest(mainFrame));
	}
	
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
	
	public TabSend(MainFrame mainFrame) {
		initPossibleAlgorithmConfigurations(mainFrame);
		
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
				
				SwingUtilities.updateComponentTreeUI(mainFrame);
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
		panelSendConfigurationGeneral.setLayout(new MigLayout("", "[91px,grow]", "[][30px][grow]"));
		
		JLabel lblGeneralConfiguration = new JLabel("General Configuration");
		panelSendConfigurationGeneral.add(lblGeneralConfiguration, "cell 0 0,alignx left,aligny center");
		
		JPanel panel_1 = new JPanel();
		panelSendConfigurationGeneral.add(panel_1, "cell 0 1,alignx left,aligny top");
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblIterations = new JLabel("Iterations");
		panel_1.add(lblIterations);
		
		JSpinner spinnerIterations = new JSpinner();
		panel_1.add(spinnerIterations);
	}
}