package net.sf.gaia.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import net.sf.gaia.sys.GaiaClassLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Jar;

/**
 * Classe utilitária para métodos específicos dos Gaia.
 * 
 * @author daniel.joppi
 * 
 */
public abstract class GaiaUtils {

	private static final Log logger = LogFactory.getLog(GaiaUtils.class);
	
	/**
	 * Método que cria uma lista de arquivos em instâncias de URL, os capturando
	 * no diretório passado em pelo parâmento path com a extenção designada por
	 * ext.
	 * 
	 * @param path
	 *            diretório onde deseja realizar a busca.
	 * @param ext
	 *            estão do arquivo.
	 * @return lista de arquivos.
	 * 
	 * @author daniel.joppi
	 * @since 03/04/2010
	 */
	public final static List<URL> getFilesFromPath(String path, String ext) {
		File mF = new File(path);
		String[] files = mF.list();
		List<URL> urls = new ArrayList<URL>();
		for (int i = 0; i < files.length; i++)
		{
			if (files[i].endsWith(ext)) {
				try {
					URL url = new URL("file:/" + path + File.separator + files[i]);
					urls.add(url);
				} catch (MalformedURLException e) {
				}
			}
		}
		
		return urls;
	}
	
	/**
	 * Método que retorna o diretório do sistema.
	 * O caminho é setado pela propriedade "gaia.dir". 
	 * 
	 * @return diretório do sistema.
	 * 
	 * @author daniel.joppi
	 * @since 12/01/2011
	 */
	public final static String getGaiaPath() {
		String path = System.getProperty("gaia.dir");
		if (path == null) {
			path = System.getProperty("user.dir");
		}
		return path;
	}
	
	/**
	 * Método que retorna o diretório temporáriodo sistema. 
	 * 
	 * @return diretório temporáriodo sistema.
	 * 
	 * @author daniel.joppi
	 * @since 12/01/2011
	 */
	public final static String getGaiaTempPath() {
		return getGaiaPath() + File.separator + "temp";
	}
	
	/**
	 * Método que retorna o diretório onde estão os jars do sistema.
	 * O caminho é setado pela propriedade "gaia.service.dir". 
	 * 
	 * @return diretório onde estão os jars do sistema.
	 * 
	 * @author daniel.joppi
	 * @since 12/01/2011
	 */
	public final static String getGaiaServicePath() {
		String path = System.getProperty("gaia.service.dir");
		if (path == null) {
			path = System.getProperty("user.dir");
		}
		return path;
	}
	
	/**
	 * Método para criar um jar a partir de uma lista de arquivos. É importante
	 * que todos os arquivos da lista files estejam contidos no diretório referenciado
	 * pela propriedade GaiaUtils.getGaiaTempPath() + File.separator + "classes".
	 * 
	 * @param jarName
	 *            nome do jar.
	 * 
	 * @author daniel.joppi
	 * @since 18/01/2011
	 */
	public final static void createJarFile(String jarName) {
		createJarFile(jarName, null);
	}

