package io.gitee.zhangsisiyao.ForgeAPI.Event.Entity.Player;

import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.jetbrains.annotations.NotNull;

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
     * 如果该事件被取消, 那么交换将不会进行, 即物品不会进行交换.<br/>
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
