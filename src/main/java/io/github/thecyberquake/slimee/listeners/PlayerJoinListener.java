package io.github.thecyberquake.slimee.listeners;

import io.github.thecyberquake.slimee.SlimeE;
import io.github.thecyberquake.slimee.statics.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.annotation.Nonnull;

public class PlayerJoinListener implements Listener {

    private final SlimeE plugin;

    public PlayerJoinListener(@Nonnull SlimeE plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent e) {
        emc2Warning(e);
    }

    private void emc2Warning(PlayerJoinEvent e) {
        if (plugin.getManagerSupportedPlugins().isInstalledEMC2() && (e.getPlayer().isOp() || e.getPlayer().hasPermission("equitech.admin"))) {
            if (!plugin.getConfigMainClass().getBools().getEMC2DisableWarning()) {
                for (String s : Messages.messageEMC2Installed(plugin)) {
                    e.getPlayer().sendMessage(s);
                }
            }
        }
    }

}
