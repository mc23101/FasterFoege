package io.gitee.zhangsisiyao.ForgeAPI.Annotation;

import io.gitee.zhangsisiyao.ForgeAPI.Resources.ResourceType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface MinecraftResource {
    String modId();
    String name();
    ResourceType type();
}
