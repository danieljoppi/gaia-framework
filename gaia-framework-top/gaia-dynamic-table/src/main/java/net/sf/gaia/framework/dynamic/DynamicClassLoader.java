package net.sf.gaia.framework.dynamic;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import net.sf.gaia.framework.dynamic.DynamicClassLoader;
import net.sf.gaia.framework.dynamic.DynamicUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ClassLoader responsável por gerenciar as classes dinâmicas na memória.
 * 
 * @deprecated as classes dinâmicas são armazenadas num jar e carregadas na memória com os demais jars.
 * 
 * @author daniel.joppi
 *
 */
public class DynamicClassLoader extends URLClassLoader {

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DynamicClassLoader.class);
	
	/**
	 * Hash map das classes dinâmicas.
	 */
	private Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();

	public DynamicClassLoader(ClassLoader parent) {
		super(new URL[0], parent);
	}

	public void addByteArray(String clazzName, byte[] b, int off, int len) {
		final Class<?> c = defineClass(clazzName, b, off, len);
		if (c != null) {
			classMap.put(clazzName, c);
		}
	}

	@Override
	protected synchronized Class<?> loadClass(String clazzName, boolean resolve) throws ClassNotFoundException {
		if (DynamicUtils.isDynamicClassName(clazzName)) {
			final Class<?> c = classMap.get(clazzName);
			if (c != null)
				return c;
			else
				throw new ClassNotFoundException("Class " + clazzName + " was not found");
		} else {
			return super.loadClass(clazzName, resolve);
		}
	}
}