package com.github.zhangsiyao.FasterForge.ForgeBoot.Manager;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityManager {
    public static void registerTileEntity(Class<?extends TileEntity> tileEntity, ResourceLocation blockName){
        GameRegistry.registerTileEntity(tileEntity,blockName);
    }
}
