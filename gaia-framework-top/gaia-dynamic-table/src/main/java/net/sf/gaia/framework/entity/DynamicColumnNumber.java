package net.sf.gaia.framework.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.cojen.classfile.MethodInfo;

@Entity(name="SYSTB_DynamicColumnNumber")
@Table(name="SYSTB_DynamicColumnNumber")
public class DynamicColumnNumber extends DynamicColumn {

	public DynamicColumnNumber() {
		super(Long.class);
	}

	@Override
	public MethodInfo buildAnnotation(MethodInfo mi) {
		return mi;
	}
}
