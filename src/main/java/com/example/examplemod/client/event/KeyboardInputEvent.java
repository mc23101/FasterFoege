package com.example.examplemod.client.event;


import com.example.examplemod.client.gui.BackGround;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class KeyboardInputEvent {
    public static final KeyBinding MY_HOTKEY = new KeyBinding("key.my_mod.hotkey_1", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_K, "key.category.test");

    public static void init() {
        ClientRegistry.registerKeyBinding(MY_HOTKEY);
    }

    @SubscribeEvent
    public static void onKeyPressed(InputEvent.KeyInputEvent event) {
        if (MY_HOTKEY.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new BackGround());
//            EntityPlayer player=com.zhang.ForgeAPI.Minecraft.getMinecraft().player;
//            player.sendMessage(new TextComponentString("aaa"));
//            if(player!=null){
//                PlayerInventoryGui playerInventoryGui=new PlayerInventoryGui(player);
//                playerInventoryGui.initGui();
//                player.openGui(playerInventoryGui,5,player.world,player.getPosition().getX(),player.getPosition().getY(),player.getPosition().getZ());
//            }

        }
    }
}
