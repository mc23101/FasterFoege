package com.github.zhangsiyao.FasterForge.ForgeBoot.Manager;

import com.github.zhangsiyao.FasterForge.ForgeBoot.ForgeApplication;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.Map;
/**
 * <p>药水效果管理器,用于操作药水效果</p>
 * <p>例如:注册药水效果</p>
 * */
public class PotionManager {

    /**
     * 注册药水效果
     * @param potions 注册的药水
     * */
    public static void registerPotion(Potion potion){
        if(containPotion(potion.getRegistryName())){
            ForgeApplication.logger.error("=================================================================================");
            ForgeApplication.logger.error("药水:"+potion.getRegistryName()+"注册失败");
            ForgeApplication.logger.error("失败原因:药水已经存在,请更换注册名");
            ForgeApplication.logger.error("=================================================================================");
            return;
        }
        GameRegistry.findRegistry(Potion.class).registerAll(potion);
        ForgeApplication.logger.debug("药水"+potion.getRegistryName()+"注册成功");
    }

    /**
     * 检查Potion是否已经注册
     * @param location Potion的注册名
     * */
    public static boolean containPotion(ResourceLocation location){
        return GameRegistry.findRegistry(Potion.class).containsKey(location);
    }

    public static Potion getPotion(ResourceLocation location){
        if(containPotion(location)){
            return GameRegistry.findRegistry(Potion.class).getValue(location);
        }
        return null;
    }
}
