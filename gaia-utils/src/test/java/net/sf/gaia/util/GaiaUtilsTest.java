package net.sf.gaia.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import junit.framework.TestCase;
import net.sf.gaia.testcase.utils.GaiaTestUtils;

import org.apache.commons.io.IOUtils;

/**
 * Classe para testar a classe net.sf.gaia.util.GaiaUtils
 * 
 * @author daniel.joppi
 *
 */
public class GaiaUtilsTest extends TestCase {

	public GaiaUtilsTest() {
		System.setProperty("gaia.dir", GaiaTestUtils.GAIA_TEST_DIR);

		System.setProperty("gaia.service.dir", GaiaTestUtils.GAIA_TEST_SERVICE_DIR);
	}
	
	public void testGetFilesFromPath() throws Exception {
		List<URL> urls = GaiaUtils.getFilesFromPath(GaiaTestUtils.GAIA_TEST_SERVICE_DIR, ".jar");
		
		assertEquals(5, urls.size());
	}
	
	public void testGetGaiaPath() throws Exception {		
		assertEquals(GaiaTestUtils.GAIA_TEST_DIR, GaiaUtils.getGaiaPath());
	}
	
	public void testGetGaiaTempPath() throws Exception {
		assertEquals(GaiaTestUtils.GAIA_TEST_TEMP_DIR, GaiaUtils.getGaiaTempPath());
	}
	
	public void testGetGaiaServicePath() throws Exception {		
		assertEquals(GaiaTestUtils.GAIA_TEST_SERVICE_DIR, GaiaUtils.getGaiaServicePath());
	}
	
	public void testCreateJarFile() throws Exception {
		String pack = "net.sf.gaia.testcase";
		String path = GaiaTestUtils.GAIA_TEST_TEMP_DIR + File.separator + "classes" + File.separator + GaiaUtils.dot2Slash(pack) + File.separator;
		String txt = "Gaia Test Class";
		InputStream in = null;
		FileOutputStream out = null;
		
		File pathDir = new File(path);
		if (!pathDir.exists()) {
			pathDir.mkdirs();
		}
		
		File clazz1 = new File(pathDir,"GaiaClassTest1.class");
		File clazz2 = new File(pathDir,"GaiaClassTest2.class");
		File clazz3 = new File(pathDir,"GaiaClassTest3.class");
		File clazz4 = new File(pathDir,"GaiaClassTest4.class");
		
		out = new FileOutputStream(clazz1);
		in = IOUtils.toInputStream(txt);
		IOUtils.copy(in, out);
		
		out = new FileOutputStream(clazz2);
		in = IOUtils.toInputStream(txt);
		IOUtils.copy(in, out);
		
		out = new FileOutputStream(clazz3);
		in = IOUtils.toInputStream(txt);
		IOUtils.copy(in, out);
		
		out = new FileOutputStream(clazz4);
		in = IOUtils.toInputStream(txt);
		IOUtils.copy(in, out);
		
		String services = GaiaUtils.getGaiaTempPath() + File.separator + "META-INF" + File.separator + "services" + File.separator;
		
		File servicesDir = new File(services);
		if (!servicesDir.exists()) {
			servicesDir.mkdirs();
		}
		
		File file = new File(services + "net.sf.gaia.GaiaEntity");
		out = new FileOutputStream(file);
		in = IOUtils.toInputStream(pack);
		IOUtils.copy(in, out);
		
		// cria o jar
		String jarName = "gaia-test-case-entity-2.0.jar";
		GaiaUtils.createJarFile(jarName, null);
		
		File jarFile = new File(GaiaTestUtils.GAIA_TEST_SERVICE_DIR + File.separator + jarName);
		assertEquals(true, jarFile.exists());
		
		JarInputStream jarIn = new JarInputStream(new FileInputStream(jarFile));
		JarEntry entry = null;
		int count = 0;
		while ((entry = jarIn.getNextJarEntry()) != null) {
			assertNotNull(entry);
			assertNotNull(entry.getName());
			count++;
		}
		
		assertEquals(8, count);
	}
}
