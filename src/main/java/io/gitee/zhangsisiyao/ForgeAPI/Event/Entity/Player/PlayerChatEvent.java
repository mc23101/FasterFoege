package io.gitee.zhangsisiyao.ForgeAPI.Event.Entity.Player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

/**
 * 玩家与玩家之间发送消息事件 <br/>
 * 此事件通过{@link io.gitee.zhangsisiyao.ForgeAPI.Entity.Player.Player#sendMessageToPlayer(EntityPlayer, EntityPlayer, String)}执行触发<br/>
 * {@link #sender} 消息的发送者<br/>
 * {@link #message} 发送的消息<br/>
 * <br/>
 * 此事件是可取消的 {@link Cancelable}. <br/>
 * 如果事件取消，接收者将不会收到发送者所发送的消息
 * <br/>
 *  此事件是通过 {@link MinecraftForge#EVENT_BUS}执行的.
 *  **/
@Cancelable
public class PlayerChatEvent extends PlayerEvent {

    private EntityPlayer sender;


    private String message;

    public PlayerChatEvent(EntityPlayer sender, String message) {
        super(sender);
        this.message=message;
        this.sender=sender;
    }

    public EntityPlayer getSender() {
        return sender;
    }


    public String getMessage() {
        return message;
    }
}
