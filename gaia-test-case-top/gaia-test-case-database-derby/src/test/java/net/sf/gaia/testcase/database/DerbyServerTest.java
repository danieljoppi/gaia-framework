package net.sf.gaia.testcase.database;

import java.io.File;

import junit.framework.TestCase;

public class DerbyServerTest extends TestCase {

	private DerbyServer derbyServer;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		derbyServer = new DerbyServer(System.getProperty("user.dir") + File.separator + ".derby-db/");
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testStart() throws Exception {
		derbyServer.start();		
	}
	
	public void testStop() throws Exception {
		derbyServer.stop();
	}
	
}
