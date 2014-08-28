package net.sf.gaia.util;

import java.util.GregorianCalendar;

import junit.framework.TestCase;

public class SafeUtilsTest extends TestCase {

	public SafeUtilsTest() throws Exception {
		super();
	}
	
	public void testSafeLong() throws Exception {
		assertNull(SafeUtils.safeLong(null));
		assertEquals(new Long(7897), SafeUtils.safeLong("7897"));
		assertNull(SafeUtils.safeLong("2Kllkk44"));
		assertNull(SafeUtils.safeLong("Daniel Henrique Joppi"));
	}

	public void testSafeInteger() throws Exception {
		assertNull(SafeUtils.safeInteger(null));
		assertEquals(new Integer(7897), SafeUtils.safeInteger("7897"));
		assertNull(SafeUtils.safeInteger("2Kllkk44"));
		assertNull(SafeUtils.safeInteger("Daniel Henrique Joppi"));
	}

	public void testSafeDouble() throws Exception {
		assertNull(SafeUtils.safeDouble(null));
		assertEquals(new Double(7897.6464), SafeUtils.safeDouble("7897.6464"));
		assertNull(SafeUtils.safeDouble("2Kllkk44.988"));
		assertNull(SafeUtils.safeDouble("Daniel Henrique Joppi"));
	}

	public void testSafeDateFormat() throws Exception {
		
	}

	public void testSafeNotEquals() throws Exception {
		
	}

	public void testSafeEquals() throws Exception {
		
	}

	public void testSafeBoolean() throws Exception {
		assertEquals(false, SafeUtils.safeBoolean(null));
		
		assertEquals(true, SafeUtils.safeBoolean("true"));
		assertEquals(true, SafeUtils.safeBoolean("sim"));
		assertEquals(true, SafeUtils.safeBoolean("yes"));
		assertEquals(true, SafeUtils.safeBoolean("TRUE"));
		assertEquals(true, SafeUtils.safeBoolean("SiM"));
		assertEquals(true, SafeUtils.safeBoolean("yEs"));
		assertEquals(false, SafeUtils.safeBoolean("false"));
		assertEquals(false, SafeUtils.safeBoolean("não"));
		assertEquals(false, SafeUtils.safeBoolean("no"));
		assertEquals(false, SafeUtils.safeBoolean("Daniel Henrique Joppi"));
	}

	public void testSafeString() throws Exception {
		assertNull(SafeUtils.safeString(null));
		assertNull(SafeUtils.safeString(""));
		assertNull(SafeUtils.safeString("null"));
		
		assertEquals("no", SafeUtils.safeString("no"));
		assertEquals("Daniel Henrique Joppi", SafeUtils.safeString("Daniel Henrique Joppi"));
		assertEquals("Daniel Henrique Joppi   ", SafeUtils.safeString("Daniel Henrique Joppi   "));
		assertEquals("\t\t\tDaniel Henrique Joppi  \n ", SafeUtils.safeString("\t\t\tDaniel Henrique Joppi  \n "));
	}

	public void testSafeTrim() throws Exception {
		assertNull(SafeUtils.safeTrim(null));
		assertNull(SafeUtils.safeTrim(""));
		assertNull(SafeUtils.safeTrim("null"));
		
		assertEquals("no", SafeUtils.safeTrim("no"));
		assertEquals("Daniel Henrique Joppi", SafeUtils.safeTrim("Daniel Henrique Joppi"));
		assertEquals("Daniel Henrique Joppi", SafeUtils.safeTrim("Daniel Henrique Joppi   "));
		assertEquals("Daniel Henrique Joppi", SafeUtils.safeTrim("\t\t\tDaniel Henrique Joppi  \n "));
	}

	public void testSafeIsNotNull() throws Exception {
		assertEquals(false, SafeUtils.safeIsNotNull((Object) null));
		assertEquals(false, SafeUtils.safeIsNotNull(""));
		assertEquals(false, SafeUtils.safeIsNotNull("\t\t\t  \n\n"));
		assertEquals(false, SafeUtils.safeIsNotNull((Object) null, (String) null, "", "  ", (Integer) null));
		assertEquals(false, SafeUtils.safeIsNotNull((Object) null, (String) null, "  ", (Integer) null, "Daniel Henrique Joppi"));
		assertEquals(true, SafeUtils.safeIsNotNull(2312, true, "fr", " , ", new GregorianCalendar(), "Daniel Henrique Joppi"));
	}

	public void testSafeIsNull() throws Exception {
		assertEquals(true, SafeUtils.safeIsNull((Object) null));
		assertEquals(true, SafeUtils.safeIsNull(""));
		assertEquals(true, SafeUtils.safeIsNull("\t\t\t  \n\n"));
		assertEquals(true, SafeUtils.safeIsNull((Object) null, (String) null, "", "  ", (Integer) null));
		assertEquals(false, SafeUtils.safeIsNull((Object) null, (String) null, "", "  ", (Integer) null, "Daniel Henrique Joppi"));
	}
	
	public void testSafeGetterName() throws Exception {
		assertEquals("isMethod", SafeUtils.safeGetterName("method", Boolean.class));
		assertEquals("getMethod", SafeUtils.safeGetterName("method", Integer.class));
		assertEquals("getMethod", SafeUtils.safeGetterName("method", Object.class));
		
		assertEquals("isMethod", SafeUtils.safeGetterName("isMethod", Boolean.class));
		assertEquals("isGetMethod", SafeUtils.safeGetterName("getMethod", Boolean.class));
		assertEquals("getIsMethod", SafeUtils.safeGetterName("isMethod", Integer.class));
		assertEquals("getMethod", SafeUtils.safeGetterName("getMethod", Integer.class));
		assertEquals("getMethod", SafeUtils.safeGetterName("getMethod", Object.class));
	}
	
	public void testSafeSetterName() throws Exception {
		assertEquals("setMethod", SafeUtils.safeSetterName("method", Boolean.class));
		assertEquals("setMethod", SafeUtils.safeSetterName("method", Integer.class));
		assertEquals("setMethod", SafeUtils.safeSetterName("method", Object.class));

		assertEquals("setMethod", SafeUtils.safeSetterName("setMethod", Boolean.class));
		assertEquals("setMethod", SafeUtils.safeSetterName("setMethod", Integer.class));
		assertEquals("setMethod", SafeUtils.safeSetterName("setMethod", Object.class));
	}
}
