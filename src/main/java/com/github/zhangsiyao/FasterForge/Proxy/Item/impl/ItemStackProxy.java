package com.github.zhangsiyao.FasterForge.Proxy.Item.impl;

import com.github.zhangsiyao.FasterForge.Proxy.Item.IItemStackProxy;
import net.minecraft.item.ItemStack;

/**
 * FasterForge的ItemStack代理类
 * */
public class ItemStackProxy implements IItemStackProxy {
    private ItemStack itemStack;

    public ItemStackProxy(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
