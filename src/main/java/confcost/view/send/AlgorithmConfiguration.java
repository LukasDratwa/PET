package confcost.view.send;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.SendButtonListener;
import confcost.controller.algorithm.Algorithm;
import confcost.model.SendMode;

/**
 * Abstract class to represent panels for possbile algorithm configurations.
 * 
 * <hr>Created on 22.01.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public abstract class AlgorithmConfiguration extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The header
	 */
	private final @NonNull String header;

	/**
	 * The registered {@link SendButtonListener}s
	 */
	private final @NonNull List<@NonNull SendButtonListener> sendButtonListeners = new LinkedList<>();
	
	/**
	 * Constructor
	 * @param algorithm	The {@link Algorithm} to be configured
	 */
	public AlgorithmConfiguration(Class<? extends Algorithm> algorithm) {	
		setLayout(new BorderLayout(0, 0));
		
		JButton sendButton = new JButton("Send");
		sendButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				notifySendButtonPressed();
			}
		});
		add(sendButton, BorderLayout.SOUTH);
		
		// Add title
		this.header = Algorithm.getName(algorithm);
		JLabel title = new JLabel(header);
		title.setFont(new Font("Tahoma", Font.PLAIN, 14));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		add(title, BorderLayout.NORTH);
	}

	/**
	 * Creates a SendMode based on the current configuration.
	 * @param iterations	The number of iterations
	 * @param generateKeyEveryIteration	Whether or not to generate a key every iteration
	 * @return	The {@link SendMode}
	 */
	public abstract SendMode getModeInfo(final int iterations, final boolean generateKeyEveryIteration);

	/**
	 * @return the header
	 */
	public final @NonNull String getHeader() {
		return header;
	}

	/**
	 * Adds a {@link SendButtonListener}
	 * @param listener	the listener
	 */
	public void addSendButtonListener(final @NonNull SendButtonListener listener) {
		this.sendButtonListeners.add(listener);
	}
	
	/**
	 * Notifies all registered {@link SendButtonListener}.
	 */
	public void notifySendButtonPressed() {
		for (SendButtonListener l : sendButtonListeners)
			l.sendButtonClicked(this);
	}
}