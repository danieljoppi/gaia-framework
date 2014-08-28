package net.sf.gaia.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Entidade base para os conjuntos de usuários.
 * 
 * @author daniel.joppi
 *
 */
@Entity(name="SYSTB_Set")
@Table(name="SYSTB_Set")
public abstract class SysSet extends SysSecurityEntry {

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SysSet.class);

	/**
	 * Nome do conjunto.
	 */
	private String name;
	
	public SysSet() {
		
	}

	@Transient
	public abstract Set<SysUser> getUsers();
	
	public abstract void addUser(SysUser user);

	public abstract void removeUser(SysUser user);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
