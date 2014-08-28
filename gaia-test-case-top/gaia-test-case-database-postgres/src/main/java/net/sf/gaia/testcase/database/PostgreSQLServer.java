package net.sf.gaia.testcase.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.gaia.testcase.utils.GaiaTestUtils;

public class PostgreSQLServer implements ServerFake {

	private static final Log logger = LogFactory.getLog(PostgreSQLServer.class);

	private boolean geoDB = false;

	private Connection con = null;

	private static final String DB_URL = "jdbc:postgresql://"+System.getProperty("postgres.host")+":"+System.getProperty("postgres.portNumber")+"/";

	private static final String DB_URL_GAIA = DB_URL + GaiaTestUtils.GAIA_TEST_DB;
	
	private static final String DB_USER = System.getProperty("postgres.user");
	
	private static final String DB_PASS = System.getProperty("postgres.pass");
	
	public PostgreSQLServer() throws Exception {
		this(false);
	}
	
	protected PostgreSQLServer(boolean geoDB) throws Exception {
		this.geoDB = geoDB;
		
		Class.forName("org.postgresql.Driver");
		
		System.setProperty("hibernate.connection.url", DB_URL_GAIA);
	}

	private void createDB() throws Exception {
		// deleta o banco caso já exista
		try { this.removeDB(); } catch(SQLException se) { }
		
		Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		
		Statement st = con.createStatement();
		st.execute("CREATE ROLE "+GaiaTestUtils.GAIA_TEST_DB_USER+" LOGIN PASSWORD '"+GaiaTestUtils.GAIA_TEST_DB_PASS+"' SUPERUSER INHERIT CREATEDB CREATEROLE;");

		st = con.createStatement();
		st.execute(" CREATE DATABASE "+GaiaTestUtils.GAIA_TEST_DB+" " +
				   " WITH OWNER = "+GaiaTestUtils.GAIA_TEST_DB_USER+" " +
				       " ENCODING = 'UTF8' " +
				       " TABLESPACE = pg_default " +
				       ((this.isGeoDB()) ? " TEMPLATE = template_postgis " : "") +
				       " CONNECTION LIMIT = -1; ");
		con.close();
	}

	public void start() throws Exception {
		long l = System.currentTimeMillis();
		
		this.createDB();
		this.con = DriverManager.getConnection(DB_URL_GAIA, GaiaTestUtils.GAIA_TEST_DB_USER, GaiaTestUtils.GAIA_TEST_DB_PASS);
		
		if(this.isGeoDB()) {
			Statement st = this.con.createStatement();
			st.execute("ALTER TABLE geometry_columns OWNER TO "+GaiaTestUtils.GAIA_TEST_DB_USER+";");
			st = this.con.createStatement();
			st.execute("ALTER TABLE spatial_ref_sys OWNER TO "+GaiaTestUtils.GAIA_TEST_DB_USER+";");
			if(!this.con.getAutoCommit())
				this.con.commit();
		}
		
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
		st.execute("DROP DATABASE IF EXISTS "+GaiaTestUtils.GAIA_TEST_DB+";");
		st = con.createStatement();
		st.execute("DROP ROLE IF EXISTS "+GaiaTestUtils.GAIA_TEST_DB_USER+";");
		con.close();
	}
	
	public boolean isGeoDB() {
		return this.geoDB;
	}
	
	public Log getLog() {
		return logger;
	}
}
