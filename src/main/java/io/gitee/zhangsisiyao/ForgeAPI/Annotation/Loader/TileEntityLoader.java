package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

import io.gitee.zhangsisiyao.ForgeAPI.Annotation.MinecraftTileEntity;
import io.gitee.zhangsisiyao.ForgeAPI.MinecraftCore;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.util.Set;

@SuppressWarnings("all")
public class TileEntityLoader {

    private static Logger logger= LogManager.getLogger("ForgeFrame");

    private static int success=0;

    private static int error=0;

    public static void TileEntityAnnotationLoader(Reflections reflections){
        logger.info("注册TileEntity中.........");

        loadFromClass(reflections);

        logger.info("一共绑定"+(success+error)+"个TileEntity。成功:"+success+" 失败:"+error);
    }

    private static void loadFromClass(Reflections reflections){
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MinecraftTileEntity.class);
        for(Class c:classes){
            MinecraftTileEntity annotation = (MinecraftTileEntity) c.getAnnotation(MinecraftTileEntity.class);
            String[] blocks = annotation.Blocks();
            for(String block:blocks){
                ResourceLocation location = new ResourceLocation(block);
                if(MinecraftCore.ItemManger.containBlock(location)){
                    MinecraftCore.TileEntityManger.registerTileEntity(c,location);
                    logger.debug("方块"+block+"绑定TileEntity成功");
                    success++;
                }else{
                    logger.error("在"+c.getName()+"中MinecraftTileEntity注解的 参数Blocks中的元素:"+block+"绑定TileEntity失败。原因:方块"+block+"不存在.");
                    error++;
                }
            }
        }
    }

}