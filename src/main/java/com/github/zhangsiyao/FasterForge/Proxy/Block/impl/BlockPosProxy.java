package com.github.zhangsiyao.FasterForge.Proxy.Block.impl;

import com.github.zhangsiyao.FasterForge.Proxy.Block.IBlockPosProxy;
import net.minecraft.util.math.BlockPos;

public class BlockPosProxy implements IBlockPosProxy {

    private BlockPos blockPos;

    public BlockPosProxy(BlockPos blockPos) {
        this.blockPos = blockPos;
    }
}
