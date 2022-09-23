package io.gitee.zhangsisiyao.ForgeAPI.Event.Entity.Player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.GameType;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.relauncher.Side;

@Cancelable
public class PlayerGameTypeChangeEvent extends PlayerEvent {

    private final GameType newGameType;

    private final Side side;

    public PlayerGameTypeChangeEvent(EntityPlayer player, GameType newGameType, Side side) {
        super(player);
        this.newGameType = newGameType;
        this.side = side;
    }

    public Side getSide() {
        return side;
    }

    public GameType getNewGameType() {
        return newGameType;
    }
}
