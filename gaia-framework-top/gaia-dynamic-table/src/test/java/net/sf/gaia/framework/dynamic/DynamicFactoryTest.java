package net.sf.gaia.framework.dynamic;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.apache.commons.io.FileUtils;

import junit.framework.TestCase;
import net.sf.gaia.engine.EntityFactory;
import net.sf.gaia.engine.HibernateEngine;
import net.sf.gaia.entity.SysEntity;
import net.sf.gaia.entity.SysObject;
import net.sf.gaia.framework.dynamic.DynamicFactory;
import net.sf.gaia.framework.dynamic.DynamicUtils;
import net.sf.gaia.framework.entity.DynamicColumn;
import net.sf.gaia.framework.entity.DynamicColumnBoolean;
import net.sf.gaia.framework.entity.DynamicColumnClass;
import net.sf.gaia.framework.entity.DynamicColumnDate;
import net.sf.gaia.framework.entity.DynamicColumnDecimal;
import net.sf.gaia.framework.entity.DynamicColumnEntity;
import net.sf.gaia.framework.entity.DynamicColumnNumber;
import net.sf.gaia.framework.entity.DynamicColumnText;
import net.sf.gaia.framework.entity.DynamicColumnTime;
import net.sf.gaia.framework.entity.DynamicTable;
import net.sf.gaia.persistence.DatabaseSettings;
import net.sf.gaia.reflect.EntityWrapper;
import net.sf.gaia.sys.GaiaClassLoader;
import net.sf.gaia.testcase.database.MySQLServer;
import net.sf.gaia.testcase.database.PostgreSQLServer;
import net.sf.gaia.testcase.database.ServerFake;
import net.sf.gaia.testcase.utils.GaiaTestUtils;
import net.sf.gaia.util.GaiaUtils;
import net.sf.gaia.util.SafeUtils;

public class DynamicFactoryTest extends TestCase {

	public DynamicFactoryTest() throws Exception {
		System.setProperty("gaia.dir", GaiaTestUtils.GAIA_TEST_DIR);

		System.setProperty("gaia.service.dir", GaiaTestUtils.GAIA_TEST_SERVICE_DIR);
		Thread.currentThread().setContextClassLoader(GaiaClassLoader.getInstance());
	}
	
	public void testLoadDynamicEntityMySQL() throws Exception {
		if( !SafeUtils.safeBoolean(System.getProperty("gaia.mysql.test")) ) {
			System.out.println("Skip test for MySQL ...");
			return;
		}
		
		this.loadDynamicEntity("gaiatest-mysql", new MySQLServer());
	}
	
	public void testLoadDynamicEntityPostgreSQL() throws Exception {
		if( !SafeUtils.safeBoolean(System.getProperty("gaia.postgres.test")) ) {
			System.out.println("Skip test for PostgreSQL ...");
			return;
		}
		
		this.loadDynamicEntity("gaiatest-postgres", new PostgreSQLServer());
	}
	
	private void loadDynamicEntity(String unit, ServerFake server) throws Exception {
		File jar = new File(GaiaUtils.getGaiaServicePath() + File.separator + DynamicUtils.DYNAMIC_ENTITY_JAR_NAME);
		if (jar.exists()) {
			// xunxo para apagar jar
			FileUtils.writeStringToFile(jar, "");
		}
		
		server.start();

		// cria as tabelas
		EntityFactory.createDBEntitys(unit);
		// cria entidades para teste
		this.persistDynamicEntitys();
		// cria o jar
		this.createJarPack();
		// cria as tabelas dinâmicas
		this.createDBDynamicEntitys();
		
		DatabaseSettings.getEntityManagerFactory().close();
		
		server.stop();
	}
	
	private void persistDynamicEntitys() throws Exception {

		DynamicTable table1 = new DynamicTable();
		DynamicTable table2 = new DynamicTable();
		DynamicTable table3 = new DynamicTable();
		
		table1.setTableName("LegalLegal");
		table2.setTableName("MundoLegal");
		table3.setTableName("ShowLegal");
		table1.setSuperClass(SysObject.class);
		table2.setSuperClass(SysObject.class);
		table3.setSuperClass(SysObject.class);
		
		DynamicColumn column11 = new DynamicColumnBoolean();
		DynamicColumn column21 = new DynamicColumnText();
		DynamicColumn column31 = new DynamicColumnDate();
		DynamicColumn column41 = new DynamicColumnDecimal();
		
		column11.setColumnName("col11");
		column21.setColumnName("col21");
		column31.setColumnName("col31");
		column41.setColumnName("col41");
		
		List<DynamicColumn> columns1 = new ArrayList<DynamicColumn>();
		columns1.add(column11);
		columns1.add(column21);
		columns1.add(column31);
		columns1.add(column41);
		
		DynamicColumn column12 = new DynamicColumnBoolean();
		DynamicColumn column22 = new DynamicColumnNumber();
		DynamicColumn column32 = new DynamicColumnTime();
		DynamicColumn column42 = new DynamicColumnDecimal();
		
		column12.setColumnName("col12");
		column22.setColumnName("col22");
		column32.setColumnName("col32");
		column42.setColumnName("col42");
		
		List<DynamicColumn> columns2 = new ArrayList<DynamicColumn>();
		columns2.add(column12);
		columns2.add(column22);
		columns2.add(column32);
		columns2.add(column42);
		
		DynamicColumn column13 = new DynamicColumnBoolean();
		DynamicColumn column23 = new DynamicColumnText();
		DynamicColumn column33 = new DynamicColumnEntity();
		DynamicColumn column43 = new DynamicColumnClass();
		
		column13.setColumnName("col13");
		column23.setColumnName("col23");
		column33.setColumnName("col33");
		column43.setColumnName("col43");
		
		List<DynamicColumn> columns3 = new ArrayList<DynamicColumn>();
		columns3.add(column13);
		columns3.add(column23);
		columns3.add(column33);
		columns3.add(column43);
		
		// persiste as tabelas
		HibernateEngine.persist(table1);
		HibernateEngine.persist(table2);
		HibernateEngine.persist(table3);
		
		table1.setColumns(columns1);
		table2.setColumns(columns2);
		table3.setColumns(columns3);
		
		// persiste as colunas pra tabela 1
		HibernateEngine.persist(column11);
		HibernateEngine.persist(column21);
		HibernateEngine.persist(column31);
		HibernateEngine.persist(column41);
		
		// persiste as colunas pra tabela 2
		HibernateEngine.persist(column12);
		HibernateEngine.persist(column22);
		HibernateEngine.persist(column32);
		HibernateEngine.persist(column42);
		
		// persiste as colunas pra tabela 3
		HibernateEngine.persist(column13);
		HibernateEngine.persist(column23);
		HibernateEngine.persist(column33);
		HibernateEngine.persist(column43);
	}
	
