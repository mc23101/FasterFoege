package com.github.zhangsiyao.FasterForge.ForgeBoot;

import com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation.Loader;
import com.github.zhangsiyao.FasterForge.ForgeBoot.Minecraft.Annotation.Loader.*;
import com.github.zhangsiyao.FasterForge.ForgeBoot.Minecraft.EventTrigger.Entity.Player.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

public class ForgeApplication {

    public static void init(){

    }


    public static void registerTrigger(){
        PlayerEventTrigger();
    }

    public static void loadAnnotation(){
        ConfigurationBuilder configuration = new ConfigurationBuilder().forPackages("com.github.zhangsiyao.FasterForge.ForgeBoot.Minecraft.Annotation.Loader");
        configuration.addScanners(new SubTypesScanner()).addScanners(Scanners.TypesAnnotated);
        Reflections reflection = new Reflections(configuration);
        Set<Class<?>> loaders = reflection.getTypesAnnotatedWith(Loader.class);
        try {
            for(Class c:loaders){
                IAnnotationLoader loader = (IAnnotationLoader) c.newInstance();
                loader.loadFromClass();
                loader.loadFromField();
                loader.loadFromMethod();
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }



    }
    private static void PlayerEventTrigger(){
        MinecraftForge.EVENT_BUS.register(PlayerChatTrigger.class);
        MinecraftForge.EVENT_BUS.register(PlayerAdvancementEventTrigger.class);
        MinecraftForge.EVENT_BUS.register(PlayerGameModeChangeEventTrigger.class);
        MinecraftForge.EVENT_BUS.register(PlayerJoinEventTrigger.class);
        MinecraftForge.EVENT_BUS.register(PlayerArmorStandManipulateEventTrigger.class);
        MinecraftForge.EVENT_BUS.register(PlayerBedEventTrigger.class);
    }
}
