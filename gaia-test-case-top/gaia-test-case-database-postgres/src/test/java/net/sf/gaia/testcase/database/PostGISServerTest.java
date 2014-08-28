package net.sf.gaia.testcase.database;

import junit.framework.TestCase;
import net.sf.gaia.testcase.utils.GaiaTestUtils;

public class PostGISServerTest extends TestCase {

	private PostGISServer postgisServer;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		postgisServer = new PostGISServer();
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
		
		postgisServer.start();
		
		postgisServer.stop();
	}
}
