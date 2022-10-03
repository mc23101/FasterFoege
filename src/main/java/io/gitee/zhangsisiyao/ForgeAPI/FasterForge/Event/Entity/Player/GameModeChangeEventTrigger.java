package io.gitee.zhangsisiyao.ForgeAPI.FasterForge.Event.Entity.Player;

import io.gitee.zhangsisiyao.ForgeAPI.Event.Entity.Player.PlayerGameTypeChangeEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 玩家Tick事件,用于捕捉触发玩家的事件<br/>
 * 触发的事件:<br/>
 * {@link PlayerGameTypeChangeEvent}<br/>
 * */
public class GameModeChangeEventTrigger {
    private static Map<String, GameType> playerGameTypeMap=new Hashtable<>();

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEvent(TickEvent.PlayerTickEvent event){
        if(!event.player.world.isRemote){
            if(event.player instanceof  EntityPlayerMP){
                ServerEvent(event.player);
            }
        }else {
            if(event.player instanceof EntityPlayerSP){
                ClientEvent(event.player);
            }
        }
        //System.out.println(playerGameTypeMap.keySet().size());
    }
    @SideOnly(Side.CLIENT)
    private static void ClientEvent(EntityPlayer player){
        Side side=Side.CLIENT;
        EntityPlayerSP playerSP = (EntityPlayerSP) player;
        GameType gameType = playerSP.connection.getPlayerInfo(playerSP.getUniqueID()).getGameType();
        onPlayerGameModeChangedEvent(playerSP,playerSP.getUniqueID().toString(),gameType,side);
    }

    private static void ServerEvent(EntityPlayer player){
        Side side=Side.SERVER;
        EntityPlayerMP playerMP = (EntityPlayerMP) player;
        onPlayerGameModeChangedEvent(playerMP,playerMP.getUniqueID().toString(),playerMP.interactionManager.getGameType(),side);
    }

    private static void onPlayerGameModeChangedEvent(EntityPlayer player, String uuid, GameType gameType,Side side){
        if (!playerGameTypeMap.containsKey(uuid)) {
            playerGameTypeMap.put(uuid, gameType);
        }
        if (playerGameTypeMap.get(uuid)!=gameType) {
            MinecraftForge.EVENT_BUS.post(new PlayerGameTypeChangeEvent(player,gameType, playerGameTypeMap.get(uuid),side));
            playerGameTypeMap.put(uuid, gameType);
        }
        MinecraftServer minecraftServer = FMLCommonHandler.instance().getMinecraftServerInstance();
        if(minecraftServer!=null){
            List<EntityPlayerMP> players = minecraftServer.getPlayerList().getPlayers();
            Set<String> playerSet=new HashSet<>();
            for(EntityPlayerMP playerMP:players) {
                String id = playerMP.getUniqueID().toString();
                playerSet.add(id);
            }
            Map<String, GameType> map = new ConcurrentHashMap<>(playerGameTypeMap);
            for(Map.Entry<String, GameType> entry : map.entrySet()){
                if(!playerSet.contains(entry.getKey())){
                    playerGameTypeMap.remove(entry.getKey());
                }
            }
        }
    }





}
