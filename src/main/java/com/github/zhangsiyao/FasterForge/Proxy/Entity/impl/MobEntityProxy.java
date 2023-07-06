package com.github.zhangsiyao.FasterForge.Proxy.Entity.impl;

import com.github.zhangsiyao.FasterForge.Proxy.Entity.IMobEntityProxy;
import net.minecraft.entity.EntityLivingBase;

public class MobEntityProxy extends EntityLivingBaseProxy implements IMobEntityProxy {
    public MobEntityProxy(EntityLivingBase entityLivingBase) {
        super(entityLivingBase);
    }
}
