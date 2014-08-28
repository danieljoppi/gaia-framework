package net.sf.gaia.entity;

import java.text.DecimalFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entidade de Funcionário.
 * 
 * @author daniel.joppi
 *
 */
@Entity(name="SYSTB_Employee")
@Table(name="SYSTB_Employee")
public class SysEmployee extends SysUser {
	
	public static enum TypeContract {
		CLT (true, false, false), 
		PJ  (false, true, false), 
		MIXED (true, true, false), 
		STAG(true, false, true);
		
		private boolean clt;
		private boolean pj;
		private boolean stag;
		
		private TypeContract(boolean clt, boolean pj, boolean stag) {
			this.clt = clt;
			this.pj =pj;
			this.stag = stag;
		}

		public boolean isClt() {
			return clt;
		}

		public void setClt(boolean clt) {
			this.clt = clt;
		}

		public boolean isPj() {
			return pj;
		}

		public void setPj(boolean pj) {
			this.pj = pj;
		}

		public boolean isStag() {
			return stag;
		}

		public void setStag(boolean stag) {
			this.stag = stag;
		}
	}
	
	/**
	 * Graduação do funcionário.
	 */
	private SysGraduationCollege graduation;
	
	/**
	 * Mês da formação. 
	 */
	private Integer monthGraduation;
	
	/**
	 * Anda da formação.
	 */
	private Integer yearGraduation;
	
	/**
	 * Especialização do funcionário.
	 */
	private String specialization;
	
	/**
	 * Áreas de Concentração.
	 */
	private String areasConcentration;
	
	/**
	 * Escritório que o funcionário trabalha.
	 */
	private SysCompanyOffice office;
	
	/**
	 * Mês que iniciou na empresa.
	 */
	private Integer monthStartInCompany;
	
	/**
	 * Ano que iniciou na empresa.
	 */
	private Integer yearStartInCompany;
	
	/**
	 * Função do empregado na empresa.
	 */
	private SysEmployeePosition position;
	
	/**
	 * RG do funcionário.
	 */
	private String rg;
	
	/**
	 * CPF do funcionário.
	 */
	private String cpf;
	
	/**
	 * Tipo de contratação do funcionário (CLT / PJ)
	 */
	private TypeContract typeContract;
	
	public SysEmployee() {
		
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, targetEntity = SysGraduationCollege.class)
	public SysGraduationCollege getGraduation() {
		return graduation;
	}

	public void setGraduation(SysGraduationCollege graduation) {
		this.graduation = graduation;
	}

	public Integer getMonthGraduation() {
		return monthGraduation;
	}

	public void setMonthGraduation(Integer monthGraduation) {
		this.monthGraduation = monthGraduation;
	}

	public Integer getYearGraduation() {
		return yearGraduation;
	}

	public void setYearGraduation(Integer yearGraduation) {
		this.yearGraduation = yearGraduation;
	}

	@Column(length = 255)
	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getAreasConcentration() {
		return areasConcentration;
	}

	public void setAreasConcentration(String areasConcentration) {
		this.areasConcentration = areasConcentration;
	}

	@JoinColumn(nullable = false)
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, targetEntity = SysCompanyOffice.class)
	public SysCompanyOffice getOffice() {
		return office;
	}

	public void setOffice(SysCompanyOffice office) {
		this.office = office;
	}

	public Integer getMonthStartInCompany() {
		return monthStartInCompany;
	}

	public void setMonthStartInCompany(Integer monthStartInCompany) {
		this.monthStartInCompany = monthStartInCompany;
	}

	public Integer getYearStartInCompany() {
		return yearStartInCompany;
	}

	public void setYearStartInCompany(Integer yearStartInCompany) {
		this.yearStartInCompany = yearStartInCompany;
	}

	@JoinColumn(nullable = false)
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, targetEntity = SysEmployeePosition.class)
	public SysEmployeePosition getPosition() {
		return position;
	}

	public void setPosition(SysEmployeePosition position) {
		this.position = position;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		if (rg != null) {
			// retorna somente os números
			rg = rg.replaceAll("[^\\d]", "");
		}
		this.rg = rg;
	}

	@Column(length = 11)
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		if (cpf != null) {
			// retorna somente os números
			cpf = cpf.replaceAll("[^\\d]", "");
			// completa a masca de 11 caracteres
			DecimalFormat format = new DecimalFormat("00000000000");
			cpf = format.format(Long.parseLong(cpf));
		}
		this.cpf = cpf;
	}

	@Column(nullable = false)
	public TypeContract getTypeContract() {
		return typeContract;
	}

	public void setTypeContract(TypeContract typeContract) {
		this.typeContract = typeContract;
	}
}
