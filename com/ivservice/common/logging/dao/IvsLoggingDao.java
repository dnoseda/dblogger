/**
 * 
 */
package com.ivservice.common.logging.dao;

import ml.database.SQLExecute;
import ml.lib.LibSQL;
import ml.lib.VObjectCacheWrapper;

import com.ivservice.common.logging.dto.IvsLogEvent;
import com.mercadopago.framework.database.SQLFactory;

/**
 * @author damian.noseda@mercadolibre.com
 *
 */
public class IvsLoggingDao {

	private static final String DFLT_INSERT = "INSERT INTO IVS_TRACE_LOG(\r\n" + 
			"	LOG_ID, PAYMENT_ID, SERVER,\r\n" + 
			"	TIMESTAMP, ADITIONAL_DATA, CALLER,\r\n" + 
			"	LOG_LEVEL, MSG, STACKTRACE)\r\n" + 
			"VALUES(\r\n" + 
			"	?, ?, ?,\r\n" + 
			"	to_date(?, 'YYYY-MM-DD HH24:MI:SS'), ?, ?,\r\n" + 
			"	?, ?, ?\r\n" + 
			")";

	/**
	 * @param event
	 */
	public void insert(IvsLogEvent event) {
		try {
			LibSQL.execSQLInsert(new LibSQL.SQLExecuteFactory() {

				public SQLExecute newSQLExecute(String query) {
					return SQLFactory.getInstance().getSQLExecute(query);
				}
			}, VObjectCacheWrapper.getHtml("IVSL_INSERT", DFLT_INSERT), event);
		} catch (Exception e) {
			throw new RuntimeException(String.format("insert(%s)",event),e);
		}
	}

}
