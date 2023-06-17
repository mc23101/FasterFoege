package com.github.zhangsiyao.FasterForge.ForgeBoot.EventTrigger.Enitity.Player;

import com.github.zhangsiyao.FasterForge.Minecraft.Event.Player.PlayerBedEnterEvent;
import com.github.zhangsiyao.FasterForge.Minecraft.Event.Player.PlayerBedLeaveEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerBedEventTrigger {

    public static Map<String, BlockPos> playerSleepBedPos=new Hashtable<>();

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onSleepInEvent(PlayerSleepInBedEvent event){
        if(!event.getEntityPlayer().world.isRemote){
            ServerSleepIn(event);
        }else {
            ClientSleepIn(event);
        }
    }

    @SubscribeEvent(priority =EventPriority.HIGHEST)
    public static void onWorldTickEvent(TickEvent.WorldTickEvent event){
        MinecraftServer minecraftServer = FMLCommonHandler.instance().getMinecraftServerInstance();
        if(minecraftServer!=null){
            List<EntityPlayerMP> players = minecraftServer.getPlayerList().getPlayers();
            Set<String> playerSet=new HashSet<>();
            for(EntityPlayerMP playerMP:players) {
                String id = playerMP.getUniqueID().toString();
                playerSet.add(id);
            }
            Map<String, BlockPos> map = new ConcurrentHashMap<>(playerSleepBedPos);
            for(Map.Entry<String, BlockPos> entry : map.entrySet()){
                if(!playerSet.contains(entry.getKey())){
                    playerSleepBedPos.remove(entry.getKey());
                }
            }
        }
    }

    private static void ServerSleepIn(PlayerSleepInBedEvent event){
        Side side=Side.SERVER;
        String uuid=event.getEntityPlayer().getUniqueID().toString();
        playerSleepBedPos.put(uuid,event.getPos());
        MinecraftForge.EVENT_BUS.post(new PlayerBedEnterEvent(
                event.getEntityPlayer(),
                event.getPos(),
                event.getResultStatus(),side));
    }

    @SideOnly(Side.CLIENT)
    private static void ClientSleepIn(PlayerSleepInBedEvent event){
        Side side=Side.CLIENT;
        String uuid=event.getEntityPlayer().getUniqueID().toString();
        playerSleepBedPos.put(uuid,event.getPos());
        MinecraftForge.EVENT_BUS.post(new PlayerBedEnterEvent(
                event.getEntityPlayer(),
                event.getPos(),
                event.getResultStatus(),side));
    }


    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onWakeupEvent(PlayerWakeUpEvent event){
        if(!event.getEntityPlayer().world.isRemote){
            ServerWakeUp(event);
        }else {
            ClientWakeUp(event);
        }
    }

    private static void ServerWakeUp(PlayerWakeUpEvent event){
        Side side= Side.SERVER;
        EntityPlayer player=event.getEntityPlayer();
        MinecraftForge.EVENT_BUS.post(new PlayerBedLeaveEvent(
                player,
                playerSleepBedPos.get(player.getUniqueID().toString()),
                event.shouldSetSpawn(),side));
        playerSleepBedPos.remove(player.getUniqueID().toString());
    }

    @SideOnly(Side.CLIENT)
    private static void ClientWakeUp(PlayerWakeUpEvent event){
        Side side= Side.CLIENT;
        EntityPlayer player=event.getEntityPlayer();
        MinecraftForge.EVENT_BUS.post(new PlayerBedLeaveEvent(
                player,
                playerSleepBedPos.get(player.getUniqueID().toString()),
                event.shouldSetSpawn(),side));
        playerSleepBedPos.remove(player.getUniqueID().toString());
    }

}
