package confcost.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.SendButtonListener;
import confcost.model.SendMode;

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
	private TabSend tabSend;
	
	private final @NonNull SendMode sendMode;
	private List<SendButtonListener> sendButtonListeners = new LinkedList<SendButtonListener>();

	public AlgorithmConfiguration(String header, TabSend tabSend, SendMode sendMode) {
		this.tabSend = tabSend;
		this.sendMode = sendMode;
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

	public void addSendButtonListener(SendButtonListener listener) {
		this.sendButtonListeners.add(listener);
	}
	
	public void notifySendButtonListeners() {
		for (SendButtonListener l : sendButtonListeners)
			l.sendButtonClicked(this);
	}

	/**
	 * @return the sendMode
	 */
	public SendMode getSendMode() {
		return sendMode;
	}

	/**
	 * @return the tabSend
	 */
	public TabSend getTabSend() {
		return tabSend;
	}

	/**
	 * @param tabSend the tabSend to set
	 */
	public void setTabSend(TabSend tabSend) {
		this.tabSend = tabSend;
	}
}