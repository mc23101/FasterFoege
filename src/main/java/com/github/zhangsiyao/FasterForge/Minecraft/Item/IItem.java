package com.github.zhangsiyao.FasterForge.Minecraft.Item;

import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 为了方便后续向其他版本移植FasterForge,定义此接口，用来规范Item物品的所有方法名称
*/
public interface IItem {

    /**
     * 设置在玩家背包一堆物品的最大数量，默认为64
     * */
    public IItem setMaxItemStackSize(int maxStackSize);
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ);

    public float getDestroySpeed(ItemStack stack, IBlockState state);

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn);


    /* ======================================== FORGE START =====================================*/
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack);

}
