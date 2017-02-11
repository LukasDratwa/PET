package confcost.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.CryptoIteration;
import confcost.model.CryptoPass;

public class PassPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -795649707680699060L;
	
	private ModelList<CryptoIteration> iterations;
	private final @NonNull IterationPanel iterationPanel;
	private final @NonNull JTextArea data;

	public PassPanel() {
		this.setLayout(new BorderLayout());
		
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		this.data = new JTextArea();
		this.add(data, BorderLayout.SOUTH);

		this.add(new JLabel("Iterations"), BorderLayout.NORTH);
		
		this.iterationPanel = new IterationPanel();
		this.add(iterationPanel, BorderLayout.CENTER);
		
		this.iterations = new ModelList<>();
		JList<CryptoIteration> iterationList = new JList<>();
		iterationList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				iterationPanel.set(iterations.getElementAt(iterationList.getSelectedIndex()));
			}
		});
		iterationList.setPreferredSize(new Dimension(100, 0));
		iterationList.setModel(this.iterations);
		this.add(iterationList, BorderLayout.WEST);
	}
	
	public void set(CryptoPass pass) {
		System.out.println("Displaying "+pass);
		if (pass != null) {
			this.iterations.set(pass.getIterations());
		
			this.data.setText(pass.getAlgorithm()+","+pass.getKeyExchange()+" *"+pass.getNumIterations()+"\n"+
					"Init "+pass.getMinInitTime().getInitTime()+"ms /"+pass.getAvgInitTime()+"ms /"+pass.getMaxInitTime().getInitTime() + "ms\n"+
					"Remote init "+pass.getMinRemoteInitTime().getRemoteInitTime()+"/"+pass.getAvgRemoteInitTime()+"/"+pass.getMaxRemoteInitTime().getRemoteInitTime() + "ms\n"+
					"Encryption "+pass.getMinEncryptionTime().getEncryptionTime()+"ms /"+pass.getAvgEncryptionTime()+"ms /"+pass.getMaxEncryptionTime().getEncryptionTime() + "ms\n"+
					"Decryption "+pass.getMinDecryptionTime().getDecryptionTime()+"ms /"+pass.getAvgDecryptionTime()+"ms /"+pass.getMaxDecryptionTime().getEncryptionTime() + "ms\n"
					);
		} else {
			this.data.setText("No pass selected");
		}
	}
}
