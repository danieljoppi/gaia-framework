package net.sf.gaia.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entidade de relação entre Projeto e entidades de segurança.
 * 
 * @author daniel.joppi
 *
 */
@Entity(name="SYSTB_EntrysProject")
@Table(name="SYSTB_EntrysProject")
public class SysEntrysProject extends SysObject {

	/**
	 * Entidade que trabalha no projeto (usuário ou grupo)
	 */
	private SysSecurityEntry entry;
	
	/**
	 * Projeto.
	 */
	private SysProject project;
	
	public SysEntrysProject() {
		
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, targetEntity = SysSecurityEntry.class)
	public SysSecurityEntry getEntry() {
		return entry;
	}

	public void setEntry(SysSecurityEntry entry) {
		this.entry = entry;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, targetEntity = SysProject.class)
	public SysProject getProject() {
		return project;
	}

	public void setProject(SysProject project) {
		this.project = project;
	}
}
