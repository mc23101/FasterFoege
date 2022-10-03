package io.gitee.zhangsisiyao.ForgeAPI.FasterForge.Event.Entity.Player;

import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerArmorStandManipulateEventTrigger {
    @SubscribeEvent
    public static void onEntityInterfaceSpec(PlayerInteractEvent.EntityInteractSpecific event){
        if(event.getTarget() instanceof EntityArmorStand){
            EntityArmorStand armorStand=(EntityArmorStand) event.getTarget();

        }
    }

    private static void ServerEvent(){

    }

    @SideOnly(Side.CLIENT)
    private static void ClientEvent(){

    }
}
