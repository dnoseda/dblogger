/**
 * 
 */
package com.ivservice.common.logging;

import static com.ivservice.common.logging.IvsLoggerTestHelper.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Calendar;

import junit.framework.TestCase;
import ml.lib.VObjectCacheWrapper;
import ml.lib.factory.CommonObjectFactoryCache;
import ml.logger.MLLogFactory;
import ml.logger.MLLogger;

import org.ho.yaml.Yaml;

import com.ivservice.common.logging.dao.IvsLoggingDao;
import com.ivservice.common.logging.dto.IvsLogEvent;


/**
 * @author damian.noseda@mercadolibre.com
 *
 */
public class IvsLoggerTest extends TestCase{
	MLLogger logger = MLLogFactory.getLogger(getClass());
	public void testGetCaller() throws Exception {
		logger.info(IvsLogger.getCurrentCaller());
	}
	public void testconf() throws Exception {
		IvsLogConfig conf1 = new IvsLogConfig();
		conf1.setAttachCaller(true);
		conf1.setLevel(IvsLogLevel.DEBUG);
		conf1.setThrowableAllways(true);
		conf1.setTimeZone(Calendar.getInstance().getTimeZone().getID());
		logger.info(Yaml.dump(conf1));
		Yaml.loadType(VObjectCacheWrapper.getHtml("IVS_LOGGER_CONFIG",IvsLogger. DFLT_IVS_LOGGER_CONFIG),IvsLogConfig.class);
	}
	public void testLog() throws Exception {
		IvsLoggingDao dao = mock(IvsLoggingDao.class);
		CommonObjectFactoryCache.configurateInstance(IvsLoggingDao.class,dao);
		configurateFullNotBuffered();
		doAllLog();
		verify(dao, times(15)).insert(any(IvsLogEvent.class));
	}
	/**
	 * 
	 */

	public void testBuffered() throws Exception {
		int max = 123;
		
		configurateBuffered();
		IvsLoggingDao dao = mock(IvsLoggingDao.class);
		CommonObjectFactoryCache.configurateInstance(IvsLoggingDao.class,dao);		
		for(int i = 0; i < max; i++){
			IvsLogger.debug("mensaje %d", i);
		}
		IvsLogger.close();
		verify(dao,times(max)).insert(any(IvsLogEvent.class));
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		CommonObjectFactoryCache.clearAllConfigs();
	}
	
	public void testLevels() throws Exception {
		IvsLoggingDao dao = mock(IvsLoggingDao.class);
		CommonObjectFactoryCache.configurateInstance(IvsLoggingDao.class,dao);
		configurateFatalOnly();
		doAllLog();
		
		// solo debería correr trace
		verify(dao, times(3)).insert(any(IvsLogEvent.class));
	}

	public void testNone() throws Exception {
		IvsLoggingDao dao = mock(IvsLoggingDao.class);
		CommonObjectFactoryCache.configurateInstance(IvsLoggingDao.class,dao);
		configurateNone();
		doAllLog();
		
		// solo debería correr trace
		verify(dao, times(0)).insert(any(IvsLogEvent.class));
	}
	
	public void testOnlyStartWith() throws Exception {
		IvsLoggingDao dao = mock(IvsLoggingDao.class);
		CommonObjectFactoryCache.configurateInstance(IvsLoggingDao.class,dao);
		configurateNotThisPackage();
		doAllLog();
		
		verify(dao, times(0)).insert(any(IvsLogEvent.class));
	}
	
	
	public void testThisPackage() throws Exception {
		IvsLoggingDao dao = mock(IvsLoggingDao.class);
		CommonObjectFactoryCache.configurateInstance(IvsLoggingDao.class,dao);
		
		class OnlyLogThis{
			void log(){
				IvsLogger.trace("estoy traceando");
				IvsLogger.trace("estoy traceando con '%s'","valores");
				IvsLogger.trace(new Exception(),"estoy traceando con '%s' y exception","valores");
				IvsLogger.debug("estoy traceando");
				IvsLogger.debug("estoy traceando con '%s'","valores");
				IvsLogger.debug(new Exception(),"estoy traceando con '%s' y exception","valores");
				IvsLogger.info("estoy traceando");
				IvsLogger.info("estoy traceando con '%s'","valores");
				IvsLogger.info(new Exception(),"estoy traceando con '%s' y exception","valores");
				IvsLogger.error("estoy traceando");
				IvsLogger.error("estoy traceando con '%s'","valores");
				IvsLogger.error(new Exception(),"estoy traceando con '%s' y exception","valores");
				IvsLogger.fatal("estoy traceando");
				IvsLogger.fatal("estoy traceando con '%s'","valores");
				IvsLogger.fatal(new Exception(),"estoy traceando con '%s' y exception","valores");
			}
		};
		
		configurateOnlyPagkage(OnlyLogThis.class.getName());
		
		new OnlyLogThis().log();
		
		doAllLog();
		
		verify(dao, times(15)).insert(any(IvsLogEvent.class));
	}
	/**
	 * 
	 */
	private void doAllLog() {
		IvsLogger.trace("estoy traceando");
		IvsLogger.trace("estoy traceando con '%s'","valores");
		IvsLogger.trace(new Exception(),"estoy traceando con '%s' y exception","valores");
		IvsLogger.debug("estoy traceando");
		IvsLogger.debug("estoy traceando con '%s'","valores");
		IvsLogger.debug(new Exception(),"estoy traceando con '%s' y exception","valores");
		IvsLogger.info("estoy traceando");
		IvsLogger.info("estoy traceando con '%s'","valores");
		IvsLogger.info(new Exception(),"estoy traceando con '%s' y exception","valores");
		IvsLogger.error("estoy traceando");
		IvsLogger.error("estoy traceando con '%s'","valores");
		IvsLogger.error(new Exception(),"estoy traceando con '%s' y exception","valores");
		IvsLogger.fatal("estoy traceando");
		IvsLogger.fatal("estoy traceando con '%s'","valores");
		IvsLogger.fatal(new Exception(),"estoy traceando con '%s' y exception","valores");
	}
	
	
}
