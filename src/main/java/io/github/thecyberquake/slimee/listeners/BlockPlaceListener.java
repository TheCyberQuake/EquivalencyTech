package io.github.thecyberquake.slimee.listeners;

import io.github.thecyberquake.slimee.SlimeE;
import io.github.thecyberquake.slimee.statics.ContainerStorage;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    private final SlimeE plugin;

    public BlockPlaceListener(SlimeE plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChestPlace(BlockPlaceEvent e) {
        if (ContainerStorage.isCraftable(e.getItemInHand(), plugin) && e.getItemInHand().getType() != Material.CHEST) {
            e.setCancelled(true);
        }
    }

}
