package com.github.zhangsiyao.FasterForge.Proxy.Entity.impl;

import com.github.zhangsiyao.FasterForge.Proxy.Entity.IEntityProxy;
import net.minecraft.entity.Entity;

public class EntityProxy implements IEntityProxy {

    private Entity entity;

    public EntityProxy(Entity entity) {
        this.entity = entity;
    }
}
