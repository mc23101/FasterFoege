package io.gitee.zhangsisiyao.FasterForge.ForgeBoot.Annotation.Loader;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

@SuppressWarnings("all")
public class AnnotationFactory {
    public static void AnnotationLoader(Object mod){
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


    public static void throwException(Logger logger, String errorType, ResourceLocation location, String reason, Class aClass){
        logger.error("=================================================================================");
        logger.error(errorType+":"+location+"注册失败");
        logger.error("失败原因:"+reason);
        logger.error("错误位置:"+aClass.getName()+"类");
        logger.error("=================================================================================");
    }

    public static void throwException(Logger logger, String errorType,ResourceLocation location, String reason,Class aClass,String fieldName){
        logger.error("=================================================================================");
        logger.error(errorType+":"+location+"注册失败");
        logger.error("失败原因:"+reason);
        logger.error("错误位置:"+aClass.getName()+"类中的"+fieldName+"字段");
        logger.error("=================================================================================");
    }
}
