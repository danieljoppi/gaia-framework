package net.sf.gaia.testcase.database;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MySQLSpatialServer extends MySQLServer {

	private static final Log logger = LogFactory.getLog(MySQLSpatialServer.class);

	public MySQLSpatialServer() throws Exception {
		super(true);
	}
	
	@Override
	public Log getLog() {
		return logger;
	}
}
