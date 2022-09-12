package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

import io.gitee.zhangsisiyao.ForgeAPI.Annotation.MinecraftResource;
import io.gitee.zhangsisiyao.ForgeAPI.Manager.ResourceManager;
import io.gitee.zhangsisiyao.ForgeAPI.Resources.CustomResource;
import io.gitee.zhangsisiyao.ForgeAPI.Utils.ReflectionUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

@SuppressWarnings("all")
public class ResourceLoader {

    private static final String errorType="资源";

    private static Logger logger= LogManager.getLogger("ForgeFrame");

    private static int success=0;

    private static int error=0;

    public static void ResourceAnnotationLoader(Reflections reflections){
        loadFromClass(reflections);
        loadFromField(reflections);
        logger.info("一共注册"+(success+error)+"个资源文件.成功:"+success+" 失败:"+error);
    }

    private static void loadFromClass(Reflections reflections){
        try {
            Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MinecraftResource.class);
            for(Class c:classes){
                MinecraftResource annotation = (MinecraftResource) c.getAnnotation(MinecraftResource.class);
                String modId = annotation.modId();
                String name=annotation.name();
                ResourceLocation location = new ResourceLocation(modId, name);

                boolean isExtended= ReflectionUtil.isExtendFrom(c, IResource.class);
                boolean isRegistered= ResourceManager.containResource(location);
                boolean canRegister= isExtended && !isRegistered;

                if(canRegister){
                    IResource resource = (IResource) c.newInstance();
                    ResourceManager.registerResource(location,resource);
                    logger.debug("资源:"+modId+":"+name+"注册成功!");
                    success++;
                }else if(!isExtended){
                    error++;
                    AnnotationFactory.throwException(logger,errorType,location,"资源应实现"+ IResource.class.getName()+"的子类接口",c);
                }else if(isRegistered){
                    error++;
                    AnnotationFactory.throwException(logger,errorType,location,location+"名称已被注册",c);
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void loadFromField(Reflections reflections){
        try{
            Set<Field> fieldsAnnotatedWith = reflections.getFieldsAnnotatedWith(MinecraftResource.class);
            for(Field field:fieldsAnnotatedWith){
                field.setAccessible(true);
                MinecraftResource annotation = field.getAnnotation(MinecraftResource.class);
                String modId = annotation.modId();
                String name = annotation.name();
                ResourceLocation location = new ResourceLocation(modId, name);

                boolean isFile=ReflectionUtil.isExtendFrom(field.getType(), File.class);
                boolean isString=ReflectionUtil.isExtendFrom(field.getType(), String.class);
                boolean isURL=ReflectionUtil.isExtendFrom(field.getType(), URL.class);
                boolean isIResource=ReflectionUtil.isExtendFrom(field.getType(), IResource.class);

                boolean isExtended= isFile||isString||isURL||isIResource;
                boolean isRegistered= ResourceManager.containResource(location);
                boolean isStatic= Modifier.isStatic(field.getModifiers());
                boolean canRegister= isExtended && !isRegistered && isStatic;

                Class DeclaringClass =field.getDeclaringClass();

                if(canRegister){
                    Object resourceObject = field.get(field.getDeclaringClass());
                    IResource resource = null;
                    if(resourceObject!=null){
                        if(isFile){
                            resource=new CustomResource(location,(File) resourceObject);
                        }else if(isString){
                            resource=ResourceLoader.of(location,(String) resourceObject);
                        }else if(isURL){
                            resource=new CustomResource(location,(URL)resourceObject);
                        }else if(isIResource){
                            resource=(IResource) resourceObject;
                        }
                        byte[] check = new byte[1024];
                        if(resource!=null&&resource.getInputStream().read(check) != -1) {
                            success++;
                            ResourceManager.registerResource(location,resource);
                            logger.debug("资源:"+location+"加载成功!!");
                        }else{
                            error++;
                            AnnotationFactory.throwException(logger,errorType,location,"无法加载到在"+field.getDeclaringClass().getName()+"处+"+field.getName()+"字段的资源，资源不存在.",DeclaringClass);
                        }
                    }
                }else if(!isExtended){
                    error++;
                    AnnotationFactory.throwException(logger,errorType,location,"无法加载到在"+field.getDeclaringClass().getName()+"处+"+field.getName()+"字段的资源，资源类型错误，请确保字段的类型为String、File或者URL.",DeclaringClass);
                }else if(isRegistered){
                    error++;
                    AnnotationFactory.throwException(logger,errorType,location,location+"名称已被注册",DeclaringClass,field.getName());
                }else if(!isStatic){
                    error++;
                    AnnotationFactory.throwException(logger,errorType,location,"字段:"+field.getName()+"为非static",DeclaringClass,field.getName());
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static IResource of(ResourceLocation location,String path){
        File file=new File(path);
        if(file.exists()&&file.isFile()){
            return new CustomResource(location,file);
        }else{
            try {
                URL url=new URL(path);
                return new CustomResource(location,url);
            } catch (MalformedURLException e) {
                return null;
            }
        }
    }
}
