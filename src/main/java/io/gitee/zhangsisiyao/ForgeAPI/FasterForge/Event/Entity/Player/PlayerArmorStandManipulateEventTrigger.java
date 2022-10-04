package io.gitee.zhangsisiyao.ForgeAPI.FasterForge.Event.Entity.Player;

import io.gitee.zhangsisiyao.ForgeAPI.Event.Entity.Player.PlayerArmorStandManipulateEvent;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerArmorStandManipulateEventTrigger {
    @SubscribeEvent
    public static void onEntityInterfaceSpec(PlayerInteractEvent.EntityInteractSpecific event){
        if(event.getTarget() instanceof EntityArmorStand){
            EntityArmorStand armorStand=(EntityArmorStand) event.getTarget();
            if(!event.getEntityPlayer().world.isRemote){
                if(ServerEvent(event.getEntityPlayer(), armorStand,event.getLocalPos())){
                    event.setCanceled(true);
                }
            }else {
                ClientEvent(event.getEntityPlayer(), armorStand,event.getLocalPos());
            }
        }
    }

       private static boolean ServerEvent(EntityPlayer entityPlayer, EntityArmorStand armorStand, Vec3d vec3d){
        return postPlayerArmorStandManipulateEvent(entityPlayer,armorStand,vec3d,Side.SERVER);
    }

    @SideOnly(Side.CLIENT)
    private static boolean ClientEvent(EntityPlayer entityPlayer, EntityArmorStand armorStand,Vec3d vec3d){
        return postPlayerArmorStandManipulateEvent(entityPlayer,armorStand,vec3d,Side.CLIENT);
    }

    private static boolean postPlayerArmorStandManipulateEvent(EntityPlayer entityPlayer,
                                                               EntityArmorStand armorStand,
                                                               Vec3d vec3d,Side side){
        ItemStack handItemStack=entityPlayer.getHeldItemMainhand();
        EntityEquipmentSlot clickedSlot=getClickedSlot(armorStand,vec3d);
        ItemStack armorStandItem = armorStand.getItemStackFromSlot(clickedSlot);
        PlayerArmorStandManipulateEvent event = null;
        //玩家手中物品为空，点击盔甲架物品为空，则不触发事件
        if(handItemStack.isEmpty()&&armorStandItem.isEmpty()){
            return false;
        }
        //玩家手中物品为空，点击盔甲架物品不为空，则触发事件
        if(!armorStandItem.isEmpty()&& handItemStack.isEmpty()){
            event= new PlayerArmorStandManipulateEvent(entityPlayer, armorStand, handItemStack, armorStandItem, clickedSlot, side);
        }
        //玩家手中不为空,点击盔甲架交换或放置
        if(!handItemStack.isEmpty()){
            EntityEquipmentSlot equipmentSlot = handItemStack.getItem().getEquipmentSlot(handItemStack);
            if(equipmentSlot!=null && equipmentSlot!=EntityEquipmentSlot.MAINHAND&&equipmentSlot!=EntityEquipmentSlot.OFFHAND){
                event= new PlayerArmorStandManipulateEvent(entityPlayer, armorStand, handItemStack, armorStandItem, equipmentSlot, side);
            }
        }
        return event != null && MinecraftForge.EVENT_BUS.post(event);
    }

    private static EntityEquipmentSlot getClickedSlot(EntityArmorStand armorStand, Vec3d vec3d){
        EntityEquipmentSlot entityequipmentslot =EntityEquipmentSlot.MAINHAND;
        boolean flag = armorStand.isSmall();
        double d0 = flag ? vec3d.y * 2.0D : vec3d.y;
        if (d0 >= 0.1D && d0 < 0.1D + (flag ? 0.8D : 0.45D) )
        {
            entityequipmentslot = EntityEquipmentSlot.FEET;
        }
        else if (d0 >= 0.9D + (flag ? 0.3D : 0.0D) && d0 < 0.9D + (flag ? 1.0D : 0.7D))
        {
            entityequipmentslot = EntityEquipmentSlot.CHEST;
        }
        else if (d0 >= 0.4D && d0 < 0.4D + (flag ? 1.0D : 0.8D) )
        {
            entityequipmentslot = EntityEquipmentSlot.LEGS;
        }
        else if (d0 >= 1.6D )
        {
            entityequipmentslot = EntityEquipmentSlot.HEAD;
        }

        return entityequipmentslot;
    }

}
