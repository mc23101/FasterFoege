package io.gitee.zhangsisiyao.FasterForge.Event.Entity.Player;

import io.gitee.zhangsisiyao.FasterForge.ForgeBoot.Event.Entity.Player.PlayerGameModeChangeEventTrigger;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.relauncher.Side;
import org.jetbrains.annotations.NotNull;

/**
 *当玩家与装甲架交互并且进行交换, 取回或放置物品时触发本事件.<br/>
 * 此事件通过{@link PlayerGameModeChangeEventTrigger}触发<br/>
 * {@link PlayerArmorStandManipulateEvent#entityPlayer} 与装甲架交互的玩家<br/>
 * {@link PlayerArmorStandManipulateEvent#armorStand} 装甲架实体<br/>
 * {@link PlayerArmorStandManipulateEvent#armorStandItem} 与玩家交互的装甲架装备<br/>
 * {@link PlayerArmorStandManipulateEvent#slot} 与玩家交互的装甲架装备槽<br/>
 * {@link PlayerArmorStandManipulateEvent#side} 事件的提供方(客户端服务端)<br/>
 * <br/>
 * 事件是可取消的{@link Cancelable}<br/>
 * 如果事件被取消:<br/>
 * 在服务器,如果该事件被取消, 那么交换将不会进行, 即物品不会进行交换.<br/>
 * 在客户端,无明显影响<br/>
 * <br/>
 * 此事件是通过 {@link MinecraftForge#EVENT_BUS}执行的.<br/>
 * */
@Cancelable
public class PlayerArmorStandManipulateEvent extends PlayerEvent {

    private final  EntityArmorStand armorStand;
    private final ItemStack playerHandItem;

    private final ItemStack armorStandItem;

    private final EntityEquipmentSlot slot;

    private final Side side;

    public PlayerArmorStandManipulateEvent(@NotNull EntityPlayer entityPlayer,
                                           @NotNull EntityArmorStand armorStand,
                                           @NotNull ItemStack playerHandItem,
                                           @NotNull ItemStack armorStandItem,
                                           @NotNull EntityEquipmentSlot slot,
                                           @NotNull Side side) {
        super(entityPlayer);
        this.armorStand=armorStand;
        this.playerHandItem=playerHandItem;
        this.armorStandItem=armorStandItem;
        this.slot=slot;
        this.side=side;
    }

    /**
     * 返回盔甲架的物品 如果玩家手持的物品为null时, 并且盔甲架的物品也为null时, 那么则玩家和盔甲架之间将不会有物品交换. <br/>
     * 如果玩家手持的物品为null时，但是盔甲架的物品不为null时，那么玩家将获得盔甲架的物品.<br/>
     * 如果玩家手持的物品不为null时，但盔甲架的物品为null时，则玩家物品将被放在盔甲架上.<br/>
     * 如果玩家手持的物品不为null时，盔甲架的物品也不为null时，则玩家物品将和盔甲架的物品将进行交换.<br/>
     * */
    public EntityArmorStand getArmorStand() {
        return armorStand;
    }

    public ItemStack getPlayerHandItem() {
        return playerHandItem;
    }

    public ItemStack getArmorStandItem() {
        return armorStandItem;
    }

    public EntityEquipmentSlot getSlot() {
        return slot;
    }

    public Side getSide() {
        return side;
    }
}
