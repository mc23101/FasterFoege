package com.github.zhangsiyao.FasterForge.Proxy.Entity.Player.impl;

import com.github.zhangsiyao.FasterForge.Proxy.Entity.Player.IPlayerProxyProxy;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerProxy implements IPlayerProxyProxy {
    private EntityPlayer entityPlayer;

    public PlayerProxy(EntityPlayer entityPlayer) {
        this.entityPlayer = entityPlayer;
    }
}
