package com.SiYao.ForgeAPI.Item;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.Map;

public class ItemManager {

    private static Map<String,CreativeTabs> creativeTabsMap =new HashMap<>();

    public static CreativeTabs registerCreativeTabs(String name,ItemStack icon){
        CreativeTabs creativeTab = new CreativeTabs(I18n.format(name)) {
            @Override
            public ItemStack getTabIconItem() {
                return icon;
            }
        };
        creativeTabsMap.put(name,creativeTab);
        return creativeTab;
    }

    public static void registerItem(Item...items){
        GameRegistry.findRegistry(Item.class).registerAll(items);
    }
}
