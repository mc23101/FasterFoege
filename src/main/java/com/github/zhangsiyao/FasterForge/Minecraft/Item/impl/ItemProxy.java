package com.github.zhangsiyao.FasterForge.Minecraft.Item.impl;

import com.github.zhangsiyao.FasterForge.Minecraft.Item.IItemPropertyProxy;
import com.github.zhangsiyao.FasterForge.Minecraft.Item.IItemProxy;
import com.github.zhangsiyao.FasterForge.Minecraft.Resource.ResourceName;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

/**
 * FasterForge的物品代理，用于对Minecraft的Item进行操作.
 * 在进行版本移植时，应相应修改此类.
 * */
//TODO 在版本移植时，修改此类信息.
@SuppressWarnings("all")
public class ItemProxy implements IItemProxy {
    private final Item item;

    protected ItemProxy(Item item){
        this.item=item;
    }

    public ItemProxy(){
        item=new Item();
    }

    public Item getItem() {
        return item;
    }


    /**
     * 获取一堆物品的最大数量
     * @return 返回物品堆最大数量
     * */
    public final int getMaxStackSize(){
        return item.getItemStackLimit();
    }

    /**
     * 设置物品堆最大数量
     * @param size 物品堆数量
     * @return 返回修改后的物品代理
     * */
    public final IItemProxy setMaxStackSize(int size){
        item.setMaxStackSize(size);
        return this;
    }


    public final void addPropertyOverride(ResourceName key, IItemPropertyProxy property)
    {
        item.addPropertyOverride(new ResourceLocation(key.getLocation()), (IItemPropertyGetter) property.getProperty());
    }


    public final ItemPropertyProxy getProperty(ResourceName key){
        return new ItemPropertyProxy(item.getPropertyGetter(new ResourceLocation(key.getLocation())));
    }



}
