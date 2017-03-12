package confcost.view.statistics;

import java.awt.BorderLayout;
import java.text.DecimalFormat;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.SendMode;
import confcost.model.statistics.CryptoIteration;
import confcost.model.statistics.StatisticsNameSet;

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
			final @NonNull SendMode mode = ci.getPass().getSendMode();
			
			final @NonNull StatisticsNameSet names = ci.getStatistics().getNames();
			StringBuilder text = new StringBuilder(mode.toString()+"\n");
			
			text.append("    Key length (b): " + mode.keyLength + " bits\n");
			text.append("    Message length (b): " + mode.messageLength + " bits\n");

	    	DecimalFormat df = new DecimalFormat("#.0"); 
	    	
			if (ci.getStatistics().getInitTime() >= 0)
				text.append("    "+names.getInitTimeName()+" (μs): " + df.format(ci.getStatistics().getInitTime()/1000) + "\n");

			if (ci.getStatistics().getRemoteInitTime() >= 0)
				text.append("    "+names.getRemoteInitTimeName()+" (μs): " + df.format(ci.getStatistics().getRemoteInitTime()/1000) + "\n");
			
			if (ci.getStatistics().getRunTime() >= 0)
				text.append("    "+names.getRunTimeName()+" (μs): " + df.format(ci.getStatistics().getRunTime()/1000) + "\n");

			if (ci.getStatistics().getRemoteRunTime() >= 0)
				text.append("    "+names.getRemoteRunTimeName()+" (μs): " + df.format(ci.getStatistics().getRemoteRunTime()/1000) + "");
			
			data.setText(text.toString());
		}
	}
}
