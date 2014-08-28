package net.sf.gaia.framework.entity;

import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.cojen.classfile.MethodInfo;

@Entity(name="SYSTB_DynamicColumnDate")
@Table(name="SYSTB_DynamicColumnDate")
public class DynamicColumnDate extends DynamicColumn {

	public DynamicColumnDate() {
		super(GregorianCalendar.class);
	}

	@Override
	public MethodInfo buildAnnotation(MethodInfo mi) {
		return mi;
	}
}
