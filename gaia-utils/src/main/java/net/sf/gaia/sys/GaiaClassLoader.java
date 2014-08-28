package net.sf.gaia.sys;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.sf.gaia.util.GaiaUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ClassLoader para gerenciar as adições e remoções de novas classes no sistema.
 * 
 * @author daniel.joppi
 * 
 */
public final class GaiaClassLoader extends URLClassLoader {

	private static final Log logger = LogFactory.getLog(GaiaClassLoader.class);

	private static GaiaClassLoader instance;
	
	private Map<String, URL> mapJars = new HashMap<String, URL>();
	
	private GaiaClassLoader(ClassLoader parent) {
		super(new URL[0], parent);
	}

	public static final GaiaClassLoader getInstance() {
		if (instance == null) {
			ClassLoader loader = null;
			if (ClassLoader.class.getClassLoader() != null) {
				loader = ClassLoader.class.getClassLoader();
			} else if (Thread.currentThread().getContextClassLoader() != null) { 
				loader = Thread.currentThread().getContextClassLoader();
			} else {
				loader = ClassLoader.getSystemClassLoader();
			}
			instance = new GaiaClassLoader(loader);
			logger.info("Using parent class loader: " + loader.getClass().getName());
		}
		return instance;
	}
	
	public void addJar(String path) throws MalformedURLException {
        String urlPath = "jar:file:/" + path + "!/";
        //this.addURL(new URL(urlPath));
        if (this.mapJars.containsKey(path)) {
        	this.mapJars.remove(path);
        }
        this.mapJars.put(path, new URL(urlPath));
    }

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		try {
			return super.loadClass(name);
		} catch (ClassNotFoundException cnfe) {
			Set<String> keys = this.mapJars.keySet();
			for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
				try {
					URL url = this.mapJars.get(it.next());
					String filePath = url.getFile();
					JarFile jarFile = new JarFile(filePath);
					JarEntry entry = jarFile.getJarEntry(GaiaUtils.dot2Slash(name) + ".class");
					
					return this.defineClass(jarFile, entry);
				} catch (Exception e) { }
			}
			throw new ClassNotFoundException(name, cnfe);
		}
	}
	
	public Class<?> defineClass(JarFile jar, JarEntry entry) throws IOException {
		byte[] array = new byte[1024];
        InputStream in = jar.getInputStream(entry);
        ByteArrayOutputStream out = new ByteArrayOutputStream(array.length);
        int length = in.read(array);
        while (length > 0) {
            out.write(array, 0, length);
            length = in.read(array);
        }
        String className = GaiaUtils.slash2Dot(entry.getName()).replace(".class", "");
        
        return defineClass(className, out.toByteArray(), 0, out.size());
	}
}
