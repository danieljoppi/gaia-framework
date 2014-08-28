package net.sf.gaia.entity;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Entidade do Usuário.
 * 
 * @author daniel.joppi
 *
 */
@Entity(name="SYSTB_User")
@Table(name="SYSTB_User")
public class SysUser extends SysSecurityEntry {

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SysUser.class);
	
	/**
	 * Nome completo do usuário.
	 */
	private String fullName;
	
	/**
	 * Senha cifrada do usuário.
	 */
	private String password;
	
	/**
	 * Data de nascimento.
	 */
	private GregorianCalendar birthDay;
	
	/**
	 * Número do telefone da empresa.
	 */
	private String phoneCompanyNumber;
	
	/**
	 * Número do telefone de casa.
	 */
	private String phoneHomeNumber;
	
	/**
	 * Número do celular.
	 */
	private String mobileNumber;
	
	/**
	 * Outro número de telefone.
	 */
	private String otherPhoneNumber;
	
	/**
	 * Email da empresa.
	 */
	private String emailCompany;
	
	/**
	 * Email pessoal.
	 */
	private String emailHome;
		
	/**
	 * Grupo do usuário.
	 */
	private SysGroup group;
	
	/**
	 * Papéis do usuário.
	 */
	private Set<SysRole> roles;
	
	public SysUser() {
		
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public GregorianCalendar getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(GregorianCalendar birthDay) {
		this.birthDay = birthDay;
	}

	public String getPhoneCompanyNumber() {
		return phoneCompanyNumber;
	}

	public void setPhoneCompanyNumber(String phoneCompanyNumber) {
		this.phoneCompanyNumber = phoneCompanyNumber;
	}

	public String getPhoneHomeNumber() {
		return phoneHomeNumber;
	}

	public void setPhoneHomeNumber(String phoneHomeNumber) {
		this.phoneHomeNumber = phoneHomeNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getOtherPhoneNumber() {
		return otherPhoneNumber;
	}

	public void setOtherPhoneNumber(String otherPhoneNumber) {
		this.otherPhoneNumber = otherPhoneNumber;
	}

	public String getEmailCompany() {
		return emailCompany;
	}

	public void setEmailCompany(String emailCompany) {
		this.emailCompany = emailCompany;
	}

	public String getEmailHome() {
		return emailHome;
	}

	public void setEmailHome(String emailHome) {
		this.emailHome = emailHome;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public SysGroup getGroup() {
		return group;
	}

	public void setGroup(SysGroup group) {
		if (getGroup() != null) {
			getGroup().removeUser(this);
		}
		this.group = group;
		this.group.addUser(this);
	}
	
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, targetEntity = SysRole.class, mappedBy = "users")
	public Set<SysRole> getRoles() {
		if (roles == null) {
			roles = new HashSet<SysRole>();
		}
		return roles;
	}
	
	public void setRoles(Set<SysRole> roles) {
		this.roles = this.safeSetCollection(roles);
	}

	public void addRole(SysRole role) {
		if (role != null) {
			this.getRoles().add(role);
			role.getUsers().add(this);
		}
	}

	public void removeRole(SysRole role) {
		if (role != null) {
			this.getRoles().remove(role);
			role.getUsers().remove(this);
		}
	}
}
