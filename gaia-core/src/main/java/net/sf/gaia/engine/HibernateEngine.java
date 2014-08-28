package net.sf.gaia.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.persistence.EntityManagerFactory;

import net.sf.gaia.persistence.DatabaseSettings;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.dialect.Dialect;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.ejb.EntityManagerImpl;

/**
 * Classe que disponibiliza métodos para persistir entidades do Gaia no banco de dados.
 * 
 * @author daniel.joppi
 * 
 */
public final class HibernateEngine {

	private static final Log logger = LogFactory.getLog(HibernateEngine.class);

	private static Ejb3Configuration ejbConfiguration;
	
	private static String PERSISTENCE_UNIT = "gaia";
	
	/**
	 * Método que persiste um Object no banco de dados.
	 * 
	 * @param o objeto a ser persistido no banco de dados.
	 * 
	 * @author daniel.joppi
	 * @since 20/08/2010
	 */
	public final static void persist(Object o) {

		if (logger.isDebugEnabled()) {
			logger.debug("Persisting object " + o);
		}
		EntityManagerImpl manager = ((EntityManagerImpl) DatabaseSettings.getEntityManager());
		Session session = manager.getSession();
		Transaction trans = session.getTransaction();
		
		try {
			if (trans == null) {
				trans = session.beginTransaction();
			} else if (!trans.isActive()) {
				trans.begin();
			}
			
			// Persists the object
			session.saveOrUpdate(o);
			session.flush();
			
			trans.commit();
			
		} catch (RuntimeException e) {
			if (trans.isActive()) {
				trans.rollback();
			}
			logger.error("Error persisting object " + o.getClass().getName() + " #@" + o.hashCode(), e);
			throw e;
		}
	}

	/**
	 * Método que remove um Object no banco de dados.
	 * 
	 * @param o objeto a ser removido no banco de dados.
	 * 
	 * @author daniel.joppi
	 * @since 20/08/2010
	 */
	public final static void remove(Object o) {

		if (logger.isDebugEnabled()) {
			logger.debug("Removing object " + o);
		}
		EntityManagerImpl manager = ((EntityManagerImpl) DatabaseSettings.getEntityManager());
		Session session = manager.getSession();
		Transaction trans = session.getTransaction();
		
		try {
			if (trans == null) {
				trans = session.beginTransaction();
			} else if (!trans.isActive()) {
				trans.begin();
			}
			// Remove the object
			session.merge(o);
			session.delete(o);

			trans.commit();
			
		} catch (RuntimeException e) {
			if (trans.isActive()) {
				trans.rollback();
			}
			logger.error("Error removing object " + o.getClass().getName() + " #@" + o.hashCode(), e);
			throw e;
		}
	}
	
	/**
	 * Método que carrega todas os dados de uma determinada entidade.
	 * 
	 * @param clazz classe da entidade que será carregada.
	 * 
	 * @author daniel.joppi
	 * @since 11/12/2011
	 */
	public final static <T> List<T> load(Class<T> clazz) {
		return load(clazz, null);
	}
	
	/**
	 * Método que carrega todas os dados de uma determinada entidade.
	 * 
	 * @param clazz classe da entidade que será carregada.
	 * @param criteria filtro que será aplicado a consulta dos dados.
	 * 
	 * @author daniel.joppi
	 * @since 11/12/2011
	 */
	public final static <T> List<T> load(Class<T> clazz, Criterion criterion) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Load data for entity " + clazz.getName());
		}
		
		Criteria criteria = ((EntityManagerImpl) DatabaseSettings.getEntityManager()).getSession().createCriteria(clazz);
		
		if(criterion != null) {
			criteria.add(criterion);
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return (List<T>) criteria.list();
	}

	public final static <T> EntityManagerFactory recreateEntityManagerFactory(final Set<Class<T>> classes) {
		return createEntityManagerFactory(classes, null);
	}
	
	public final static <T> EntityManagerFactory createEntityManagerFactory(final Set<Class<T>> classes, String unit) {
		if (unit != null) {
			PERSISTENCE_UNIT = unit;
			System.setProperty("gaia.hibernate.persitence.unit", PERSISTENCE_UNIT);
		}
		if (PERSISTENCE_UNIT == null) {
			PERSISTENCE_UNIT = System.getProperty("gaia.hibernate.persitence.unit");
		}
		
		System.setProperty("hibernate.bytecode.provider", "cglib");
		
		ejbConfiguration = new Ejb3Configuration();

		logger.debug("Adding annotated classes...");
		if (classes != null) {
			for (final Class<T> c : classes) {
				if (logger.isDebugEnabled()) {
					logger.debug("Adding annotated class " + c.getName());
				}
				ejbConfiguration.addAnnotatedClass(c);
			}
		}
		
		long l1 = System.currentTimeMillis();
		ejbConfiguration = ejbConfiguration.configure(PERSISTENCE_UNIT, new HashMap<Object,Object>());
		ejbConfiguration.addProperties(System.getProperties());
		final Configuration config = ejbConfiguration.getHibernateConfiguration();
		config.setProperty("hibernate.query.jpaql_strict_compliance", "false");

		if (logger.isDebugEnabled()) {
			logger.debug("cfg.configure time: " + (System.currentTimeMillis() - l1) + "ms");
		}
		l1 = System.currentTimeMillis();
		if (logger.isDebugEnabled()) {
			logger.debug("building EMF... classes: " + classes);
		}
		final EntityManagerFactory emf = ejbConfiguration.buildEntityManagerFactory();
		if (logger.isDebugEnabled()) {
			logger.debug("cfg.buildEntityManagerFactory time: " + (System.currentTimeMillis() - l1) + "ms");
		}
		
		DatabaseSettings.setEntityManagerFactory(emf);		
		return emf;
	}

	/**
	 * 
	 * @param <T>
	 * @param classes
	 * @deprecated não funco muito bem
	 */
	public final static <T> void includeNewClasses(Set<Class<T>> classes) {
		logger.debug("Adding annotated classes...");
		if (classes != null) {
			for (final Class<T> c : classes) {
				if (logger.isDebugEnabled()) {
					logger.debug("Adding annotated class " + c.getName());
				}
				ejbConfiguration.addAnnotatedClass(c);
			}
		}
		
		long l1 = System.currentTimeMillis();
		if (logger.isDebugEnabled()) {
			logger.debug("building EMF... classes: " + classes);
		}
		final EntityManagerFactory emf = ejbConfiguration.buildEntityManagerFactory();
		if (logger.isDebugEnabled()) {
			logger.debug("cfg.buildEntityManagerFactory time: " + (System.currentTimeMillis() - l1) + "ms");
		}
		
		DatabaseSettings.setEntityManagerFactory(emf);	
	}

	public static String getPersistenceUnit() {
		return PERSISTENCE_UNIT;
	}
	
	public static Dialect getDialect() {
		Properties prop = ejbConfiguration.getProperties();
		return Dialect.getDialect(prop);
	}
}
