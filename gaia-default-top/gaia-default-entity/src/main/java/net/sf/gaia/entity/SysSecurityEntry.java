package net.sf.gaia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Entidade de segurança.
 * Controla acesso a criações de Usuário ou de Conjuntos.
 * 
 * @author daniel.joppi
 * 
 */
@Entity(name="SYSTB_SecurityEntry")
@Table(name="SYSTB_SecurityEntry")
public abstract class SysSecurityEntry extends SysObject {

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SysSecurityEntry.class);

	private String nickName;
	
	public SysSecurityEntry() {
		
	}

	@Column(unique = true, nullable = false)
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
