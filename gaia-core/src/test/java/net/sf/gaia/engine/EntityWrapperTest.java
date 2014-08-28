package net.sf.gaia.engine;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import junit.framework.TestCase;
import net.sf.gaia.entity.SysGroup;
import net.sf.gaia.entity.SysObject;
import net.sf.gaia.entity.SysUser;
import net.sf.gaia.reflect.EntityWrapper;

public class EntityWrapperTest extends TestCase {

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(EntityWrapperTest.class);

	public EntityWrapperTest() {
		
	}
	
	public void testWrapperValue() {
		SysGroup group = new SysGroup();
		SysUser user1 = new SysUser();
		SysUser user2 = new SysUser();
		
		user1.setGroup(group);
		user2.setGroup(group);
		
		EntityWrapper<SysUser> entity = new EntityWrapper<SysUser>(user1);
		entity.setValue("fullName", "Daniel Henrique Joppi");
		entity.setValue("group.name", "Users");
		
		user2.setFullName("Ana Paula Cristofolini");
		
		assertEquals("Daniel Henrique Joppi", entity.getValue("fullName"));
		assertEquals("Users", entity.getValue("group.name"));
		
		Object o = new EntityWrapper<SysGroup>(group).getValue("users");
		assertEquals(true, Collection.class.isAssignableFrom(o.getClass()));
		
		Collection<?> list = (Collection<?>) o;
		assertEquals(2, list.size());		
	}

	public class SysTest extends SysObject {
		private BigDecimal decimal;
		
		public BigDecimal getDecimal() {
			return this.decimal;
		}
		
		public void setDecimal(BigDecimal decimal) {
			this.decimal = decimal;
		}
	}
	
	public void testCastValue() throws Exception {
		SysTest o = new SysTest() ;
		
		EntityWrapper<SysTest> wrapper = new EntityWrapper<SysTest>(o);
		wrapper.setValue("decimal", "888.9");
		Object r = wrapper.getValue("decimal");
		
		assertNotNull(r);
		assertEquals(BigDecimal.class, r.getClass());
		assertEquals("888.9", ((BigDecimal)r).toString());
		
		wrapper.setValue("decimal", Double.valueOf("888.9"));
		r = wrapper.getValue("decimal");
		
		assertNotNull(r);
		assertEquals(BigDecimal.class, r.getClass());
		
	}
}