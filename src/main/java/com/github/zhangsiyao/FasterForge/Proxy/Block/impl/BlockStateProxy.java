package com.github.zhangsiyao.FasterForge.Proxy.Block.impl;

import com.github.zhangsiyao.FasterForge.Proxy.Block.IBlockStateProxy;
import net.minecraft.block.state.IBlockState;

public class BlockStateProxy implements IBlockStateProxy {
    private IBlockState blockState;

    public BlockStateProxy(IBlockState blockState) {
        this.blockState = blockState;
    }
}
