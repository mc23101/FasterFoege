package io.gitee.zhangsisiyao.ForgeAPI;

import com.alibaba.fastjson2.JSON;
import io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader.AnnotationFactory;
import io.gitee.zhangsisiyao.ForgeAPI.Font.FontRender;
import io.gitee.zhangsisiyao.ForgeAPI.Model.CustomModelLoader;
import io.gitee.zhangsisiyao.ForgeAPI.NetWork.NetworkPack;
import io.gitee.zhangsisiyao.ForgeAPI.Resources.CustomResourceListener;
import io.gitee.zhangsisiyao.ForgeAPI.Resources.CustomResourcePack;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("all")
public class MinecraftCore {

    private static Logger logger= LogManager.getLogger("ForgeFrame");

    public static String MODID="";

    public static Object mod;

    /**
     * <p>API初始化工作</p>
     * <p>请在{@link net.minecraftforge.fml.common.event.FMLPreInitializationEvent}事件中调用此方法</p>
     * <p>以便确保MinecraftForgeAPI能够正常工作</p>
     * @param o {@link net.minecraftforge.fml.common.event.FMLPreInitializationEvent}的Class类
     * <p>正常情况下在此事件中调用MinecraftCore.preinit(this);即可</p>
     * */
    public static void preinit(Object o){

        mod=o;
        Package pack = o.getClass().getPackage();
        ConfigurationBuilder configuration = new ConfigurationBuilder().forPackages(pack.getName());
        configuration.addScanners(new SubTypesScanner()).addScanners(Scanners.FieldsAnnotated,Scanners.TypesAnnotated,Scanners.ConstructorsAnnotated,Scanners.MethodsAnnotated);
        Reflections reflections = new Reflections(configuration);


        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Mod.class);

        for (Class c :typesAnnotatedWith){
            Mod annotation = (Mod) c.getAnnotation(Mod.class);
            MinecraftCore.MODID=annotation.modid();
        }
        AnnotationFactory.AnnotationLoader(o);
        ResourceManger.registerCustomModelLoder(new CustomModelLoader());
        ResourceManger.registerCustomResourceManger(new CustomResourceListener());
    }

    public static void init(){
        FontRender fontRender = FontRender.fontRender;
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

        public static IResource getResource(ResourceLocation location){
            try {
                Class simpleReloadableResourceManagerClass = SimpleReloadableResourceManager.class;
                Field domainResourceManagers = simpleReloadableResourceManagerClass.getDeclaredField("domainResourceManagers");
                domainResourceManagers.setAccessible(true);
                Map<String, FallbackResourceManager> resourcePackMap = (Map<String, FallbackResourceManager>) domainResourceManagers.get(Minecraft.getMinecraft().getResourceManager());
                FallbackResourceManager fallbackResourceManager = resourcePackMap.get(MinecraftCore.MODID);

                return fallbackResourceManager.getResource(location);
            } catch (NoSuchFieldException | IOException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static boolean containResource(ResourceLocation location){
            boolean bool=false;
            try {
                Class simpleReloadableResourceManagerClass = SimpleReloadableResourceManager.class;
                Field domainResourceManagers = simpleReloadableResourceManagerClass.getDeclaredField("domainResourceManagers");
                domainResourceManagers.setAccessible(true);
                Map<String, FallbackResourceManager> resourcePackMap = (Map<String, FallbackResourceManager>) domainResourceManagers.get(Minecraft.getMinecraft().getResourceManager());
                FallbackResourceManager fallbackResourceManager = resourcePackMap.get(MinecraftCore.MODID);

                Class<FallbackResourceManager> fallbackResourceManagerClass = FallbackResourceManager.class;
                Field resourcePacksField = fallbackResourceManagerClass.getDeclaredField("resourcePacks");
                resourcePacksField.setAccessible(true);
                List<IResourcePack> resourcePacks = (List<IResourcePack>) resourcePacksField.get(fallbackResourceManager);
                for(IResourcePack resourcePack:resourcePacks){
                    if(resourcePack.resourceExists(location)){
                        bool=true;
                        break;
                    }
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return bool;
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
         * @param modId modID
         * @param location 资源在Minecraft中表示的位置
         * @param resource 资源
         * @param type 资源类型
         * */
        public static void registerResource(ResourceLocation location, IResource resource){

            try {
                //获取Mod容器
                Class simpleReloadableResourceManagerClass = SimpleReloadableResourceManager.class;
                Field domainResourceManagers = simpleReloadableResourceManagerClass.getDeclaredField("domainResourceManagers");
                domainResourceManagers.setAccessible(true);
                Map<String, FallbackResourceManager> resourcePackMap = (Map<String, FallbackResourceManager>) domainResourceManagers.get(Minecraft.getMinecraft().getResourceManager());
                FallbackResourceManager fallbackResourceManager = resourcePackMap.get(MinecraftCore.MODID);

                //获取资源包
                Class<FallbackResourceManager> fallbackResourceManagerClass = FallbackResourceManager.class;
                Field resourcePacksField = fallbackResourceManagerClass.getDeclaredField("resourcePacks");
                resourcePacksField.setAccessible(true);
                List<IResourcePack> resourcePacks = (List<IResourcePack>) resourcePacksField.get(fallbackResourceManager);
                CustomResourcePack pack=null;
                for(IResourcePack resourcePack: resourcePacks){
                    if(resourcePack.getPackName().equals("ForgeFrame")){
                        pack= (CustomResourcePack) resourcePack;
                        break;
                    }
                }

                if(pack==null){
                    pack=new CustomResourcePack("ForgeFrame");
                    resourcePacks.add(pack);
                }

                //添加资源
                if(!pack.resourceExists(location)){
                    pack.resources.put(location,resource);
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
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


    /**
     * 附魔管理器,用于操作附魔
     * <p>例如:注册附魔属性</p>
     * */
    public static class EnchantmentManger{
        /**
         * 注册附魔属性
         * @param enchantments 注册的附魔属性
         * */
        public static void registerEnchantment(Enchantment...enchantments){
            GameRegistry.findRegistry(Enchantment.class).registerAll(enchantments);
        }

        /**
         * 检查附魔属性是否已经注册
         * @param location 附魔属性的注册名
         * */
        public static boolean containEnchantment(ResourceLocation location){
            return GameRegistry.findRegistry(Enchantment.class).containsKey(location);
        }
    }

    public static class TileEntityManger{
        public static void registerTileEntity(Class<?extends TileEntity> tileEntity,ResourceLocation blockName){
            GameRegistry.registerTileEntity(tileEntity,blockName);
        }
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
