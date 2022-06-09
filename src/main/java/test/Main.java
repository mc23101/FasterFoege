package test;

import com.SiYao.ForgeAPI.MinecraftCore;
import com.SiYao.ForgeAPI.Model.CustomModelLoader;
import com.SiYao.ForgeAPI.Resources.ResourceType;
import com.SiYao.ForgeAPI.Utils.ResourcesUtil;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
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

    public static final CreativeTabs EXAMPLE_CREATIVE_TAB = MinecraftCore.registerCreativeTabs("advancements.story.upgrade_tools.description",new ItemStack(Items.DIAMOND));
    public static Item firstItem= MinecraftCore.registerItem(new Item()
            .setCreativeTab(EXAMPLE_CREATIVE_TAB)
            .setRegistryName(new ResourceLocation(Main.MODID,"example")));
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ModelLoaderRegistry.registerLoader(new CustomModelLoader());
        logger = event.getModLog();
        MinecraftCore.registerChannel(this,"test");

        MinecraftCore.preinit();
        MinecraftCore.registerResource(new ResourceLocation("custom","textures/example.png"), ResourcesUtil.getResourcesPath("assets/example.png"), ResourceType.TEXTURE);

        ModelLoader.setCustomModelResourceLocation(Main.firstItem, 0, new ModelResourceLocation(Main.firstItem.getRegistryName(), "inventory"));

    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        KeyboardInputEvent.init();
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

    }
}
