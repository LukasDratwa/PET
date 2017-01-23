package confcost.view2;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Class to represent the possible configurations for the algorithm RSA.
 * 
 * <hr>Created on 22.01.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public class AlgorithmConfigurationRSA extends AlgorithmConfiguration {
	private static final long serialVersionUID = 1L;
	private JTextField textFieldWestMsglength;
	private JTextField textFieldCenterKeylength;

	public AlgorithmConfigurationRSA(MainFrame mainFrame) {
		super("RSA", mainFrame);
		
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
		
		textFieldWestMsglength = new JTextField();
		panelWestNorth.add(textFieldWestMsglength);
		textFieldWestMsglength.setColumns(10);
		
		JPanel panelCenter = new JPanel();
		panel.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCenterNorth = new JPanel();
		panelCenter.add(panelCenterNorth, BorderLayout.NORTH);
		
		JLabel lblCenterKeylength = new JLabel("Schluessellaenge");
		panelCenterNorth.add(lblCenterKeylength);
		
		textFieldCenterKeylength = new JTextField();
		panelCenterNorth.add(textFieldCenterKeylength);
		textFieldCenterKeylength.setColumns(10);
		initSendClickedListener();
	}

	@Override
	protected void initSendClickedListener() {
		getBtnSend().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Send RSA");
			}
		});
	}
}