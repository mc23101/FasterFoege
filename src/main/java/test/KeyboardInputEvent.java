package test;


import com.SiYao.ForgeAPI.Minecraft;
import com.SiYao.ForgeAPI.NetWork.NetworkPack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;

import java.util.Set;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class KeyboardInputEvent {
    public static final KeyBinding MY_HOTKEY = new KeyBinding("key.my_mod.hotkey_1", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_K, "key.category.test");

    public static void init() {
        ClientRegistry.registerKeyBinding(MY_HOTKEY);
    }

    @SubscribeEvent
    public static void onKeyPressed(InputEvent.KeyInputEvent event) {
        if (MY_HOTKEY.isPressed()) {
//            gui gaui= new gui();
//            Minecraft.getMinecraft().displayGuiScreen(gaui);
            Minecraft.sendToServer("test",new NetworkPack("TEST","500",null));
            ///System.out.println(new ResourceLocation("aaaa").getResourceDomain());
            //System.out.println(ModelLoaderRegistry.getActualLocation(new ResourceLocation("aaa")));
//            System.out.println(Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry("mymod:textures/example.png"));
            SimpleReloadableResourceManager resourceManager = (SimpleReloadableResourceManager) net.minecraft.client.Minecraft.getMinecraft().getResourceManager();
            Set<String> resourceDomains = resourceManager.getResourceDomains();
            for (String s:resourceDomains){
                System.out.println(s);
            }

        }
    }
}
