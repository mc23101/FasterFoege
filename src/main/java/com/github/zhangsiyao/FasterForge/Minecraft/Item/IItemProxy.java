package com.github.zhangsiyao.FasterForge.Minecraft.Item;

import com.github.zhangsiyao.FasterForge.Minecraft.Entity.Player.IPlayerProxy;
import com.github.zhangsiyao.FasterForge.Minecraft.Item.impl.ItemPropertyProxy;
import com.github.zhangsiyao.FasterForge.Minecraft.Nbt.INbt;
import com.github.zhangsiyao.FasterForge.Minecraft.Resource.ResourceName;
import com.github.zhangsiyao.FasterForge.Minecraft.World.IWorldProxy;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public interface IItemProxy {

    Object getItem();
    int getMaxStackSize();

    IItemProxy setMaxStackSize(int size);

    void addPropertyOverride(ResourceName key, IItemPropertyProxy property);

    ItemPropertyProxy getProperty(ResourceName key);

    boolean updateItemStackNBT(INbt nbt);

    public boolean hasCustomProperties();

    EnumActionResult onItemUse(IPlayerProxy player, IWorldProxy worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ);

}
