package net.sf.gaia.engine;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.sf.gaia.entity.SysGroup;
import net.sf.gaia.entity.SysRole;
import net.sf.gaia.entity.SysUser;
import net.sf.gaia.persistence.DatabaseSettings;
import net.sf.gaia.sys.GaiaClassLoader;
import net.sf.gaia.testcase.database.MySQLServer;
import net.sf.gaia.testcase.database.PostgreSQLServer;
import net.sf.gaia.testcase.utils.GaiaTestUtils;
import net.sf.gaia.util.SafeUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class HibernateEngineTest extends TestCase {

	private static final Log logger = LogFactory.getLog(HibernateEngineTest.class);

	public HibernateEngineTest() throws Exception {
		System.setProperty("gaia.dir", GaiaTestUtils.GAIA_TEST_DIR);

		System.setProperty("gaia.service.dir", GaiaTestUtils.GAIA_TEST_SERVICE_DIR);
		Thread.currentThread().setContextClassLoader(GaiaClassLoader.getInstance());
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testGaiaEngineMySQL() throws Exception {
		if( !SafeUtils.safeBoolean(System.getProperty("gaia.mysql.test")) ) {
			logger.info("Skip test for MySQL ...");
			return;
		}
		
		MySQLServer server = new MySQLServer();
		server.start();

		// cria as tabelas
		EntityFactory.createDBEntitys("gaiatest-mysql");
		// persiste os dados nas tabelas
		this.persistEntitys();
		// captura os dados
		this.loadEntitys();
		// captura os dados
		this.removeEntitys();


		DatabaseSettings.getEntityManagerFactory().close();
		
		//server.stop();
	}
	
	public void testGaiaEnginePostgreSQL() throws Exception {
		if( !SafeUtils.safeBoolean(System.getProperty("gaia.postgres.test")) ) {
			logger.info("Skip test for PostgreSQL ...");
			return;
		}
		
		PostgreSQLServer server = new PostgreSQLServer();
		server.start();

		// cria as tabelas
		EntityFactory.createDBEntitys("gaiatest-postgres");
		// persiste os dados nas tabelas
		this.persistEntitys();
		// captura os dados
		this.loadEntitys();
		// captura os dados
		this.removeEntitys();
		
		DatabaseSettings.getEntityManagerFactory().close();
		
		//server.stop();
	}
	
	private void persistEntitys() throws Exception {
		SysGroup group = new SysGroup();
		group.setName("Users");
		group.setNickName("Users");
		HibernateEngine.persist(group);
		
		SysUser user1 = new SysUser();
		user1.setFullName("Daniel Henrique Joppi");
		user1.setNickName("daniel.joppi");
		user1.setGroup(group);
		HibernateEngine.persist(user1);

		SysUser user2 = new SysUser();
		user2.setFullName("Ana Paula Cristofolini");
		user2.setNickName("ana.cristofolini");
		user2.setGroup(group);
		HibernateEngine.persist(user2);
		
		SysRole role1 = new SysRole();
		role1.setName("Administrator");
		role1.setNickName("Administrator");
		role1.addUser(user1);		
		HibernateEngine.persist(role1);
		
		SysRole role2 = new SysRole();
		role2.setName("Colaborator");
		role2.setNickName("Colaborator");
		role2.addUser(user1);		
		role2.addUser(user2);
		HibernateEngine.persist(role2);
	}
	
	private void loadEntitys() throws Exception {
		int count = 0;
		for(SysUser o : HibernateEngine.load(SysUser.class)) {
			SysGroup g = o.getGroup();
			assertNotNull(g);
			assertNotNull(g.getUsers());

			assertEquals(2, g.getUsers().size());
			assertEquals(SysUser.class.getName(), o.safeSysType());
			assertEquals(SysGroup.class.getName(), g.safeSysType());
			
			count++;
		}
		assertEquals(2, count);
		
		Criterion criterion = Restrictions.like("fullName", "Daniel Henrique Joppi");
		List<SysUser> users = HibernateEngine.load(SysUser.class, criterion);
		
		assertNotNull(users);
		assertEquals(1, users.size());
		
		SysUser user = users.get(0);
		assertEquals("Daniel Henrique Joppi", user.getFullName());
		assertEquals("Users", user.getGroup().getNickName());
		assertEquals(2, user.getRoles().size());
		
		count = 0;
		for(SysRole o : user.getRoles()) {	
			if (o.getName().equals("Administrator")) {
				count ++;
				assertEquals(1, o.getUsers().size());
			} else if (o.getName().equals("Colaborator")) {
				count ++;
				assertEquals(2, o.getUsers().size());
			}
		}
		assertEquals(2, count);
	}

	public void removeEntitys() throws Exception {
		Criterion criterion = Restrictions.like("name", "Colaborator");
		List<SysRole> roles = HibernateEngine.load(SysRole.class, criterion);
		
		assertNotNull(roles);
		assertNotNull(roles.get(0));
		
		List<SysUser> l = new ArrayList<SysUser>();
		l.addAll(roles.get(0).getUsers());
		int count = 0;
		for(SysUser o : l) {
			SysGroup g = o.getGroup();
			assertNotNull(g);
			assertNotNull(g.getUsers());

			assertEquals(2, g.getUsers().size());
			assertEquals(SysUser.class.getName(), o.safeSysType());
			assertEquals(SysGroup.class.getName(), g.safeSysType());
			
			o.removeRole(roles.get(0));
			HibernateEngine.persist(o);
			
			count++;
		}
		assertEquals(2, count);
		
		HibernateEngine.remove(roles.get(0));
		
		criterion = Restrictions.like("fullName", "Daniel Henrique Joppi");
		List<SysUser> users = HibernateEngine.load(SysUser.class, criterion);

		assertNotNull(users);
		assertEquals(1, users.size());

		SysUser user = users.get(0);
		assertEquals("Daniel Henrique Joppi", user.getFullName());
		assertEquals("Users", user.getGroup().getNickName());
		assertEquals(1, user.getRoles().size());
	}
}
