package net.sf.gaia.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entidade de Projeto de Trabalho.
 * 
 * @author daniel.joppi
 *
 */
@Entity(name="SYSTB_ProjectWork")
@Table(name="SYSTB_ProjectWork")
public class SysProjectWork extends SysProject {

	/**
	 * Número gerador do projeto.
	 */
	private long genCodeNumber;
	
	/**
	 * Proposta de projeto original.
	 */
	private SysProjectOffer projectOffer;

	public SysProjectWork() {
		
	}

	@Override
	@Column(unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator_SYSTB_ProjectWork")
	@SequenceGenerator(name = "generator_SYSTB_ProjectWork", sequenceName = "SEQ_SYSTB_ProjectWork", initialValue = 1, allocationSize = 1000)
	public long getGenCodeNumber() {
		return genCodeNumber;
	}

	@Override
	public void setGenCodeNumber(long genCodeNumber) {
		this.genCodeNumber = genCodeNumber;
	}

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, targetEntity = SysProjectOffer.class)
	public SysProjectOffer getProjectOffer() {
		return projectOffer;
	}

	public void setProjectOffer(SysProjectOffer projectOffer) {
		this.projectOffer = projectOffer;
	}
}
