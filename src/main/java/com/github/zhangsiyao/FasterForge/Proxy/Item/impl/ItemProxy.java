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

    public Item getItem() {
        return item;
    }


    /**
     * 获取一堆物品的最大数量
     * @return 返回物品堆最大数量
     * */
    public final int getMaxStackSize(){
        return item.getItemStackLimit();
    }

    /**
     * 设置物品堆最大数量
     * @param size 物品堆数量
     * @return 返回修改后的物品代理
     * */
    public final IItemProxy setMaxStackSize(int size){
        item.setMaxStackSize(size);
        return this;
    }


    public final void addPropertyOverride(ResourceName key, IItemPropertyProxy property)
    {
        item.addPropertyOverride(new ResourceLocation(key.getLocation()), (IItemPropertyGetter) property.getProperty());
    }


    public final ItemPropertyProxy getProperty(ResourceName key){
        return new ItemPropertyProxy(item.getPropertyGetter(new ResourceLocation(key.getLocation())));
    }

    @Override
    public boolean updateItemStackNBT(INbt nbt) {
        return false;
    }

    @Override
    public boolean hasCustomProperties() {
        return false;
    }

    @Override
    public float getDestroySpeed() {
        return 0;
    }

    @Override
    public int getMaxDamage() {
        return 0;
    }

    @Override
    public IItemProxy setMaxDamage(int damage) {
        return null;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public boolean canHarvestBlock(IBlockStateProxy blockIn) {
        return false;
    }

    @Override
    public IItemProxy setFull3D() {
        return null;
    }

    @Override
    public boolean isFull3D() {
        return false;
    }

    @Override
    public boolean shouldRotateAroundWhenRendering() {
        return false;
    }

    @Override
    public IItemProxy setUnlocalizedName(String unlocalizedName) {
        return null;
    }

    @Override
    public String getUnlocalizedNameInefficiently(IItemStackProxy stack) {
        return null;
    }

    @Override
    public String getUnlocalizedName() {
        return null;
    }

    @Override
    public IItemProxy setContainerItem(IItemProxy containerItem) {
        return null;
    }

    @Override
    public boolean getShareTag() {
        return false;
    }

    @Override
    public IItemProxy getContainerItem() {
        return null;
    }

    @Override
    public boolean hasContainerItem() {
        return false;
    }

    @Override
    public boolean isMap() {
        return false;
    }

    @Override
    public Action getItemUseAction(IItemStackProxy stack) {
        return null;
    }

    @Override
    public int getMaxItemUseDuration(IItemStackProxy stack) {
        return 0;
    }

    @Override
    public void onItemUse(OnItemUse itemUse) {

    }

    @Override
    public void onItemRightClick(OnRightClick onRightClick) {
        this.onRightClick = onRightClick;
    }

    @Override
    public void onItemUseFinish(OnItemUseFinish onItemUseFinish) {

    }

    @Override
    public void onBlockDestroyed(OnBlockDestroyed onBlockDestroyed) {

    }

    @Override
    public void onItemInteractionEntity(OnInteractionEntity onInteractionEntity) {

    }

    @Override
    public void onItemUpdate(OnItemUpdate onItemUpdate) {

    }

    @Override
    public void onItemCreated(OnItemCreated onItemCreated) {

    }

    @Override
    public void onPlayerStoppedUsing(OnPlayerStoppedUsing onPlayerStoppedUsing) {

    }

    @Override
    public void onDroppedByPlayer(OnDroppedByPlayer onDroppedByPlayer) {

    }

    @Override
    public void onItemUseFirst(OnItemUseBegin onItemUseBegin) {

    }

    @Override
    public void onBlockStartBreak(OnBlockStartBreak onBlockStartBreak) {

    }

    @Override
    public void onUsingTick(OnUsingTick onUsingTick) {

    }

    @Override
    public void onLeftClickEntity(OnLeftClickEntity onLeftClickEntity) {

    }

    @Override
    public void onArmorTick(OnArmorTick onArmorTick) {

    }


}
