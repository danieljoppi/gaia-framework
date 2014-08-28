package net.sf.gaia.framework.dynamic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Table;

import net.sf.gaia.engine.EntityFactory;
import net.sf.gaia.engine.HibernateEngine;
import net.sf.gaia.entity.SysEntity;
import net.sf.gaia.framework.entity.DynamicColumn;
import net.sf.gaia.framework.entity.DynamicTable;
import net.sf.gaia.persistence.DatabaseSettings;
import net.sf.gaia.util.GaiaUtils;
import net.sf.gaia.util.SafeUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cojen.classfile.ClassFile;
import org.cojen.classfile.CodeBuilder;
import org.cojen.classfile.LocalVariable;
import org.cojen.classfile.MethodInfo;
import org.cojen.classfile.Modifiers;
import org.cojen.classfile.TypeDesc;
import org.cojen.classfile.attribute.Annotation;

/**
 * Classe responsável por gerenciar a criação e o carregamento das classes dinâmicas.
 * 
 * @author daniel.joppi
 * @since 11/01/2011
 * 
 */
public abstract class DynamicFactory {

	private static final Log logger = LogFactory.getLog(DynamicFactory.class);


	/**
	 * Método responsável por recarregar as entidades do sistema incluindo as
	 * entidades dinâmicas criadas no pacote "gaia-dyn-entity-2.0.jar".
	 * Sempre recria o pacote "gaia-dyn-entity-2.0.jar", mesmo que já exista.
	 * 
	 * @param c classe base para carregar as entidades baseadas nela.
	 * 
	 * @author daniel.joppi
	 * @since 19/01/2011
	 */
	public final static <T> void loadDynamicEntity(Class<T> c) {
		DynamicFactory.loadDynamicEntity(c, true);
	}

	/**
	 * Método responsável por recarregar as entidades do sistema incluindo as
	 * entidades dinâmicas criadas no pacote "gaia-dyn-entity-2.0.jar".
	 * 
	 * @param c classe base para carregar as entidades baseadas nela.
	 * @param createPack
	 *            se true cria o pacote "gaia-dyn-entity-2.0.jar". Caso false
	 *            verifica se o arquivo existe e o utiliza, senão o cria.
	 * 
	 * @author daniel.joppi
	 * @since 19/01/2011
	 */
	public final static <T> void loadDynamicEntity(Class<T> c, boolean createPack) {
		if (createPack) {
			DynamicFactory.createJarPack();
		}

		long l = System.currentTimeMillis();
		logger.info("Loading system classes and dynamic classes ...");
				
		Set<Class<SysEntity>> classes = EntityFactory.loadEntitys(SysEntity.class);
		logger.info("Find "+classes.size()+" entitys in "+GaiaUtils.getGaiaServicePath()+" ... "+(System.currentTimeMillis()-l)+" ms");
		
		l = System.currentTimeMillis();
		EntityManagerFactory emf = HibernateEngine.recreateEntityManagerFactory(classes);
		DatabaseSettings.setEntityManagerFactory(emf);
		logger.info("Finish deploy entitys ...  "+(System.currentTimeMillis()-l)+" ms");
	}
	
