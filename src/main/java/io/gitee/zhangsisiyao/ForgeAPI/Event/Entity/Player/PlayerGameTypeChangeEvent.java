package io.gitee.zhangsisiyao.ForgeAPI.Event.Entity.Player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.GameType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.relauncher.Side;
/**
 * 玩家模式改变事件<br/>
 * 此事件通过{@link io.gitee.zhangsisiyao.ForgeAPI.FasterForge.Event.Entity.Player.GameModeChangeEventTrigger}触发<br/>
 * {@link PlayerGameTypeChangeEvent#entityPlayer} 改变模式的玩家<br/>
 * {@link PlayerGameTypeChangeEvent#newGameType} 新的游戏模式<br/>
 * {@link PlayerGameTypeChangeEvent#oldGameType} 旧的游戏模式<br/>
 * {@link PlayerGameTypeChangeEvent#side} 触发事件的提供方（客户端或服务端）<br/>
 * <br/>
 * 此事件可取消 {@link Cancelable}.<br/>
 * 取消后,优先级较低或同级的此事件将被忽略<br/>
 * 此事件是通过 {@link MinecraftForge#EVENT_BUS}执行的.
 * */
@Cancelable
public class PlayerGameTypeChangeEvent extends PlayerEvent {

    private final GameType newGameType;

    private final GameType oldGameType;

    private final Side side;

    public PlayerGameTypeChangeEvent(EntityPlayer entityPlayer, GameType newGameType,GameType oldGameType, Side side) {
        super(entityPlayer);
        this.newGameType = newGameType;
        this.side = side;
        this.oldGameType=oldGameType;
    }

    public Side getSide() {
        return side;
    }

    public GameType getNewGameType() {
        return newGameType;
    }

    public GameType getOldGameType() {
        return oldGameType;
    }
}
