package io.github.thecyberquake.slimee.gui;

import dev.dbassett.skullcreator.SkullCreator;
import dev.triumphteam.gui.guis.GuiItem;
import io.github.thecyberquake.slimee.SlimeE;
import io.github.thecyberquake.slimee.configuration.ConfigMain;
import io.github.thecyberquake.slimee.configuration.ConfigStrings;
import io.github.thecyberquake.slimee.misc.Utils;
import io.github.thecyberquake.slimee.statics.Messages;
import io.github.thecyberquake.slimee.statics.SkullTextures;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GUIItems {

    private GUIItems() {
        throw new IllegalStateException("Utility class");
    }

    public static GuiItem guiOrbInfo(SlimeE plugin, Player player) {
        GuiItem g = new GuiItem(SkullCreator.itemFromBase64(SkullTextures.GUI_INFO_ALL));
        ItemStack i = g.getItemStack();
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(guiDisplayNameInfo(plugin));
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.setLore(guiDisplayLoreInfo(plugin, player));
        i.setItemMeta(im);
        g.setItemStack(i);
        g.setAction(event -> event.setCancelled(true));
        return g;
    }

    public static GuiItem guiOrbBorder(SlimeE plugin) {
        GuiItem g = new GuiItem(Material.GRAY_STAINED_GLASS_PANE);
        ItemStack i = g.getItemStack();
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Messages.THEME_PASSIVE_GRAY + plugin.getConfigMainClass().getStrings().getGuiBorderName());
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(im);
        g.setItemStack(i);
        g.setAction(event -> event.setCancelled(true));
        return g;
    }

    public static GuiItem guiOrbFiller(SlimeE plugin) {
        GuiItem g = new GuiItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemStack i = g.getItemStack();
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Messages.THEME_PASSIVE_GRAY + plugin.getConfigMainClass().getStrings().getGuiFillerName());
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(im);
        g.setItemStack(i);
        g.setAction(event -> event.setCancelled(true));
        return g;
    }

    public static GuiItem guiEMCItem(SlimeE plugin, ItemStack itemStack, boolean isVanilla) {

        GuiItem g = new GuiItem(itemStack);
        ItemMeta im = itemStack.getItemMeta();

        if (isVanilla) {
            im.setDisplayName(ChatColor.WHITE + Utils.materialFriendlyName(itemStack.getType()));
        } else {
            im.setDisplayName(ChatColor.WHITE + ChatColor.stripColor(im.getDisplayName()));
        }

        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        im.setLore(getEmcItemLore(plugin, itemStack));
        itemStack.setItemMeta(im);
        g.setItemStack(itemStack);
        return g;
    }

    public static List<String> getEmcItemLore(SlimeE plugin, ItemStack itemStack) {
        ConfigStrings c = plugin.getConfigMainClass().getStrings();
        List<String> lore = new ArrayList<>();
        lore.add(Messages.THEME_EMC_PURPLE + "EMC: " + Utils.getEMC(plugin, itemStack));
        lore.add("");
        lore.add(Messages.THEME_CLICK_INSTRUCTION + "Left Click: " + ChatColor.WHITE + c.getGuiEntryLeftClick());
        lore.add(Messages.THEME_CLICK_INSTRUCTION + "Right Click: " + ChatColor.WHITE + c.getGuiEntryRightClick());
        return lore;
    }

    public static String guiDisplayNameInfo(SlimeE plugin) {
        return ChatColor.RED + plugin.getConfigMainClass().getStrings().getGuiInfoName();
    }

    public static List<String> guiDisplayLoreInfo(SlimeE plugin, Player player) {
        List<String> l = new ArrayList<>();
        int recipesKnown = ConfigMain.getLearnedItemAmount(plugin, player);
        int recipesTotal = Utils.totalRecipes(plugin);
        l.add("" + ChatColor.GOLD + ChatColor.BOLD + plugin.getConfigMainClass().getStrings().getGuiInfoRecipes() + ": " + ChatColor.WHITE + recipesKnown + "/" + recipesTotal);
        l.add("");
        l.add("" + ChatColor.GOLD + ChatColor.BOLD + "EMC: " + ChatColor.WHITE + ConfigMain.getPlayerEmc(plugin, player));
        return l;
    }

}
