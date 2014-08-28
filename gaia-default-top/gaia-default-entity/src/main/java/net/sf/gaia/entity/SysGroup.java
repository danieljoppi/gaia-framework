package net.sf.gaia.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Entidade que representa um Grupo de usuários.
 * 
 * @author daniel.joppi
 *
 */
@Entity(name="SYSTB_Group")
@Table(name="SYSTB_Group")
public class SysGroup extends SysSet {

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SysGroup.class);
	
	/**
	 * Conjunto de usuários.
	 */
	private Set<SysUser> users;
	
	/**
	 * Grupo superior.
	 */
	private SysGroup parent;
	
	public SysGroup() {
		
	}

	@Override
	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, targetEntity = SysUser.class, mappedBy = "group")
	public Set<SysUser> getUsers() {
		if (this.users == null) {
			this.users = new HashSet<SysUser>();
		}
		
		return this.users;
	}

	public void setUsers(Set<SysUser> users) {
		this.users = this.safeSetCollection(users);
	}

	public void addUser(SysUser user) {
		if (user != null) {
			if (user.getGroup() != null && !user.getGroup().equals(this)) {
				user.setGroup(this);
			}
			this.getUsers().add(user);
		}
	}

	public void removeUser(SysUser user) {
		if (user != null) {
			if (user.getGroup() != null && !user.getGroup().equals(this)) {
				user.setGroup(null);
			}
			this.getUsers().remove(user);
		}
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public SysGroup getParent() {
		return parent;
	}

	public void setParent(SysGroup parent) {
		this.parent = parent;
	}
}
