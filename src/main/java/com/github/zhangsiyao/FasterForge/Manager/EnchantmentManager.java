package com.github.zhangsiyao.FasterForge.Manager;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
/**
 * 附魔管理器,用于操作附魔
 * <p>例如:注册附魔属性</p>
 * */
public class EnchantmentManager {
    /**
     * 注册附魔属性
     * @param enchantments 注册的附魔属性
     * */
    public static void registerEnchantment(Enchantment...enchantments){
        GameRegistry.findRegistry(Enchantment.class).registerAll(enchantments);
    }

    /**
     * 检查附魔属性是否已经注册
     * @param location 附魔属性的注册名
     * */
    public static boolean containEnchantment(ResourceLocation location){
        return GameRegistry.findRegistry(Enchantment.class).containsKey(location);
    }
}
