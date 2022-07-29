package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

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
        ResourceLoader.ResourceAnnotationLoader(reflections);
        TileEntityLoader.TileEntityAnnotationLoader(reflections);
    }

}
