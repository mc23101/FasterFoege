package test;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT,modid= Main.MODID)
public class ModelReg {
    @SubscribeEvent
    public static void onModelReg(ModelRegistryEvent event) {

    }
}
