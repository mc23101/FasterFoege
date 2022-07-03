package io.gitee.zhangsisiyao.ForgeAPI.Annotation;

import io.gitee.zhangsisiyao.ForgeAPI.Annotation.Enum.BlockMaterial;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface MinecraftBlock {
    String modId() default "";
    String name() default "";
    BlockMaterial material() default BlockMaterial.ROCK;
}
