package com.github.zhangsiyao.FasterForge.Proxy.World.impl;

import com.github.zhangsiyao.FasterForge.Proxy.World.IWorldProxy;
import net.minecraft.world.World;

public class WorldProxy implements IWorldProxy {
    private World world;

    public WorldProxy(World world) {
        this.world = world;
    }
}
