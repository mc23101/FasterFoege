package io.gitee.zhangsisiyao.ForgeAPI.FasterForge.Event.Entity.Player;

import io.gitee.zhangsisiyao.ForgeAPI.Event.Entity.Player.PlayerChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ServerChatListener {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEvent(net.minecraftforge.event.ServerChatEvent event){
        if(event.getPlayer().world.isRemote){
            MinecraftForge.EVENT_BUS.post(new PlayerChatEvent(event.getPlayer(),event.getMessage(), Side.CLIENT));
        }else{
            MinecraftForge.EVENT_BUS.post(new PlayerChatEvent(event.getPlayer(),event.getMessage(), Side.SERVER));
        }
    }
}
