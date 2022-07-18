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
    private static final Logger logger = LogManager.getLogger(BlockLoader.class);

    private static int success=0;
    private static int error=0;

    public static void BlockAnnotationLoader(Object o){
        logger.info("注册方块中.........");
        Package pack = o.getClass().getPackage();
        Reflections reflections=new Reflections(pack.getName());

        loadFromClass(reflections);

        loadFromField(reflections);

        logger.info("一共注册"+success+error+"个方块。成功:"+success+"  失败:"+error);
    }

    private static void loadFromClass(Reflections reflections){
        try {
            Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MinecraftBlock.class);
            for(Class c:classes){
                MinecraftBlock annotation = (MinecraftBlock) c.getAnnotation(MinecraftBlock.class);
                String modId = annotation.modId();
                String name=annotation.name();
                BlockMaterial blockMaterial=annotation.material();
                Material material=BlockLoader.getMaterial(blockMaterial);
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
                    logger.error("在"+c.getName()+"处的MinecraftBlock注解使用错误,请将此注解作用在net.minecraft.block.Block的子类上!");
                }else if(isRegistered){
                    error++;
                    logger.error("在"+c.getName()+"处的modId:"+modId+",name:"+name+"已经被注册!!!");
                }
            }
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void loadFromField(Reflections reflections){
        try {
            Set<Field> fieldsAnnotatedWith = reflections.getFieldsAnnotatedWith(MinecraftBlock.class);
            for(Field field:fieldsAnnotatedWith){
                field.setAccessible(true);
                MinecraftBlock annotation = field.getAnnotation(MinecraftBlock.class);
                String modId = annotation.modId();
                String name=annotation.name();
                Object object = field.get(field.getDeclaringClass());
                ResourceLocation location = new ResourceLocation(modId, name);
                boolean isExtended=ReflectionUtil.isExtendFrom(field.getType(),Block.class);
                boolean isStatic=Modifier.isStatic(field.getModifiers());
                boolean isRegistered=MinecraftCore.ItemManger.containBlock(location);
                boolean isNull=object==null;
                boolean canRegister=isExtended && isStatic && !isRegistered && !isNull;
                if(canRegister){
                    Block block = (Block)object;
                    block.setRegistryName(location);
                    MinecraftCore.ItemManger.registerBlocks(block);
                    MinecraftCore.ItemManger.registerItems(new ItemBlock(block).setRegistryName(location));
                    success++;
                }else if(!isExtended){
                    error++;
                    logger.error("在"+field.getDeclaringClass().getName()+"处的MinecraftBlock注解使用错误,请将此注解作用在net.minecraft.block.Block的对象上!");
                }else if(isRegistered){
                    error++;
                    logger.error("在"+field.getDeclaringClass().getName()+"处的modId:"+modId+",name:"+name+"已经被注册!!!");
                }else if(isNull){
                    error++;
                    logger.error("在"+field.getDeclaringClass().getName()+"中的字段:"+field.getName()+"对象为null");
                }else if(!isStatic){
                    error++;
                    logger.error("在"+field.getDeclaringClass().getName()+"中的字段:"+field.getName()+"注解MinecraftBlock注解使用错误，请作用在static字段上.");
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