	/**
	 * Método para criar um jar a partir de uma lista de arquivos. É importante
	 * que todos os arquivos da lista files estejam contidos no diretório classPath.
	 * 
	 * @param jarName
	 *            nome do jar.
	 * @param classPath
	 *            diretório que contém todos os arquivos a serem adicionados ao jar.
	 *            Caso nulo é utilizado o valor de: 
	 *            	- GaiaUtils.getGaiaTempPath() + File.separator + "classes".
	 * 
	 * @author daniel.joppi
	 * @since 12/01/2011
	 */
	public final static void createJarFile(String jarName, String classPath) {
		try {
			if (classPath == null) {
				classPath = getGaiaTempPath() + File.separator + "classes";
			}
			
			logger.info("Creating pack classes from: " + classPath + " ...");
			long l = System.currentTimeMillis();

			if (!jarName.endsWith(".jar")) {
				jarName += ".jar";
			}
			
			File jarDir = new File(getGaiaServicePath());
			if (!jarDir.exists()) {
				jarDir.mkdir();
			}
						
			File jarTempDir = new File(classPath);
			if (!jarTempDir.exists()) {
				jarTempDir.mkdir();
			}

			Project proj = new Project();
			Target target = new Target();
			Jar jar = new Jar();
			jar.setBasedir(jarTempDir);
			
			File fileJar = new File(jarDir, jarName);
			// Delete the jar file if it already exists
			if (fileJar.exists() == true) {
				fileJar.delete();
			}
			
			jar.setDestFile(fileJar);

			jar.setProject(proj);
			
			target.addTask(jar);
			target.setName("createJar");
			target.setProject(proj);
			
			proj.addTarget(target);
			proj.setDefault("createJar");
			
			jar.execute();
			logger.info("Created jar file: " + jarName + " ... " + (System.currentTimeMillis() - l) + " ms");
		} catch (Exception e) {
			logger.error("Can't create " + jarName, e);
		}
	}
	
	/**
	 * Método que busca todas as classes do pacote passado no parâmetro
	 * packageName, verificando em todos os jars contidos no diretório passado
	 * pelo parâmetro path.
	 * 
	 * @param packageName
	 *            nome do pacote que deseje realizar a busca das classes.
	 * @param path
	 *            diretório que possui os jars.
	 * @param clazz
	 *            designa uma classe específica paca busca na lista de pacotes,
	 *            mas também captura suas subclasses.
	 * @return conjunto de classes do pacote desejado.
	 * 
	 * @author daniel.joppi
	 * @since 25/04/2010
	 */
	public final static <E> Set<Class<E>> getClassesFromJarFile(String packageName, String path, Class<E> clazz) {

		return GaiaUtils.getClassesFromJarFile(new String[] { packageName }, path, clazz);
	}

	/**
	 * Método que busca todas as classes e subclasses designadas pelo parâmentro
	 * clazz da lista de pacote passado no parâmetro packageNames, verificando
	 * em todos os jars contidos no diretório passado pelo parâmetro path.
	 * 
	 * @param packageNames
	 *            lista de nomes dos pacotes que desejam realizar a busca das
	 *            classes.
	 * @param path
	 *            diretório que possui os jars.
	 * @param clazz
	 *            designa uma classe específica paca busca na lista de pacotes,
	 *            mas também captura suas subclasses.
	 * @return conjunto de classes dos pacotes desejados.
	 * 
	 * @author daniel.joppi
	 * @since 04/05/2010
	 */
	public final static <E> Set<Class<E>> getClassesFromJarFile(String[] packageNames, String path, Class<E> clazz) {
		Set<Class<E>> classes = new HashSet<Class<E>>();
		List<URL> urls = GaiaUtils.getFilesFromPath(path, ".jar");
		
		String[] pckPaths = new String[packageNames.length]; 
		for (int i=0; i<packageNames.length; i++) {
			pckPaths[i] = GaiaUtils.dot2Slash(packageNames[i]) + "/";
		}
		
		GaiaClassLoader loader = null;
		if (Thread.currentThread().getContextClassLoader() instanceof GaiaClassLoader) {
			loader = (GaiaClassLoader) Thread.currentThread().getContextClassLoader();
		} else {
			loader = GaiaClassLoader.getInstance();
		}
		
		for (int i = 0; i < urls.size(); i++) {
			try {
				String filePath = urls.get(i).getFile();
				
				JarFile jarFile = new JarFile(filePath);
				for (Enumeration<JarEntry> e = jarFile.entries(); e.hasMoreElements();) {
					JarEntry current = e.nextElement();
					//verifica em toda a lista de pacotes
					for (String packages : pckPaths)
					{
						if (current.getName().length() > packages.length()
								&& current.getName().substring(0, packages.length()).equals(packages)
								&& current.getName().endsWith(".class"))
						{
							String className = GaiaUtils.slash2Dot(current.getName()).replace(".class", "");
							loader.addJar(filePath);

							Class<?> c = null;
							try {
								c = loader.loadClass(className);
							} catch (ClassNotFoundException cnfe) {
								try {
						            c = loader.defineClass(jarFile, current);
								} catch (Exception cnfex) {
									logger.error("Package: "+packages+" - Class: "+className); 
									logger.error("Class "+current.getName()+" in Jar File: "+filePath, cnfe);
								}
							}
							// se a classe é uma subclasse de clazz
							// se não for especificado um class pega todas as classes do pacote
							if(c != null && (clazz == null || clazz.isAssignableFrom(c)))
							{
								classes.add((Class<E>) c);
							}
						}
					}
				}
			} catch (Exception e) {
			}
		}
		return classes;
	}

