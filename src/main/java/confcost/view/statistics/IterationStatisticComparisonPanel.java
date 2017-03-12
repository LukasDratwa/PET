package confcost.view.statistics;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.statistics.CryptoPass;

/**
 * <hr>Created on 19.02.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public class IterationStatisticComparisonPanel extends JPanel {
	private static final long serialVersionUID = -6436736247904526338L;

	public IterationStatisticComparisonPanel(List<CryptoPass> passes) {
		this.setLayout(new BorderLayout());
		
		JLabel label = new JLabel("Aggregated Statistics ("+passes.size()+")");
		this.add(label, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		JPanel container = new JPanel(new GridLayout(6, 1)); // x rows and 1 column

		container.add(createInitTime(passes));
		container.add(createRemoteInitTime(passes));
		container.add(createRunTime(passes));
		container.add(createRemoteRunTime(passes));
		
		scrollPane.setViewportView(container);
		this.add(scrollPane, BorderLayout.CENTER);
	}
	
	class IterationStatisticTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1445146757994573243L;

		private String[] columnNames = {"Metric", "Min", "Mean", "Max", "Standard Deviation"};
	    private Object[][] data;
	    
	    public IterationStatisticTableModel(Object[][] values) {
	    	data = values;
	    }

	    public int getColumnCount() {
	        return columnNames.length;
	    }

	    public int getRowCount() {
	        return data.length;
	    }

	    public String getColumnName(int col) {
	        return columnNames[col];
	    }

	    public Object getValueAt(int row, int col) {
	        return data[row][col];
	    }
	}
	
	/**
	 * 
	 * @param passes
	 * @return
	 */
	private final JPanel createInitTime(final @NonNull List<CryptoPass> passes) {
		String[] names = new String[passes.size()];
		Object[][] rows = new Object[passes.size()][];
		int i = 0;
		for (CryptoPass pass : passes) {
			if (pass.getStatistics().getSumInitTime() != null)
				rows[i] = StatisticsRowCreator.createPassRow(pass.toString(), pass.getStatistics().getSumInitTime());
			else if (pass.getStatistics().getInitTime() >= 0)
				rows[i] = StatisticsRowCreator.createPassRow(pass.toString(), pass.getStatistics().getInitTime());
			else
				rows[i] = StatisticsRowCreator.createPassRow(pass.toString(), "n/a");
			names[i] = pass.getStatistics().getNames().getInitTimeName();
			i++;
		}

		return createTable(aggregateNames(names), rows);
	}
	
	/**
	 * 
	 * @param passes
	 * @return
	 */
	private final JPanel createRemoteInitTime(final @NonNull List<CryptoPass> passes) {
		String[] names = new String[passes.size()];
		Object[][] rows = new Object[passes.size()][];
		int i = 0;
		for (CryptoPass pass : passes) {
			if (pass.getStatistics().getSumRemoteInitTime() != null)
				rows[i] = StatisticsRowCreator.createPassRow(pass.toString(), pass.getStatistics().getSumRemoteInitTime());
			else if (pass.getStatistics().getRemoteInitTime() >= 0)
				rows[i] = StatisticsRowCreator.createPassRow(pass.toString(), pass.getStatistics().getRemoteInitTime());
			else
				rows[i] = StatisticsRowCreator.createPassRow(pass.toString(), "n/a");
			names[i] = pass.getStatistics().getNames().getRemoteInitTimeName();
			i++;
		}
		
		return createTable(aggregateNames(names), rows);
	}
	
	private final JPanel createRunTime(final @NonNull List<CryptoPass> passes) {
		String[] names = new String[passes.size()];
		Object[][] rows = new Object[passes.size()][];
		int i = 0;
		for (CryptoPass pass : passes) {
			rows[i] = StatisticsRowCreator.createPassRow(pass.toString(), pass.getStatistics().getSumRunTime());
			names[i] = pass.getStatistics().getNames().getRunTimeName();
			i++;
		}

		return createTable(aggregateNames(names), rows);
	}
	
	private final JPanel createRemoteRunTime(final @NonNull List<CryptoPass> passes) {
		final @NonNull String[] names = new String[passes.size()];
		final @NonNull Object[][] rows = new Object[passes.size()][];
		int i = 0;
		for (CryptoPass pass : passes) {
			rows[i] = StatisticsRowCreator.createPassRow(pass.toString(), pass.getStatistics().getSumRemoteRunTime());
			names[i] = pass.getStatistics().getNames().getRemoteRunTimeName();
			i++;
		}

		return createTable(aggregateNames(names), rows);
	}
	
	private final @NonNull JPanel createTable(final @NonNull String name, final @NonNull Object[][] rows) {
		final @NonNull JPanel content = new JPanel(new BorderLayout());
		final @NonNull JLabel header = new JLabel(name);
		content.add(header, BorderLayout.NORTH);

		final @NonNull JTable table = new JTable(new IterationStatisticTableModel(rows));
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		content.add(new JScrollPane(table), BorderLayout.CENTER);
		
		return content;
	}
	
	/**
	 * Aggregates the specified strings. Returns a string containing each string in names exactly once.
	 * @param names	The names
	 * @return	The aggregation
	 */
	public final String aggregateNames(final String[] names) {
		List<String> added = new LinkedList<String>();
		
		StringBuilder ret = new StringBuilder();
		
		for (String s : names) {
			if (!added.contains(s)) {
				if (ret.length() != 0) ret.append(" | ");
				ret.append(s);
				added.add(s);
			}
		}
		
		return ret.toString();
	}
}