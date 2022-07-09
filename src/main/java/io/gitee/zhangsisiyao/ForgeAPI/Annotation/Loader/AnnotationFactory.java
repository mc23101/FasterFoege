package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

public class AnnotationFactory {
    public static void AnnotationLoader(Object o){
        ItemLoader.ItemAnnotationLoader(o);
        BlockLoader.BlockAnnotationLoader(o);
        EntityLoader.EntityAnnotationLoader(o);
    }
}
