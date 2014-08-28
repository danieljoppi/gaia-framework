package net.sf.gaia.testcase.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.gaia.testcase.utils.GaiaTestUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MySQLServer implements ServerFake {

	private static final Log logger = LogFactory.getLog(MySQLServer.class);

	private boolean geoDB = false;

	private Connection con = null;

	private static final String DB_URL = "jdbc:mysql://"+System.getProperty("mysql.host")+":"+System.getProperty("mysql.portNumber")+"/";

	private static final String DB_URL_GAIA = DB_URL + GaiaTestUtils.GAIA_TEST_DB;
	
	private static final String DB_USER = System.getProperty("mysql.user");
	
	private static final String DB_PASS = System.getProperty("mysql.pass");
	

	public MySQLServer() throws Exception {
		this(false);
	}
	
	protected MySQLServer(boolean geoDB) throws Exception {
		this.geoDB = geoDB;
		
		Class.forName("com.mysql.jdbc.Driver");

		System.setProperty("hibernate.connection.url", DB_URL_GAIA);
	}

	private void createDB() throws Exception {
		// deleta o banco caso já exista
		try { this.removeDB(); } catch(SQLException se) { }
		
		Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		Statement st = con.createStatement();
		st.execute("CREATE DATABASE " + GaiaTestUtils.GAIA_TEST_DB + ";");
		st = con.createStatement();
		st.execute(" GRANT ALL PRIVILEGES ON "+GaiaTestUtils.GAIA_TEST_DB+".* " + 
				" TO "+GaiaTestUtils.GAIA_TEST_DB_USER+"@'%' " + 
				" IDENTIFIED BY '"+GaiaTestUtils.GAIA_TEST_DB_PASS+"'; ");

		con.close();
	}

	public void start() throws Exception {
		long l = System.currentTimeMillis();
		
		this.createDB();
		this.con = DriverManager.getConnection(DB_URL_GAIA, GaiaTestUtils.GAIA_TEST_DB_USER, GaiaTestUtils.GAIA_TEST_DB_PASS);
		
		this.getLog().info("Started Database in "+(System.currentTimeMillis()-l)+" ms ... "+DB_URL_GAIA);
	}

	public void stop() throws Exception {
		long l = System.currentTimeMillis();
		
		if (con != null) {
			con.close();
		}
		this.removeDB();
		
		this.getLog().info("Stopped Database in "+(System.currentTimeMillis()-l)+" ms ...");
	}
	
	private void removeDB() throws Exception {
		Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

		Statement st = con.createStatement();
		st.execute("DROP DATABASE "+GaiaTestUtils.GAIA_TEST_DB+";");
		con.close();
	}
	
	public boolean isGeoDB() {
		return this.geoDB;
	}
	
	public Log getLog() {
		return logger;
	}
}
