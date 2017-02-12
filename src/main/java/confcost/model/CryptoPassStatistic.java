package confcost.model;

import java.util.List;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

/**
 * <hr>Created on 12.02.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public class CryptoPassStatistic {
	private SummaryStatistics sumStatisticsInitTime,
							  sumStatisticsRemoteInitTime,
							  sumStatisticsEncryption,
							  sumStatisticsDecryption,
							  sumStatisticsMsgLength,
							  sumStatisticsKeyLength;
	
	public CryptoPassStatistic(List<CryptoIteration> iterations) {
		sumStatisticsInitTime = new SummaryStatistics();
		sumStatisticsRemoteInitTime = new SummaryStatistics();
		sumStatisticsEncryption = new SummaryStatistics();
		sumStatisticsDecryption = new SummaryStatistics();
		sumStatisticsMsgLength = new SummaryStatistics();
		sumStatisticsKeyLength = new SummaryStatistics();
		
		for(CryptoIteration ci : iterations) {
			sumStatisticsInitTime.addValue(ci.getInitTime());
			sumStatisticsRemoteInitTime.addValue(ci.getRemoteInitTime());
			sumStatisticsEncryption.addValue(ci.getEncryptionTime());
			sumStatisticsDecryption.addValue(ci.getDecryptionTime());
			sumStatisticsMsgLength.addValue(ci.getMessageLength());
			sumStatisticsKeyLength.addValue(ci.getKeyLength());
		}
	}

	/**
	 * @return the sumStatisticsInitTime
	 */
	public SummaryStatistics getSumStatisticsInitTime() {
		return sumStatisticsInitTime;
	}

	/**
	 * @param sumStatisticsInitTime the sumStatisticsInitTime to set
	 */
	public void setSumStatisticsInitTime(SummaryStatistics sumStatisticsInitTime) {
		this.sumStatisticsInitTime = sumStatisticsInitTime;
	}

	/**
	 * @return the sumStatisticsRemoteInitTime
	 */
	public SummaryStatistics getSumStatisticsRemoteInitTime() {
		return sumStatisticsRemoteInitTime;
	}

	/**
	 * @param sumStatisticsRemoteInitTime the sumStatisticsRemoteInitTime to set
	 */
	public void setSumStatisticsRemoteInitTime(SummaryStatistics sumStatisticsRemoteInitTime) {
		this.sumStatisticsRemoteInitTime = sumStatisticsRemoteInitTime;
	}

	/**
	 * @return the sumStatisticsEncryption
	 */
	public SummaryStatistics getSumStatisticsEncryption() {
		return sumStatisticsEncryption;
	}

	/**
	 * @param sumStatisticsEncryption the sumStatisticsEncryption to set
	 */
	public void setSumStatisticsEncryption(SummaryStatistics sumStatisticsEncryption) {
		this.sumStatisticsEncryption = sumStatisticsEncryption;
	}

	/**
	 * @return the sumStatisticsDecryption
	 */
	public SummaryStatistics getSumStatisticsDecryption() {
		return sumStatisticsDecryption;
	}

	/**
	 * @param sumStatisticsDecryption the sumStatisticsDecryption to set
	 */
	public void setSumStatisticsDecryption(SummaryStatistics sumStatisticsDecryption) {
		this.sumStatisticsDecryption = sumStatisticsDecryption;
	}

	/**
	 * @return the sumStatisticsMsgLength
	 */
	public SummaryStatistics getSumStatisticsMsgLength() {
		return sumStatisticsMsgLength;
	}

	/**
	 * @param sumStatisticsMsgLength the sumStatisticsMsgLength to set
	 */
	public void setSumStatisticsMsgLength(SummaryStatistics sumStatisticsMsgLength) {
		this.sumStatisticsMsgLength = sumStatisticsMsgLength;
	}

	/**
	 * @return the sumStatisticsKeyLength
	 */
	public SummaryStatistics getSumStatisticsKeyLength() {
		return sumStatisticsKeyLength;
	}

	/**
	 * @param sumStatisticsKeyLength the sumStatisticsKeyLength to set
	 */
	public void setSumStatisticsKeyLength(SummaryStatistics sumStatisticsKeyLength) {
		this.sumStatisticsKeyLength = sumStatisticsKeyLength;
	}
}