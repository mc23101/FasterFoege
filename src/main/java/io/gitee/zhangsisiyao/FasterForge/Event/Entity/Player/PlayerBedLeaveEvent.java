package io.gitee.zhangsisiyao.FasterForge.Event.Entity.Player;

import io.gitee.zhangsisiyao.FasterForge.ForgeBoot.Event.Entity.Player.PlayerBedEventTrigger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.jetbrains.annotations.NotNull;

/**
 * 玩家离开床时触发此事件.<br/>
 * 此事件通过{@link PlayerBedEventTrigger}触发<br/>
 * {@link PlayerBedLeaveEvent#entityPlayer} 离开床的玩家<br/>
 * {@link PlayerBedLeaveEvent#bedPos} 床的位置<br/>
 * {@link PlayerBedLeaveEvent#isSetSpawn} 是否设置重生点<br/>
 * {@link PlayerBedLeaveEvent#side} 事件的提供方(客户端或服务端)<br/>
 * <br/>
 * 此事件是不可取消的<br/>
 * 此事件通过{@link MinecraftForge#EVENT_BUS}执行
 * */
public class PlayerBedLeaveEvent extends PlayerEvent {

    private final BlockPos bedPos;

    private boolean isSetSpawn;

    private final Side side;

    public PlayerBedLeaveEvent(@NotNull EntityPlayer entityPlayer,
                               @NotNull BlockPos bedPos,
                               @NotNull boolean isSetSpawn,
                               @NotNull Side side) {
        super(entityPlayer);
        this.bedPos=bedPos;
        this.isSetSpawn=isSetSpawn;
        this.side=side;
    }

    public BlockPos getBedPos() {
        return bedPos;
    }

    public boolean isSetSpawn() {
        return isSetSpawn;
    }

    public Side getSide() {
        return side;
    }

    public void setSpawn(boolean isSetSpawn){
        this.isSetSpawn=isSetSpawn;
    }
}
