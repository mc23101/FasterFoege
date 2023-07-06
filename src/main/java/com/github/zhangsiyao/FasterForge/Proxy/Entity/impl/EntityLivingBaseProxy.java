package com.github.zhangsiyao.FasterForge.Proxy.Entity.impl;

import com.github.zhangsiyao.FasterForge.Proxy.Entity.IEntityLivingBaseProxy;
import net.minecraft.entity.EntityLivingBase;

public class EntityLivingBaseProxy extends EntityProxy implements IEntityLivingBaseProxy {
    private EntityLivingBase entityLivingBase;

    public EntityLivingBaseProxy(EntityLivingBase entityLivingBase) {
        super(entityLivingBase);
        this.entityLivingBase = entityLivingBase;
    }
}
