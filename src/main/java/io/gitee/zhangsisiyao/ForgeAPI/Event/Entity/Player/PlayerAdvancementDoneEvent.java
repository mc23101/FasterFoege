package io.gitee.zhangsisiyao.ForgeAPI.Event.Entity.Player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PlayerAdvancementDoneEvent extends PlayerEvent {
    public PlayerAdvancementDoneEvent(EntityPlayer player) {
        super(player);
    }
}
