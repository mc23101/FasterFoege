package io.gitee.zhangsisiyao.ForgeAPI;

import com.alibaba.fastjson2.JSON;
import io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader.AnnotationFactory;
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
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
public class MinecraftCore {

    /**
     * <p>API初始化工作</p>
     * <p>请在{@link net.minecraftforge.fml.common.event.FMLPreInitializationEvent}事件中调用此方法</p>
     * <p>以便确保MinecraftForgeAPI能够正常工作</p>
     * @param o {@link net.minecraftforge.fml.common.event.FMLPreInitializationEvent}的Class类
     * <p>正常情况下在此事件中调用MinecraftCore.preinit(this);即可</p>
     * */
    public static void preinit(Object o){
        AnnotationFactory.AnnotationLoader(o);
        ResourceManger.registerCustomModelLoder(new CustomModelLoader());
        ResourceManger.registerCustomResourceManger(new CustomResourceListener());
    }

    public static void init(){

    }

    /**
     * 物品管理器,用于操作Item和Block
     * <p>例如:注册物品、注册方块</p>
     * */
    public static class ItemManger{
        /**
         * 用于存放所有注册过的创造模式物品页
         * */
        private static Map<String, CreativeTabs> creativeTabsMap =new HashMap<>();

        /**
         * 用于存放此mod注册的所有物品
         * */
        private static Map<ResourceLocation,Item> itemMap=new HashMap<>();

        /**
         * 用于存放此mod注册的所有方块
         * */
        private static Map<ResourceLocation,Block> blockMap=new HashMap<>();

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
            itemMap.put(item.getRegistryName(),item);
            return item;
        }

        /**
         * 注册物品
         * @param items 注册的物品(可变参数)
         * */
        public static void registerItems(Item...items){
            GameRegistry.findRegistry(Item.class).registerAll(items);
            for(Item item:items){
                itemMap.put(item.getRegistryName(),item);
            }
        }

        /**
         * 注册方块
         * @param blocks 注册的方块(可变参数)
         * */
        public static void registerBlocks(Block...blocks){
            GameRegistry.findRegistry(Block.class).registerAll(blocks);
            for(Block block:blocks){
                blockMap.put(block.getRegistryName(),block);
            }
        }

        /**
         * 获取mod已经注册过的物品
         * @param location 物品的注册名
         * @return 返回物品，如果物品未被注册，则返回null
         * */
        public static Item getItem(ResourceLocation location){
            if(itemMap.containsKey(location)){
                return itemMap.get(location);
            }
            return null;
        }

        /**
         * 获取mod已经注册过的方块
         * @param location 方块的注册名
         * @return 返回方块,如果方块未被注册，则返回null
         * */
        public static Block getBlock(ResourceLocation location){
            if(blockMap.containsKey(location)){
                return  blockMap.get(location);
            }
            return null;
        }

        /**
         * 检查Item是否已经注册
         * @param location Item的注册名
         * */
        public static boolean containItem(ResourceLocation location){
            return GameRegistry.findRegistry(Item.class).containsKey(location);
        }

        /**
         * 检查Block是否已经注册
         * @param location Block的注册名
         * */
        public static boolean containBlock(ResourceLocation location){
            return GameRegistry.findRegistry(Block.class).containsKey(location);
        }
    }

    /**
     * 效果管理器
     * */
    public static class EffectManger{

    }

    /**
     * 实体管理器,用于操作实体
     * <p>例如:注册实体、注册实体渲染</p>
     * <p>注意事项:一般情况下，注册实体和注册实体渲染是成对存在的</p>
     * <p>注册实体，则此实体应该有对应的实体渲染</p>
     * */
    public static class EntityManger{

        /**
         * 注册实体和怪物蛋
         * <p>注意事项:一般情况下，注册实体和注册实体渲染是成对存在的</p>
         * <p>注册实体，则此实体应该有对应的实体渲染</p>
         * @param registryName 实体注册名(modId+注册名)
         * @param entityClass 实体的class类
         * @param entityName 实体的名称(尽量和注册名一样)
         * @param id 实体的自定义ID(请使用UUID)
         * @param mod 模组的主类
         * @param trackingRange 实体的追踪范围
         * @param updateFrequency 实体更新频率
         * @param sendsVelocityUpdates 是否发送速度数据包
         * @param eggPrimary 怪物蛋颜色1
         * @param eggSecondary 怪物蛋颜色2

         * */
        public  static void registerEntity(ResourceLocation registryName, Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggPrimary, int eggSecondary){
            EntityRegistry.registerModEntity(registryName,entityClass, entityName, id,mod,trackingRange, updateFrequency,sendsVelocityUpdates,eggPrimary, eggSecondary);
        }

        /**
         * 注册实体的渲染类
         * <p>注意事项:一般情况下，注册实体和注册实体渲染是成对存在的</p>
         * <p>注册实体，则此实体应该有对应的实体渲染</p>
         * @param EntityClass 实体的class类
         * @param render 实体的渲染类
         * */
        public static  void registerEntityRender(Class<?extends Entity> EntityClass,Class<?extends Render> render){
            RenderingRegistry.registerEntityRenderingHandler(EntityClass, new IRenderFactory()
            {
                @Override
                public Render createRenderFor(RenderManager manager) {
                    Render render1=null;
                    try {
                        render1 = render.getConstructor(RenderManager.class).newInstance(manager);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    return render1;
                }
            });
        }

        /**
         * 检查Entity是否被注册
         * @param location Entity的注册名
         * */
        public static boolean containEntity(ResourceLocation location){
            return GameRegistry.findRegistry(EntityEntry.class).containsKey(location);
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
         * <p>例如:ResourceLocation location=new ResourceLocation(你的modID,你的自定义资源名字);</p>
         * @param location 资源在Minecraft中表示的位置
         * @param absolutaPath 资源在电脑上的绝对位置
         * @param type 资源类型
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
     * <p>药水效果管理器,用于操作药水效果</p>
     * <p>例如:注册药水效果</p>
     * */
    public static class PotionManger{
        /**
         * 用于存放此mod注册的药水效果
         * */
        private static Map<ResourceLocation,Potion> PotionMap=new HashMap<>();


        /**
         * 注册药水效果
         * @param potions 注册的药水
         * */
        public static void registerPotion(Potion...potions){
            GameRegistry.findRegistry(Potion.class).registerAll(potions);
            for(Potion p:potions){
                PotionMap.put(p.getRegistryName(),p);
            }
        }

        /**
         * 检查Potion是否已经注册
         * @param location Potion的注册名
         * */
        public static boolean containPotion(ResourceLocation location){
            return GameRegistry.findRegistry(Potion.class).containsKey(location);
        }
    }



    public static class EnchantmentManger{

    }

    public static class TileEntityManger{

    }


    /**
     * 网络管理器,用于与服务器通信
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
}
