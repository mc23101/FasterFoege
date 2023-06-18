package com.github.zhangsiyao.FasterForge.ForgeBoot.Manager;

import com.github.zhangsiyao.FasterForge.FasterForge;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockManager {

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
            FasterForge.logger.error("=================================================================================");
            FasterForge.logger.error("方块:"+block.getRegistryName()+"注册失败");
            FasterForge.logger.error("失败原因:方块已经存在,请更换注册名");
            FasterForge.logger.error("=================================================================================");
            return;
        }
        if(ItemManager.containItem(block.getRegistryName())){
            FasterForge.logger.error("=================================================================================");
            FasterForge.logger.error("方块:"+block.getRegistryName()+"注册失败");
            FasterForge.logger.error("失败原因:方块所绑定的物品名称已经存在,请更换注册名");
            FasterForge.logger.error("=================================================================================");
            return;
        }
        GameRegistry.findRegistry(Block.class).register(block);
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        ItemManager.registerItem(itemBlock);
        FasterForge.logger.debug("方块"+block.getRegistryName()+"注册成功");
    }

    /**
     * 获取mod已经注册过的方块
     * @param location 方块的注册名
     * @return 返回方块,如果方块未被注册，则返回null
     * */
    public static Block getBlock(ResourceLocation location){
        if(containBlock(location)){
            return GameRegistry.findRegistry(Block.class).getValue(location);
        }
        return null;
    }
}
