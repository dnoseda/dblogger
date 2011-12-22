/**
 * 
 */
package com.ivservice.common.logging;

import ml.lib.factory.CommonObjectFactoryCache;

/**
 * @author damian.noseda@mercadolibre.com
 *
 */
public class IvsLoggerTestHelper {
	/**
	 * 
	 */
	public static void configurateFatalOnly() {
		IvsLogConfig conf = new IvsLogConfig();
		conf.setAttachCaller(true);
		conf.setBuffered(false);
		conf.setLevel(IvsLogLevel.FATAL);
		CommonObjectFactoryCache.configurateInstance(IvsLogger.class.getName()+".config", conf);
	}
	public static void configurateFullNotBuffered() {
		IvsLogConfig conf = new IvsLogConfig();
		conf.setAttachCaller(true);
		conf.setBuffered(false);
		conf.setLevel(IvsLogLevel.TRACE);
		CommonObjectFactoryCache.configurateInstance(IvsLogger.class.getName()+".config", conf);
	}
	/**
	 * 
	 */
	public static void configurateBuffered() {
		int limit = 50;
		IvsLogConfig conf = new IvsLogConfig();
		conf.setAttachCaller(true);
		conf.setBuffered(true);
		conf.setLevel(IvsLogLevel.DEBUG);
		conf.setLimit(limit);
		CommonObjectFactoryCache.configurateInstance(IvsLogger.class.getName()+".config", conf);
	}
	
	public static void configurateNone() {
		int limit = 50;
		IvsLogConfig conf = new IvsLogConfig();
		conf.setAttachCaller(true);
		conf.setBuffered(false);
		conf.setLevel(IvsLogLevel.NONE);
		conf.setLimit(limit);
		CommonObjectFactoryCache.configurateInstance(IvsLogger.class.getName()+".config", conf);
	}
	
	public static void  configurateNotThisPackage(){
		configurateOnlyPagkage("naaaaot exists");
	}
	
	public static void  configurateOnlyPagkage(String pack){
		int limit = 50;
		IvsLogConfig conf = new IvsLogConfig();
		conf.setAttachCaller(true);
		conf.setBuffered(false);
		conf.setOnlyPackage(pack);
		conf.setLevel(IvsLogLevel.TRACE);
		conf.setLimit(limit);
		CommonObjectFactoryCache.configurateInstance(IvsLogger.class.getName()+".config", conf);
	}
}
