package io.gitee.zhangsisiyao.ForgeAPI.FasterForge.Event;

import io.gitee.zhangsisiyao.ForgeAPI.Event.Entity.Player.PlayerChatEvent;
import net.minecraftforge.common.MinecraftForge;

public class ServerChatListener {
    public static void onChat(net.minecraftforge.event.ServerChatEvent event){
        MinecraftForge.EVENT_BUS.post(new PlayerChatEvent(event.getPlayer(),event.getMessage()));
    }
}
