package net.sf.gaia.framework.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.cojen.classfile.MethodInfo;

@Entity(name="SYSTB_DynamicColumnBoolean")
@Table(name="SYSTB_DynamicColumnBoolean")
public class DynamicColumnBoolean extends DynamicColumn {

	public DynamicColumnBoolean() {
		super(Boolean.class);
	}

	@Override
	public MethodInfo buildAnnotation(MethodInfo mi) {
		return mi;
	}
}
