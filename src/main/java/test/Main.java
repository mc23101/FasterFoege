package test;

import com.SiYao.ForgeAPI.Minecraft;
import com.SiYao.ForgeAPI.Model.CustomModelLoader;
import com.SiYao.ForgeAPI.Resources.CustomResourceManger;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main
{
    public static final String MODID = "mymod";
    public static final String NAME = "Example Mod";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ModelLoaderRegistry.registerLoader(new CustomModelLoader());
        logger = event.getModLog();
        Minecraft.registerChannel(this,"test");
        SimpleReloadableResourceManager resourceManager = (SimpleReloadableResourceManager) net.minecraft.client.Minecraft.getMinecraft().getResourceManager();
        resourceManager.registerReloadListener(new CustomResourceManger());
//        SimpleReloadableResourceManager Manager = (SimpleReloadableResourceManager) resourceManager;
//        Manager.reloadResourcePack(CustomResourcePack.INSTANCE);

    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        KeyboardInputEvent.init();
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

    }
}
