package com.SiYao.ForgeAPI;

import com.SiYao.ForgeAPI.NetWork.NetworkPack;
import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.client.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
public class Minecraft {
    /**
     * 用于储存所有注册过的通道
     * */
    private static Map<String, FMLEventChannel> channels=new HashMap<>();

    /**
     * 用于存放所有注册过的创造模式物品页
     * */
    private static Map<String, CreativeTabs> creativeTabsMap =new HashMap<>();

    /**
     *在客户端中打开Gui
     * @param guiScreen 继承GuiScreen的类
     * */
    public static void DisplayGui(@Nullable GuiScreen guiScreen){
        net.minecraft.client.Minecraft.getMinecraft().displayGuiScreen(guiScreen);
    }

    /**
     * 注册通道
     * @param object 注册通道的类(this)
     * @param name 通道名称
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

    /**
     * 注册创造模式物品栏
     * @param name 物品栏显示的名字
     * @param icon 物品栏显示的图标
     * @return 返回注册的创造模式物品栏
     * */
    public static CreativeTabs registerCreativeTabs(String name, ItemStack icon){
        CreativeTabs creativeTab = new CreativeTabs(I18n.format(name)) {
            @Override
            public ItemStack getTabIconItem() {
                return icon;
            }
        };
        creativeTabsMap.put(name,creativeTab);
        return creativeTab;
    }

    public static void registerItem(Item...items){
        GameRegistry.findRegistry(Item.class).registerAll(items);
    }

    public static void registerCustomResourceManger(ISelectiveResourceReloadListener listener){

    }

    public static void registerCustomModelLoder(){

    }
}
