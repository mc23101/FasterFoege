package io.gitee.zhangsisiyao.ForgeAPI.Event.Entity.Player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.relauncher.Side;


/**
 * 玩家加入游戏事件<br/>
 * 该事件通过{@link io.gitee.zhangsisiyao.ForgeAPI.FasterForge.Event.Entity.Player.PlayerJoinEventTrigger}触发<br/>
 * {@link PlayerJoinEvent#entityPlayer} 加入游戏的玩家<br/>
 * {@link PlayerJoinEvent#side} 触发事件的提供方（客户端或服务端）<br/>
 * <br/>
 * 此事件可取消 {@link Cancelable}.<br/>
 * 取消后,优先级较低或同级的此事件将被忽略<br/>
 * 此事件是通过 {@link MinecraftForge#EVENT_BUS}执行的.
 * */
@Cancelable
public class PlayerJoinEvent extends PlayerEvent {

    private final Side side;

    public PlayerJoinEvent(EntityPlayer entityPlayer, Side side) {
        super(entityPlayer);
        this.side = side;
    }

    public Side getSide() {
        return side;
    }
}
