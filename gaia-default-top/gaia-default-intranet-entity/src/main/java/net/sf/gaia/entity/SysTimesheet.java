package net.sf.gaia.entity;

import java.util.GregorianCalendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.sf.gaia.util.SafeUtils;

/**
 * Entidade de Apontamento de Hora.
 * 
 * @author daniel.joppi
 *
 */
@Entity(name="SYSTB_Timesheet")
@Table(name="SYSTB_Timesheet")
public class SysTimesheet extends SysObject {

	/**
	 * Projeto.
	 */
	private SysProject project;
	
	/**
	 * Funcionário / usuário.
	 */
	private SysEmployee employee;
	
	/**
	 * Atividade trabalhada no projeto.
	 */
	private SysActivityProject activityProject;
	
	/**
	 * Horas trabalhadas.
	 */
	private Integer hours;
	
	/**
	 * Descrição do trabalho.
	 */
	private String description;
	
	/**
	 * Data de criação.
	 */
	private GregorianCalendar createDate;
	
	/**
	 * Data de atualização.
	 */
	private GregorianCalendar updateDate;
	
	public SysTimesheet() {
		
	}

	@JoinColumn(nullable = false)
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, targetEntity = SysProject.class)
	public SysProject getProject() {
		return project;
	}

	public void setProject(SysProject project) {
		this.project = project;
	}

	@JoinColumn(nullable = false, updatable = false)
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, targetEntity = SysEmployee.class)
	public SysEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(SysEmployee employee) {
		this.employee = employee;
	}

	@JoinColumn(nullable = false)
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, targetEntity = SysActivityProject.class)
	public SysActivityProject getActivityProject() {
		return activityProject;
	}

	public void setActivityProject(SysActivityProject activityProject) {
		this.activityProject = activityProject;
	}

	@Column(nullable = false)
	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	@Column(length = 255)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		description = SafeUtils.safeString(description);
		this.description = (description != null) ? description.trim() : null;
	}

	@Column(nullable = false)
	public GregorianCalendar getCreateDate() {
		if (createDate ==  null) {
			createDate = this.getUpdateDate();
		}
		return createDate;
	}

	public void setCreateDate(GregorianCalendar createDate) {
		this.createDate = createDate;
	}

	@Column(nullable = false)
	public GregorianCalendar getUpdateDate() {
		if (updateDate == null) {
			updateDate = new GregorianCalendar();
			updateDate.setTimeInMillis(System.currentTimeMillis());
		}
		return updateDate;
	}

	public void setUpdateDate(GregorianCalendar updateDate) {
		this.updateDate = updateDate;
	}
}
