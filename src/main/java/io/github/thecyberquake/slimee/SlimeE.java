package io.github.thecyberquake.slimee;

import co.aikar.commands.PaperCommandManager;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import io.github.thecyberquake.slimee.commands.Commands;
import io.github.thecyberquake.slimee.configuration.ConfigMain;
import io.github.thecyberquake.slimee.item.EQItems;
import io.github.thecyberquake.slimee.listeners.ManagerEvents;
import io.github.thecyberquake.slimee.misc.ManagerSupportedPlugins;
import io.github.thecyberquake.slimee.recipes.EmcDefinitions;
import io.github.thecyberquake.slimee.recipes.Recipes;
import io.github.thecyberquake.slimee.runnables.ManagerRunnables;
import io.github.thebusybiscuit.slimefun4.libraries.dough.updater.GitHubBuildsUpdater;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class SlimeE extends JavaPlugin implements SlimefunAddon {

    private static SlimeE instance;
    private PaperCommandManager commandManager;

    private ConfigMain configMainClass;
    private EmcDefinitions emcDefinitions;
    private EQItems eqItems;
    private Recipes recipes;
    private ManagerEvents managerEvents;
    private ManagerRunnables managerRunnables;
    private ManagerSupportedPlugins managerSupportedPlugins;

    private boolean isUnitTest = false;

    public PaperCommandManager getCommandManager() {
        return commandManager;
    }

    public static SlimeE getInstance() {
        return instance;
    }

    public ConfigMain getConfigMainClass() {
        return configMainClass;
    }

    public EmcDefinitions getEmcDefinitions() {
        return emcDefinitions;
    }

    public EQItems getEqItems() {
        return eqItems;
    }

    public Recipes getRecipes() {
        return recipes;
    }

    public ManagerEvents getManagerEvents() {
        return managerEvents;
    }

    public ManagerRunnables getManagerRunnables() {
        return managerRunnables;
    }

    public ManagerSupportedPlugins getManagerSupportedPlugins() {
        return managerSupportedPlugins;
    }

    public SlimeE() {
        super();
    }

    protected SlimeE(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
        isUnitTest = true;
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/TheCyberQuake/SlimeE/issues";
    }

    @Override
    public void onEnable() {
        Config cfg = new Config(this);

        getLogger().info("########################################");
        getLogger().info("   SlimeE - Created by TheCyberQuake    ");
        getLogger().info("########################################");

        instance = this;

        if (this.getConfig().getBoolean("auto-update") && this.getDescription().getVersion().startsWith("DEV")) {
            new GitHubBuildsUpdater(this, this.getFile(), "TheCyberQuake/SlimeE/master").start();
        }

        configMainClass = new ConfigMain(this);
        eqItems = new EQItems(this);
        managerSupportedPlugins = new ManagerSupportedPlugins(this);
        emcDefinitions = new EmcDefinitions(this);
        recipes = new Recipes(this);
        managerEvents = new ManagerEvents(this);
        managerRunnables = new ManagerRunnables(this);

        registerCommands();

        if (!isUnitTest) {
            int pluginId = 18137;
            Metrics metrics = new Metrics(this, pluginId);
            metrics.addCustomChart(new SimplePie("slimefun", () -> String.valueOf(getManagerSupportedPlugins().isInstalledSlimefun())));
            metrics.addCustomChart(new SimplePie("emc2", () -> String.valueOf(getManagerSupportedPlugins().isInstalledEMC2())));
        }

    }

    @Override
    public void onDisable() {
        saveConfig();
        configMainClass.saveAdditionalConfigs();
    }

    private void registerCommands() {
        commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new Commands(this));
    }






}
