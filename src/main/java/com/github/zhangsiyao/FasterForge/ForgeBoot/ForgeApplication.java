package com.github.zhangsiyao.FasterForge.ForgeBoot;

import com.github.zhangsiyao.FasterForge.FasterForge;
import com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation.EventTrigger;
import com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation.AnnotationLoader;
import com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation.Loader.IAnnotationLoader;
import net.minecraftforge.common.MinecraftForge;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

public class ForgeApplication {

    public static void registerEventTrigger(){
        FasterForge.logger.info("加载事件触发器中........");
        ConfigurationBuilder configuration = new ConfigurationBuilder().forPackages("com.github.zhangsiyao.FasterForge.ForgeBoot.EventTrigger");
        configuration.addScanners(new SubTypesScanner()).addScanners(Scanners.TypesAnnotated);
        Reflections reflection = new Reflections(configuration);
        Set<Class<?>> triggers = reflection.getTypesAnnotatedWith(EventTrigger.class);
        for (Class c:triggers){
            MinecraftForge.EVENT_BUS.register(c);
        }
    }

    public static void registerAnnotationLoader(){
        FasterForge.logger.info("加载注解加载器中.......");
        ConfigurationBuilder configuration = new ConfigurationBuilder().forPackages("com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation.Loader");
        configuration.addScanners(new SubTypesScanner()).addScanners(Scanners.TypesAnnotated);
        Reflections reflection = new Reflections(configuration);
        Set<Class<?>> loaders = reflection.getTypesAnnotatedWith(AnnotationLoader.class);
        try {
            for(Class c:loaders){
                FasterForge.logger.info(c.getSimpleName()+"加载成功");
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
