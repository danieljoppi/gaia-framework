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
 * Entidade de Proposta de Projeto.
 * 
 * @author daniel.joppi
 *
 */
@Entity(name="SYSTB_ProjectOffer")
@Table(name="SYSTB_ProjectOffer")
public class SysProjectOffer extends SysProject {

	/**
	 * Número gerador do projeto.
	 */
	private long genCodeNumber;
	
	/**
	 * Projeto de trabalho.
	 */
	private SysProjectWork projectWork;

	public SysProjectOffer() {
		
	}

	@Override
	@Column(unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator_SYSTB_ProjectOffer")
	@SequenceGenerator(name = "generator_SYSTB_ProjectOffer", sequenceName = "SEQ_SYSTB_ProjectOffer", initialValue = 1, allocationSize = 1000)
	public long getGenCodeNumber() {
		return genCodeNumber;
	}

	@Override
	public void setGenCodeNumber(long genCodeNumber) {
		this.genCodeNumber = genCodeNumber;
	}

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, targetEntity = SysProjectWork.class, mappedBy = "projectOffer")
	public SysProjectWork getProjectWork() {
		return projectWork;
	}

	public void setProjectWork(SysProjectWork projectWork) {
		this.projectWork = projectWork;
	}
}
