package com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注册Minecraft的实体
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface MinecraftEntity {
    /**
     * Mod的Id
     * */
    String modId() ;
    /**
     * 实体注册名&&实体名称
     * */
    String name() ;
    /**
     * 实体数字id
     * */
    int Id();
    /**
     * 实体追踪范围
     * */
    int trackingRange() default 20;
    /**
     * 更新频率
     * */
    int updateFrequency() default 1;
    /**
     * 是否发送速度信息包
     * */
    boolean sendsVelocityUpdates() default true;
    /**
     * 怪物蛋颜色1
     * */
    int eggPrimary() default 14833957;
    /**
     * 怪物蛋颜色2
     * */
    int eggSecondary() default 0;
}
