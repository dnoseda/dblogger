/**
 * 
 */
package com.ivservice.common.logging.dto;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

import com.ivservice.common.dto.IvsDto;

/**
 * @author damian.noseda@mercadolibre.com
 * 
 */
public class IvsLogEvent extends IvsDto {

	private static final int ADITIONAL_DATA_LIMIT = 1000;
	private static final int STACKTRACE_MAX = 4000;
	private static final int MSG_MAX = 1000;

	private static final long serialVersionUID = 1L;
	private String logId;
	private long paymentId;
	private String server;
	private Calendar timestamp;
	private String aditionalData;
	private String caller;
	private String logLevel;
	private String msg;
	private String stacktrace;
	/**
	 * @return the logId
	 */
	public String getLogId() {
		return logId;
	}
	/**
	 * @param logId the logId to set
	 */
	public void setLogId(String logId) {
		this.logId = logId;
	}
	/**
	 * @return the paymentId
	 */
	public long getPaymentId() {
		return paymentId;
	}
	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}
	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}
	/**
	 * @param server the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}
	/**
	 * @return the timestamp
	 */
	public Calendar getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Calendar timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return the aditionalData
	 */
	public String getAditionalData() {
		return aditionalData;
	}
	/**
	 * @param aditionalData the aditionalData to set
	 */
	public void setAditionalData(String aditionalData) {
		this.aditionalData = StringUtils.substring(aditionalData,0,ADITIONAL_DATA_LIMIT);
	}
	/**
	 * @return the caller
	 */
	public String getCaller() {
		return caller;
	}
	/**
	 * @param caller the caller to set
	 */
	public void setCaller(String caller) {
		this.caller = caller;
	}
	/**
	 * @return the logLevel
	 */
	public String getLogLevel() {
		return logLevel;
	}
	/**
	 * @param logLevel the logLevel to set
	 */
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = StringUtils.substring(msg, 0,MSG_MAX);
	}
	/**
	 * @return the stacktrace
	 */
	public String getStacktrace() {
		return stacktrace;
	}
	/**
	 * @param stacktrace the stacktrace to set
	 */
	public void setStacktrace(String stacktrace) {
		this.stacktrace = StringUtils.substring(stacktrace,0,STACKTRACE_MAX);
	}
}
