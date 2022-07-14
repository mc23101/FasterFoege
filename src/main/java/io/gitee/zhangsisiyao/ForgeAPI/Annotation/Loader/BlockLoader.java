package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

import io.gitee.zhangsisiyao.ForgeAPI.Annotation.Enum.BlockMaterial;
import io.gitee.zhangsisiyao.ForgeAPI.Annotation.Exception.IllegalClassException;
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
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class BlockLoader {
    private static Logger logger = LogManager.getLogger(BlockLoader.class);

    public static void BlockAnnotationLoader(Object o){
        logger.info("注册方块中.........");
        Package pack = o.getClass().getPackage();
        Reflections reflections=new Reflections(pack.getName());
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MinecraftBlock.class);
        for(Class c:classes){
            if(ReflectionUtil.isExtendFrom(c,Block.class)){
                try {
                    MinecraftBlock annotation = (MinecraftBlock) c.getAnnotation(MinecraftBlock.class);
                    String modId = annotation.modId();
                    String name=annotation.name();
                    BlockMaterial blockMaterial=annotation.material();
                    Material material=BlockLoader.getMaterial(blockMaterial);
                    Block block = null;
                    Constructor constructor = c.getConstructor(Material.class);
                    constructor.setAccessible(true);
                    block = (Block) constructor.newInstance(material);
                    if(block!=null){
                        block.setRegistryName(new ResourceLocation(modId,name));
                        MinecraftCore.ItemManger.registerBlocks(block);
                        MinecraftCore.ItemManger.registerItems(new ItemBlock(block).setRegistryName(new ResourceLocation(modId,name)));
                    }
                    logger.info("方块:"+modId+":"+name+"注册成功!");
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    throw new IllegalClassException("MinecraftBlock注解使用错误,请将此注解作用在Block的子类上!");
                } catch (IllegalClassException e) {
                    e.printStackTrace();
                }
            }


        }
        logger.info("一共注册"+classes.size()+"个方块。");
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
