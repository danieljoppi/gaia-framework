package net.sf.gaia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entidade dos Escritórios da Empresa.
 * 
 * @author daniel.joppi
 *
 */
@Entity(name="SYSTB_CompanyOffice")
@Table(name="SYSTB_CompanyOffice")
public class SysCompanyOffice extends SysObject {

	private String code;
	
	private String location;
	
	private String phoneNumber;
	
	private String address;
	
	private String description;
	
	public SysCompanyOffice() {
		
	}

	@Column(length = 5, nullable = false, updatable = false)
	public String getCode() {
		if (code != null) {
			code = code.toUpperCase();
		}
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(nullable = false)
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(length = 1024)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
