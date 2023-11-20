package de.fh.dortmund.model;

public class WaitTimeData {

	private boolean isWithinWaitTime;
	private long waitTime;

	public boolean isWithinWaitTime() {
		return isWithinWaitTime;
	}

	public void setWithinWaitTime(boolean isWithinWaitTime) {
		this.isWithinWaitTime = isWithinWaitTime;
	}

	public long getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}

}
