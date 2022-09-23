package io.gitee.zhangsisiyao.ForgeAPI.Event.Entity.Player;

import net.minecraft.advancements.Advancement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.relauncher.Side;


/**
 * 玩家完成一个成就事件<br/>
 * {@link #entityPlayer} 完成成就的玩家<br/>
 * {@link #advancement}  完成的成就<br/>
 *
 * 此事件可取消.
 * 此事件是通过 {@link MinecraftForge#EVENT_BUS}执行的.
 * */
@Cancelable
public class PlayerAdvancementEvent extends PlayerEvent {
    private final Advancement advancement;

    private final Side side;

    public PlayerAdvancementEvent(EntityPlayer player, Advancement advancement, Side side) {
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
