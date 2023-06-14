package com.github.zhangsiyao.FasterForge.Event.Entity.Player;

import com.github.zhangsiyao.FasterForge.ForgeBoot.Minecraft.EventTrigger.Entity.Player.PlayerAdvancementEventTrigger;
import net.minecraft.advancements.Advancement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.jetbrains.annotations.NotNull;


/**
 * 玩家完成一个成就事件<br/>
 * 此事件通过{@link PlayerAdvancementEventTrigger}触发<br/>
 * {@link #entityPlayer} 完成成就的玩家<br/>
 * {@link #advancement}  完成的成就<br/>
 * <br/>
 * 此事件不可取消.<br/>
 * 此事件是通过 {@link MinecraftForge#EVENT_BUS}执行的.
 * */
public class PlayerAdvancementEvent extends PlayerEvent {
    private final Advancement advancement;

    private final Side side;

    public PlayerAdvancementEvent(@NotNull EntityPlayer player, @NotNull Advancement advancement,@NotNull Side side) {
        super(player);
        this.advancement = advancement;
        this.side = side;
    }


    public Side getSide() {
        return side;
    }

    public Advancement getAdvancement()
    {
        return advancement;
    }
}
