package io.gitee.zhangsisiyao.ForgeAPI.FasterForge.Event.Entity.Player;

import io.gitee.zhangsisiyao.ForgeAPI.Event.Entity.Player.PlayerAdvancementEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AdvancementListener {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEvent(AdvancementEvent event){
        MinecraftForge.EVENT_BUS.post(new PlayerAdvancementEvent(event.getEntityPlayer(), event.getAdvancement()));
    }
}
