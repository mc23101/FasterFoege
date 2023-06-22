package com.github.zhangsiyao.FasterForge.Minecraft.Item.impl;

import com.github.zhangsiyao.FasterForge.Minecraft.Item.IItem;

/*
* FasterForge的抽象物品类
* */
public abstract class AbstractIItem implements IItem {

    private final ItemProxy item=new ItemProxy();

    public AbstractIItem(){
    }

    public final int getMaxStackSize(){
        return item.getMaxStackSize();
    }

    public final IItem setMaxStackSize(int size){
        item.setMaxStackSize(size);
        return this;
    }
}
