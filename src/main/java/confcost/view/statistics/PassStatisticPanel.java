package confcost.view.statistics;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.statistics.CryptoPass;
import confcost.model.statistics.PassStatistics;

/**
 * Panel displaying statistics for a {@link CryptoPass}.
 * 
 * <hr>Created on 12.02.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public class PassStatisticPanel extends JPanel {
	private static final long serialVersionUID = 5811821084252979623L;
	
	/**
	 * Constructor
	 * @param pass	The {@link CryptoPass}
	 */
	public PassStatisticPanel(final @NonNull CryptoPass pass) {
		this.setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Detailed Statistics");
		this.add(title, BorderLayout.NORTH);
		
		// Create content panel
		JPanel content = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;

		content.add(new JLabel("Description: "), c);
		c.gridx++;
		content.add(new JLabel(pass.toString()), c);

		c.gridx = 0;
		c.gridy++;
		content.add(new JLabel("Connection: "), c);
		c.gridx++;
		content.add(new JLabel(pass.getOther().getHostName()), c);

		c.gridx = 0;
		c.gridy++;
		content.add(new JLabel("Generate key every iteration?: "), c);
		c.gridx++;
		content.add(new JLabel(""+pass.getSendMode().generateKeyEveryIteration), c);

		c.gridy++;
		c.gridx = 0;
		content.add(new JLabel("Message length (b): "), c);
		c.gridx++;
		content.add(new JLabel(""+pass.getSendMode().messageLength), c);

		c.gridy++;
		c.gridx = 0;
		content.add(new JLabel("Key length (b): "), c);
		c.gridx++;
		content.add(new JLabel(""+pass.getSendMode().keyLength), c);

		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy++;
		content.add(new JSeparator(), c);
		c.gridwidth = 1;

		if (pass.getStatistics().getInitTime() >= 0 || pass.getStatistics().getRemoteInitTime() >= 0) {
			if (pass.getStatistics().getInitTime() >= 0) {
				c.gridx = 0;
				c.gridy++;
				content.add(new JLabel(pass.getStatistics().getNames().getInitTimeName()+" (μs)"), c);
				c.gridx++;
				content.add(new JLabel(""+pass.getStatistics().getInitTime()/1000), c);
			}
			if (pass.getStatistics().getRemoteInitTime() >= 0) {
				c.gridx = 0;
				c.gridy++;
				content.add(new JLabel(pass.getStatistics().getNames().getRemoteInitTimeName()+" (μs)"), c);
				c.gridx++;
				content.add(new JLabel(""+pass.getStatistics().getRemoteInitTime()/1000), c);
			}
			c.gridx = 0;
			c.gridwidth = 2;
			c.gridy++;
			content.add(new JSeparator(), c);
		}

		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 2;
		c.weightx = 2;
		c.weighty = 2;
		JScrollPane tableContainer = new JScrollPane();
		JTable table = new JTable(new IterationStatisticTableModel(pass));
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		tableContainer.setViewportView(table);
		content.add(tableContainer, c);
		
		this.add(content, BorderLayout.CENTER);
	}
	
	/**
	 * The table model for iteration statistics.
	 *
	 */
	public class IterationStatisticTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1445146757994573243L;

	    private Object[][] data;
	    
	    public IterationStatisticTableModel(final @NonNull CryptoPass pass) {
	    	final @NonNull PassStatistics stat = pass.getStatistics();
	    	
	    	List<Object[]> dataList = new LinkedList<>();
	    	
	    	if (stat.getSumInitTime() != null) {
	    		dataList.add(StatisticsRowCreator.createPassRow(
	    				stat.getNames().getInitTimeName(), stat.getSumInitTime()));
	    	}
	    	
	    	if (stat.getSumRemoteInitTime() != null) {
	    		dataList.add(StatisticsRowCreator.createPassRow(
	    				stat.getNames().getRemoteInitTimeName(), stat.getSumRemoteInitTime()));
	    	}
	    	
	    	if (stat.getSumRunTime() != null) {
	    		dataList.add(StatisticsRowCreator.createPassRow(
	    				stat.getNames().getRunTimeName(), stat.getSumRunTime()));
	    	}
	    	
	    	if (stat.getSumRemoteRunTime() != null) {
	    		dataList.add(StatisticsRowCreator.createPassRow(
	    				stat.getNames().getRemoteRunTimeName(), stat.getSumRemoteRunTime()));
	    	}

	    	this.data = new Object[dataList.size()][];
	    	dataList.toArray(this.data);
	    }

	    public int getColumnCount() {
	        return StatisticsRowCreator.PASS_COLUMNS.length;
	    }

	    public int getRowCount() {
	        return data.length;
	    }

	    public String getColumnName(int col) {
	        return StatisticsRowCreator.PASS_COLUMNS[col];
	    }

	    public Object getValueAt(int row, int col) {
	        return data[row][col];
	    }
	}
}