package io.github.thecyberquake.slimee.listeners;

import io.github.thecyberquake.slimee.SlimeE;
import io.github.thecyberquake.slimee.gui.GuiTransmutationOrb;
import io.github.thecyberquake.slimee.statics.ContainerStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class OrbOpenListener implements Listener {

    private final SlimeE plugin;

    public OrbOpenListener(@Nonnull SlimeE plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onClick(PlayerInteractEvent e) {
        if (e.getItem() != null && e.getItem().getItemMeta() != null && (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            Player player = e.getPlayer();
            ItemStack i = e.getItem();
            if (ContainerStorage.isTransmutationOrb(i, plugin)) {
                e.setCancelled(true);
                GuiTransmutationOrb gui = GuiTransmutationOrb.buildGui(plugin, player);
                gui.open(player);
            }
        }
    }

}
