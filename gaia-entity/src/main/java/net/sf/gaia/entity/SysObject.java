package net.sf.gaia.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Entidade base que deve ser estendida pelas demais entidades do projeto gaia.
 * 
 * @author daniel.joppi
 * 
 */
@Entity(name="SYSTB_Object")
@Table(name="SYSTB_Object")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SysObject implements SysEntity {

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SysObject.class);

	/**
	 * Id do GaiaObject.
	 */
	private long id;

	/**
	 * Tipo do SysObject.
	 */
	private String sysType;

	public SysObject() {

	}

	@Id
	@Column(unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator_SYSTB_Object")
	@SequenceGenerator(name = "generator_SYSTB_Object", sequenceName = "SEQ_SYSTB_Object", initialValue = 1, allocationSize = 1000)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(length = 96, nullable = false)
	public String getSysType() {
		if (sysType == null || (sysType != null && "".equals(sysType.trim()))) {
			sysType = this.getClass().getName();
		}
		
		return this.encodeSysType(sysType);
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}
	
	@Transient
	public String safeSysType() {
		String sysType = this.getSysType();
		
		return this.decodeSysType(sysType);
	}
	
	protected String encodeSysType(String sysType) {
		if (sysType.startsWith("net.sf.gaia.dyn.entity.")) {
			sysType = sysType.replace("net.sf.gaia.dyn.entity.", "D_.");
		}
		if (sysType.startsWith("net.sf.gaia")) {
			sysType = sysType.replace("net.sf.gaia", "G_");
		}
		if (sysType.contains(".entity.")) {
			sysType = sysType.replace(".entity.", "E_.");
		}
		return sysType;
	}
	
	protected String decodeSysType(String sysType) {
		if (sysType.startsWith("D_.")) {
			sysType = sysType.replace("D_.", "net.sf.gaia.dyn.entity.");
		}
		if (sysType.startsWith("G_")) {
			sysType = sysType.replace("G_", "net.sf.gaia");
		}
		if (sysType.contains("E_.")) {
			sysType = sysType.replace("E_.", ".entity.");
		}
		return sysType;
	}
	
	/**
	 * Método que retorna uma nova instância de um objeto GaiaObject.
	 * 
	 * @param <T> tipo da classe de retorno que extende GaiaObject.
	 * @param clazzName string com nome da classe e pacote. 
	 * @return nova instância de <T>.
	 * 
	 * @author daniel.joppi
	 * @since 20/11/2011
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public <T extends SysObject> T newInstance(String clazzName) {
		try {
	      Class<?> clazz = Class.forName(clazzName);
	      return (T) clazz.newInstance();
	    } catch (Exception x) {	     
	    }
	    return null;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == this) {
			return true;
		} else if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof SysObject)) {
			return false;
		} else if (getId() == 0) {
			return false;
		}
		
		return ((SysObject) obj).getId() == getId();
	}

	@Override
	public int hashCode()
	{
		if (getId() == 0) {
			return super.hashCode();
		} else {
			return (int) Math.abs(0x213814c6 ^ (getId() & 0xFFFFFFFF));
		}
	}
	
	protected <E> Set<E> safeSetCollection(Set<E> set) {
		// Workaraund: PersistSet está duplicando os dados no postgres
		// TODO ver se futuras implementações do hibernate corrigem o problema
		// @author daniel.joppi 26/01/2011
		if (set instanceof HashSet<?>) {
			return set;
		} else {
			HashSet<E> newSet = new HashSet<E>();
			newSet.addAll(set);
			return newSet;
		}
	}
}
