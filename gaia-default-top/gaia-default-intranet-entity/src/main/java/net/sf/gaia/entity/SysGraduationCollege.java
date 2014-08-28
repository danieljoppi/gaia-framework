package net.sf.gaia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entidade de Graduações.
 * 
 * @author daniel.joppi
 *
 */
@Entity(name="SYSTB_GraduationCollege")
@Table(name="SYSTB_GraduationCollege")
public class SysGraduationCollege extends SysObject {

	/**
	 * Nome da graduação.
	 */
	private String name;
	
	/**
	 * Descrição da graduação.
	 */
	private String description;
	
	public SysGraduationCollege() {
		
	}

	@Column(length = 255, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 1024)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
