package com.github.zhangsiyao.FasterForge.Minecraft.Block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockProxy {
    private final Block block;


    public BlockProxy() {
        this.block = new Block(Material.AIR);
    }

    public Block getBlock() {
        return block;
    }
}
