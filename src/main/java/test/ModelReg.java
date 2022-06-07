package test;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT,modid= Main.MODID)
public class ModelReg {
    @SubscribeEvent
    public static void onModelReg(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(ItemInitializer.firstItem, 0, new ModelResourceLocation(ItemInitializer.firstItem.getRegistryName(), "inventory"));
    }
}
