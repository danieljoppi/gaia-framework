package net.sf.gaia.persistence.schema;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.AssertionFailure;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.util.StringHelper;

/**
 * 
 * @author daniel.joppi
 *
 */
public class SafeNamingStrategy implements NamingStrategy {

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SafeNamingStrategy.class);

	public static final SafeNamingStrategy INSTANCE = new SafeNamingStrategy();

	/**
	 * Return a table name for an entity class
	 * 
	 * @param className the fully-qualified class name
	 * @return a table name
	 */
	public String classToTableName(String className) {
		return safeName(StringHelper.unqualify(className));
	}

	/**
	 * Return a column name for a property path expression
	 * 
	 * @param propertyName a property path
	 * @return a column name
	 */
	public String propertyToColumnName(String propertyName) {
		return safeName(StringHelper.unqualify(propertyName));
	}

	/**
	 * Alter the table name given in the mapping document
	 * 
	 * @param tableName a table name
	 * @return a table name
	 */
	public String tableName(String tableName) {
		return safeName(StringHelper.unqualify(tableName));
	}

	/**
	 * Alter the column name given in the mapping document
	 * 
	 * @param columnName a column name
	 * @return a column name
	 */
	public String columnName(String columnName) {
		return safeName(StringHelper.unqualify(columnName));
	}

	// FIXME Isso não deve ser static! Verificar como obter o NamingStrategy a partir do Configuration
	private static Map<String, String> oldTableMap = new HashMap<String, String>();

	public static Map<String, String> getOldTableMap() {
		return oldTableMap;
	}

	public static String getOldTableName(String newTableName) {
		return oldTableMap.get(newTableName);
	}

	/**
	 * Return a collection table name ie an association having a join table
	 * 
	 * @param ownerEntity
	 * @param ownerEntityTable owner side table name
	 * @param associatedEntity
	 * @param associatedEntityTable reverse side table name if any
	 * @param propertyName collection role
	 */
	public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity, String associatedEntityTable, String propertyName) {
		String unsafeName = new StringBuilder(ownerEntityTable).append("_").append(
				associatedEntityTable != null ? associatedEntityTable : StringHelper.unqualify(propertyName)).toString();
		String safeName = tableName(new StringBuilder(ownerEntityTable).append("_").append(StringHelper.unqualify(propertyName)).toString());

		if (!safeName.equals(unsafeName))
			oldTableMap.put(safeName, unsafeName);

		return safeName;
	}

	/**
	 * Return the join key column name ie a FK column used in a JOINED strategy
	 * or for a secondary table
	 * 
	 * @param joinedColumn joined column name (logical one) used to join with
	 * @param joinedTable joined table name (ie the referenced table) used to join with
	 */
	public String joinKeyColumnName(String joinedColumn, String joinedTable) {
		return columnName(joinedColumn);
	}

	/**
	 * Return the foreign key column name for the given parameters
	 * 
	 * @param propertyName the property name involved
	 * @param propertyEntityName
	 * @param propertyTableName the property table name involved (logical one)
	 * @param referencedColumnName the referenced column name involved (logical one)
	 */
	public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName, String referencedColumnName) {
		String header = propertyName != null ? StringHelper.unqualify(propertyName) : propertyTableName;
		if (header == null)
			throw new AssertionFailure("NamingStrategy not properly filled");
		return columnName(header + "_" + referencedColumnName);
	}

	/**
	 * Return the logical column name used to refer to a column in the metadata
	 * (like index, unique constraints etc) A full bijection is required between
	 * logicalNames and physical ones logicalName have to be case insersitively
	 * unique for a given table
	 * 
	 * @param columnName given column name if any
	 * @param propertyName property name of this column
	 */
	public String logicalColumnName(String columnName, String propertyName) {
		return StringHelper.isNotEmpty(columnName) ? columnName : StringHelper.unqualify(propertyName);
	}

	/**
	 * Returns the logical collection table name used to refer to a table in the
	 * mapping metadata
	 * 
	 * @param tableName the metadata explicit name
	 * @param ownerEntityTable owner table entity table name (logical one)
	 * @param associatedEntityTable reverse side table name if any (logical one)
	 * @param propertyName collection role
	 */
	public String logicalCollectionTableName(String tableName, String ownerEntityTable, String associatedEntityTable, String propertyName) {
		if (tableName != null) {
			return tableName;
		} else {
			// use of a stringbuffer to workaround a JDK bug
			return safeName(new StringBuffer(ownerEntityTable).append("_").append(associatedEntityTable != null ? associatedEntityTable : StringHelper.unqualify(propertyName)).toString());
		}
	}

	/**
	 * Returns the logical foreign key column name used to refer to this column
	 * in the mapping metadata
	 * 
	 * @param columnName given column name in the metadata if any
	 * @param propertyName property name
	 * @param referencedColumn referenced column name (logical one) in the join
	 */
	public String logicalCollectionColumnName(String columnName, String propertyName, String referencedColumn) {
		return StringHelper.isNotEmpty(columnName) ? columnName : safeName(StringHelper.unqualify(propertyName) + "_" + referencedColumn);
	}

	private static final Set<String> keywordSet = new HashSet<String>();
	static {
		keywordSet.add("date");
		keywordSet.add("time");
		keywordSet.add("level");
		keywordSet.add("order");
		keywordSet.add("key");
		keywordSet.add("asc");
		keywordSet.add("read");
		keywordSet.add("join");
		keywordSet.add("left");
		keywordSet.add("inner");
		keywordSet.add("outer");
		keywordSet.add("full");
		keywordSet.add("outer");
		keywordSet.add("select");
		keywordSet.add("update");
		keywordSet.add("insert");
		keywordSet.add("option");
		keywordSet.add("trigger");
		keywordSet.add("unique");
		// Enable this just for oracle
		// keywordSet.add("scale");
		// keywordSet.add("size");
	}

	protected Set<String> getKeywordSet() {
		return keywordSet;
	}

	private final static int MAX_TABLE_NAME = 54;

	protected int getMaxTableName() {
		return MAX_TABLE_NAME;
	}

	private final static String VOWELS = "aeiou_";

	public final String safeName(String code) {
		code = code.replace('.', '_');
		if (code.startsWith("_"))
			code = "n" + code;
		else if (getKeywordSet().contains(code.toLowerCase()))
			code = "n" + code;

		// Remove vowels
		if (code.length() > getMaxTableName())
			code = cleanVowels(code);

		// Compress
		if (code.length() > getMaxTableName())
			code = compress(code);

		return code;
	}

	private static String cleanVowels(String code) {
		StringBuffer bufferCode = new StringBuffer(30);
		int hash = 0x4242;
		char c[] = code.toCharArray();
		for (int i = 0; i < c.length; i++) {
			int vPos = VOWELS.indexOf(c[i]);
			if (i > 0 && vPos != -1 && (i > 1 || c[i] != '_')) {
				hash ^= ((hash << vPos) ^ (hash >> i)) + c[i];
			} else {
				bufferCode.append(c[i]);
			}
		}
		hash = hash & 0xffff;
		return bufferCode.append(Integer.toHexString(hash)).toString();
	}

	private static String compress(String code) {
		int hash = 0x42424242;
		char c[] = code.substring(20).toCharArray();
		for (int i = 0; i < c.length; i++) {
			hash ^= ((hash << 5) + ~hash) ^ (c[i] << i) + c[i];
		}
		String num = Integer.toHexString(hash & 0xffffffff);
		return code.substring(0, 20) + num;
	}
}