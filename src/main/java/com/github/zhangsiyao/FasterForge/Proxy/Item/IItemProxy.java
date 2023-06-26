package com.github.zhangsiyao.FasterForge.Proxy.Item;

import com.github.zhangsiyao.FasterForge.Minecraft.Resource.ResourceName;
import com.github.zhangsiyao.FasterForge.Proxy.Block.IBlockPosProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Block.IBlockStateProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Constant.Action;
import com.github.zhangsiyao.FasterForge.Proxy.Constant.Facing;
import com.github.zhangsiyao.FasterForge.Proxy.Constant.Hand;
import com.github.zhangsiyao.FasterForge.Proxy.Entity.IEntity;
import com.github.zhangsiyao.FasterForge.Proxy.Entity.IEntityLivingBase;
import com.github.zhangsiyao.FasterForge.Proxy.Entity.Player.IPlayerProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Item.impl.ItemPropertyProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Nbt.INbt;
import com.github.zhangsiyao.FasterForge.Proxy.World.IWorldProxy;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
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
        void run(IItemStackProxy stack, IWorldProxy worldIn, IEntityLivingBase entity);
    }
    /**
     * 物品Item使用完成事件
     * */
    void onItemUseFinish(OnItemUseFinish onItemUseFinish);


    @FunctionalInterface
    interface OnBlockDestroyed{
        void run(IItemStackProxy stack, IWorldProxy worldIn, IBlockStateProxy state, IBlockPosProxy pos, IEntityLivingBase entityBase);
    }
    /**
     * 手持物品Item破坏方块Block触发的事件
     * */
    void onBlockDestroyed(OnBlockDestroyed onBlockDestroyed);

    @FunctionalInterface
    interface OnItemInteractionEntity{
        void run(IItemStackProxy stack, IPlayerProxy playerIn, IEntityLivingBase target, Hand hand);
    }
    /**
     * 手持物品Item交互实体触发的事件
     * */
    void onItemInteractionEntity(OnItemInteractionEntity onItemInteractionEntity);


    @FunctionalInterface
    interface OnItemUpdate{
        void run(IItemStackProxy stack, IWorldProxy worldIn, IEntity entityIn, int itemSlot, boolean isSelected);
    }
    /**
     * 物品Item在玩家背包时每tick都会执行此事件
     * */
    void onItemUpdate(OnItemUpdate onItemUpdate);


    @FunctionalInterface
    interface OnItemCreated{
        void run(IItemStackProxy stack, IWorldProxy worldIn, IPlayerProxy playerIn);
    }
    /**
     * 猜测
     * 物品Item在合成表合成出的时候触发的事件
     * */
    //TODO 检验这个是什么事件
    void onItemCreated(OnItemCreated onItemCreated);

    @FunctionalInterface
    interface OnPlayerStoppedUsing{
        void run(IItemStackProxy stack, IWorldProxy worldIn, IEntityLivingBase entityLiving, int timeLeft);
    }
    void onPlayerStoppedUsing(OnPlayerStoppedUsing onPlayerStoppedUsing);


    @FunctionalInterface
    interface OnDroppedByPlayer{
        /**
         * 当玩家将道具掉落到游戏世界中时调用
         * @param item 物品堆(物品堆改变之前的数据)
         * @param player 掉落物品的玩家
         * @return 返回false将阻止道具从玩家的库存中移除并在游戏世界中生成
         * */
        boolean run(IItemStackProxy item, IPlayerProxy player);
    }
    /**
     * 玩家掉落物品Item触发的事件
     * */
    void onDroppedByPlayer(OnDroppedByPlayer onDroppedByPlayer);


    @FunctionalInterface
    interface OnItemUseFirst{
        /**
         * 当物品使用时被调用(在方块生成之间被调用)
         * @param player 使用物品的玩家
         * @param world 当前的世界
         * @param pos 目标位置
         * @param side 玩家面向目标的方向
         * @param hand 玩家哪只手持有物品
         * */
        void run(IPlayerProxy player, IWorldProxy world, IBlockPosProxy pos,Facing side, float hitX, float hitY, float hitZ, Hand hand);
    }
    void onItemUseFirst(OnItemUseFirst onItemUseFirst);

    @FunctionalInterface
    interface OnBlockStartBreak{
        /**
         * 方块被破坏之前被调用</br>
         * Note：在客户端和服务器端都被调用!
         * @param stack 当前使用的物品堆
         * @param pos 方块的位置
         * @param player 手持此物品的玩家
         * */
        boolean run(IItemStackProxy stack, IBlockPosProxy pos,IPlayerProxy player);
    }
    void onBlockStartBreak(OnBlockStartBreak onBlockStartBreak);
    /* ======================================== 物品事件 =====================================*/

}
