package com.github.zhangsiyao.FasterForge.ForgeBoot.Minecraft.Annotation;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface MinecraftEnchantment {
    String modId();
    String name();
    Enchantment.Rarity rarity();
    EnumEnchantmentType type();
    EntityEquipmentSlot[] slot();
}
