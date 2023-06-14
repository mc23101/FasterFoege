package com.github.zhangsiyao.FasterForge;

import com.github.zhangsiyao.FasterForge.ForgeBoot.ForgeApplication;
import com.github.zhangsiyao.FasterForge.Manager.ResourceManager;
import com.github.zhangsiyao.FasterForge.Model.CustomModelLoader;
import com.github.zhangsiyao.FasterForge.Resources.CustomResourceListener;
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

    public static String MODID="";

    public static String NAME="";

    public static String VERSION="";

    public static Class mod;

    public static Reflections reflection;

    /**
     * <p>API初始化工作</p>
     * <p>请在{@link net.minecraftforge.fml.common.event.FMLPreInitializationEvent}事件中调用此方法</p>
     * <p>以便确保MinecraftForgeAPI能够正常工作</p>
     * @param o {@link net.minecraftforge.fml.common.event.FMLPreInitializationEvent}的Class类
     * <p>正常情况下在此事件中调用MinecraftCore.preinit(this);即可</p>
     * */
    public static void preinit(Class mod){
        MinecraftCore.mod=mod;
        initModInfo();
    }

    private static void initModInfo(){
        Package pack = mod.getClass().getPackage();
        ConfigurationBuilder configuration = new ConfigurationBuilder().forPackages(pack.getName());
        configuration.addScanners(new SubTypesScanner()).addScanners(Scanners.FieldsAnnotated,Scanners.TypesAnnotated,Scanners.ConstructorsAnnotated,Scanners.MethodsAnnotated);
        reflection = new Reflections(configuration);
        Set<Class<?>> typesAnnotatedWith = reflection.getTypesAnnotatedWith(Mod.class);
        for (Class c :typesAnnotatedWith){
            Mod annotation = (Mod) c.getAnnotation(Mod.class);
            MinecraftCore.MODID=annotation.modid();
            MinecraftCore.NAME=annotation.name();
            MinecraftCore.VERSION=annotation.name();
        }
    }

    @SideOnly(Side.CLIENT)
    private static void registerResourceListener(){
        ResourceManager.registerCustomModelLoder(new CustomModelLoader());
        ResourceManager.registerCustomResourceManger(new CustomResourceListener());
    }

    public static void init(){

    }

}
