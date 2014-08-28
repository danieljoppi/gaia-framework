package net.sf.gaia.engine;

import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;
import net.sf.gaia.entity.SysSecurityEntry;
import net.sf.gaia.entity.SysEntity;
import net.sf.gaia.entity.SysGroup;
import net.sf.gaia.entity.SysObject;
import net.sf.gaia.entity.SysSet;
import net.sf.gaia.entity.SysUser;
import net.sf.gaia.testcase.utils.GaiaTestUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe para testar a classe net.sf.gaia.engine.EntityFactory
 * 
 * @author daniel.joppi
 *
 */
public class EntityFactoryTest extends TestCase {

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(EntityFactoryTest.class);

	public EntityFactoryTest() throws Exception {
		System.setProperty("gaia.dir", GaiaTestUtils.GAIA_TEST_DIR);

		System.setProperty("gaia.service.dir", GaiaTestUtils.GAIA_TEST_SERVICE_DIR);
	}
	
	public void testLoadEntitys() throws Exception {
		Set<Class<SysEntity>> classes = EntityFactory.loadEntitys(SysEntity.class);
		
		assertEquals(17, classes.size());
		
		int count = 0;
		
		Class<SysEntity> clazz = null;
		for (Iterator<Class<SysEntity>> it = classes.iterator(); it.hasNext();) {
			clazz = it.next();
			
			if(SysGroup.class.isAssignableFrom(clazz)) {
				count++;
			} else if(SysSet.class.isAssignableFrom(clazz)) {
				count++;
			} else if(SysUser.class.isAssignableFrom(clazz)) {
				count++;
			} else if(SysSecurityEntry.class.isAssignableFrom(clazz)) {
				count++;
			} else if(SysObject.class.isAssignableFrom(clazz)) {
				count++;
			}
		}
		
		assertEquals(classes.size(), count);
	}
	
	public void testNewEntityInstance() throws Exception {
		SysUser user = EntityFactory.newEntityInstance("net.sf.gaia.entity.SysUser");		
		assertNotNull(user);
		assertEquals(SysUser.class, user.getClass());

		SysGroup group = EntityFactory.newEntityInstance("net.sf.gaia.entity.SysGroup");
		assertNotNull(group);
		assertEquals(SysGroup.class, group.getClass());

		SysObject object = EntityFactory.newEntityInstance("net.sf.gaia.entity.SysObject");
		assertNull(object);
	}
}
