package io.gitee.zhangsisiyao.FasterForge.ForgeBoot;

import io.gitee.zhangsisiyao.FasterForge.ForgeBoot.Event.Entity.Player.*;
import net.minecraftforge.common.MinecraftForge;

public class FasterForge {
    public static void registerTrigger(){
        PlayerEventTrigger();
    }

    private static void PlayerEventTrigger(){
        MinecraftForge.EVENT_BUS.register(PlayerChatTrigger.class);
        MinecraftForge.EVENT_BUS.register(PlayerAdvancementEventTrigger.class);
        MinecraftForge.EVENT_BUS.register(PlayerGameModeChangeEventTrigger.class);
        MinecraftForge.EVENT_BUS.register(PlayerJoinEventTrigger.class);
        MinecraftForge.EVENT_BUS.register(PlayerArmorStandManipulateEventTrigger.class);
    }
}
