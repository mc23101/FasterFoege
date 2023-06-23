package com.github.zhangsiyao.FasterForge.Minecraft.Item;

import com.github.zhangsiyao.FasterForge.Minecraft.Block.IBlockPosProxy;
import com.github.zhangsiyao.FasterForge.Minecraft.Block.IBlockStateProxy;
import com.github.zhangsiyao.FasterForge.Minecraft.Constant.Action;
import com.github.zhangsiyao.FasterForge.Minecraft.Item.impl.ItemPropertyProxy;
import com.github.zhangsiyao.FasterForge.Minecraft.Nbt.INbt;
import com.github.zhangsiyao.FasterForge.Minecraft.Resource.ResourceName;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public interface IItemProxy {


    /* ======================================== 物品属性 =====================================*/
    /**
     * 获取Minecraft底层Item
     * @return 返回底层Item的Object
     * */
    Object getItem();

    /**
     * 获取最大物品堆数量
     * @return 最大物品堆数量
     * */
    int getMaxStackSize();

    /**
     * 设置最大物品堆数量
     * @param size 物品堆数量
     * @return 修改后物品的引用
     * * */
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

    public Action getItemUseAction(IItemStackProxy stack);

    int getMaxItemUseDuration(IItemStackProxy stack);
    /* ======================================== 物品属性 =====================================*/

    /* ======================================== 物品事件 =====================================*/


    /* ======================================== 物品事件 =====================================*/

}
