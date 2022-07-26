package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

import io.gitee.zhangsisiyao.ForgeAPI.Annotation.MinecraftResource;
import io.gitee.zhangsisiyao.ForgeAPI.MinecraftCore;
import io.gitee.zhangsisiyao.ForgeAPI.Resources.ResourceType;
import io.gitee.zhangsisiyao.ForgeAPI.Utils.ReflectionUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.util.Set;

@SuppressWarnings("all")
public class ResourceLoader {

    private static Logger logger= LogManager.getLogger("ForgeFrame");

    private static int success=0;

    private static int error=0;

    public static void ResourceAnnotationLoader(Reflections reflections){
        logger.info("注册资源中.....");


        loadFromClass(reflections);
    }

    public static void loadFromClass(Reflections reflections){
        try {
            Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MinecraftResource.class);
            for(Class c:classes){
                MinecraftResource annotation = (MinecraftResource) c.getAnnotation(MinecraftResource.class);
                String modId = annotation.modId();
                String name=annotation.name();
                ResourceType type = annotation.type();
                ResourceLocation location = new ResourceLocation(modId, name);

                boolean isExtended= ReflectionUtil.isExtendFrom(c, IResource.class);
                boolean isRegistered= MinecraftCore.ResourceManger.containResource(location);
                boolean canRegister= isExtended && !isRegistered;

                if(canRegister){
                    IResource resource = (IResource) c.newInstance();
                    MinecraftCore.ResourceManger.registerResource(location,resource,type);
                    logger.debug("资源:"+modId+":"+name+"注册成功!");
                    success++;
                }else if(!isExtended){
                    error++;
                    logger.error("在"+c.getName()+"处的MinecraftResource注解使用错误,请将此注解作用在实现net.minecraft.client.resources.IResource接口 的子类上!");
                }else if(isRegistered){
                    error++;
                    logger.error("在"+c.getName()+"处的modId:"+modId+",name:"+name+"已经被注册!!!");
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void loadFromField(Reflections reflections){

    }
}
