package com.github.zhangsiyao.FasterForge.Proxy.Item.impl;

import com.github.zhangsiyao.FasterForge.Proxy.Block.IBlockStateProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Constant.Action;
import com.github.zhangsiyao.FasterForge.Proxy.Item.IItemPropertyProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Item.IItemProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Item.IItemStackProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Nbt.INbt;
import com.github.zhangsiyao.FasterForge.Minecraft.Resource.ResourceName;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * FasterForge的物品代理，用于对Minecraft的Item进行操作.
 * 在进行版本移植时，应相应修改此类.
 * */
//TODO 在版本移植时，修改此类信息.
@SuppressWarnings("all")
public class ItemProxy implements IItemProxy {
    private final Item item;

   private OnItemUseFinish onItemUseFinish;

   private OnRightClick onRightClick;

    protected ItemProxy(Item item){
        this.item=item;
    }

    public ItemProxy(){
        item=new Item() {
            @Override
            public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
                return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
            }

            @Override
            public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
                onRightClick.run(null,null,null);
                return super.onItemRightClick(worldIn, playerIn, handIn);
            }

            @Override
            public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
                return super.onItemUseFinish(stack, worldIn, entityLiving);
            }

            @Override
            public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
                System.out.println("攻击实体");
                return super.hitEntity(stack, target, attacker);
            }

            @Override
            public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
                System.out.println("破坏方块");
                return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
            }

            @Override
            public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
                System.out.println("交互实体");
                return super.itemInteractionForEntity(stack, playerIn, target, hand);
            }

            @Override
            public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

                super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
            }

            @Override
            public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
                System.out.println("ItemCreated");
                super.onCreated(stack, worldIn, playerIn);
            }


        };
    }



}
