package com.github.zhangsiyao.FasterForge.ForgeBoot.Manager;

import com.github.zhangsiyao.FasterForge.ForgeBoot.ForgeApplication;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.Map;
/**
 * 物品管理器,用于操作Item和Block
 * <p>例如:注册物品、注册方块</p>
 * */
public class ItemManager {


    /**
     * 注册创造模式物品栏
     * @param name 物品栏显示的名字
     * @param icon 物品栏显示的图标
     * @return 返回注册的创造模式物品栏
     * */
    public static CreativeTabs registerCreativeTabs(String name, ItemStack icon){
        ItemStack itemStack=icon==null?new ItemStack(Items.DIAMOND):icon;
        CreativeTabs creativeTab = new CreativeTabs(I18n.format(name)) {
            @Override
            public ItemStack getTabIconItem() {
                return itemStack;
            }
        };
        return creativeTab;
    }

    /**
     * 注册物品
     * @param item 注册的物品
     * */
    public static void registerItem(Item item){
        if(containItem(item.getRegistryName())){
            ForgeApplication.logger.error("=================================================================================");
            ForgeApplication.logger.error("物品:"+item.getRegistryName()+"注册失败");
            ForgeApplication.logger.error("失败原因:物品已经存在,请更换注册名");
            ForgeApplication.logger.error("=================================================================================");
            return;
        }
        GameRegistry.findRegistry(Item.class).register(item);
        ForgeApplication.logger.debug("物品:"+item.getRegistryName()+"注册成功");

    }

    /**
     * 获取mod已经注册过的物品
     * @param location 物品的注册名
     * @return 返回物品，如果物品未被注册，则返回null
     * */
    public static Item getItem(ResourceLocation location){
        if(containItem(location)){
            return GameRegistry.findRegistry(Item.class).getValue(location);
        }
        return null;
    }

    /**
     * 检查Item是否已经注册
     * @param location Item的注册名
     * */
    public static boolean containItem(ResourceLocation location){
        return GameRegistry.findRegistry(Item.class).containsKey(location);
    }


}
