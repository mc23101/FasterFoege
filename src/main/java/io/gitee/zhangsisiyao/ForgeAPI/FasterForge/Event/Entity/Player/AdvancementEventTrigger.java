package io.gitee.zhangsisiyao.ForgeAPI.FasterForge.Event.Entity.Player;

import io.gitee.zhangsisiyao.ForgeAPI.Event.Entity.Player.PlayerAdvancementEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

public class AdvancementEventTrigger {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEvent(AdvancementEvent event){
        if(event.getEntityPlayer().world.isRemote){
            MinecraftForge.EVENT_BUS.post(new PlayerAdvancementEvent(event.getEntityPlayer(), event.getAdvancement(), Side.CLIENT));
        }else {
            MinecraftForge.EVENT_BUS.post(new PlayerAdvancementEvent(event.getEntityPlayer(), event.getAdvancement(), Side.SERVER));
        }

    }
}
