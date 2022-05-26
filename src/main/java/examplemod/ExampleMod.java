package examplemod;

import ForgeAPI.NetWork.NetworkManager;
import examplemod.client.event.KeyboardInputEvent;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ExampleMod.MODID, name = ExampleMod.NAME, version = ExampleMod.VERSION)
public class ExampleMod
{
    public static final String MODID = "mod";
    public static final String NAME = "Example Mod";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        NetworkManager.registerChannel(this,"test");
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        KeyboardInputEvent.init();
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
