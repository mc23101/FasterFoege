package io.gitee.zhangsisiyao.FasterForge.ForgeBoot.Event.Entity.Player;

import io.gitee.zhangsisiyao.FasterForge.Event.Entity.Player.PlayerJoinEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerJoinEventTrigger {

    public static List<String> playerJoinList=new CopyOnWriteArrayList<>();

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEvent(EntityJoinWorldEvent event){
        if(event.getWorld().isRemote){
            if(event.getEntity() instanceof EntityPlayerSP){
                onClientEvent((EntityPlayerSP)event.getEntity(),event.getWorld());
            }
        }else {
            if(event.getEntity() instanceof EntityPlayerMP){
                onServerEvent((EntityPlayerMP) event.getEntity(),event.getWorld());
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onWorldTickEvent(TickEvent.WorldTickEvent event){
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

    @SideOnly(Side.CLIENT)
    private static void onClientEvent(EntityPlayerSP playerSP,World world){
        Side side=Side.CLIENT;
        onPlayerJoinEvent(playerSP,side);
    }

    private static void onServerEvent(EntityPlayerMP playerMP, World world){
        Side side=Side.SERVER;
        onPlayerJoinEvent(playerMP,side);
    }


    private static void onPlayerJoinEvent(EntityPlayer player,Side side){
        String uuid = player.getUniqueID().toString();
        if(!playerJoinList.contains(uuid)){
            MinecraftForge.EVENT_BUS.post(new PlayerJoinEvent(player,side));
            playerJoinList.add(uuid);
        }
    }
}
