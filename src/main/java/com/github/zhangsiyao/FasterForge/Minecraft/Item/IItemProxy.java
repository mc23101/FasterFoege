package com.github.zhangsiyao.FasterForge.Minecraft.Item;

import com.github.zhangsiyao.FasterForge.Minecraft.Block.IBlockPosProxy;
import com.github.zhangsiyao.FasterForge.Minecraft.Block.IBlockStateProxy;
import com.github.zhangsiyao.FasterForge.Minecraft.Constant.Action;
import com.github.zhangsiyao.FasterForge.Minecraft.Constant.Facing;
import com.github.zhangsiyao.FasterForge.Minecraft.Constant.Hand;
import com.github.zhangsiyao.FasterForge.Minecraft.Entity.Player.IPlayerProxy;
import com.github.zhangsiyao.FasterForge.Minecraft.Item.impl.ItemPropertyProxy;
import com.github.zhangsiyao.FasterForge.Minecraft.Nbt.INbt;
import com.github.zhangsiyao.FasterForge.Minecraft.Resource.ResourceName;
import com.github.zhangsiyao.FasterForge.Minecraft.World.IWorldProxy;
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


    /**
     * 新增/覆盖物品的property属性
     * @param key 属性键
     * @param property 属性值
     * */
    void addPropertyOverride(ResourceName key, IItemPropertyProxy property);

    /**
     * 获取物品property属性(只能在Client调用)
     * @param key 属性键名
     * @return 返回修改后的物品代理
     * */
    ItemPropertyProxy getProperty(ResourceName key);


    /**
     * 更新物品item的nbt数据
     * @param nbt 要更新的nbt数据
     * */
    boolean updateItemStackNBT(INbt nbt);

    boolean hasCustomProperties();

    float getDestroySpeed(ItemStack stack, IBlockPosProxy state);

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
    @FunctionalInterface
    interface OnItemUse{
        void run(IPlayerProxy playerProxy, IWorldProxy worldProxy, IBlockPosProxy blockPosProxy, Hand hand, Facing facing, float hitX, float hitY, float hitZ);
    }
    void onItemUse(OnItemUse itemUse);

    @FunctionalInterface
    interface  OnItemRightClick{
        void run(IWorldProxy worldProxy,IPlayerProxy playerProxy,Hand hand);
    }
    void onItemRightClick(OnItemRightClick onItemRightClick);

    interface OnItemUseFinish{
        void run();
    }
    void onItemUseFinish(OnItemUseFinish onItemUseFinish);

    /* ======================================== 物品事件 =====================================*/

}
