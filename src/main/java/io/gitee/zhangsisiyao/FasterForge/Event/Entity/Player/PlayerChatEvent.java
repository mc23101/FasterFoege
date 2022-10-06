package io.gitee.zhangsisiyao.FasterForge.Event.Entity.Player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.relauncher.Side;
import org.jetbrains.annotations.NotNull;

/**
 * 玩家与玩家之间发送消息事件 <br/>
 * 此事件通过{@link NetHandlerPlayServer#processChatMessage(CPacketChatMessage)}执行触发<br/>
 * {@link #sender} 消息的发送者<br/>
 * {@link #message} 发送的消息<br/>
 * <br/>
 * 此事件是可取消的 {@link Cancelable}. <br/>
 * 如果事件取消：<br/>
 * 在客户端,服务器将不会收到发送者所发送的消息<br/>
 * 在服务端,服务器不会分发消息到每个玩家中<br/>
 * <br/>
 *  此事件是通过 {@link MinecraftForge#EVENT_BUS}执行的.
 *  **/
@Cancelable
public class PlayerChatEvent extends PlayerEvent {
    private final EntityPlayer sender;

    private final String message;

    private final Side side;

    public PlayerChatEvent(@NotNull EntityPlayer sender,@NotNull String message,@NotNull Side side) {
        super(sender);
        this.message=message;
        this.sender=sender;
        this.side = side;
    }

    public EntityPlayer getSender() {
        return sender;
    }

    public Side getSide() {
        return side;
    }

    public String getMessage() {
        return message;
    }
}
