package com.github.zhangsiyao.FasterForge.ForgeBoot.EventTrigger.Enitity.Player;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * 尝试寻找实现方法中
 * */
public class PlayerPreLoginEventTrigger {
    public static void onWorldTick(TickEvent.WorldTickEvent event){
        MinecraftServer minecraftServer = FMLCommonHandler.instance().getMinecraftServerInstance();
        if(minecraftServer!=null){

        }
    }
}