	/**
	 * Método que busca todos os input stream dos arquivos com extensão
	 * designadas pelo parâmentro fileExt da lista de diretórios passado no
	 * parâmetro fileDirs, verificando em todos os jars contidos no diretório
	 * passado pelo parâmetro path.
	 * 
	 * @param fileDirs
	 *            lista de nomes dos diretórios que desejam realizar a busca dos
	 *            arquivos.
	 * @param path
	 *            diretório que possui os jars.
	 * @param fileExt
	 *            estão do arquivo.
	 * @return lista de input streams dos diretórios desejados.
	 * @throws ClassNotFoundException
	 * 
	 * @author daniel.joppi
	 * @since 04/05/2010
	 */
	public final static List<InputStream> getInputSteamsFromJarFile(String[] fileDirs, String path, String fileExt) {
		List<InputStream> inputs = new ArrayList<InputStream>();
		List<URL> urls = GaiaUtils.getFilesFromPath(path, ".jar");
					
		for (int i = 0; i < urls.size(); i++) {
			try {
				ZipFile zipFile = new ZipFile(urls.get(i).getFile());
				for (Enumeration<ZipEntry> e = (Enumeration<ZipEntry>) zipFile.entries(); e.hasMoreElements();) {
					ZipEntry current = e.nextElement();
					//verifica em toda a lista de diretorios
					for (String fileDir : fileDirs)
					{
						if (current.getName().length() > fileDir.length()
								&& current.getName().substring(0, fileDir.length()).equals(fileDir)
								&& current.getName().endsWith(fileExt))
						{
							inputs.add(zipFile.getInputStream(current));
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return inputs;
	}

	private final static Pattern DOT = Pattern.compile("(\\.)");
	private final static Pattern SPLASH = Pattern.compile("(/)");
	
	/**
	 * Método que faz a simples troca de "." por "/".
	 * 
	 * @param str string
	 * @return ex "net.sf.gaia.engine.GaiaEntity", o método
	 *         irá retorna a String: "net/sf/gaia/engine/GaiaEngine".
	 * 
	 * @author daniel.joppi
	 * @since 11/01/2011
	 */
	public final static String dot2Slash(String str) {
		return DOT.matcher(str).replaceAll("/");
	}
	
	/**
	 * Método que faz a simples troca de "/" por ".".
	 * 
	 * @param str string
	 * @return ex "net/sf/gaia/engine/GaiaEngine", o método
	 *         irá retorna a String: "net.sf.gaia.engine.GaiaEntity".
	 * 
	 * @author daniel.joppi
	 * @since 12/01/2011
	 */
	public final static String slash2Dot(String str) {
		return SPLASH.matcher(str).replaceAll("\\.");
	}
	
	/**
	 * Método responsável gerar a assinatura do tipo da classe.
	 * 
	 * @param type
	 *            tipo da classe.
	 * @return caso seja uma classe "net.sf.gaia.engine.GaiaEntity", o método
	 *         irá retorna a String: "Lnet/sf/gaia/engine/GaiaEngine;".
	 * 
	 * @author daniel.joppi
	 * @since 11/01/2011
	 */
	public final static String typeSignature(String type) {
		return ("L" + dot2Slash(type) + ";");
	}
}