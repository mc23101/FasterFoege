package com.github.zhangsiyao.FasterForge.Minecraft.Item.impl;

import com.github.zhangsiyao.FasterForge.Minecraft.Item.IItem;
import com.github.zhangsiyao.FasterForge.Proxy.Item.impl.ItemProxy;

/*
* FasterForge的抽象物品类
* */
public abstract class AbstractIItem implements IItem {

    private final ItemProxy item=new ItemProxy();

    private String str="dawdada";

    public AbstractIItem(){
        item.onItemRightClick((a,b,c)-> System.out.println(str));
    }

    public final int getMaxStackSize(){
        return item.getMaxStackSize();
    }

    public final IItem setMaxStackSize(int size){
        item.setMaxStackSize(size);
        return this;
    }

    public ItemProxy getItem() {
        return item;
    }
}
