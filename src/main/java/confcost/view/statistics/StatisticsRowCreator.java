package confcost.view.statistics;

import java.text.DecimalFormat;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.eclipse.jdt.annotation.NonNull;

import confcost.model.statistics.CryptoPass;

/**
 * Abstract helper class for constructing statistical rows
 * @author Marc Eichler
 *
 */
public abstract class StatisticsRowCreator {
	/**
	 * Columns names for {@link CryptoPass} data rows
	 */
	public static final @NonNull String[] PASS_COLUMNS = {"Metric", "Min", "Mean", "Max", "Standard Deviation"};

	/**
	 * Creates a row of data for a passes' attribute (like init time).
	 * 
	 * The data is assumed to be in ns, and will be printed in μs.
	 *  
	 * @param name	The row name
	 * @param stat	The {@link SummaryStatistics}
	 * @return	The row
	 */
	public static @NonNull Object[] createPassRow(final @NonNull String name, final @NonNull SummaryStatistics stat) {
    	DecimalFormat df = new DecimalFormat("#.0"); 
    	
    	Object[] row = new Object[PASS_COLUMNS.length];
    	row[0] = name;
    	if (stat == null) {
    		for(int i = 1; i < row.length; i++) row[i] = "N/A";
    	} else {
	    	row[1] = df.format(stat.getMin()/1000);
	    	row[2] = df.format(stat.getMean()/1000);
			row[3] = df.format(stat.getMax()/1000);
			row[4] = df.format(stat.getStandardDeviation()/1000);
    	}
    	return row;
	}
	

	/**
	 * Creates a row of data for a passes' attribute (like init time), utilizing a single value. This is
	 * useful for displaying, for example, an average.
	 * 
	 * The value is assumed to be in ns, and will be printed in μs.
	 *  
	 * @param name	The row name
	 * @param value	The value
	 * @return	The row
	 */
	public static @NonNull Object[] createPassRow(final @NonNull String name, final @NonNull long value) {
    	DecimalFormat df = new DecimalFormat("#.0"); 
    	
    	Object[] row = new Object[PASS_COLUMNS.length];
    	row[0] = name;
		row[1] = "";
    	row[2] = df.format(value/1000);
		row[3] = "";
		row[4] = "";
    	return row;
	}

	/**
	 * Creates a row of data for a passes' attribute (like init time), utilizing a single value. This is
	 * useful for displaying, for example, an average.
	 *  
	 * @param name	The row name
	 * @param value	The value
	 * @return	The row
	 */
	public static @NonNull Object[] createPassRow(final @NonNull String name, final @NonNull String value) {    	
    	Object[] row = new Object[PASS_COLUMNS.length];
    	row[0] = name;
		row[1] = "";
    	row[2] = value;
		row[3] = "";
		row[4] = "";
    	return row;
	}
	
	
}
