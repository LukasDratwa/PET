package confcost.view2;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
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

	public PassPanel() {
		this.setLayout(new BorderLayout());

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
		if (pass != null)
			this.iterations.set(pass.getIterations());
		
		// Set data in data panel
	}
}
