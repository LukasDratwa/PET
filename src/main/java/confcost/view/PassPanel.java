package confcost.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.CryptoIteration;
import confcost.model.CryptoPass;

/**
 * <hr>Created on 12.02.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>, Marc Eichler
 */
public class PassPanel extends JPanel {
	private static final long serialVersionUID = -795649707680699060L;
	
	private ModelList<CryptoIteration> iterations;
	private final @NonNull IterationPanel iterationPanel;
	private IterationStatisticPanel iterationStatistic;

	public PassPanel() {
		this.setLayout(new BorderLayout());
		
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		this.add(new JLabel("Iterations"), BorderLayout.NORTH);
		
		this.iterationPanel = new IterationPanel();
		this.add(iterationPanel, BorderLayout.CENTER);
		
		this.iterations = new ModelList<>();
		JList<CryptoIteration> iterationList = new JList<CryptoIteration>();
		iterationList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				iterationPanel.set(iterations.getElementAt(iterationList.getSelectedIndex()));
			}
		});
		iterationList.setModel(this.iterations);
		JScrollPane scrollPaneIterations = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneIterations.setViewportView(iterationList);
		scrollPaneIterations.setMaximumSize(new Dimension(300, 0));
		this.add(scrollPaneIterations, BorderLayout.WEST);
	}
	
	public void set(CryptoPass pass) {
		System.out.println("Displaying "+pass);
		if (pass != null) {
			this.iterations.set(pass.getIterations());
		
			if(iterationStatistic != null) {
				this.remove(iterationStatistic);
			}
			iterationStatistic = new IterationStatisticPanel(pass);
			this.add(iterationStatistic, BorderLayout.SOUTH);
			this.revalidate();
			this.repaint();
		}
	}
}