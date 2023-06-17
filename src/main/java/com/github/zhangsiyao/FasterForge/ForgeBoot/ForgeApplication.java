package com.github.zhangsiyao.FasterForge.ForgeBoot;

import com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation.EventTrigger;
import com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation.AnnotationLoader;
import com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation.Loader.IAnnotationLoader;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

public class ForgeApplication {

    public static final Logger logger= LogManager.getLogger("FasterForge");

    public static void registerEventTrigger(){
        ConfigurationBuilder configuration = new ConfigurationBuilder().forPackages("com.github.zhangsiyao.FasterForge.ForgeBoot.EventTrigger");
        configuration.addScanners(new SubTypesScanner()).addScanners(Scanners.TypesAnnotated);
        Reflections reflection = new Reflections(configuration);
        Set<Class<?>> triggers = reflection.getTypesAnnotatedWith(EventTrigger.class);
        for (Class c:triggers){
            MinecraftForge.EVENT_BUS.register(c);
        }
    }

    public static void registerAnnotationLoader(){
        ConfigurationBuilder configuration = new ConfigurationBuilder().forPackages("com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation.Loader");
        configuration.addScanners(new SubTypesScanner()).addScanners(Scanners.TypesAnnotated);
        Reflections reflection = new Reflections(configuration);
        Set<Class<?>> loaders = reflection.getTypesAnnotatedWith(AnnotationLoader.class);
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
}
