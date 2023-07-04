package com.github.zhangsiyao.FasterForge.Proxy.Item.impl;

import com.github.zhangsiyao.FasterForge.Minecraft.Constant.Hand;
import com.github.zhangsiyao.FasterForge.Proxy.Block.impl.BlockPosProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Entity.Player.Impl.PlayerProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Item.IItemProxy;
import com.github.zhangsiyao.FasterForge.Proxy.World.impl.WorldProxy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
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


    private OnItemUse onItemUse;

    private OnRightClick onRightClick;

    private OnItemUseEnd onItemUseEnd;

    private OnBlockDestroyed onBlockDestroyed;

    private OnInteractionEntity onInteractionEntity;

    private OnItemUpdate onItemUpdate;

    private OnItemCreated onItemCreated;

    private OnPlayerStoppedUsing onPlayerStoppedUsing;

    private OnDroppedByPlayer onDroppedByPlayer;

    private  OnItemUseBegin onItemUseBegin;

    private OnBlockStartBreak onBlockStartBreak;

    private OnUsingTick onUsingTick;

    private OnLeftClickEntity onLeftClickEntity;

    private OnArmorTick onArmorTick;

    private OnEntityItemTick onEntityItemTick;

    private OnEntitySwingTick onEntitySwingTick;

    private OnHorseArmorTick onHorseArmorTick;



    protected ItemProxy(Item item){
        this.item=item;
    }

    public ItemProxy(){
        item=new Item() {

            @Override
            public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
                onItemUse.run(new PlayerProxy(player),new WorldProxy(worldIn),new BlockPosProxy(pos),getHand(),null,hitX,hitY,hitZ);
                return EnumActionResult.SUCCESS;
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


    @Override
    public void onItemUse(OnItemUse itemUse) {
        this.onItemUse=itemUse;
    }

    @Override
    public void onRightClick(OnRightClick onRightClick) {
        this.onRightClick=onRightClick;
    }

    @Override
    public void onItemUseEnd(OnItemUseEnd onItemUseFinish) {
        this.onItemUseEnd=onItemUseFinish;
    }

    @Override
    public void onBlockDestroyed(OnBlockDestroyed onBlockDestroyed) {
        this.onBlockDestroyed=onBlockDestroyed;
    }

    @Override
    public void onInteractionEntity(OnInteractionEntity onInteractionEntity) {
        this.onInteractionEntity=onInteractionEntity;
    }

    @Override
    public void onItemUpdate(OnItemUpdate onItemUpdate) {
        this.onItemUpdate=onItemUpdate;
    }

    @Override
    public void onItemCreated(OnItemCreated onItemCreated) {
        this.onItemCreated=onItemCreated;
    }

    @Override
    public void onPlayerStoppedUsing(OnPlayerStoppedUsing onPlayerStoppedUsing) {
        this.onPlayerStoppedUsing=onPlayerStoppedUsing;
    }

    @Override
    public void onDroppedByPlayer(OnDroppedByPlayer onDroppedByPlayer) {
        this.onDroppedByPlayer=onDroppedByPlayer;
    }

    @Override
    public void onItemUseBegin(OnItemUseBegin onItemUseBegin) {
        this.onItemUseBegin=onItemUseBegin;
    }

    @Override
    public void onBlockStartBreak(OnBlockStartBreak onBlockStartBreak) {
        this.onBlockStartBreak=onBlockStartBreak;
    }

    @Override
    public void onUsingTick(OnUsingTick onUsingTick) {
        this.onUsingTick=onUsingTick;
    }

    @Override
    public void onLeftClickEntity(OnLeftClickEntity onLeftClickEntity) {
        this.onLeftClickEntity=onLeftClickEntity;
    }

    @Override
    public void onArmorTick(OnArmorTick onArmorTick) {
        this.onArmorTick=onArmorTick;
    }

    @Override
    public void onEntityItemTick(OnEntityItemTick onEntityItemTick) {
        this.onEntityItemTick=onEntityItemTick;
    }

    @Override
    public void onEntitySwing(OnEntitySwingTick onEntitySwingTick) {
        this.onEntitySwingTick=onEntitySwingTick;
    }

    @Override
    public void onHouseArmorTick(OnHorseArmorTick onHorseArmorTick) {
        this.onHorseArmorTick=onHorseArmorTick;
    }
}
