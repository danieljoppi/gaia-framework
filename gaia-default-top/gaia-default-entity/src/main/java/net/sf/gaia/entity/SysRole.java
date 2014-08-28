package net.sf.gaia.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Entidade que representa um Papel de usuários.
 * 
 * @author daniel.joppi
 *
 */
@Entity(name="SYSTB_Role")
@Table(name="SYSTB_Role")
public class SysRole extends SysSet {

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SysRole.class);
	
	/**
	 * Conjunto de usuários.
	 */
	private Set<SysUser> users;

	public SysRole() {
	}

	@Override
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, targetEntity = SysUser.class)
	public Set<SysUser> getUsers() {
		if (users == null) {
			users = new HashSet<SysUser>();
		}
		return users;
	}

	public void setUsers(Set<SysUser> users) {
		this.users = this.safeSetCollection(users);
	}

	public void addUser(SysUser user) {
		if (user != null) {
			this.getUsers().add(user);
			user.getRoles().add(this);
		}
	}

	public void removeUser(SysUser user) {
		if (user != null) {
			this.getUsers().remove(user);
			user.getRoles().remove(this);
		}
	}

}
