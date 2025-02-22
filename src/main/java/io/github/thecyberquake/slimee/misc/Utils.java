package io.github.thecyberquake.slimee.misc;

import io.github.thecyberquake.slimee.SlimeE;
import io.github.thecyberquake.slimee.item.builders.CondensatorChest;
import io.github.thecyberquake.slimee.item.builders.DissolutionChest;
import io.github.thecyberquake.slimee.item.builders.TransmutationOrb;
import io.github.thecyberquake.slimee.statics.ContainerStorage;
import io.github.thecyberquake.slimee.statics.Messages;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static Double getEMC(SlimeE plugin, ItemStack itemStack) {
        SlimefunItem sfItem = null;
        if (SlimeE.getInstance().getManagerSupportedPlugins().isInstalledSlimefun()) {
            sfItem = SlimefunItem.getByItem(itemStack);
        }
        if (sfItem != null) {
            return plugin.getEmcDefinitions().getEmcSlimefun().get(sfItem.getId());
        }
        if (ContainerStorage.isCraftable(itemStack, plugin)) {
            ItemStack eqStack = plugin.getEqItems().getEqItemMap().get(eqNameConfig(itemStack.getItemMeta().getDisplayName()));
            return plugin.getEmcDefinitions().getEmcEQ().get(eqStack.getItemMeta().getDisplayName());
        } else {
            return plugin.getEmcDefinitions().getEmcExtended().get(itemStack.getType());
        }
    }


    public static String eqNameConfig(String name) {
        if (name.substring(0,2) == "§x") {
            String first12 = name.substring(0,12);
            return StringUtils.removeStart(name.replace(" ", "_"), first12);
        } else {
            return ChatColor.stripColor(name.replace(" ", "_"));
        }
    }

    public static String toTitleCase(String string) {
        final char[] delimiters = { ' ', '_' };
        return WordUtils.capitalizeFully(string, delimiters);
    }

    public static String materialFriendlyName(Material m) {
        return toTitleCase(m.name().replace("_", " "));
    }

    public static void givePlayerOrb(SlimeE plugin, Player player) {
        TransmutationOrb i = plugin.getEqItems().getTransmutationOrb();
        player.getPlayer().getInventory().addItem(i.getItemClone());
        player.getPlayer().sendMessage(Messages.messageCommandItemGiven(plugin, i.getItem().getItemMeta().getDisplayName()));
    }

    public static void givePlayerDChest(SlimeE plugin, Player player) {
        DissolutionChest i = plugin.getEqItems().getDissolutionChest();
        player.getPlayer().getInventory().addItem(i.getItemClone());
        player.getPlayer().sendMessage(Messages.messageCommandItemGiven(plugin, i.getItem().getItemMeta().getDisplayName()));
    }

    public static void givePlayerCChest(SlimeE plugin, Player player) {
        CondensatorChest i = plugin.getEqItems().getCondensatorChest();
        player.getPlayer().getInventory().addItem(i.getItemClone());
        player.getPlayer().sendMessage(Messages.messageCommandItemGiven(plugin, i.getItem().getItemMeta().getDisplayName()));
    }

    public static double roundDown(double number, int places) {
        BigDecimal value = BigDecimal.valueOf(number);
        value = value.setScale(places, RoundingMode.DOWN);
        return value.doubleValue();
    }

    public static int totalRecipes(SlimeE plugin) {
        int recExtended = plugin.getEmcDefinitions().getEmcExtended().size();
        int recEQ = plugin.getEmcDefinitions().getEmcEQ().size();
        int recSF = plugin.getEmcDefinitions().getEmcSlimefun().size();
        return recExtended + recEQ + recSF;
    }

    public static boolean canBeSynth(SlimeE plugin, ItemStack itemStack) {
        if (itemStack.hasItemMeta()) {
            SlimefunItem sfItem = null;
            if (SlimeE.getInstance().getManagerSupportedPlugins().isInstalledSlimefun()) {
                sfItem = SlimefunItem.getByItem(itemStack);
            }
            return ContainerStorage.isCraftable(itemStack, plugin) || sfItem != null;
        } else {
            return true;
        }
    }

}
