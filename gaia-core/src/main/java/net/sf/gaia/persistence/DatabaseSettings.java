package net.sf.gaia.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe que controla a comunicação com a API do Hibernate. E gerencia as
 * configurações com o banco de dados.
 * 
 * @author daniel.joppi
 * 
 */
public abstract class DatabaseSettings {

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DatabaseSettings.class);
	
	/**
	 * Interface used to interact with the entity manager factory for the persistence unit.
	 */
	//@PersistenceUnit(unitName="gaia")
	private static EntityManagerFactory entityManagerFactory;
	
	/**
	 * Interface used to interact with the persistence context.
	 */
	//@PersistenceContext(unitName="gaia")
	private static EntityManager entityManager;

	public synchronized static EntityManagerFactory getEntityManagerFactory() {
		return DatabaseSettings.entityManagerFactory;
	}

	public synchronized static void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		if( entityManagerFactory == null) {
			return;
		}
		if (DatabaseSettings.entityManagerFactory != null && !DatabaseSettings.entityManagerFactory.equals(entityManagerFactory)) {
			DatabaseSettings.entityManagerFactory.close();
		}
		DatabaseSettings.entityManagerFactory = entityManagerFactory;
		DatabaseSettings.setEntityManager(entityManagerFactory.createEntityManager());
	}

	public synchronized static EntityManager getEntityManager() {
		return entityManager;
	}

	protected synchronized static void setEntityManager(EntityManager entityManager) {
		DatabaseSettings.entityManager = entityManager;
	}
}
