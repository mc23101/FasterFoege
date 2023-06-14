package com.github.zhangsiyao.FasterForge.Manager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.lang.reflect.InvocationTargetException;
/**
 * 实体管理器,用于操作实体
 * <p>例如:注册实体、注册实体渲染</p>
 * <p>注意事项:一般情况下，注册实体和注册实体渲染是成对存在的</p>
 * <p>注册实体，则此实体应该有对应的实体渲染</p>
 * */
public class EntityManager {
    /**
     * 注册实体和怪物蛋
     * <p>注意事项:一般情况下，注册实体和注册实体渲染是成对存在的</p>
     * <p>注册实体，则此实体应该有对应的实体渲染</p>
     * @param registryName 实体注册名(modId+注册名)
     * @param entityClass 实体的class类
     * @param entityName 实体的名称(尽量和注册名一样)
     * @param id 实体的自定义ID(请使用UUID)
     * @param mod 模组的主类
     * @param trackingRange 实体的追踪范围
     * @param updateFrequency 实体更新频率
     * @param sendsVelocityUpdates 是否发送速度数据包
     * @param eggPrimary 怪物蛋颜色1
     * @param eggSecondary 怪物蛋颜色2

     * */
    public  static void registerEntity(ResourceLocation registryName, Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggPrimary, int eggSecondary){
        EntityRegistry.registerModEntity(registryName,entityClass, entityName, id,mod,trackingRange, updateFrequency,sendsVelocityUpdates,eggPrimary, eggSecondary);
    }

    /**
     * 注册实体的渲染类
     * <p>注意事项:一般情况下，注册实体和注册实体渲染是成对存在的</p>
     * <p>注册实体，则此实体应该有对应的实体渲染</p>
     * @param EntityClass 实体的class类
     * @param render 实体的渲染类
     * */
    public static  void registerEntityRender(Class<?extends Entity> EntityClass,Class<?extends Render> render){
        RenderingRegistry.registerEntityRenderingHandler(EntityClass, new IRenderFactory()
        {
            @Override
            public Render createRenderFor(RenderManager manager) {
                Render render1=null;
                try {
                    render1 = render.getConstructor(RenderManager.class).newInstance(manager);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    e.printStackTrace();
                }
                return render1;
            }
        });
    }

    /**
     * 检查Entity是否被注册
     * @param location Entity的注册名
     * */
    public static boolean containEntity(ResourceLocation location){
        return GameRegistry.findRegistry(EntityEntry.class).containsKey(location);
    }
}
