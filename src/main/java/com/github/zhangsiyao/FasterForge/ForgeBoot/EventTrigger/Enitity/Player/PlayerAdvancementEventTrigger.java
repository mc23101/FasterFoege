package com.github.zhangsiyao.FasterForge.ForgeBoot.EventTrigger.Enitity.Player;

import com.github.zhangsiyao.FasterForge.Minecraft.Event.Player.PlayerAdvancementEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

public class PlayerAdvancementEventTrigger {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onAdvancementEvent(AdvancementEvent event){
        if(event.getEntityPlayer().world.isRemote){
            MinecraftForge.EVENT_BUS.post(new PlayerAdvancementEvent(event.getEntityPlayer(), event.getAdvancement(), Side.CLIENT));
        }else {
            MinecraftForge.EVENT_BUS.post(new PlayerAdvancementEvent(event.getEntityPlayer(), event.getAdvancement(), Side.SERVER));
        }

    }
}
