package com.github.zhangsiyao.FasterForge.Minecraft.Item.impl;

import net.minecraft.item.IItemPropertyGetter;

/**
 * FasterForge的IItemProperty代理,这个property规定了物品的一些属性，比如：是不是食物，或者这个物品在创造模式的哪一个物品栏
 * 在进行版本移植时，应相应修改此类
 * */
//TODO 在版本移植时，修改此类信息.
public class ItemPropertyProxy {
    private final IItemPropertyGetter property;

    public ItemPropertyProxy(){
        property= (stack, worldIn, entityIn) -> 0;
    }

    protected ItemPropertyProxy(IItemPropertyGetter property){
        this.property=property;
    }

    public IItemPropertyGetter getProperty() {
        return property;
    }
}
