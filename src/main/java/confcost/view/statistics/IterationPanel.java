package confcost.view.statistics;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.CryptoIteration;

public class IterationPanel extends JPanel {
	private static final long serialVersionUID = 8861998372720649766L;

	private JTextArea data;
	
	public IterationPanel() {
		this.setLayout(new BorderLayout());
		
		data = new JTextArea();
		data.setEditable(false);
		this.add(data, BorderLayout.CENTER);
	}
	
	public void set(final @NonNull CryptoIteration ci) {
		System.out.println("Displaying Iteration "+ci);
		if (ci == null) {
			data.setText("");
		} else {
			String text = ci.getAlgorithm() + " | " + ci.getKeyExchange() + "\n"
				+ "    key length: " + ci.getKeyLength() + " bits\n"
				+ "    message length: " + ci.getMessageLength() + " bits\n"
				+ "    init time: " + ci.getInitTime() + " μs\n"
				+ "    remote time init: " + ci.getRemoteInitTime() + " μs\n"
				+ "    encryption time: " + ci.getEncryptionTime() + " μs\n"
				+ "    decryption time: " + ci.getDecryptionTime() + " μs";
			data.setText(text);
		}
	}
}
