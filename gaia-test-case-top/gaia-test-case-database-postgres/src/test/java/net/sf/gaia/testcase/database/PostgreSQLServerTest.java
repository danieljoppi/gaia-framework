package net.sf.gaia.testcase.database;

import junit.framework.TestCase;
import net.sf.gaia.testcase.utils.GaiaTestUtils;

public class PostgreSQLServerTest extends TestCase {

	private PostgreSQLServer postgreServer;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		postgreServer = new PostgreSQLServer();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testServer() throws Exception {
		if( !GaiaTestUtils.safeBoolean(System.getProperty("gaia.postgres.test")) ) {
			System.out.println("Skip test for PostgreSQL ...");
			return;
		}
		
		postgreServer.start();
		
		postgreServer.stop();
	}
}
