package net.sf.gaia.testcase.database;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PostGISServer extends PostgreSQLServer {

	private static final Log logger = LogFactory.getLog(PostGISServer.class);

	public PostGISServer() throws Exception {
		super(true);
	}
	
	@Override
	public Log getLog() {
		return logger;
	}
}
