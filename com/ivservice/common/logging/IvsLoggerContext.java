/**
 * 
 */
package com.ivservice.common.logging;

import java.util.Stack;

import org.apache.commons.lang.StringUtils;

import com.ivservice.common.dto.IvsDto;

public class IvsLoggerContext extends IvsDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String paymentId;
	private String additionalData;
	private Stack<String> aditionalDataStack = new Stack<String>();
	/**
	 * @return the additionalData
	 */
	public String getAdditionalData() {
		return additionalData;
	}
	/**
	 * @param additionalData the additionalData to set
	 */
	public IvsLoggerContext setAdditionalData(String additionalData) {
		this.additionalData = StringUtils.right(additionalData,100);
		return this;
	}
	public IvsLoggerContext pushAdditionalData(String aditionalData){
		aditionalDataStack.push(this.additionalData);
		this.additionalData =  StringUtils.right(additionalData,100);
		return this;
	}
	public IvsLoggerContext popAdditionalData(){
		if(!aditionalDataStack.isEmpty()){
			this.additionalData = aditionalDataStack.pop();
		}
		return this;
	}
	/**
	 * @param paymentId the paymentId to set
	 */
	public IvsLoggerContext setPaymentId(String paymentId) {
		this.paymentId = paymentId;
		return this;
	}
	/**
	 * @return the paymentId
	 */
	public long getPaymentId() {
		return paymentId==null ? Long.valueOf(0): Long.valueOf(paymentId);
	}
	/**
	 * @param paymentId the paymentId to set
	 */
	public IvsLoggerContext setPaymentId(long paymentId) {
		this.paymentId = String.valueOf(paymentId);
		return this;
	}		
	
}