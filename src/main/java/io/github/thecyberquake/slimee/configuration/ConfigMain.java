package io.github.thecyberquake.slimee.configuration;

import io.github.thecyberquake.slimee.SlimeE;
import io.github.thecyberquake.slimee.misc.Utils;
import io.github.thecyberquake.slimee.statics.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigMain {

    private final SlimeE plugin;

    private final ConfigStrings strings;
    private final ConfigEMC emc;
    private final ConfigBooleans bools;

    private File learnedItemsConfigFile;
    private FileConfiguration learnedItemsConfig;
    private File playerEMCConfigFile;
    private FileConfiguration playerEMCConfig;
    private File blockStoreConfigFile;
    private FileConfiguration blockStoreConfig;
    private File dChestConfigFile;
    private FileConfiguration dChestConfig;
    private File cChestConfigFile;
    private FileConfiguration cChestConfig;

    public ConfigStrings getStrings() {
        return strings;
    }
    public ConfigEMC getEmc() {
        return emc;
    }
    public ConfigBooleans getBools() {
        return bools;
    }

    public File getLearnedItemsConfigFile() {
        return learnedItemsConfigFile;
    }

    public FileConfiguration getLearnedItemsConfig() {
        return learnedItemsConfig;
    }

    public File getPlayerEMCConfigFile() {
        return playerEMCConfigFile;
    }

    public FileConfiguration getPlayerEMCConfig() {
        return playerEMCConfig;
    }

    public File getBlockStoreConfigFile() {
        return blockStoreConfigFile;
    }

    public FileConfiguration getBlockStoreConfig() {
        return blockStoreConfig;
    }

    public File getDChestConfigFile() {
        return dChestConfigFile;
    }

    public FileConfiguration getDChestConfig() {
        return dChestConfig;
    }

    public File getCChestConfigFile() {
        return cChestConfigFile;
    }

    public FileConfiguration getCChestConfig() {
        return cChestConfig;
    }

    public static final String DIS_CHEST_CFG = "DIS_CHESTS";
    public static final String CON_CHEST_CFG = "CON_CHESTS";

    public ConfigMain(SlimeE plugin) {

        this.plugin = plugin;

        strings = new ConfigStrings(plugin);
        emc = new ConfigEMC(plugin);
        bools = new ConfigBooleans(plugin);

        sortConfigs();
    }

    private void sortConfigs() {
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
        createAdditionalConfigs();
    }

    private void createAdditionalConfigs() {
        createLearnedConfig();
        createEmcConfig();
        createBlockStoreConfig();
        createDChestConfig();
        createCChestConfig();
    }

    public void saveAdditionalConfigs() {
        saveEmcConfig();
        saveLearnedConfig();
        saveBlockStoreConfig();
        saveDChestConfig();
        saveCChestConfig();
    }

    private void createLearnedConfig() {
        learnedItemsConfigFile = new File(plugin.getDataFolder(), "learned_items.yml");
        if (!learnedItemsConfigFile.exists()) {
            learnedItemsConfigFile.getParentFile().mkdirs();
            plugin.saveResource("learned_items.yml", false);
        }
        learnedItemsConfig = new YamlConfiguration();
        try {
            learnedItemsConfig.load(learnedItemsConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void saveLearnedConfig() {
        try {
            learnedItemsConfig.save(learnedItemsConfigFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Unable to save " + learnedItemsConfigFile.getName());
        }
    }

    private void createEmcConfig() {
        playerEMCConfigFile = new File(plugin.getDataFolder(), "player_emc.yml");
        if (!playerEMCConfigFile.exists()) {
            playerEMCConfigFile.getParentFile().mkdirs();
            plugin.saveResource("player_emc.yml", false);
        }
        playerEMCConfig = new YamlConfiguration();
        try {
            playerEMCConfig.load(playerEMCConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void saveEmcConfig() {
        try {
            playerEMCConfig.save(playerEMCConfigFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Unable to save " + playerEMCConfigFile.getName());
        }
    }

    private void createBlockStoreConfig() {
        blockStoreConfigFile = new File(plugin.getDataFolder(), "block_storage.yml");
        if (!blockStoreConfigFile.exists()) {
            blockStoreConfigFile.getParentFile().mkdirs();
            plugin.saveResource("block_storage.yml", false);
        }
        blockStoreConfig = new YamlConfiguration();
        try {
            blockStoreConfig.load(blockStoreConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void saveBlockStoreConfig() {
        try {
            blockStoreConfig.save(blockStoreConfigFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Unable to save " + blockStoreConfigFile.getName());
        }
    }

    private void createDChestConfig() {
        dChestConfigFile = new File(plugin.getDataFolder(), "dissolution_chests.yml");
        if (!dChestConfigFile.exists()) {
            dChestConfigFile.getParentFile().mkdirs();
            plugin.saveResource("dissolution_chests.yml", false);
        }
        dChestConfig = new YamlConfiguration();
        try {
            dChestConfig.load(dChestConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void saveDChestConfig() {
        try {
            dChestConfig.save(dChestConfigFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Unable to save " + dChestConfigFile.getName());
        }
    }

    private void createCChestConfig() {
        cChestConfigFile = new File(plugin.getDataFolder(), "condensation_chests.yml");
        if (!cChestConfigFile.exists()) {
            cChestConfigFile.getParentFile().mkdirs();
            plugin.saveResource("condensation_chests.yml", false);
        }
        cChestConfig = new YamlConfiguration();
        try {
            cChestConfig.load(cChestConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void saveCChestConfig() {
        try {
            cChestConfig.save(cChestConfigFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Unable to save " + cChestConfigFile.getName());
        }
    }

    public static void addLearnedItem(SlimeE plugin, String uuid, String itemName) {
        FileConfiguration c = plugin.getConfigMainClass().getLearnedItemsConfig();
        c.set(uuid + "." + ChatColor.stripColor(itemName), true);
    }

    public static void removeLearnedItem(SlimeE plugin, Player player, String itemName) {
        FileConfiguration c = plugin.getConfigMainClass().getLearnedItemsConfig();
        c.set(player.getUniqueId().toString() + "." + itemName, null);
    }

    public static List<String> getLearnedItems(SlimeE plugin, String uuid) {
        FileConfiguration c = plugin.getConfigMainClass().getLearnedItemsConfig();
        List<String> list = new ArrayList<>();
        if (c.contains(uuid)) {
            list.addAll(c.getConfigurationSection(uuid).getKeys(false));
            java.util.Collections.sort(list);
        }
        return list;
    }

    public static int getLearnedItemAmount(SlimeE plugin, Player player) {
        return getLearnedItems(plugin, player.getUniqueId().toString()).size();
    }

    public static void addPlayerEmc(SlimeE plugin, Player player, Double emcValue, Double totalEmc, int stackAmount) {
        double playerEmc = getPlayerEmc(plugin, player);
        int burnRate = plugin.getConfigMainClass().getEmc().getBurnRate();
        if (burnRate > 0) {
            totalEmc -= ((totalEmc / 100) * burnRate);
        }
        totalEmc = Utils.roundDown(totalEmc, 2);
        Double sum = playerEmc + totalEmc;
        if (sum.equals(Double.POSITIVE_INFINITY)) {
            sum = Double.MAX_VALUE;
        }
        setPlayerEmc(plugin, player, sum);
        player.sendMessage(Messages.messageGuiEmcGiven(plugin, player, emcValue, totalEmc, stackAmount, burnRate));
    }

    public static void addPlayerEmc(SlimeE plugin, String uuid, Double totalEmc) {
        double playerEmc = getPlayerEmc(plugin, uuid);
        int burnRate = plugin.getConfigMainClass().getEmc().getBurnRate();
        if (burnRate > 0) {
            totalEmc -= ((totalEmc / 100) * burnRate);
        }
        totalEmc = Utils.roundDown(totalEmc, 2);
        Double sum = playerEmc + totalEmc;
        if (sum.equals(Double.POSITIVE_INFINITY)) {
            sum = Double.MAX_VALUE;
        }
        setPlayerEmc(plugin, uuid, sum);
    }

    public static void removePlayerEmc(SlimeE plugin, Player player, Double emcValue) {
        setPlayerEmc(plugin, player, getPlayerEmc(plugin, player) - emcValue);
    }

    public static void removePlayerEmc(SlimeE plugin, String uuid, Double emcValue) {
        setPlayerEmc(plugin, uuid, getPlayerEmc(plugin, uuid) - emcValue);
    }

    public static void setPlayerEmc(SlimeE plugin, Player player, Double emcValue) {
        FileConfiguration c = plugin.getConfigMainClass().getPlayerEMCConfig();
        c.set(player.getUniqueId().toString(), emcValue);
    }

    public static void setPlayerEmc(SlimeE plugin, String uuid, Double emcValue) {
        FileConfiguration c = plugin.getConfigMainClass().getPlayerEMCConfig();
        c.set(uuid, emcValue);
    }

    public static double getPlayerEmc(SlimeE plugin, Player player) {
        FileConfiguration c = plugin.getConfigMainClass().getPlayerEMCConfig();
        double amount = 0;
        if (c.contains(player.getUniqueId().toString())) {
            amount = Utils.roundDown(c.getDouble(player.getUniqueId().toString()),2);
        }
        return amount;
    }

    public static double getPlayerEmc(SlimeE plugin, String uuid) {
        FileConfiguration c = plugin.getConfigMainClass().getPlayerEMCConfig();
        double amount = 0;
        if (c.contains(uuid)) {
            amount = c.getDouble(uuid);
        }
        return amount;
    }


    public static Integer getNextDChestID(SlimeE plugin) {
        FileConfiguration c = plugin.getConfigMainClass().blockStoreConfig;
        ConfigurationSection section = c.getConfigurationSection(DIS_CHEST_CFG);
        int nextValue = 1;
        if (section != null) {
            for (String key : section.getKeys(false)) {
                int value = Integer.parseInt(key);
                if (value > nextValue) {
                    nextValue = value;
                }
            }
            nextValue++;
        }
        return nextValue;
    }

    public static void addDChestStore(SlimeE plugin, Location location) {
        FileConfiguration c = plugin.getConfigMainClass().blockStoreConfig;
        c.set(DIS_CHEST_CFG + "." + getNextDChestID(plugin).toString(), location);
    }

    @Nullable
    public static Integer getDChestIdStore(SlimeE plugin, Location location) {
        FileConfiguration c = plugin.getConfigMainClass().blockStoreConfig;
        ConfigurationSection section = c.getConfigurationSection(DIS_CHEST_CFG);
        if (section != null) {
            for (String key : section.getKeys(false)) {
                Location l = section.getLocation(key);
                if (l.equals(location)) {
                    return Integer.parseInt(key);
                }
            }
        }
        return -2;
    }

    public static void removeDChestStore(SlimeE plugin, Integer id) {
        FileConfiguration c = plugin.getConfigMainClass().blockStoreConfig;
        ConfigurationSection section = c.getConfigurationSection(DIS_CHEST_CFG);
        section.set(id.toString(), null);
    }

    public static void setupDChest(SlimeE plugin, Integer id, Player player) {
        FileConfiguration c = plugin.getConfigMainClass().dChestConfig;
        c.set(id + ".OWNING_PLAYER", player.getUniqueId().toString());
        c.set(id + ".LEVEL", 1);
    }

    public static void removeDChest(SlimeE plugin, Integer id) {
        FileConfiguration c = plugin.getConfigMainClass().dChestConfig;
        if (String.valueOf(id) != null) {
            c.set(String.valueOf(id), null);
        }
    }

    public static boolean isOwnerDChest(SlimeE plugin, Player player, Integer id) {
        FileConfiguration c = plugin.getConfigMainClass().dChestConfig;
        String owningPlayer = c.getString(id + ".OWNING_PLAYER");
        if (owningPlayer == null) {
            SlimeE.getInstance().getLogger().warning("A Dissolution Chest in block storage (ID " + id.toString() + ") was not found in dissolution_chests.yml! Possible corruption occurred, removing from block storage");
            removeDChestStore(plugin, id);
            return false;
        } else {
            return owningPlayer.equals(player.getUniqueId().toString());
        }
    }

    public static String getOwnerDChest(SlimeE plugin, Integer id) {
        FileConfiguration c = plugin.getConfigMainClass().dChestConfig;
        String owningPlayer = c.getString(id + ".OWNING_PLAYER");
        if (owningPlayer == null) {
            SlimeE.getInstance().getLogger().warning("A Dissolution Chest in block storage (ID " + id.toString() + ") was not found in dissolution_chests.yml! Possible corruption occurred, removing from block storage");
            removeDChestStore(plugin, id);
            return null;
        } else {
            return owningPlayer;
        }
    }

    public static Location getDChestLocation(SlimeE plugin, Integer id) {
        FileConfiguration c = plugin.getConfigMainClass().blockStoreConfig;
        ConfigurationSection section = c.getConfigurationSection(DIS_CHEST_CFG);
        return section.getLocation(id.toString());
    }

    public static List<Location> getAllDChestLocations(SlimeE plugin) {
        FileConfiguration c = plugin.getConfigMainClass().blockStoreConfig;
        ConfigurationSection section = c.getConfigurationSection(DIS_CHEST_CFG);
        List<Location> ids = new ArrayList<>();
        if (section != null) {
            for (String s : section.getKeys(false)) {
                ids.add(section.getLocation(s));
            }
        }
        return ids;
    }

    public static Integer getNextCChestID(SlimeE plugin) {
        FileConfiguration c = plugin.getConfigMainClass().blockStoreConfig;
        ConfigurationSection section = c.getConfigurationSection(CON_CHEST_CFG);
        int nextValue = 1;
        if (section != null) {
            for (String key : section.getKeys(false)) {
                int value = Integer.parseInt(key);
                if (value > nextValue) {
                    nextValue = value;
                }
            }
            nextValue++;
        }
        return nextValue;
    }

    public static void addCChestStore(SlimeE plugin, Location location) {
        FileConfiguration c = plugin.getConfigMainClass().blockStoreConfig;
        c.set(CON_CHEST_CFG + "." + getNextCChestID(plugin).toString(), location);
    }

    @Nullable
    public static Integer getCChestIdStore(SlimeE plugin, Location location) {
        FileConfiguration c = plugin.getConfigMainClass().blockStoreConfig;
        ConfigurationSection section = c.getConfigurationSection(CON_CHEST_CFG);
        if (section != null) {
            for (String key : section.getKeys(false)) {
                Location l = section.getLocation(key);
                if (l.equals(location)) {
                    return Integer.parseInt(key);
                }
            }
        }
        return -2;
    }

    public static void removeCChestStore(SlimeE plugin, Integer id) {
        FileConfiguration c = plugin.getConfigMainClass().blockStoreConfig;
        ConfigurationSection section = c.getConfigurationSection(CON_CHEST_CFG);
        section.set(id.toString(), null);
    }

    public static void setupCChest(SlimeE plugin, Integer id, Player player) {
        FileConfiguration c = plugin.getConfigMainClass().cChestConfig;
        c.set(id + ".OWNING_PLAYER", player.getUniqueId().toString());
        c.set(id + ".LEVEL", 1);
    }

    public static void removeCChest(SlimeE plugin, Integer id) {
        FileConfiguration c = plugin.getConfigMainClass().cChestConfig;
        if (String.valueOf(id) != null) {
            c.set(String.valueOf(id), null);
        }
    }

    public static boolean isOwnerCChest(SlimeE plugin, Player player, Integer id) {
        FileConfiguration c = plugin.getConfigMainClass().cChestConfig;
        String owningPlayer = c.getString(id + ".OWNING_PLAYER");
        if (owningPlayer == null) {
            SlimeE.getInstance().getLogger().warning("A Condensate Chest in block storage (ID " + id.toString() + ") was not found in condensate_chests.yml! Possible corruption occurred, removing from block storage");
            removeCChestStore(plugin, id);
            return false;
        } else {
            return owningPlayer.equals(player.getUniqueId().toString());
        }
    }

    public static String getOwnerCChest(SlimeE plugin, Integer id) {
        FileConfiguration c = plugin.getConfigMainClass().cChestConfig;
        String owningPlayer = c.getString(id + ".OWNING_PLAYER");
        if (owningPlayer == null) {
            SlimeE.getInstance().getLogger().warning("A Condensate Chest in block storage (ID " + id.toString() + ") was not found in condensate_chests.yml! Possible corruption occurred, removing from block storage");
            removeCChestStore(plugin, id);
            return null;
        } else {
            return owningPlayer;
        }
    }

    public static Location getCChestLocation(SlimeE plugin, Integer id) {
        FileConfiguration c = plugin.getConfigMainClass().blockStoreConfig;
        ConfigurationSection section = c.getConfigurationSection(CON_CHEST_CFG);
        return section.getLocation(id.toString());
    }

    public static List<Location> getAllCChestLocations(SlimeE plugin) {
        FileConfiguration c = plugin.getConfigMainClass().blockStoreConfig;
        ConfigurationSection section = c.getConfigurationSection(CON_CHEST_CFG);
        List<Location> ids = new ArrayList<>();
        if (section != null) {
            for (String s : section.getKeys(false)) {
                ids.add(section.getLocation(s));
            }
        }
        return ids;
    }

    @Nullable
    public static ItemStack getCChestItem(SlimeE plugin, Integer id) {
        FileConfiguration c = plugin.getConfigMainClass().cChestConfig;
        return c.getItemStack(id + ".ITEM");
    }

    public static void setCChestItem(SlimeE plugin, Integer id, ItemStack itemStack) {
        FileConfiguration c = plugin.getConfigMainClass().cChestConfig;
        c.set(id + ".ITEM", itemStack);
    }

}
