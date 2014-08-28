package net.sf.gaia.framework.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.sf.gaia.entity.SysObject;
import net.sf.gaia.framework.dynamic.DynamicUtils;

import org.cojen.classfile.MethodInfo;
import org.cojen.classfile.attribute.Annotation;

@Entity(name="SYSTB_DynamicColumnEntity")
@Table(name="SYSTB_DynamicColumnEntity")
public class DynamicColumnEntity extends DynamicColumn {

	public DynamicColumnEntity() {
		super(SysObject.class);
	}
	
	public DynamicColumnEntity(Class<?> type) {
		super(type);
	}

	@Override
	public MethodInfo buildAnnotation(MethodInfo mi) {
		//if(true) return mi;
        // Add annotation class
//		ConstantUTFInfo fetchType = new ConstantUTFInfo(FetchType.class.getName());
//		ConstantUTFInfo lazy = new ConstantUTFInfo("LAZY");
//		
//		Annotation.EnumConstValue annEnum = new Annotation.EnumConstValue(fetchType, lazy);
//		Annotation.MemberValue valueAnn = new Annotation.MemberValue(Annotation.MEMBER_TAG_ENUM, annEnum);
		
        Annotation anT = mi.addRuntimeVisibleAnnotation(DynamicUtils.typeFromClass(OneToOne.class));
//      anT.putMemberValue("fetch", valueAnn);
		return mi;
	}
}
