package ForgeAPI.NetWork;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class NetworkManager {
    public static Map<String,FMLEventChannel> channels=new HashMap<>();

    public static FMLEventChannel registerChannel(Object object,String name){
        MinecraftForge.EVENT_BUS.register(object);
        FMLCommonHandler.instance().bus().register(object);
        // 注册通道
        FMLEventChannel channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(name);
        channel.register(object);
        channels.put(name,channel);
        return channel;
    }


    public static void sendToServer(String channelName,NetworkPack pack){
        byte[] array = JSON.toJSONString(pack).getBytes(StandardCharsets.UTF_8);
        ByteBuf buf = Unpooled.wrappedBuffer(array);
        FMLProxyPacket packet = new FMLProxyPacket(new PacketBuffer(buf), channelName);
        channels.get(channelName).sendToServer(packet);
    }


    public static NetworkPack toNetworkPack(byte[] array){
        NetworkPack networkPack = JSON.parseObject(new String(array,StandardCharsets.UTF_8),NetworkPack.class);
        return networkPack;
    }


}
