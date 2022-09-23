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

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class PlayerTickListener {
    private static Map<String, GameType> playerGameTypeMap=new Hashtable<>();

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEvent(TickEvent.PlayerTickEvent event){
        if(event.side==Side.CLIENT){
            EntityPlayerSP player = (EntityPlayerSP) event.player;
            ClientEvent(player);
        }else {
            EntityPlayerMP player = (EntityPlayerMP) event.player;
            ServerEvent(player);
        }
    }
    private static void ClientEvent(EntityPlayerSP playerSP){
        Side side=Side.CLIENT;
        GameType gameType = playerSP.connection.getPlayerInfo(playerSP.getUniqueID()).getGameType();
        onPlayerGameModeChangedEvent(playerSP,playerSP.getUniqueID().toString(),gameType,side);
    }

    private static void ServerEvent(EntityPlayerMP playerMP){
        Side side=Side.SERVER;
        onPlayerGameModeChangedEvent(playerMP,playerMP.getUniqueID().toString(),playerMP.interactionManager.getGameType(),side);
    }

    private static void onPlayerGameModeChangedEvent(EntityPlayer player, String uuid, GameType gameType,Side side){
        if(!playerGameTypeMap.containsKey(uuid)) {
            playerGameTypeMap.put(uuid, gameType);
        }
        if (playerGameTypeMap.get(uuid)!=gameType) {
            MinecraftForge.EVENT_BUS.post(new PlayerGameTypeChangeEvent(player,gameType, side));
            playerGameTypeMap.put(uuid, gameType);
        }
        MinecraftServer minecraftServer = FMLCommonHandler.instance().getMinecraftServerInstance();
        if(minecraftServer!=null){
            List<EntityPlayerMP> players = minecraftServer.getPlayerList().getPlayers();
            Map<String,GameType> curry=new Hashtable<>();
            for(EntityPlayerMP playerMP:players){
                String id=playerMP.getUniqueID().toString();
                if(playerGameTypeMap.containsKey(id)){
                    curry.put(uuid,playerGameTypeMap.get(id));
                }
            }
            playerGameTypeMap=curry;
        }
    }





}
