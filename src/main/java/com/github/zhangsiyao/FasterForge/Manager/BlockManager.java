package com.github.zhangsiyao.FasterForge.Manager;

import com.github.zhangsiyao.FasterForge.ForgeBoot.ForgeApplication;
import com.github.zhangsiyao.FasterForge.MinecraftCore;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.Map;

public class BlockManager {

    /**
     * 用于存放此mod注册的所有方块
     * */
    private static Map<ResourceLocation, Block> blockMap=new HashMap<>();

    /**
     * 检查Block是否已经注册
     * @param location Block的注册名
     * */
    public static boolean containBlock(ResourceLocation location){
        return GameRegistry.findRegistry(Block.class).containsKey(location);
    }


    /**
     * 注册方块
     * @param block 注册的方块
     * */
    public static void registerBlock(Block block){
        if(containBlock(block.getRegistryName())){
            ForgeApplication.logger.error("=================================================================================");
            ForgeApplication.logger.error("方块:"+block.getRegistryName()+"注册失败");
            ForgeApplication.logger.error("失败原因:方块已经存在,请更换注册名");
            ForgeApplication.logger.error("=================================================================================");
            return;
        }
        if(ItemManager.containItem(block.getRegistryName())){
            ForgeApplication.logger.error("=================================================================================");
            ForgeApplication.logger.error("方块:"+block.getRegistryName()+"注册失败");
            ForgeApplication.logger.error("失败原因:方块所绑定的物品名称已经存在,请更换注册名");
            ForgeApplication.logger.error("=================================================================================");
            return;
        }
        GameRegistry.findRegistry(Block.class).register(block);
        ItemManager.registerItem(new ItemBlock(block));
    }

    /**
     * 获取mod已经注册过的方块
     * @param location 方块的注册名
     * @return 返回方块,如果方块未被注册，则返回null
     * */
    public static Block getBlock(ResourceLocation location){
        if(blockMap.containsKey(location)){
            return  blockMap.get(location);
        }
        return null;
    }
}
