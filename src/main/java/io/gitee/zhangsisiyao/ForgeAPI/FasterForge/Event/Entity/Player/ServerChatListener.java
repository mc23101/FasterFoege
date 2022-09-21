package io.gitee.zhangsisiyao.ForgeAPI.FasterForge.Event.Entity.Player;

import io.gitee.zhangsisiyao.ForgeAPI.Event.Entity.Player.PlayerChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ServerChatListener {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEvent(net.minecraftforge.event.ServerChatEvent event){
        if(MinecraftForge.EVENT_BUS.post(new PlayerChatEvent(event.getPlayer(),event.getMessage()))){
            event.setCanceled(true);
        }
    }
}
