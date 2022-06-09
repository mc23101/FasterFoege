package test;

import com.SiYao.ForgeAPI.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ItemInitializer {

    public static final CreativeTabs EXAMPLE_CREATIVE_TAB = Minecraft.registerCreativeTabs("advancements.story.upgrade_tools.description",new ItemStack(Items.DIAMOND));

    public static Item firstItem;

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        // 注意 setRegistryName 调用。
        // 每一个物品都对应唯一一个注册名，用于和其他物品区分开来。这个注册名不能含有大写字母。
        // 此方法返回被注册的 Item 对象。
        firstItem = new Item()
                .setCreativeTab(EXAMPLE_CREATIVE_TAB)
                .setRegistryName(new ResourceLocation(Main.MODID,"example"));
        event.getRegistry().registerAll(firstItem);
    }
}
