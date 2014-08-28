package net.sf.gaia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entidade de Atividade do Projeto.
 * 
 * @author daniel.joppi
 *
 */
@Entity(name="SYSTB_ActivityProject")
@Table(name="SYSTB_ActivityProject")
public class SysActivityProject extends SysObject {

	/**
	 * Projeto relacionado.
	 */
	private SysProject project;
	
	/**
	 * Nome da atividade.
	 */
	private String name;
	
	/**
	 * Total de horas da atividade.
	 */
	private Integer totalHours;
	
	/**
	 * Descrição da atividade.
	 */
	private String description;
	
	public SysActivityProject() {
		
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public SysProject getProject() {
		return project;
	}

	public void setProject(SysProject project) {
		this.project = project;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(Integer totalHours) {
		this.totalHours = totalHours;
	}

	@Column(length=1024)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
