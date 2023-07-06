package com.github.zhangsiyao.FasterForge.Proxy.Entity.impl;

import com.github.zhangsiyao.FasterForge.Proxy.Entity.IEntityItemProxy;
import net.minecraft.entity.item.EntityItem;


public class EntityItemProxy implements IEntityItemProxy {
    private EntityItem entityItem;

    public EntityItemProxy(EntityItem entityItem) {
        this.entityItem = entityItem;
    }
}