	/**
	 * Método responsável por criar o jar que contêm os arquivos class
	 * compilados para cada tabela denâmica.
	 * 
	 * @author daniel.joppi
	 * @since 11/01/2011
	 */
	public final static void createJarPack() {
		List<DynamicTable> tables = HibernateEngine.load(DynamicTable.class);
		
		String pack = DynamicUtils.DYNAMIC_ENTITY_PACKAGE;
		String gaiaBuildPath = GaiaUtils.getGaiaTempPath() + File.separator + "classes" + File.separator;
		String path = gaiaBuildPath + GaiaUtils.dot2Slash(pack) + File.separator;
		
		File dir = new File(path);
		if (dir.exists()) {
			try {
				FileUtils.deleteDirectory(dir);
			} catch (IOException e) { }
		}
		dir.mkdirs();
		
		for (int i = 0; i < tables.size(); i++) {
			DynamicTable table = tables.get(i);

			String clazzTable = pack + "." + table.getTableName();
			logger.debug("Creating class " + clazzTable + " ...");
			ClassFile cf = createClassFile(table);
			
			// cria o arquivo class
			try {
				
				File file = new File(path + table.getTableName() + ".class");
				file.createNewFile();
				FileOutputStream out = new FileOutputStream(file);
				
				cf.writeTo(out);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// cria services
		try {
			String services = gaiaBuildPath + "META-INF" + File.separator + "services" + File.separator;
			
			File servicesDir = new File(services);
			if (!servicesDir.exists()) {
				servicesDir.mkdirs();
			}
			
			File file = new File(services + "net.sf.gaia.GaiaEntity");
			FileOutputStream out = new FileOutputStream(file);
			InputStream in = IOUtils.toInputStream(DynamicUtils.DYNAMIC_ENTITY_PACKAGE);
			IOUtils.copy(in, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// cria o jar
		String jarName = DynamicUtils.DYNAMIC_ENTITY_JAR_NAME;
		GaiaUtils.createJarFile(jarName);
	}

	/**
	 * Método responsável por criar o método set.
	 * 
	 * @author daniel.joppi
	 * @since 11/01/2011
	 */
	private static MethodInfo createSetter(ClassFile cf, String fieldName, Class<?> clazzField) {
		String methodName = SafeUtils.safeSetterName(fieldName, clazzField);
		TypeDesc typeField = DynamicUtils.typeFromClass(clazzField);

		TypeDesc[] params = new TypeDesc[] { typeField };
        MethodInfo mi = cf.addMethod(Modifiers.PUBLIC, methodName, null, params);
        
        CodeBuilder b = new CodeBuilder(mi);
        
        LocalVariable field = b.getParameter(0);
        b.loadThis();
        b.loadLocal(field);
        b.storeField(fieldName, typeField);
        
        b.returnVoid();
        
        return mi;
	}

	/**
	 * Método responsável por criar o método get.
	 * 
	 * @author daniel.joppi
	 * @since 11/01/2011
	 */
	private static MethodInfo createGetter(ClassFile cf, String fieldName, Class<?> clazzField) {
		String methodName = SafeUtils.safeGetterName(fieldName, clazzField);
		TypeDesc typeField = DynamicUtils.typeFromClass(clazzField);

        MethodInfo mi = cf.addMethod(Modifiers.PUBLIC, methodName, typeField, null);
		CodeBuilder b = new CodeBuilder(mi);
        
		b.loadThis();
		b.loadField(fieldName, typeField);
        b.returnValue(typeField);
        
        return mi;
	}
	
	/**
     * Creates a ClassFile which defines a simple interactive HelloWorld class.
     *
     * @param className name given to class
     */
    private static ClassFile createClassFile(DynamicTable table) {		
		String className = DynamicUtils.DYNAMIC_ENTITY_PACKAGE + "." + table.getTableName();
		
    	// Create a ClassFile with the super class of Object.
    	ClassFile cf = new ClassFile(className, table.getSuperClass());
    	cf.setTarget("1.5");
        
        // Default constructor works only if super class has an accessible no-arg constructor.
        cf.addDefaultConstructor();
        for (int i=0; i<table.getColumns().size(); i++) {
        	DynamicColumn column = table.getColumns().get(i);
        	
        	String fieldName = column.getColumnName();
        	
        	cf.addField(Modifiers.PRIVATE, fieldName, DynamicUtils.typeFromClass(column.getType()));
        	
            // Add the get and set method, and construct a CodeBuilder for defining the bytecode.
            createSetter(cf, fieldName, column.getType());
        	MethodInfo mig = createGetter(cf, fieldName, column.getType());
        	column.buildAnnotation(mig);
        }
        
        // Add annotation class
        String safeName = DynamicUtils.safeDynamicTableName(table.getTableName(), table.getPrefix());
        Annotation anE = cf.addRuntimeVisibleAnnotation(DynamicUtils.typeFromClass(Entity.class));
        anE.putMemberValue("name", safeName);
        Annotation anT = cf.addRuntimeVisibleAnnotation(DynamicUtils.typeFromClass(Table.class));
        anT.putMemberValue("name", safeName);
        
        return cf;
    }
}
