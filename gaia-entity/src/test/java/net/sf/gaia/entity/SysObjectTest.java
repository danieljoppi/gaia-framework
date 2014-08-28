package net.sf.gaia.entity;

import junit.framework.TestCase;

public class SysObjectTest extends TestCase {

	public void testSafeSysType() throws Exception {
		SysObject o = new SysObject() {	};
		
		o.setSysType(SysObject.class.getName());
		
		String type = o.getSysType();
		assertEquals("G_E_.SysObject", type);
		String safe = o.safeSysType();
		assertEquals("net.sf.gaia.entity.SysObject", safe);
		
		o.setSysType("net.sf.gaia.dyn.entity.D_LegalLegal");
		type = o.getSysType();
		assertEquals("D_.D_LegalLegal", type);
		safe = o.safeSysType();
		assertEquals("net.sf.gaia.dyn.entity.D_LegalLegal", safe);
		
		o.setSysType("net.sf.gaia.dyn.framework.entity.E_LegalLegal");
		type = o.getSysType();
		assertEquals("G_.dyn.frameworkE_.E_LegalLegal", type);
		safe = o.safeSysType();
		assertEquals("net.sf.gaia.dyn.framework.entity.E_LegalLegal", safe);
	}
}
