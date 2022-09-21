package io.gitee.zhangsisiyao.ForgeAPI.Event.Entity.Player;

import net.minecraft.advancements.Advancement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;


/**
 * 玩家完成一个成就事件<br/>
 * {@link #entityPlayer} 完成成就的玩家<br/>
 * {@link #advancement}  完成的成就<br/>
 *
 * 此事件可取消.
 * */
@Cancelable
public class PlayerAdvancementEvent extends PlayerEvent {
    private final Advancement advancement;

    public PlayerAdvancementEvent(EntityPlayer player, Advancement advancement) {
        super(player);
        this.advancement = advancement;
    }

    public Advancement getAdvancement()
    {
        return advancement;
    }
}
