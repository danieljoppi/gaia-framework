package net.sf.gaia.framework.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.cojen.classfile.MethodInfo;

@Entity(name="SYSTB_DynamicColumnClass")
@Table(name="SYSTB_DynamicColumnClass")
public class DynamicColumnClass extends DynamicColumn {

	public DynamicColumnClass() {
		super(Class.class);
	}

	@Override
	public MethodInfo buildAnnotation(MethodInfo mi) {
		return mi;
	}
}
