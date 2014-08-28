package net.sf.gaia.framework.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.gaia.entity.SysObject;
import net.sf.gaia.framework.dynamic.DynamicUtils;
import net.sf.gaia.util.GaiaUtils;
import net.sf.gaia.util.SafeUtils;

/**
 * Entidade responsável por armazenar os dados referente 
 * a uma tabela dinâmica.
 * 
 * @author daniel.joppi
 * 
 */
@Entity(name="SYSTB_DynamicTable")
@Table(name="SYSTB_DynamicTable")
public class DynamicTable extends SysObject {

	/**
	 * Prefixo da tabela dinâmica.
	 */
	private String prefix;
	
	/**
	 * Nome da tabela dinâmica.
	 */
	private String tableName;
	
	/**
	 * Nome da tabela dinâmica visualizada pelo usuário.
	 */
	private String displayName;
	
	/**
	 * Super Class que a classe dinâmica irá extender.
	 */
	private Class<? extends SysObject> superClass;
	
	private String tableType;
	
	/**
	 * Lista de colunas da tabela dinâmica.
	 */
	private List<DynamicColumn> columns;
	
	public DynamicTable() {
		super();
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		if(prefix == null) {
			prefix = "DYNTB";
		}
		this.prefix = prefix;
	}

	@Column(unique = true, nullable = false)
	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDisplayName() {
		if(this.displayName == null) {
			this.displayName = this.tableName;
		}
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Class<? extends SysObject> getSuperClass() {
		if (this.superClass == null) {
			this.superClass = SysObject.class;
		}
		return this.superClass;
	}

	public void setSuperClass(Class<? extends SysObject> superClass) {
		this.superClass = superClass;
	}

	@Column(length = 96, nullable = false)
	public String getTableType() {
		if (SafeUtils.safeIsNull(this.tableType)) {
			tableType = DynamicUtils.DYNAMIC_ENTITY_PACKAGE+"."+this.getTableName();
		}
		
		return encodeSysType(tableType);
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	
	@Transient
	public String safeTableType() {
		String tableType = this.getTableType();
		
		return this.decodeSysType(tableType);
	}

	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, targetEntity = DynamicColumn.class, mappedBy = "table")
	public List<DynamicColumn> getColumns() {
		if (this.columns == null) {
			this.columns = new ArrayList<DynamicColumn>();
		}
		return this.columns;
	}

	public void setColumns(List<DynamicColumn> columns) {
		this.columns = columns;
		
		for (DynamicColumn column : columns) {
			if(column.getTable() == null || !column.getTable().equals(this)) {
				column.setTable(this);
			}
		}
	}

	public void addColumn(DynamicColumn column) {
		if (column != null) {
			if (column.getTable() != null && !column.getTable().equals(this)) {
				column.setTable(this);
			}

			getColumns().add(column);
		}
	}

	public void removeColumn(DynamicColumn column) {
		if (column != null) {
			if (column.getTable() != null) {
				column.setTable(null);
			}
			
			this.getColumns().remove(this);
		}
	}
}
