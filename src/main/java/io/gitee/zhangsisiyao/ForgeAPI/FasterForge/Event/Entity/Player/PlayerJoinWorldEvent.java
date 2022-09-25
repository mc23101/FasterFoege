package io.gitee.zhangsisiyao.ForgeAPI.FasterForge.Event.Entity.Player;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerJoinWorldEvent {
    @SubscribeEvent
    public static void onEvent(EntityJoinWorldEvent event){
        if(event.getWorld().isRemote){
            if(event.getEntity() instanceof EntityPlayerSP){
                onClientEvent((EntityPlayerSP)event.getEntity(),event.getWorld());
            }
        }else {
            if(event.getEntity() instanceof EntityPlayerMP){
                onServerEvent((EntityPlayerMP) event.getEntity(),event.getWorld());
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private static void onClientEvent(EntityPlayerSP playerSP,World world){

    }

    private static void onServerEvent(EntityPlayerMP playerMP, World world){

    }
}
