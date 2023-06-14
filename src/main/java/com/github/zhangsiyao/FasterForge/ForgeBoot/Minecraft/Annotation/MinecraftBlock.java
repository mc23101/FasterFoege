package com.github.zhangsiyao.FasterForge.ForgeBoot.Minecraft.Annotation;

import com.github.zhangsiyao.FasterForge.ForgeBoot.Minecraft.Annotation.Enum.BlockMaterial;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>通过注解注册Minecraft的Block方块</p>
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface MinecraftBlock {
    /**
     * ModID
     * */
    String modId();
    /**
     * 方块名称
     * */
    String name();

    /**
     * 方块材质
     * */
    BlockMaterial material() default BlockMaterial.NULL;
}
