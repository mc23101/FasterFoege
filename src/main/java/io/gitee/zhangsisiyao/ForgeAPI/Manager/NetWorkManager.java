package io.gitee.zhangsisiyao.ForgeAPI.Manager;

import com.alibaba.fastjson2.JSON;
import io.gitee.zhangsisiyao.ForgeAPI.NetWork.NetworkPack;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
/**
 * 网络管理器,用于与服务器通信
 * */
public class NetWorkManager {
    /**
     * 用于储存所有注册过的通道
     * */
    private static Map<String, FMLEventChannel> channels=new HashMap<>();

    /**
     * 注册通道
     * @param object 注册通道的类(this)
     * @param name 通道名称
     * @return
     * */
    public static FMLEventChannel registerChannel(Object object,String name){
        // 注册通道
        FMLEventChannel channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(name);
        channel.register(object);
        channels.put(name,channel);
        return channel;
    }

    /**
     * 发送数据包到服务器
     * @param channelName 发送的通道
     * @param pack 数据包
     * */
    public static void sendToServer(String channelName, NetworkPack pack){
        byte[] array = JSON.toJSONString(pack).getBytes(StandardCharsets.UTF_8);
        ByteBuf buf = Unpooled.wrappedBuffer(array);
        FMLProxyPacket packet = new FMLProxyPacket(new PacketBuffer(buf), channelName);
        getChannel(channelName).sendToServer(packet);
    }

    /**
     * 解析数据包
     * @param array 从通道获取的byte数据
     * @return 解析的数据包结果
     * */
    public static NetworkPack toNetworkPack(byte[] array){
        NetworkPack networkPack = JSON.parseObject(new String(array,StandardCharsets.UTF_8),NetworkPack.class);
        return networkPack;
    }

    /**
     * 获取已经注册的Channel
     * @param channelName Channel的名称
     * @return 已经注册的Channel
     * */
    public static FMLEventChannel getChannel(String channelName){
        if(channels.containsKey(channelName)){
            return channels.get(channelName);
        }
        return  null;
    }
}
