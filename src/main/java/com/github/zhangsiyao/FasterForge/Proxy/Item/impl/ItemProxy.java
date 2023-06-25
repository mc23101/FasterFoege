package com.github.zhangsiyao.FasterForge.Proxy.Item.impl;

import com.github.zhangsiyao.FasterForge.Proxy.Block.IBlockStateProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Constant.Action;
import com.github.zhangsiyao.FasterForge.Proxy.Item.IItemPropertyProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Item.IItemProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Item.IItemStackProxy;
import com.github.zhangsiyao.FasterForge.Proxy.Nbt.INbt;
import com.github.zhangsiyao.FasterForge.Minecraft.Resource.ResourceName;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

/**
 * FasterForge的物品代理，用于对Minecraft的Item进行操作.
 * 在进行版本移植时，应相应修改此类.
 * */
//TODO 在版本移植时，修改此类信息.
@SuppressWarnings("all")
public class ItemProxy implements IItemProxy {
    private final Item item;

   private OnItemUseFinish onItemUseFinish;

   private OnItemRightClick onItemRightClick;

    protected ItemProxy(Item item){
        this.item=item;
    }

    public ItemProxy(){
        item=new Item() {

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
    public void onItemRightClick(OnItemRightClick onItemRightClick) {
        this.onItemRightClick=onItemRightClick;
    }

    @Override
    public void onItemUseFinish(OnItemUseFinish onItemUseFinish) {

    }



}
