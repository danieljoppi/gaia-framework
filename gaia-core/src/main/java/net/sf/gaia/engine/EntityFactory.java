package net.sf.gaia.engine;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;

import net.sf.gaia.entity.SysEntity;
import net.sf.gaia.persistence.DatabaseSettings;
import net.sf.gaia.sys.GaiaClassLoader;
import net.sf.gaia.util.GaiaUtils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe que carrega as entity class dos jars no classpath.
 * 
 * @author daniel.joppi
 *
 */
public abstract class EntityFactory {

	private static final Log logger = LogFactory.getLog(EntityFactory.class);
	
	public static void createDBEntitys(String unit) throws Exception {
		Set<Class<SysEntity>> classes = (Set<Class<SysEntity>>) EntityFactory.loadEntitys(SysEntity.class);
		
		EntityManagerFactory emf = HibernateEngine.createEntityManagerFactory(classes, unit);
		DatabaseSettings.setEntityManagerFactory(emf);
	}
	
	/**
	 * Método que realiza a busca de todas as entitys contidas nos jars do projeto,
	 * contidos no diretório net.sf.gaia.utils.GaiaUtils.getGaiaServicePath().
	 * 
	 * @param c classe base para carregar as entidades baseadas nela.
	 * @return conjunto de entity.
	 * 
	 * @author daniel.joppi
	 * @since 03/05/2010
	 */
	public final static <T> Set<Class<T>> loadEntitys(Class<T> c) {
		String path = GaiaUtils.getGaiaServicePath(); 
			
		long l = System.currentTimeMillis();
		List<InputStream> inputs = GaiaUtils.getInputSteamsFromJarFile(new String[] { "META-INF/services" }, path, "net.sf.gaia.GaiaEntity");
		logger.info("In: " + path);
		logger.info("Find "+inputs.size()+" packages ... "+(System.currentTimeMillis() - l)+" ms");
		
		String[] packages = new String[inputs.size()];
		logger.debug("Loading classes in packages:");
		for (int i=0; i<inputs.size(); i++) {
			InputStream input = inputs.get(i);
			try {
				packages[i] = IOUtils.toString(input);
			} catch (IOException e) {
				packages[i] = "";
			}
			logger.debug(" - "+packages[i]);
		}
		l = System.currentTimeMillis();
		Set<Class<T>> classes = GaiaUtils.getClassesFromJarFile(packages, path, c);
		
		Set<Class<T>> entitys = new HashSet<Class<T>>();
		for (Class<T> clazz : classes) {
			Entity entity = clazz.getAnnotation(Entity.class);
			if (entity != null) {
				entitys.add(clazz);
			}
		}
		
		logger.info("Find "+entitys.size()+" classes ... "+(System.currentTimeMillis() - l)+" ms");
		return entitys;
	}

	/**
	 * Método que retorna uma nova instância de um objeto carregado na memória.
	 * 
	 * @param <T> tipo da classe de retorno.
	 * @param clazzName string com nome da classe e pacote. 
	 * @return nova instância de <T>.
	 * 
	 * @author daniel.joppi
	 * @since 20/11/2011
	 */
	@SuppressWarnings("unchecked")
	public final static <T> T newEntityInstance(String clazzName) {
		try {
			Class<T> clazz = (Class<T>) Thread.currentThread().getContextClassLoader().loadClass(clazzName);
			return clazz.newInstance();
		} catch (Exception e) {
			return null;
		}
	}
}
