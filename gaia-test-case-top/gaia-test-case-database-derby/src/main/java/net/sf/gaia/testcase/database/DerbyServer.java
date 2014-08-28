package net.sf.gaia.testcase.database;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URL;
import java.util.Properties;

import org.apache.derby.drda.NetworkServerControl;

public final class DerbyServer {

	private NetworkServerControl container;

	private String dir;

	public DerbyServer(String dir) {
		this.dir = dir;
	}

	public void start() throws Exception {
		container = new NetworkServerControl();
		container.setTraceDirectory(dir);

		Properties props = container.getCurrentProperties();
		URL url = ClassLoader.getSystemResource("net/sf/gaia/testcase/database/derby.properties");
        props.load(url.openStream());
		props.setProperty("derby.drda.traceDirectory", dir);

		PrintWriter out = new PrintWriter(System.out, true);
		container.start(out);
	}

	public void stop() throws Exception {
		container.shutdown();
	}
}
