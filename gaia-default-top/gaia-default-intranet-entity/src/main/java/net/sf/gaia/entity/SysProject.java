package net.sf.gaia.entity;

import java.text.DecimalFormat;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entidade de Projeto.
 * 
 * @author daniel.joppi
 *
 */
@Entity(name="SYSTB_Project")
@Table(name="SYSTB_Project")
public abstract class SysProject extends SysObject {

	/**
	 * Cliente.
	 */
	private SysCustomer customer;
	
	/**
	 * Coordenador do projeto.
	 */
	private SysUser coordinator;
	
	/**
	 * Número do projeto.
	 */
	private String codeNumber;
	
	/**
	 * Código do trabalho.
	 */
	private String codeWork;
	
	/**
	 * Data de inicio.
	 */
	private GregorianCalendar startDate;
	
	/**
	 * Data de fim.
	 */
	private GregorianCalendar finishDate;
	
	/**
	 * Se o projeto está aberto.
	 */
	private Boolean active;
	
	/**
	 * Descrição do projeto.
	 */
	private String description;
	
	/**
	 * Nome fantasia.
	 */
	private String assumedName;
	
	/**
	 * Lista de atividades do projeto.
	 */
	private Set<SysActivityProject> activitys;
	
	/**
	 * Lista de entidades que trabalham no projeto.
	 */
	private Set<SysEntrysProject> entrys;
	
	public SysProject() {
		
	}
	
	@JoinColumn(nullable = false, updatable = false)
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, targetEntity = SysCustomer.class)
	public SysCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(SysCustomer customer) {
		this.customer = customer;
	}

	@JoinColumn(nullable = false)
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, targetEntity = SysUser.class)
	public SysUser getCoordinator() {
		return coordinator;
	}

	public void setCoordinator(SysUser coordinator) {
		this.coordinator = coordinator;
	}

	@Transient
	public abstract long getGenCodeNumber();

	public abstract void setGenCodeNumber(long genCodeNumber);

	@Column(length = 5, unique = true, nullable = false, updatable = false)
	public String getCodeNumber() {
		if (codeNumber == null) {			
			DecimalFormat format = new DecimalFormat("00000");
			codeNumber = format.format(this.getGenCodeNumber());
		}
		return codeNumber;
	}

	public void setCodeNumber(String codeNumber) {
		this.codeNumber = codeNumber;
	}

	@Column(length = 5, nullable = false, updatable = false)
	public String getCodeWork() {
		if (codeWork != null) {
			codeWork = codeWork.toUpperCase();
		}
		return codeWork;
	}

	public void setCodeWork(String codeWork) {
		this.codeWork = codeWork;
	}
	
	public GregorianCalendar getStartDate() {
		return startDate;
	}

	public void setStartDate(GregorianCalendar startDate) {
		this.startDate = startDate;
	}

	public GregorianCalendar getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(GregorianCalendar finishDate) {
		this.finishDate = finishDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Column(length=1024)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAssumedName() {
		return assumedName;
	}

	public void setAssumedName(String assumedName) {
		this.assumedName = assumedName;
	}

	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, targetEntity = SysActivityProject.class, mappedBy = "project")
	public Set<SysActivityProject> getActivitys() {
		if (activitys == null) {
			activitys = new HashSet<SysActivityProject>();
		}
		return activitys;
	}

	public void setActivitys(Set<SysActivityProject> activitys) {
		this.activitys = this.safeSetCollection(activitys);
	}

	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, targetEntity = SysEntrysProject.class, mappedBy = "project")
	public Set<SysEntrysProject> getEntrys() {
		if (entrys == null) {
			entrys = new HashSet<SysEntrysProject>();
		}
		return entrys;
	}

	public void setEntrys(Set<SysEntrysProject> entrys) {
		this.entrys = this.safeSetCollection(entrys);
	}

	/**
	 * Método que retorna a sigla do projeto. Esta é a concatenação de:
	 * 	this.getCodeNumber() + "-" + this.getCustomer().getCode() + "-" + this.getCodeWork().
	 * Se algum dos métodos retorna null, a sigla retorna null.
	 * 
	 * @return sigla do projeto.
	 * 
	 * @author daniel.joppi
	 * @since 12/04/2011
	 */
	@Transient
	public String getSigla() {
		if (this.getCodeNumber() == null || this.getCustomer() == null || this.getCustomer().getCode() == null || this.getCodeWork() == null) {
			return null;
		}
		return this.getCodeNumber() + "-" + this.getCustomer().getCode() + "-" + this.getCodeWork();
	}
}
