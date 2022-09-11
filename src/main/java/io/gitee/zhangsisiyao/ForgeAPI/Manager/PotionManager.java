package io.gitee.zhangsisiyao.ForgeAPI.Manager;

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
     * 用于存放此mod注册的药水效果
     * */
    private static Map<ResourceLocation, Potion> PotionMap=new HashMap<>();


    /**
     * 注册药水效果
     * @param potions 注册的药水
     * */
    public static void registerPotion(Potion...potions){
        GameRegistry.findRegistry(Potion.class).registerAll(potions);
        for(Potion p:potions){
            PotionMap.put(p.getRegistryName(),p);
        }
    }

    /**
     * 检查Potion是否已经注册
     * @param location Potion的注册名
     * */
    public static boolean containPotion(ResourceLocation location){
        return GameRegistry.findRegistry(Potion.class).containsKey(location);
    }
}
