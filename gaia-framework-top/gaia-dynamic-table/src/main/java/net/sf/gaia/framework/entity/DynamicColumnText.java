package net.sf.gaia.framework.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.cojen.classfile.MethodInfo;

@Entity(name="SYSTB_DynamicColumnText")
@Table(name="SYSTB_DynamicColumnText")
public class DynamicColumnText extends DynamicColumn {

	public DynamicColumnText() {
		super(String.class);
	}

	@Override
	public MethodInfo buildAnnotation(MethodInfo mi) {
		return mi;
	}
}
