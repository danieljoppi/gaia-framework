package net.sf.gaia.testcase.database;

import org.apache.commons.logging.Log;

/**
 * Interface para implementação de classes que simulam um banco de dados.
 * 
 * @author daniel.joppi
 *
 */
public interface ServerFake {

	public void start() throws Exception;
	
	public void stop() throws Exception;
	
	public boolean isGeoDB();
	
	public Log getLog();
}
