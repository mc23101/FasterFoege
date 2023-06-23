package com.github.zhangsiyao.FasterForge.Minecraft.Item;

import com.github.zhangsiyao.FasterForge.Minecraft.Block.IBlockPosProxy;
import com.github.zhangsiyao.FasterForge.Minecraft.Block.IBlockStateProxy;
import com.github.zhangsiyao.FasterForge.Minecraft.Item.impl.ItemPropertyProxy;
import com.github.zhangsiyao.FasterForge.Minecraft.Nbt.INbt;
import com.github.zhangsiyao.FasterForge.Minecraft.Resource.ResourceName;
import net.minecraft.item.Item;

public interface IItemProxy {

    Object getItem();
    int getMaxStackSize();

    IItemProxy setMaxStackSize(int size);

    void addPropertyOverride(ResourceName key, IItemPropertyProxy property);

    ItemPropertyProxy getProperty(ResourceName key);

    boolean updateItemStackNBT(INbt nbt);

    boolean hasCustomProperties();

    float getDestroySpeed(IItemStackProxy stack, IBlockPosProxy state);

    int getMaxDamage();

    IItemProxy setMaxDamage(int damage);

    boolean isDamageable();

    boolean canHarvestBlock(IBlockStateProxy blockIn);

    IItemProxy setFull3D();

    boolean isFull3D();

    boolean shouldRotateAroundWhenRendering();

    IItemProxy setUnlocalizedName(String unlocalizedName);

    String getUnlocalizedNameInefficiently(IItemStackProxy stack);

    String getUnlocalizedName();

    IItemProxy setContainerItem(IItemProxy containerItem);

    boolean getShareTag();

    IItemProxy getContainerItem();

    boolean hasContainerItem();

    boolean isMap();

}
