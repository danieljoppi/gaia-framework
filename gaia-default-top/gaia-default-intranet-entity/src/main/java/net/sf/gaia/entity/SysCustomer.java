package net.sf.gaia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entidade de Cliente.
 * 
 * @author daniel.joppi
 *
 */
@Entity(name="SYSTB_Customer")
@Table(name="SYSTB_Customer")
public class SysCustomer extends SysObject {

	/**
	 * Nome do cliente.
	 */
	private String name;

	/**
	 * Código do cliente.
	 */
	private String code;
	
	/**
	 * Descrição do cliente.
	 */
	private String description;
	
	public SysCustomer() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 5, unique = true)
	public String getCode() {
		if (code != null) {
			code = code.toUpperCase();
		}
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(length = 1024)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
