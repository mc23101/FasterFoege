package com.github.zhangsiyao.FasterForge.Proxy.Item.impl;

import com.github.zhangsiyao.FasterForge.Minecraft.Constant.Hand;
import com.github.zhangsiyao.FasterForge.Proxy.Block.impl.BlockPosProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Block.impl.BlockStateProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Entity.Player.impl.PlayerProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Entity.impl.EntityItemProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Entity.impl.EntityLivingBaseProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Entity.impl.EntityProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Entity.impl.MobEntityProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Item.IItemProxy;
import com.github.zhangsiyao.FasterForge.Proxy.World.impl.WorldProxy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
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

    private OnItemTick onItemTick;

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

    private OnAttackEntity onAttackEntity;

    protected ItemProxy(Item item){
        this.item=item;
    }

    public ItemProxy(){
        item=new Item() {

            @Override
            public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
                onItemUse.run(new PlayerProxy(player));
                return EnumActionResult.SUCCESS;
            }

            @Override
            public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
                onRightClick.run(new PlayerProxy(playerIn));
                return null;
            }

            @Override
            public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
                onItemUseEnd.run(new ItemStackProxy(stack), new WorldProxy(worldIn), new EntityLivingBaseProxy(entityLiving));
                return null;
            }

            @Override
            public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
                onPlayerStoppedUsing.run(new ItemStackProxy(stack),new WorldProxy(worldIn),new EntityLivingBaseProxy(entityLiving),timeLeft);
            }

            @Override
            public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
                onAttackEntity.run(new ItemStackProxy(stack),new EntityLivingBaseProxy(target),new EntityLivingBaseProxy(attacker));
                return true;
            }

            @Override
            public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
                onBlockDestroyed.run(new ItemStackProxy(stack),new WorldProxy(worldIn),new BlockStateProxy(state),new BlockPosProxy(pos),new EntityLivingBaseProxy(entityLiving));
                return true;
            }

            @Override
            public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
                onInteractionEntity.run(new ItemStackProxy(stack),new PlayerProxy(playerIn),new EntityLivingBaseProxy(target),hand==EnumHand.MAIN_HAND? Hand.MAIN_HAND:Hand.OFF_HAND);
                return true;
            }

            @Override
            public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
                onItemTick.run(new ItemStackProxy(stack),new WorldProxy(worldIn),new EntityProxy(entityIn),itemSlot,isSelected);
            }

            @Override
            public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
                onDroppedByPlayer.run(new ItemStackProxy(item),new PlayerProxy(player));
                return super.onDroppedByPlayer(item, player);
            }

            @Override
            public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
                onItemUseBegin.run(new PlayerProxy(player),new WorldProxy(world),new BlockPosProxy(pos),null,hitX,hitY,hitZ,hand==EnumHand.MAIN_HAND? Hand.MAIN_HAND:Hand.OFF_HAND);
                return null;
            }

            @Override
            public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
                onLeftClickEntity.run(new ItemStackProxy(stack),new PlayerProxy(player),new EntityProxy(entity));
                return true;
            }

            @Override
            public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
                onBlockStartBreak.run(new ItemStackProxy(itemstack),new BlockPosProxy(pos),new PlayerProxy(player));
                return true;
            }

            @Override
            public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
                onUsingTick.run(new ItemStackProxy(stack),new EntityLivingBaseProxy(player),count);
                super.onUsingTick(stack, player, count);
            }

            @Override
            public boolean onEntityItemUpdate(EntityItem entityItem) {
                onEntityItemTick.run(new EntityItemProxy(entityItem));
                return true;
            }

            @Override
            public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
                onArmorTick.run(new WorldProxy(world),new PlayerProxy(player),new ItemStackProxy(itemStack));
            }

            @Override
            public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
                onEntitySwingTick.run(new EntityLivingBaseProxy(entityLiving),new ItemStackProxy(stack));
                return true;
            }

            @Override
            public void onHorseArmorTick(World world, EntityLiving horse, ItemStack armor) {
                onHorseArmorTick.run(new WorldProxy(world),new MobEntityProxy(horse),new ItemStackProxy(armor));
            }

            @Override
            public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
                onItemCreated.run(new ItemStackProxy(stack),new WorldProxy(worldIn),new PlayerProxy(playerIn));
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
    public void onItemTick(OnItemTick onItemTick) {
        this.onItemTick=onItemTick;
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

    @Override
    public void onAttackEntity(OnAttackEntity onAttackEntity) {
        this.onAttackEntity=onAttackEntity;
    }
}
