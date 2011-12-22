/**
 * 
 */
package com.ivservice.common.logging;

public enum IvsLogLevel{
	TRACE(1), DEBUG(2), INFO(3), ERROR(4), FATAL(5), NONE(6);
	private int level;
	IvsLogLevel(int level){
		this.level = level;
	}
	public boolean isEnable(IvsLogLevel current){
		return level <= current.level;
	}
}