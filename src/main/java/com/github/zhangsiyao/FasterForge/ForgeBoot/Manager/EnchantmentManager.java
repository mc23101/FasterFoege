package com.github.zhangsiyao.FasterForge.ForgeBoot.Manager;

import com.github.zhangsiyao.FasterForge.ForgeBoot.ForgeApplication;
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
    public static void registerEnchantment(Enchantment enchantment){
        if(containEnchantment(enchantment.getRegistryName())){
            ForgeApplication.logger.error("=================================================================================");
            ForgeApplication.logger.error("附魔:"+enchantment.getRegistryName()+"注册失败");
            ForgeApplication.logger.error("失败原因:附魔已经存在,请更换注册名");
            ForgeApplication.logger.error("=================================================================================");
            return;
        }
        GameRegistry.findRegistry(Enchantment.class).register(enchantment);
        ForgeApplication.logger.debug("附魔"+enchantment.getRegistryName()+"注册成功");
    }

    /**
     * 检查附魔属性是否已经注册
     * @param location 附魔属性的注册名
     * */
    public static boolean containEnchantment(ResourceLocation location){
        return GameRegistry.findRegistry(Enchantment.class).containsKey(location);
    }

    public static Enchantment getEnchantment(ResourceLocation location){
        if(containEnchantment(location)){
           return GameRegistry.findRegistry(Enchantment.class).getValue(location);
        }
        return null;
    }
}
