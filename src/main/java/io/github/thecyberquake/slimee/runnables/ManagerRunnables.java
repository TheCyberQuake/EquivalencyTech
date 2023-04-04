package io.github.thecyberquake.slimee.runnables;

import io.github.thecyberquake.slimee.SlimeE;

public class ManagerRunnables {

    private final SlimeE plugin;

    private RunnableSave runnableSave;
    private RunnableEQTick runnableEQTick;

    public RunnableSave getRunnableSave() {
        return runnableSave;
    }

    public RunnableEQTick getRunnableEQTick() {
        return runnableEQTick;
    }

    public ManagerRunnables(SlimeE plugin) {
        this.plugin = plugin;
        setupRunnables();
    }

    private void setupRunnables() {

        runnableSave = new RunnableSave(plugin);
        runnableSave.runTaskTimer(plugin, 0, 100L);

        runnableEQTick = new RunnableEQTick(plugin);
        runnableEQTick.runTaskTimer(plugin, 0, 20L);

    }


}
