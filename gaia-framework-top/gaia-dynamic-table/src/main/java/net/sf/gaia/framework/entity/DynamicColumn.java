package net.sf.gaia.framework.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.cojen.classfile.MethodInfo;

import net.sf.gaia.entity.SysObject;

/**
 * Entidade que armazena os dados referentes a uma coluna de uma tabela dinâmica.
 * 
 * @author daniel.joppi
 * 
 */
@Entity(name="SYSTB_DynamicColumn")
@Table(name="SYSTB_DynamicColumn")
public abstract class DynamicColumn extends SysObject {

	/**
	 * Referência para a tabela dinâmica
	 */
	private DynamicTable table;
	
	/**
	 * Nome da coluna.
	 */
	private String columnName;
	
	/**
	 * Nome da coluna visualizada pelo usuário.
	 */
	private String displayName;
	
	/**
	 * Tipo da coluna.
	 */
	private Class<?> type;
	
	/**
	 * Ordem de visualização da coluna.
	 */
	private Integer order;
	
	/**
	 * Se a coluna é obrigatória.
	 */
	private Boolean mandatory;
	
	public DynamicColumn() {
		super();
	}
	
	protected DynamicColumn(Class<?> type) {
		super();
		
		this.type = type;
	}
	
	public abstract MethodInfo buildAnnotation(MethodInfo mi);

	@ManyToOne(fetch = FetchType.LAZY)
	public DynamicTable getTable() {
		return this.table;
	}

	public void setTable(DynamicTable table) {
		this.table = table;
	}

	@Column(nullable = false)
	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDisplayName() {
		if (this.displayName == null) {
			this.displayName = this.columnName;
		}
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Column(nullable = false)
	public Class<?> getType() {
		return this.type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public Integer getOrder() {
		if (this.order == null) {
			this.order = 0;
		}
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Boolean isMandatory() {
		if (this.mandatory == null) {
			this.mandatory = true;
		}
		return this.mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	} 
}
