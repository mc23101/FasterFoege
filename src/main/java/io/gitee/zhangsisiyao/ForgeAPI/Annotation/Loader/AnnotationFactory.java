package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

public class AnnotationFactory {
    public static void AnnotationLoader(Object mod){
        ItemLoader.ItemAnnotationLoader(mod);
        BlockLoader.BlockAnnotationLoader(mod);
        EntityLoader.EntityAnnotationLoader(mod);
        EntityLoader.EntityRenderAnnotationLoader(mod);
        PotionLoader.PotionAnnotationLoader(mod);
    }
}
