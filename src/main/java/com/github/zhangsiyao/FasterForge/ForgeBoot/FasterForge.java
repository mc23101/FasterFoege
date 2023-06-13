package com.github.zhangsiyao.FasterForge.ForgeBoot;

import com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation.Loader.*;
import com.github.zhangsiyao.FasterForge.ForgeBoot.Event.Entity.Player.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

public class FasterForge {
    public static void registerTrigger(){
        PlayerEventTrigger();
    }

    public static void loadAnnotation(Object mod){
        Package pack = mod.getClass().getPackage();
        ConfigurationBuilder configuration = new ConfigurationBuilder().forPackages(pack.getName());
        configuration.addScanners(new SubTypesScanner()).addScanners(Scanners.FieldsAnnotated,Scanners.TypesAnnotated,Scanners.ConstructorsAnnotated,Scanners.MethodsAnnotated);
        Reflections reflections = new Reflections(configuration);

        ItemLoader.ItemAnnotationLoader(reflections);
        BlockLoader.BlockAnnotationLoader(reflections);
        EntityLoader.EntityAnnotationLoader(reflections);
        PotionLoader.PotionAnnotationLoader(reflections);
        EnchantmentLoader.EnchantmentAnnotationLoader(reflections);
        TileEntityLoader.TileEntityAnnotationLoader(reflections);
        if(FMLCommonHandler.instance().getEffectiveSide()== Side.CLIENT){
            ResourceLoader.ResourceAnnotationLoader(reflections);
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
