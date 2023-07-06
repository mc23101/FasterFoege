package com.github.zhangsiyao.FasterForge.Minecraft.Item.impl;

import com.github.zhangsiyao.FasterForge.Minecraft.Item.IItemStack;
import com.github.zhangsiyao.FasterForge.Proxy.Item.IItemStackProxy;

public class ItemStack implements IItemStack {
    private IItemStackProxy iItemStackProxy;

    public ItemStack(IItemStackProxy iItemStackProxy) {
        this.iItemStackProxy = iItemStackProxy;
    }
}
