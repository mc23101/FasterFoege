package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

public class AnnotationFactory {
    public static void AnnotationLoader(Object mod){
        ItemLoader.ItemAnnotationLoader(mod);
        BlockLoader.BlockAnnotationLoader(mod);
        EntityLoader.EntityAnnotationLoader(mod);
        PotionLoader.PotionAnnotationLoader(mod);
        EnchantmentLoader.EnchantmentAnnotationLoader(mod);
        ResourceLoader.ResourceAnnotationLoader(mod);
        TileEntityLoader.TileEntityAnnotationLoader(mod);
    }
}
