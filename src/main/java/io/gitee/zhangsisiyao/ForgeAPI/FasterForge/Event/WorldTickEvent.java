package io.gitee.zhangsisiyao.ForgeAPI.FasterForge.Event;

import io.gitee.zhangsisiyao.ForgeAPI.FasterForge.Event.Entity.Player.PlayerJoinEventTrigger;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorldTickEvent {
    @SubscribeEvent
    public static void onEvent(TickEvent.WorldTickEvent event){
        MinecraftServer minecraftServer = FMLCommonHandler.instance().getMinecraftServerInstance();
        if(minecraftServer!=null){
            List<EntityPlayerMP> players = minecraftServer.getPlayerList().getPlayers();
            Set<String> playerSet=new HashSet<>();
            for(EntityPlayerMP playerMP:players){
                String s=playerMP.getUniqueID().toString();
                playerSet.add(s);
            }
            for(String s:PlayerJoinEventTrigger.playerJoinList){
                if(!playerSet.contains(s)){
                    PlayerJoinEventTrigger.playerJoinList.remove(s);
                }
            }
        }
    }
}
