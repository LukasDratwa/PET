package confcost.view2;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.CryptoIteration;

public class IterationPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8861998372720649766L;

	private JTextArea data;
	
	public IterationPanel() {
		this.setLayout(new BorderLayout());
		
		data = new JTextArea();
		this.add(data, BorderLayout.CENTER);
	}
	
	public void set(final @NonNull CryptoIteration ci) {
		if (ci == null) {
			data.setText("");
		} else {
			data.setText(ci.getAlgorithm() + "|"+ci.getKeyExchange());
		}
	}
}
