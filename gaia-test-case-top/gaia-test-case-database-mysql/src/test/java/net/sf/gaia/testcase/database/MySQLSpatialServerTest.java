package net.sf.gaia.testcase.database;

import junit.framework.TestCase;
import net.sf.gaia.testcase.utils.GaiaTestUtils;

public class MySQLSpatialServerTest extends TestCase {

	private MySQLSpatialServer mySQLServer;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		mySQLServer = new MySQLSpatialServer();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testServer() throws Exception {
		if( !GaiaTestUtils.safeBoolean(System.getProperty("gaia.mysql.test")) ) {
			System.out.println("Skip test for MySQL ...");
			return;
		}
		
		mySQLServer.start();

		mySQLServer.stop();
	}
}
