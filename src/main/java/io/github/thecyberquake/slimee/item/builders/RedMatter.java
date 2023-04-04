package io.github.thecyberquake.slimee.item.builders;

import dev.dbassett.skullcreator.SkullCreator;
import io.github.thecyberquake.slimee.SlimeE;
import io.github.thecyberquake.slimee.configuration.ConfigStrings;
import io.github.thecyberquake.slimee.statics.ContainerStorage;
import io.github.thecyberquake.slimee.statics.Messages;
import io.github.thecyberquake.slimee.statics.SkullTextures;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RedMatter {

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

    public RedMatter(SlimeE plugin) {

        this.plugin = plugin;

        ConfigStrings c = plugin.getConfigMainClass().getStrings();

        item = SkullCreator. itemFromBase64(SkullTextures.ITEM_RED_MATTER);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(Messages.THEME_ITEM_NAME_GENERAL + c.getItemRedMatterName());
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + c.getGeneralCraftingItem());
        im.setLore(lore);
        item.setItemMeta(im);

        ContainerStorage.setItemID(item, plugin,"RMATTER");
        ContainerStorage.makeRedMatter(item, plugin);
        ContainerStorage.makeCraftable(item, plugin);

    }

}
