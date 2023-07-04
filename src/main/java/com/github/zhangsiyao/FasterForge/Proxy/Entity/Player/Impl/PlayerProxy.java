package com.github.zhangsiyao.FasterForge.Proxy.Entity.Player.Impl;

import com.github.zhangsiyao.FasterForge.Proxy.Entity.Player.IPlayerProxy;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerProxy implements IPlayerProxy {
    private EntityPlayer entityPlayer;

    public PlayerProxy(EntityPlayer entityPlayer) {
        this.entityPlayer = entityPlayer;
    }
}
