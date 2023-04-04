package io.github.thecyberquake.slimee.runnables;

import io.github.thecyberquake.slimee.SlimeE;
import org.bukkit.scheduler.BukkitRunnable;

public class RunnableSave extends BukkitRunnable {

    public final SlimeE plugin;

    public RunnableSave(SlimeE plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getConfigMainClass().saveAdditionalConfigs();
    }
}