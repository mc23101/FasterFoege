package com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation.Loader;

import com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation.AnnotationLoader;
import com.github.zhangsiyao.FasterForge.Minecraft.Annotation.MinecraftBlock;
import com.github.zhangsiyao.FasterForge.ForgeBoot.Manager.BlockManager;
import com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation.Enum.BlockMaterial;
import com.github.zhangsiyao.FasterForge.MinecraftCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

@SuppressWarnings("all")
@AnnotationLoader
public class BlockLoader extends com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation.Loader.AnnotationLoader {
    public BlockLoader() {
    }

    @Override
    public void loadFromClass() {
        try {
            Set<Class<?>> classes = MinecraftCore.reflection.getTypesAnnotatedWith(MinecraftBlock.class);
            for(Class c:classes){
                MinecraftBlock annotation = (MinecraftBlock) c.getAnnotation(MinecraftBlock.class);
                String modId = annotation.modId();
                String name=annotation.name();
                BlockMaterial blockMaterial=annotation.material();
                Material material=BlockLoader.getMaterial(blockMaterial);
                ResourceLocation location = new ResourceLocation(modId, name);

                Constructor constructor = c.getConstructor(Material.class);
                constructor.setAccessible(true);
                Block block = (Block) constructor.newInstance(material);
                block.setRegistryName(location);
                BlockManager.registerBlock(block);
            }
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadFromField() {
        try {
            Set<Field> fields = MinecraftCore.reflection.getFieldsAnnotatedWith(MinecraftBlock.class);
            for(Field field:fields){
                MinecraftBlock annotation = field.getAnnotation(MinecraftBlock.class);
                String modId = annotation.modId();
                String name=annotation.name();
                Class DeclaringClass=field.getDeclaringClass();
                ResourceLocation location = new ResourceLocation(modId, name);
                field.setAccessible(true);
                if(!isStaticField(field,MinecraftBlock.class)){
                    continue;
                }
                Block block= (Block) field.get(DeclaringClass);
                block.setRegistryName(location);
                BlockManager.registerBlock(block);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadFromMethod() {

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
                return Material.AIR;
        }
    }


}
