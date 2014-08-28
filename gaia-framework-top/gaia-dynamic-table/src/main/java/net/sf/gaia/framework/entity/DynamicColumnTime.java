package net.sf.gaia.framework.entity;

import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.cojen.classfile.MethodInfo;


@Entity(name="SYSTB_DynamicColumnTime")
@Table(name="SYSTB_DynamicColumnTime")
public class DynamicColumnTime extends DynamicColumn {

	public DynamicColumnTime() {
		super(Time.class);
	}

	@Override
	public MethodInfo buildAnnotation(MethodInfo mi) {
		return mi;
	}
}
