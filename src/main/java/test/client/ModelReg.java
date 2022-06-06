package test.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import test.test;

@Mod.EventBusSubscriber(value = Side.CLIENT,modid= test.MODID)
public class ModelReg {
    @SubscribeEvent
    public static void onModelReg(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(ItemInitializer.firstItem, 0, new ModelResourceLocation(ItemInitializer.firstItem.getRegistryName(), "inventory"));
    }
}
