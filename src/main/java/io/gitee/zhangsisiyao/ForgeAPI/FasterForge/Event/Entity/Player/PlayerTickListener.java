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
        if(event.player.world.isRemote){
            EntityPlayerSP player = (EntityPlayerSP) event.player;
            GameType gameType = player.connection.getPlayerInfo(player.getUniqueID()).getGameType();
            onPlayerGameModeChangedEvent(player,player.getUniqueID().toString(),gameType,Side.CLIENT);
        }else {
            EntityPlayerMP player = (EntityPlayerMP) event.player;
            onPlayerGameModeChangedEvent(player,player.getUniqueID().toString(),player.interactionManager.getGameType(),Side.SERVER);
        }
    }

    private static void onPlayerGameModeChangedEvent(EntityPlayer player, String uuid, GameType gameType,Side side){
        if(!playerGameTypeMap.containsKey(uuid)) {
            playerGameTypeMap.put(uuid, gameType);
        }
        if (playerGameTypeMap.get(uuid)!=gameType) {
            MinecraftForge.EVENT_BUS.post(new PlayerGameTypeChangeEvent(player,gameType, side));
            playerGameTypeMap.put(uuid, gameType);
        }
        clearPlayerGameTypeMap();
    }

    private static void clearPlayerGameTypeMap(){
        MinecraftServer minecraftServer = FMLCommonHandler.instance().getMinecraftServerInstance();
        if(minecraftServer!=null){
            List<EntityPlayerMP> players = minecraftServer.getPlayerList().getPlayers();
            Map<String,GameType> curry=new Hashtable<>();
            for(EntityPlayerMP playerMP:players){
                String uuid=playerMP.getUniqueID().toString();
                if(playerGameTypeMap.containsKey(uuid)){
                    curry.put(uuid,playerGameTypeMap.get(uuid));
                }
            }
            playerGameTypeMap=curry;
        }
    }

}
