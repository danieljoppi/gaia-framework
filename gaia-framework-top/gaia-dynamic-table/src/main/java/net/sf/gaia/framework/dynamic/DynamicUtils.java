package net.sf.gaia.framework.dynamic;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Calendar;

import net.sf.gaia.entity.SysObject;
import net.sf.gaia.framework.entity.DynamicColumn;
import net.sf.gaia.framework.entity.DynamicColumnBoolean;
import net.sf.gaia.framework.entity.DynamicColumnClass;
import net.sf.gaia.framework.entity.DynamicColumnDate;
import net.sf.gaia.framework.entity.DynamicColumnDecimal;
import net.sf.gaia.framework.entity.DynamicColumnEntity;
import net.sf.gaia.framework.entity.DynamicColumnNumber;
import net.sf.gaia.framework.entity.DynamicColumnText;
import net.sf.gaia.framework.entity.DynamicColumnTime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cojen.classfile.TypeDesc;

/**
 * Classe utilitária para tabelas dinâmicas.
 * 
 * @author daniel.joppi
 *
 */
public abstract class DynamicUtils {

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DynamicUtils.class);
	
	public static final String DYNAMIC_ENTITY_PACKAGE = "net.sf.gaia.dyn.entity";
	
	public static final String DYNAMIC_ENTITY_JAR_NAME = "gaia-dyn-entity-2.0.jar";
	
	/**
	 * Método utilitário para verificar um nome seguro para
	 * uma determinada tabela dinâmica no banco de dados.
	 * 
	 * @param tableName nome da tabela.
	 * @return "DYNTB_" + tableName.
	 * 
	 * @author daniel.joppi
	 * @since 11/01/2011
	 */
	final static String safeDynamicTableName(String tableName) {
		return safeDynamicTableName(tableName, "DYNTB");
	}
	
	/**
	 * Método utilitário para verificar um nome seguro para
	 * uma determinada tabela dinâmica no banco de dados.
	 * 
	 * @param tableName nome da tabela.
	 * @param prefix para o nome da tabela.
	 * @return "DYNTB_" + tableName.
	 * 
	 * @author daniel.joppi
	 * @since 11/01/2011
	 */
	final static String safeDynamicTableName(String tableName, String prefix) {
		if (prefix == null) {
			prefix = "DYNTB";
		} else {
			prefix = prefix.toUpperCase();
		}
		
		if (tableName.startsWith(prefix + "_")) {
			return tableName;
		} else {
			return prefix + "_" + tableName;
		}
	}
	
	final static boolean isDynamicClassName(String clazzName) {
		return clazzName != null && (clazzName.startsWith(DYNAMIC_ENTITY_PACKAGE + "."));
	}
	
	public static TypeDesc typeFromClass(Class<?> clazz) {
		if (clazz == null){
			return TypeDesc.VOID;
		} else {
			return TypeDesc.forClass(clazz.getName());
		}
	}
	
	public static DynamicColumn createDynamicColumn(Class<?> type) {
		if (Boolean.class.isAssignableFrom(type)) { 
			return new DynamicColumnBoolean();
        } else if (Class.class.isAssignableFrom(type)) {
        	return new DynamicColumnClass();
        } else if (Calendar.class.isAssignableFrom(type)) { 
        	return new DynamicColumnDate();
        } else if (Time.class.isAssignableFrom(type)) { 
        	return new DynamicColumnTime();
        } else if (BigDecimal.class.isAssignableFrom(type) || Double.class.isAssignableFrom(type) || Float.class.isAssignableFrom(type)) {
        	return new DynamicColumnDecimal();
        } else if (Long.class.isAssignableFrom(type) || Integer.class.isAssignableFrom(type)) { 
        	return new DynamicColumnNumber();
        } else if (String.class.isAssignableFrom(type)) {
        	return new DynamicColumnText();
        } else if (SysObject.class.isAssignableFrom(type)) { 
        	return new DynamicColumnEntity();
        }
		return null;
	}
}