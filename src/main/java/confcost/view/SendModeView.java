package confcost.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.SendMode;

public class SendModeView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 820579156551064066L;

	private static final int WIDTH = 20;
	private static final int HEIGHT = 20;
	
	/**
	 * The {@link SendPanel}
	 */
	private final @NonNull SendPanel sendPanel;
	
	/**
	 * The SendMode
	 */
	private final @NonNull SendMode sendMode;
	
	private final @NonNull JButton sendButton;

	public SendModeView(SendPanel sendPanel, SendMode sendMode) {
		this.sendPanel = sendPanel;
		this.sendMode = sendMode;
		
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		this.setLayout(new BorderLayout());
		
		this.add(new Label(sendMode.keyExchange + ", "+ sendMode.messageExchange), BorderLayout.CENTER);
		
		this.sendButton = new JButton(">>");
		
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SendModeView.this.sendButtonPressed();
			}
		});
		
		this.add(sendButton, BorderLayout.EAST);
	}
	
	private void sendButtonPressed() {
		this.sendPanel.notifySendButtonPressed(this);
	}
	
	/**
	 * @return the {@link SendMode}
	 */
	public SendMode getSendMode() {
		return this.sendMode;
	}
}
