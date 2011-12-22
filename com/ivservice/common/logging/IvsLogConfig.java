/**
 * 
 */
package com.ivservice.common.logging;

import java.util.TimeZone;

import com.ivservice.common.dto.IvsDto;

public class IvsLogConfig extends IvsDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IvsLogLevel level = IvsLogLevel.ERROR;
	private boolean throwableAllways = false;
	private String timeZone = TimeZone.getDefault().getID();
	private boolean attachCaller = false;
	private boolean buffered = false;
	private long limit= 100;
	private String onlyPackage = "";
	/**
	 * @return the onlyPackage
	 */
	public String getOnlyPackage() {
		return onlyPackage;
	}

	/**
	 * @param onlyPackage the onlyPackage to set
	 */
	public void setOnlyPackage(String onlyPackage) {
		this.onlyPackage = onlyPackage;
	}

	/**
	 * @return the limit
	 */
	public long getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(long limit) {
		this.limit = limit;
	}

	/**
	 * @return the buffered
	 */
	public boolean isBuffered() {
		return buffered;
	}

	/**
	 * @param buffered the buffered to set
	 */
	public void setBuffered(boolean buffered) {
		this.buffered = buffered;
	}

	/**
	 * @return the timeZone
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * @param timeZone the timeZone to set
	 */
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	/**
	 * @return the throwableAllways
	 */
	public boolean isThrowableAllways() {
		return throwableAllways;
	}

	/**
	 * @param throwableAllways the throwableAllways to set
	 */
	public void setThrowableAllways(boolean throwableAllways) {
		this.throwableAllways = throwableAllways;
	}

	/**
	 * @return the attachCaller
	 */
	public boolean isAttachCaller() {
		return attachCaller;
	}

	/**
	 * @param attachCaller the attachCaller to set
	 */
	public void setAttachCaller(boolean attachCaller) {
		this.attachCaller = attachCaller;
	}

	/**
	 * @return the level
	 */
	public IvsLogLevel getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(IvsLogLevel level) {
		this.level = level;
	}
}