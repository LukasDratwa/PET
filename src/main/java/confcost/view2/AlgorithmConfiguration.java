package confcost.view2;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

/**
 * Abstract class to represent panels for possbile algorithm configurations.
 * 
 * <hr>Created on 22.01.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public abstract class AlgorithmConfiguration extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton btnSend;
	private String header;
	private JFrame mainFrame;

	public AlgorithmConfiguration(String header, MainFrame mainFrame) {
		setLayout(new BorderLayout(0, 0));
		this.header = header;
		
		btnSend = new JButton("Send");
		add(btnSend, BorderLayout.SOUTH);
		
		JLabel lblAlgorithmHeader = new JLabel(header);
		lblAlgorithmHeader.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAlgorithmHeader.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblAlgorithmHeader, BorderLayout.NORTH);
	}
	
	protected abstract void initSendClickedListener();

	/**
	 * @return the btnSend
	 */
	public JButton getBtnSend() {
		return btnSend;
	}

	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * @return the mainFrame
	 */
	public JFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * @param mainFrame the mainFrame to set
	 */
	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
}