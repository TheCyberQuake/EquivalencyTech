package io.github.thecyberquake.slimee.item.builders;

import io.github.thecyberquake.slimee.SlimeE;
import io.github.thecyberquake.slimee.configuration.ConfigStrings;
import io.github.thecyberquake.slimee.statics.ContainerStorage;
import io.github.thecyberquake.slimee.statics.Messages;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CondensatorChest {

    private final ItemStack item;
    private final SlimeE plugin;

    public ItemStack getItemClone() {
        return item.clone();
    }
    public ItemStack getItem() {
        return item;
    }

    public SlimeE getPlugin() {
        return plugin;
    }

    public CondensatorChest(SlimeE plugin) {

        this.plugin = plugin;

        ConfigStrings c = plugin.getConfigMainClass().getStrings();

        item = new ItemStack(Material.CHEST);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(Messages.THEME_ITEM_NAME_GENERAL + c.getItemCondensatorChestName());
        List<String> lore = new ArrayList<>();
        for (String s : c.getItemCondensatorChestLore()) {
            lore.add(Messages.THEME_PASSIVE_GRAY + s);
        }
        lore.add("");
        lore.add(Messages.THEME_CLICK_INSTRUCTION + c.getItemCondensatorChestSet());
        im.setLore(lore);
        item.setItemMeta(im);

        ContainerStorage.setItemID(item, plugin,"CCHEST");
        ContainerStorage.makeConChest(item, plugin);
        ContainerStorage.makeCraftable(item, plugin);

    }
}
