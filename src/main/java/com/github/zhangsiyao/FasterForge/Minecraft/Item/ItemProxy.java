package com.github.zhangsiyao.FasterForge.Minecraft.Item;

import com.github.zhangsiyao.FasterForge.Minecraft.Block.BlockProxy;
import net.minecraft.item.Item;

/**
 * FasterForge的物品代理，用于对Minecraft的Item进行操作.
 * 在进行版本移植时，应相应修改此类.
 * */
//TODO 在版本移植时，修改此类信息.
@SuppressWarnings("all")
public class ItemProxy {
    private final Item item;

    private ItemProxy(Item item){
        this.item=item;
    }

    public ItemProxy(){
        item=new Item();
    }

    public static int getIdFromItem(ItemProxy item){
        return Item.getIdFromItem(item.item);
    }

    public static ItemProxy getItemById(int id)
    {
        return new ItemProxy(Item.getItemById(id));
    }

    public static ItemProxy getItemFromBlock(BlockProxy block)
    {
        return new ItemProxy(Item.getItemFromBlock(block.getBlock()));
    }

    public Item getItem() {
        return item;
    }




    public int getMaxStackSize(){
        return item.getItemStackLimit();
    }

    public ItemProxy setMaxStackSize(int size){
        item.setMaxStackSize(size);
        return this;
    }


}
