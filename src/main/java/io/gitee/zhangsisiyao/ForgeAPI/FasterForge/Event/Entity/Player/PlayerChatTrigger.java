package io.gitee.zhangsisiyao.ForgeAPI.FasterForge.Event.Entity.Player;

import io.gitee.zhangsisiyao.ForgeAPI.Event.Entity.Player.PlayerChatEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

public class PlayerChatTrigger {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onServerChat(net.minecraftforge.event.ServerChatEvent event){
        if(MinecraftForge.EVENT_BUS.post(new PlayerChatEvent(event.getPlayer(),event.getMessage(), Side.SERVER))){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onClientChat(ClientChatEvent event){
        if(MinecraftForge.EVENT_BUS.post(new PlayerChatEvent(Minecraft.getMinecraft().player,event.getMessage(), Side.CLIENT))){
            event.setCanceled(true);
        }
    }
}
