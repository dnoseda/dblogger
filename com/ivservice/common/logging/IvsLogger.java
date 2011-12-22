/**
 * 
 */
package com.ivservice.common.logging;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TimeZone;

import ml.lib.VObjectCacheWrapper;
import ml.lib.factory.CommonObjectFactoryCache;
import ml.logger.MLLogFactory;
import ml.logger.MLLogger;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.ho.yaml.Yaml;

import com.google.common.base.Supplier;
import com.ivservice.common.logging.dao.IvsLoggingDao;
import com.ivservice.common.logging.dto.IvsLogEvent;

/**
 * @author damian.noseda@mercadolibre.com
 *
 */
public class IvsLogger {
	public static final String DFLT_IVS_LOGGER_CONFIG = "level: !com.ivservice.common.logging.IvsLogLevel TRACE\r\n" + 
			"throwableAllways: true\r\n" + 
			"attachCaller: true\r\n" + 
			"timeZone: \"GMT-03:00\"\r\n" + 
			"limit: 50\r\n" + 
			"buffered: false";
	static MLLogger logger = MLLogFactory.getLogger(IvsLogger.class);
	static Queue<IvsLogEvent> queue= new LinkedList<IvsLogEvent>();
	static ThreadLocal<IvsLoggerContext> context = new ThreadLocal<IvsLoggerContext>();
	static String ip;
	static String server;
	static{
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
			server = InetAddress.getLocalHost().getHostName();
		} catch (Exception e) {
			logger.error("inicializando",e);
		}
	}
	public static IvsLoggerContext getContext(){
		if(context.get()==null){
			IvsLoggerContext value = new IvsLoggerContext();
			context.set(value);
		}
		return context.get();
	}
	static void log(IvsLogEvent event){
		if(event!=null &&
				event.getLogLevel()!=null &&
				isLevelActive(IvsLogLevel.valueOf(event.getLogLevel()))){
			
			if(getConfig().isBuffered()){
				queue.add(event);
				if(queue.size()>= getConfig().getLimit()){
					emptyQueue();
				}
			}else{
				insert(event);
			}
			
			
		}
	}
	/**
	 * @param event
	 */
	private static void insert(IvsLogEvent event) {
		IvsLoggingDao dao = CommonObjectFactoryCache.getObject(IvsLoggingDao.class);
		try {
			dao.insert(event);
		} catch (Exception e) {
			logger.error(String.format("log(%s)", event),e);
			throw new RuntimeException(String.format("log(%s)", event),e);
		}
	}
	/**
	 * @param msg
	 */
	public static void trace(String msg) {
		trace(msg, ArrayUtils.EMPTY_OBJECT_ARRAY);
	}
	/**
	 * @param template
	 * @param values
	 */
	public static void trace(String template, Object... values) {
		trace(null,template,values);
	}
	/**
	 * @param throwable
	 * @param template
	 * @param values
	 */
	public static void trace(Throwable throwable, String template, Object... values) {
		log(generateLogEvent(IvsLogLevel.TRACE, throwable, template, values));
	}
	/**
	 * @param msg
	 */
	public static void info(String msg) {
		info(msg, ArrayUtils.EMPTY_OBJECT_ARRAY);
	}
	/**
	 * @param template
	 * @param values
	 */
	public static void info(String template, Object... values) {
		info(null,template,values);
	}
	/**
	 * @param throwable
	 * @param template
	 * @param values
	 */
	public static void info(Throwable throwable, String template, Object... values) {
		log(generateLogEvent(IvsLogLevel.INFO, throwable, template, values));
	}
	/**
	 * @param msg
	 */
	public static void debug(String msg) {
		debug(msg, ArrayUtils.EMPTY_OBJECT_ARRAY);
	}
	/**
	 * @param template
	 * @param values
	 */
	public static void debug(String template, Object... values) {
		debug(null,template,values);
	}
	/**
	 * @param throwable
	 * @param template
	 * @param values
	 */
	public static void debug(Throwable throwable, String template, Object... values) {
		log(generateLogEvent(IvsLogLevel.DEBUG, throwable, template, values));
	}
	/**
	 * @param msg
	 */
	public static void error(String msg) {
		error(msg, ArrayUtils.EMPTY_OBJECT_ARRAY);
	}
	/**
	 * @param template
	 * @param values
	 */
	public static void error(String template, Object... values) {
		error(null,template,values);
	}
	/**
	 * @param throwable
	 * @param template
	 * @param values
	 */
	public static void error(Throwable throwable, String template, Object... values) {
		log(generateLogEvent(IvsLogLevel.ERROR, throwable, template, values));
	}
	/**
	 * @param msg
	 */
	public static void fatal(String msg) {
		fatal(msg, ArrayUtils.EMPTY_OBJECT_ARRAY);
	}
	/**
	 * @param template
	 * @param values
	 */
	public static void fatal(String template, Object... values) {
		fatal(null,template,values);
	}
	/**
	 * @param throwable
	 * @param template
	 * @param values
	 */
	public static void fatal(Throwable throwable, String template, Object... values) {
		log(generateLogEvent(IvsLogLevel.FATAL, throwable, template, values));
	}
	/**
	 * @param ivsLogLevel 
	 * @param throwable
	 * @param template
	 * @param values
	 * @return
	 */
	private static IvsLogEvent generateLogEvent(IvsLogLevel ivsLogLevel, Throwable throwable,
			String template, Object... values) {
		
		if(isLevelActive(ivsLogLevel)){
			IvsLogEvent event = new IvsLogEvent();
			event.setServer(server);
			event.setPaymentId(getContext().getPaymentId());
			event.setLogLevel(ivsLogLevel.toString());
			event.setAditionalData(StringUtils.defaultString(event.getAditionalData(), getContext().getAdditionalData()));
			boolean keepOnGoing = true;
			if (getConfig().isAttachCaller()) {
				event.setCaller(getCurrentCaller());
				keepOnGoing  = event.getCaller().startsWith(getConfig().getOnlyPackage());
			}
			if(keepOnGoing){
				event.setMsg(format(template, values));
				if (throwable == null && getConfig().isThrowableAllways()) {
					throwable = new Exception();
				}
				
				event.setStacktrace(throwable == null ? "null" : ExceptionUtils
						.getFullStackTrace(throwable));			
				event.setLogId(generateId());
				event.setTimestamp(getNow());
				return event;
			}
		}
		return null;
	}
	/**
	 * @param template
	 * @param values
	 * @return
	 */
	private static String format(String template, Object[] values) {
		try {
			return String.format(template, values);
		} catch (Exception e) {
			String ret = template+","+Arrays.toString(values);
			logger.error("format("+ret+")",e);
			return ret;
		}
	}
	/**
	 * @param ivsLogLevel
	 * @return
	 */
	private static boolean isLevelActive(IvsLogLevel ivsLogLevel) {
		return getConfig().getLevel().isEnable(ivsLogLevel);
	}
	/**
	 * @return
	 */
	private static Calendar getNow() {
		return Calendar.getInstance(TimeZone.getTimeZone(getConfig().getTimeZone()));
	}
	/**
	 * @return
	 */
	private static String generateId() {
		return String.format("%d.%s.%d", System.nanoTime(),server,Thread.currentThread().getId());
	}
	/**
	 * @return
	 */
	public static String getCurrentCaller() {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		String currentCaller = "";
		for (int i = 0; i< stack.length;i++) {
			StackTraceElement element = stack[i];
			String className =element.getClassName(); 
			if(!className.equals("com.ivservice.common.logging.IvsLogger")
					&&
					!className.equals("java.lang.Thread")){
				currentCaller = String.format("%s.%s",className,element.getMethodName());
				break;
			}
		}
		return currentCaller;
	}
	private static IvsLogConfig getConfig() {
		return CommonObjectFactoryCache.getObject(IvsLogger.class.getName()+".config", new Supplier<IvsLogConfig>(){
			public IvsLogConfig get() {
				return Yaml.loadType(VObjectCacheWrapper.getHtml("IVS_LOGGER_CONFIG", DFLT_IVS_LOGGER_CONFIG), IvsLogConfig.class);
			}
		});
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}
	/**
	 * 
	 */
	public static  void close() {
		emptyQueue();
	}
	private static void emptyQueue(){
		while(!queue.isEmpty()){
			insert(queue.poll());
		}
	}
}
