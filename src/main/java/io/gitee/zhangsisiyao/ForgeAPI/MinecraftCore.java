package io.gitee.zhangsisiyao.ForgeAPI;

import io.gitee.zhangsisiyao.ForgeAPI.FasterForge.Annotation.Loader.AnnotationFactory;
import io.gitee.zhangsisiyao.ForgeAPI.FasterForge.Event.Entity.Player.PlayerAdvancementEventTrigger;
import io.gitee.zhangsisiyao.ForgeAPI.FasterForge.Event.Entity.Player.PlayerChatTrigger;
import io.gitee.zhangsisiyao.ForgeAPI.FasterForge.Event.Entity.Player.PlayerGameModeChangeEventTrigger;
import io.gitee.zhangsisiyao.ForgeAPI.FasterForge.Event.Entity.Player.PlayerJoinEventTrigger;
import io.gitee.zhangsisiyao.ForgeAPI.Manager.ResourceManager;
import io.gitee.zhangsisiyao.ForgeAPI.Model.CustomModelLoader;
import io.gitee.zhangsisiyao.ForgeAPI.Resources.CustomResourceListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

@SuppressWarnings("all")
public class MinecraftCore {

    private static Logger logger= LogManager.getLogger("FasterForge");

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
        registerPlayerEventTrigger();
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
    }

    @SideOnly(Side.CLIENT)
    private static void registerResourceListener(){
        ResourceManager.registerCustomModelLoder(new CustomModelLoader());
        ResourceManager.registerCustomResourceManger(new CustomResourceListener());
    }

    public static void init(){

    }

    private static void registerPlayerEventTrigger(){
        MinecraftForge.EVENT_BUS.register(PlayerChatTrigger.class);
        MinecraftForge.EVENT_BUS.register(PlayerAdvancementEventTrigger.class);
        MinecraftForge.EVENT_BUS.register(PlayerGameModeChangeEventTrigger.class);
        MinecraftForge.EVENT_BUS.register(PlayerJoinEventTrigger.class);
        MinecraftForge.EVENT_BUS.register(PlayerAdvancementEventTrigger.class);
    }
}
