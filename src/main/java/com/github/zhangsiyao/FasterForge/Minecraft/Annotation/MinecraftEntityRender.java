package com.github.zhangsiyao.FasterForge.Minecraft.Annotation;

import net.minecraft.entity.Entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface MinecraftEntityRender {
    Class<? extends Entity> EntityClass();
}
