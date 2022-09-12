package io.gitee.zhangsisiyao.ForgeAPI;

import io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader.AnnotationFactory;
import io.gitee.zhangsisiyao.ForgeAPI.Manager.ResourceManager;
import io.gitee.zhangsisiyao.ForgeAPI.Model.CustomModelLoader;
import io.gitee.zhangsisiyao.ForgeAPI.Resources.CustomResourceListener;
import net.minecraftforge.fml.common.Mod;
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
        ResourceManager.registerCustomModelLoder(new CustomModelLoader());
        ResourceManager.registerCustomResourceManger(new CustomResourceListener());
    }

    public static void init(){

    }
}
