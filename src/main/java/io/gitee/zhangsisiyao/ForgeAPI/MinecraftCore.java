package io.gitee.zhangsisiyao.ForgeAPI;

import com.alibaba.fastjson2.JSON;
import io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader.ItemLoader;
import io.gitee.zhangsisiyao.ForgeAPI.Model.CustomModelLoader;
import io.gitee.zhangsisiyao.ForgeAPI.NetWork.NetworkPack;
import io.gitee.zhangsisiyao.ForgeAPI.Resources.CustomResource;
import io.gitee.zhangsisiyao.ForgeAPI.Resources.CustomResourceListener;
import io.gitee.zhangsisiyao.ForgeAPI.Resources.CustomResourcePack;
import io.gitee.zhangsisiyao.ForgeAPI.Resources.ResourceType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
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
public class MinecraftCore {

    /**
     * <p>API初始化工作</p>
     * <p>请在{@link net.minecraftforge.fml.common.event.FMLPreInitializationEvent}事件中调用此方法</p>
     * <p>以便确保MinecraftForgeAPI能够正常工作</p>
     * */
    public static void preinit(Object o){
        ItemLoader.ItemAnnotationLoader(o);
        ResourceManger.registerCustomModelLoder(new CustomModelLoader());
        ResourceManger.registerCustomResourceManger(new CustomResourceListener());
    }

    public static void init(){

    }

    /**
     * 物品管理器
     * */
    public static class ItemManger{
        /**
         * 用于存放所有注册过的创造模式物品页
         * */
        private static Map<String, CreativeTabs> creativeTabsMap =new HashMap<>();
        /**
         * 注册创造模式物品栏
         * @param name 物品栏显示的名字
         * @param icon 物品栏显示的图标
         * @return 返回注册的创造模式物品栏
         * */
        public static CreativeTabs registerCreativeTabs(String name, ItemStack icon){
            ItemStack itemStack=icon==null?new ItemStack(Items.DIAMOND):icon;
            CreativeTabs creativeTab = new CreativeTabs(I18n.format(name)) {
                @Override
                public ItemStack getTabIconItem() {
                    return itemStack;
                }
            };
            creativeTabsMap.put(name,creativeTab);
            return creativeTab;
        }

        /**
         * 注册物品
         * @param item 注册的物品
         * */
        public static Item registerItem(Item item){
            GameRegistry.findRegistry(Item.class).registerAll(item);
            return item;
        }

        /**
         * 注册物品
         * @param items 注册的物品(可变参数)
         * */
        public static void registerItems(Item...items){
            GameRegistry.findRegistry(Item.class).registerAll(items);
        }

        /**
         * 注册方块
         * @param blocks 注册的方块(可变参数)
         * */
        public static void registerBlocks(Block...blocks){
            GameRegistry.findRegistry(Block.class).registerAll(blocks);
        }
    }

    /**
     * Gui管理器
     * */
    public static class GuiManger{
        /**
         *在客户端中打开Gui
         * @param guiScreen 继承GuiScreen的类
         * */
        public static void DisplayGui(@Nullable GuiScreen guiScreen){
            net.minecraft.client.Minecraft.getMinecraft().displayGuiScreen(guiScreen);
        }
    }

    /**
     * 资源管理器
     * */
    public static class ResourceManger{
        /**
         * 注册自定义资源加载器
         * @param loader 实现{@link ISelectiveResourceReloadListener} 接口的类
         * */
        public static void registerCustomResourceManger(ISelectiveResourceReloadListener loader){
            SimpleReloadableResourceManager resourceManager = (SimpleReloadableResourceManager) net.minecraft.client.Minecraft.getMinecraft().getResourceManager();
            resourceManager.registerReloadListener(loader);
        }

        /**
         * 注册自定义模型加载器
         * @param customModelLoader 实现{@link ICustomModelLoader}接口的类
         * */
        public static void registerCustomModelLoder(ICustomModelLoader customModelLoader){
            ModelLoaderRegistry.registerLoader(customModelLoader);
        }

        /**
         * <p>注册自定义资源(纹理，模型等)</p>
         * <p>ResourcesLocation根目录必须是指定的custom</p>
         * <p>例如:ResourceLocation location=new ResourceLocation("custom",你的自定义资源名字);</p>
         * @param location 资源在Minecraft中表示的位置
         * @param absolutaPath 资源在电脑上的绝对位置
         * */
        public static void registerResource(ResourceLocation location, String absolutaPath, ResourceType type){
            String prefix="";
            String suffix="";
            switch (type){
                case TEXTURE:
                    prefix="textures/";
                    suffix=".png";
                    break;
            }
            ResourceLocation resourceLocation = new ResourceLocation(location.getResourceDomain(), prefix + location.getResourcePath()+suffix);
            System.out.println(resourceLocation);
            CustomResourcePack.INSTANCE.resources.put(resourceLocation,new CustomResource(location,absolutaPath));
        }
    }


    /**
     * 网络管理器
     * */
    public static class NetWorkManger{
        /**
         * 用于储存所有注册过的通道
         * */
        private static Map<String, FMLEventChannel> channels=new HashMap<>();

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
    }
}
