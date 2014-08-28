package net.sf.gaia.framework.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.cojen.classfile.MethodInfo;

@Entity(name="SYSTB_DynamicColumnDecimal")
@Table(name="SYSTB_DynamicColumnDecimal")
public class DynamicColumnDecimal extends DynamicColumn {

	public DynamicColumnDecimal() {
		super(BigDecimal.class);
	}

	@Override
	public MethodInfo buildAnnotation(MethodInfo mi) {
		return mi;
	}
}
