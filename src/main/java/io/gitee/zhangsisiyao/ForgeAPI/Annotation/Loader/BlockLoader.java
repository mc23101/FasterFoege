package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

import io.gitee.zhangsisiyao.ForgeAPI.Annotation.Enum.BlockMaterial;
import io.gitee.zhangsisiyao.ForgeAPI.Annotation.MinecraftBlock;
import io.gitee.zhangsisiyao.ForgeAPI.MinecraftCore;
import io.gitee.zhangsisiyao.ForgeAPI.Utils.ReflectionUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Set;

@SuppressWarnings("all")
public class BlockLoader {
    public static final Logger logger = LogManager.getLogger("ForgeFrame/BlockLoader");

    private static final String errorType="方块";
    private static int success=0;
    private static int error=0;

    public static void BlockAnnotationLoader(Reflections reflections) {
        loadFromClass(reflections);
        loadFromField(reflections);
        logger.info("一共注册"+(success+error)+"个方块。成功:"+success+"  失败:"+error);
    }

    /**
     *加载类上得{@link io.gitee.zhangsisiyao.ForgeAPI.Annotation.MinecraftBlock} 注解
     * @param reflections mod主类得反射包
     * */
    private static void loadFromClass(Reflections reflections)  {
        try {
            Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MinecraftBlock.class);
            for(Class c:classes){
                MinecraftBlock annotation = (MinecraftBlock) c.getAnnotation(MinecraftBlock.class);
                String modId = annotation.modId();
                String name=annotation.name();
                BlockMaterial blockMaterial=annotation.material();
                Material material=null;
                if(blockMaterial!=BlockMaterial.NULL){
                    material=BlockLoader.getMaterial(blockMaterial);
                }else {
                    material=Material.ROCK;
                }
                ResourceLocation location = new ResourceLocation(modId, name);

                boolean isExtended=ReflectionUtil.isExtendFrom(c,Block.class);
                boolean isRegistered=MinecraftCore.ItemManger.containBlock(location);
                boolean canRegister=isExtended && !isRegistered;

                if(canRegister){
                    Block block;
                    Constructor constructor = c.getConstructor(Material.class);
                    constructor.setAccessible(true);
                    block = (Block) constructor.newInstance(material);
                    block.setRegistryName(location);
                    MinecraftCore.ItemManger.registerBlocks(block);
                    MinecraftCore.ItemManger.registerItems(new ItemBlock(block).setRegistryName(location));
                    success++;
                }else if(!isExtended){
                    error++;
                    AnnotationFactory.throwException(logger,errorType,location,"方块应为"+Block.class.getName()+"的子类",c);
                }else if(isRegistered){
                    error++;
                    AnnotationFactory.throwException(logger,errorType,location,location+"名称已被注册",c);
                }
            }
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载静态变量的{@link io.gitee.zhangsisiyao.ForgeAPI.Annotation.MinecraftBlock}注解
     * @param reflections mod的主类反射包
     * */
    private static void loadFromField(Reflections reflections){
        try {
            Set<Field> fieldsAnnotatedWith = reflections.getFieldsAnnotatedWith(MinecraftBlock.class);
            for(Field field:fieldsAnnotatedWith){
                field.setAccessible(true);
                MinecraftBlock annotation = field.getAnnotation(MinecraftBlock.class);
                String modId = annotation.modId();
                String name=annotation.name();
                Class DeclaringClass=field.getDeclaringClass();
                ResourceLocation location = new ResourceLocation(modId, name);
                boolean isExtended=ReflectionUtil.isExtendFrom(field.getType(),Block.class);
                boolean isStatic=Modifier.isStatic(field.getModifiers());
                boolean isRegistered=MinecraftCore.ItemManger.containBlock(location);
                boolean canRegister=isExtended && isStatic && !isRegistered;

                if(canRegister){
                    Object object = field.get(DeclaringClass);
                    Block block = (Block)object;
                    boolean isNull=object==null;
                    if(!isNull){
                        block.setRegistryName(location);
                        MinecraftCore.ItemManger.registerBlocks(block);
                        MinecraftCore.ItemManger.registerItems(new ItemBlock(block).setRegistryName(location));
                        logger.debug("方块:"+location+"注册成功!");
                        success++;
                    }else {
                        error++;
                        AnnotationFactory.throwException(logger,errorType,location,"字段:"+field.getName()+"为Null",DeclaringClass,field.getName());
                    }
                }else if(!isExtended){
                    error++;
                    AnnotationFactory.throwException(logger,errorType,location,"方块应为"+Block.class.getName()+"的子类",DeclaringClass,field.getName());
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
        }
    }


    public static Material getMaterial(BlockMaterial blockMaterial){
        switch (blockMaterial){
            case AIR:
                return Material.AIR;
            case GRASS:
                return Material.GRASS;
            case GROUND:
                return Material.GROUND;
            case WOOD:
                return Material.WOOD;
            case ROCK:
                return Material.ROCK;


            case IRON:
                return Material.IRON;
            case ANVIL:
                return Material.ANVIL;
            case WATER:
                return Material.WATER;
            case LAVA:
                return Material.LAVA;
            case LEAVES:
                return Material.LEAVES;


            case PLANTS:
                return Material.PLANTS;
            case VINE:
                return Material.VINE;
            case SPONGE:
                return Material.SPONGE;
            case CLOTH:
                return Material.CLOTH;


            case FIRE:
                return Material.FIRE;
            case SAND:
                return Material.SAND;
            case CIRCUITS:
                return Material.CIRCUITS;
            case CARPET:
                return Material.CARPET;


            case GLASS:
                return Material.GLASS;
            case REDSTONE_LIGHT:
                return Material.REDSTONE_LIGHT;
            case TNT:
                return Material.TNT;


            case CORAL:
                return Material.CORAL;
            case ICE:
                return Material.ICE;
            case PACKED_ICE:
                return Material.PACKED_ICE;
            case SNOW:
                return Material.SNOW;


            case CRAFTED_SNOW:
                return Material.CRAFTED_SNOW;
            case CACTUS:
                return Material.CACTUS;
            case CLAY:
                return Material.CLAY;
            case GOURD:
                return Material.GOURD;


            case DRAGON_EGG:
                return Material.DRAGON_EGG;
            case PORTAL:
                return Material.PORTAL;
            case CAKE:
                return Material.CAKE;
            case WEB:
                return Material.WEB;


            case PISTON:
                return Material.PISTON;
            case BARRIER:
                return Material.BARRIER;
            case STRUCTURE_VOID:
                return Material.STRUCTURE_VOID;
            default:
                return null;
        }
    }
}
