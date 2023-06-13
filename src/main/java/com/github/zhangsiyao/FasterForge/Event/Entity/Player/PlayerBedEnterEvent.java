package com.github.zhangsiyao.FasterForge.Event.Entity.Player;

import com.github.zhangsiyao.FasterForge.ForgeBoot.Event.Entity.Player.PlayerBedEventTrigger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.entity.player.EntityPlayer.SleepResult;


/**
 * 玩家准备躺到床上时触发此事件<br/>
 * 此事件通过{@link PlayerBedEventTrigger}触发<br/>
 * {@link PlayerBedEnterEvent#entityPlayer} 准备躺在床上的玩家<br/>
 * {@link PlayerBedEnterEvent#bedPos} 床的位置<br/>
 * {@link PlayerBedEnterEvent#side} 事件的提供方(客户端或服务端)<br/>
 * <br/>
 * 此事件是不可取消的.<br/>
 * 事件通过{@link MinecraftForge#EVENT_BUS}执行<br/>
 * */
public class PlayerBedEnterEvent extends PlayerEvent {

    private final BlockPos bedPos;

    private final SleepResult sleepResult;

    private final Side side;

    public PlayerBedEnterEvent(@NotNull EntityPlayer entityPlayer,
                               @NotNull BlockPos bedPos,
                               @NotNull SleepResult sleepResult,
                               @NotNull Side side) {
        super(entityPlayer);
        this.bedPos=bedPos;
        this.sleepResult=sleepResult;
        this.side=side;
    }

    public BlockPos getBedPos() {
        return bedPos;
    }

    public Side getSide() {
        return side;
    }

    public SleepResult getSleepResult() {
        return sleepResult;
    }
}
