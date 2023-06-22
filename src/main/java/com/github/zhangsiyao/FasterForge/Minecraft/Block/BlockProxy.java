package com.github.zhangsiyao.FasterForge.Minecraft.Block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * FasterForge的block类代理,用于对Minecraft的Block进行操作. 在进行版本移植时，应相应修改此类.
 * */
//TODO 在版本移植时，修改此类信息.
public class BlockProxy {
    private final Block block;


    public BlockProxy() {
        this.block = new Block(Material.AIR);
    }

    public Block getBlock() {
        return block;
    }
}
