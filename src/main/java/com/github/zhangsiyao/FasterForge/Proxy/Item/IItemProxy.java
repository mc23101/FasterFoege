package com.github.zhangsiyao.FasterForge.Proxy.Item;

import com.github.zhangsiyao.FasterForge.Proxy.Block.IBlockPosProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Block.IBlockStateProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Constant.Action;
import com.github.zhangsiyao.FasterForge.Proxy.Constant.Facing;
import com.github.zhangsiyao.FasterForge.Proxy.Constant.Hand;
import com.github.zhangsiyao.FasterForge.Proxy.Entity.IEntityBase;
import com.github.zhangsiyao.FasterForge.Proxy.Entity.Player.IPlayerProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Item.impl.ItemPropertyProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Nbt.INbt;
import com.github.zhangsiyao.FasterForge.Minecraft.Resource.ResourceName;
import com.github.zhangsiyao.FasterForge.Proxy.World.IWorldProxy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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

    float getDestroySpeed();

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
    /**
     * 物品Item使用事件
     * */
    void onItemUse(OnItemUse itemUse);


    @FunctionalInterface
    interface  OnItemRightClick{
        void run(IWorldProxy worldProxy,IPlayerProxy playerProxy,Hand hand);
    }
    /**
     * 物品Item右键时事件
     * */
    void onItemRightClick(OnItemRightClick onItemRightClick);

    @FunctionalInterface
    interface OnItemUseFinish{
        void run(IItemStackProxy stack, IWorldProxy worldIn, IEntityBase entity);
    }
    /**
     * 物品Item使用完成事件
     * */
    void onItemUseFinish(OnItemUseFinish onItemUseFinish);


    @FunctionalInterface
    interface OnBlockDestroyed{
        void run(IItemStackProxy stack, IWorldProxy worldIn, IBlockStateProxy state, IBlockPosProxy pos, IEntityBase entityBase);
    }
    /**
     * 手持物品Item破坏方块Block触发的事件
     * */
    void onBlockDestroyed(OnBlockDestroyed onBlockDestroyed);

    /* ======================================== 物品事件 =====================================*/

}
