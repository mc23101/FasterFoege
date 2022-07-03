package io.gitee.zhangsisiyao.ForgeAPI.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface MinecraftEntity {
    String modId() ;
    String name() ;
    int trackingRange() default 20;
    int updateFrequency() default 1;
    boolean sendsVelocityUpdates() default true;
    int eggPrimary() default 14833957;
    int eggSecondary() default 0;
}