	private void createJarPack() throws Exception {
		DynamicFactory.createJarPack();
		
		File jarFile = new File(GaiaTestUtils.GAIA_TEST_SERVICE_DIR + File.separator + DynamicUtils.DYNAMIC_ENTITY_JAR_NAME);
		assertEquals(true, jarFile.exists());
		
		JarInputStream jarIn = new JarInputStream(new FileInputStream(jarFile));
		JarEntry entry = null;
		int count = 0;
		while ((entry = jarIn.getNextJarEntry()) != null) {
			assertNotNull(entry);
			assertNotNull(entry.getName());
			count++;
		}
		
		jarIn.close();
		
		assertEquals(10, count);
	}
	
	private void createDBDynamicEntitys() throws Exception {
		DynamicFactory.loadDynamicEntity(SysEntity.class, false);
		
		String nLegalLegal = DynamicUtils.DYNAMIC_ENTITY_PACKAGE+".LegalLegal";
		String nMundoLegal = DynamicUtils.DYNAMIC_ENTITY_PACKAGE+".MundoLegal";
		String nShowLegal = DynamicUtils.DYNAMIC_ENTITY_PACKAGE+".ShowLegal";
		
		// add values
		SysObject objLegalLegal = EntityFactory.newEntityInstance(nLegalLegal);
		SysObject objMundoLegal = EntityFactory.newEntityInstance(nMundoLegal);
		SysObject objShowLegal = EntityFactory.newEntityInstance(nShowLegal);
		
		EntityWrapper<SysObject> entityLegalLegal = new EntityWrapper<SysObject>(objLegalLegal);
		EntityWrapper<SysObject> entityMundoLegal = new EntityWrapper<SysObject>(objMundoLegal);
		EntityWrapper<SysObject> entityShowLegal = new EntityWrapper<SysObject>(objShowLegal);
		
		entityLegalLegal.setValue("col11", true);
		entityLegalLegal.setValue("col21", "bacana bacana");
		entityLegalLegal.setValue("col31", new GregorianCalendar());
		entityLegalLegal.setValue("col41", new BigDecimal(9929292));

		entityMundoLegal.setValue("col12", true);
		entityMundoLegal.setValue("col22", new Long(1143));
		entityMundoLegal.setValue("col32", new Time(System.currentTimeMillis()));
		entityMundoLegal.setValue("col42", new BigDecimal(9929292));

		entityShowLegal.setValue("col13", true);
		entityShowLegal.setValue("col23", "bacana bacana");
		entityShowLegal.setValue("col33", objMundoLegal);
		entityShowLegal.setValue("col43", DynamicTable.class);
		
		HibernateEngine.persist(objLegalLegal);
		HibernateEngine.persist(objMundoLegal);
		HibernateEngine.persist(objShowLegal);
		
		// get values
		Class<?> clazzLegalLegal = Thread.currentThread().getContextClassLoader().loadClass(nLegalLegal);
		Class<?> clazzMundoLegal = Thread.currentThread().getContextClassLoader().loadClass(nMundoLegal);
		Class<?> clazzShowLegal = Thread.currentThread().getContextClassLoader().loadClass(nShowLegal);
		
		List<?>  listLegalLegal = HibernateEngine.load(clazzLegalLegal);
		List<?>  listMundoLegal = HibernateEngine.load(clazzMundoLegal);
		List<?>  listShowLegal = HibernateEngine.load(clazzShowLegal);
		
		assertNotNull(listLegalLegal);
		assertNotNull(listMundoLegal);
		assertNotNull(listShowLegal);
		
		assertEquals(1, listLegalLegal.size());
		assertEquals(1, listMundoLegal.size());
		assertEquals(1, listShowLegal.size());
		
		assertNotNull(listLegalLegal.get(0));
		assertNotNull(listMundoLegal.get(0));
		assertNotNull(listShowLegal.get(0));
		
		EntityWrapper<?> ew = new EntityWrapper<Object>(listShowLegal.get(0));
		assertEquals(true, ew.getValue("col33").equals(listMundoLegal.get(0)));
		assertEquals(DynamicTable.class, ew.getValue("col43"));
	}
}
