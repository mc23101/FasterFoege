package io.gitee.zhangsisiyao.ForgeAPI.Event.Entity.Player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;

public class PlayerArmorStandManipulateEvent extends PlayerEvent {

    private final Side side;

    public PlayerArmorStandManipulateEvent(EntityPlayer player, Side side) {
        super(player);
        this.side=side;
    }

    public Side getSide() {
        return side;
    }
}