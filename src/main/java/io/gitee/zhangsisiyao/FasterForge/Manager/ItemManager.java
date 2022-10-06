package io.gitee.zhangsisiyao.FasterForge.Manager;

import net.minecraft.block.Block;
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
     * 用于存放所有注册过的创造模式物品页
     * */
    private static Map<String, CreativeTabs> creativeTabsMap =new HashMap<>();

    /**
     * 用于存放此mod注册的所有物品
     * */
    private static Map<ResourceLocation, Item> itemMap=new HashMap<>();

    /**
     * 用于存放此mod注册的所有方块
     * */
    private static Map<ResourceLocation, Block> blockMap=new HashMap<>();

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
        creativeTabsMap.put(name,creativeTab);
        return creativeTab;
    }

    /**
     * 注册物品
     * @param item 注册的物品
     * */
    public static Item registerItem(Item item){
        GameRegistry.findRegistry(Item.class).registerAll(item);
        itemMap.put(item.getRegistryName(),item);
        return item;
    }

    /**
     * 注册物品
     * @param items 注册的物品(可变参数)
     * */
    public static void registerItems(Item...items){
        GameRegistry.findRegistry(Item.class).registerAll(items);
        for(Item item:items){
            itemMap.put(item.getRegistryName(),item);
        }
    }

    /**
     * 注册方块
     * @param blocks 注册的方块(可变参数)
     * */
    public static void registerBlocks(Block...blocks){
        GameRegistry.findRegistry(Block.class).registerAll(blocks);
        for(Block block:blocks){
            blockMap.put(block.getRegistryName(),block);
        }
    }

    /**
     * 获取mod已经注册过的物品
     * @param location 物品的注册名
     * @return 返回物品，如果物品未被注册，则返回null
     * */
    public static Item getItem(ResourceLocation location){
        if(itemMap.containsKey(location)){
            return itemMap.get(location);
        }
        return null;
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

    /**
     * 检查Item是否已经注册
     * @param location Item的注册名
     * */
    public static boolean containItem(ResourceLocation location){
        return GameRegistry.findRegistry(Item.class).containsKey(location);
    }

    /**
     * 检查Block是否已经注册
     * @param location Block的注册名
     * */
    public static boolean containBlock(ResourceLocation location){
        return GameRegistry.findRegistry(Block.class).containsKey(location);
    }
}
