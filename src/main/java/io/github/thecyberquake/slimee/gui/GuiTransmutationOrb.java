package io.github.thecyberquake.slimee.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import io.github.thecyberquake.slimee.SlimeE;
import io.github.thecyberquake.slimee.configuration.ConfigMain;
import io.github.thecyberquake.slimee.misc.Utils;
import io.github.thecyberquake.slimee.statics.ContainerStorage;
import io.github.thecyberquake.slimee.statics.Messages;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class GuiTransmutationOrb extends PaginatedGui {

    public final SlimeE plugin;
    public final Player player;

    protected static final List<Integer> ARRAY_FILLER_SLOTS = Arrays.asList(0, 1, 2, 3, 5, 6, 7, 8, 45, 47, 48, 50, 51, 53);
    protected static final Integer INFO_SLOT = 4;
    protected static final Integer INPUT_SLOT = 49;
    protected static final Integer PAGE_SIZE = 36;

    public GuiTransmutationOrb(int rows, int pageSize, @NotNull String title, SlimeE plugin, Player player) {
        super(rows, pageSize, title);
        this.plugin = plugin;
        this.player = player;
    }

    public static GuiTransmutationOrb buildGui(SlimeE plugin, Player player) {

        int backSlot = 46;
        int forwardSlot = 52;

        GuiTransmutationOrb gui = new GuiTransmutationOrb(
                6,
                GuiTransmutationOrb.PAGE_SIZE,
                Messages.THEME_EMC_PURPLE + plugin.getConfigMainClass().getStrings().getItemTransmutationOrbName(),
                plugin,
                player
        );

        gui.setItem(GuiTransmutationOrb.ARRAY_FILLER_SLOTS, GUIItems.guiOrbBorder(plugin));
        gui.setItem(GuiTransmutationOrb.INFO_SLOT, GUIItems.guiOrbInfo(plugin, player));

        List<String> learnedItems = ConfigMain.getLearnedItems(plugin, player.getUniqueId().toString());

        int leftOverSlots = GuiTransmutationOrb.PAGE_SIZE - (learnedItems.size() % GuiTransmutationOrb.PAGE_SIZE);

        for (String s : learnedItems) {

            ItemStack itemStack;
            GuiItem guiItem;
            boolean isVanilla = false;

            SlimefunItem sfItem = null;
            if (SlimeE.getInstance().getManagerSupportedPlugins().isInstalledSlimefun()) {
                sfItem = SlimefunItem.getById(s);
            }

            if (!plugin.getEqItems().getEqItemMap().containsKey(s) && sfItem == null) {
                isVanilla = true;
            }

            if (isVanilla) {
                itemStack = new ItemStack(Material.valueOf(s));
            } else if (sfItem != null) {
                itemStack = sfItem.getItem().clone();
            } else {
                itemStack = plugin.getEqItems().getEqItemMap().get(s).clone();
            }

            if (Utils.getEMC(plugin, itemStack) == null) {
                // A learned item has null emc - likely removed from the config post go live - skip
                leftOverSlots += 1;
                continue;
            }

            guiItem = GUIItems.guiEMCItem(plugin, itemStack, isVanilla);

            guiItem.setAction(event -> emcItemClicked(event, plugin));
            gui.addItem(guiItem);
        }


        for (int i = 0; i < leftOverSlots; i++) {
            gui.addItem(GUIItems.guiOrbFiller(plugin));
        }

        setInputSlot(plugin, gui);

        gui.setItem(backSlot, ItemBuilder.from(Material.PAPER).setName("Previous").asGuiItem(event -> {
            event.setCancelled(true);
            gui.previous();
        }));

        gui.setItem(forwardSlot, ItemBuilder.from(Material.PAPER).setName("Next").asGuiItem(event -> {
            event.setCancelled(true);
            gui.next();
        }));

        gui.setDragAction(event -> event.setCancelled(true));
        gui.setDefaultClickAction(event -> {
            if (event.isShiftClick()) {
                if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && !event.getClickedInventory().equals(event.getInventory())) {
                    inputItemAction(event, plugin, gui, true);
                }
                event.setCancelled(true);
            }
        });

        return gui;

    }

    private static void setInputSlot(SlimeE plugin, GuiTransmutationOrb gui) {
        gui.addSlotAction(GuiTransmutationOrb.INPUT_SLOT, event -> {
            inputItemAction(event, plugin, gui, false);
            event.setCancelled(true);
        });
    }

    private static void inputItemAction(InventoryClickEvent e, SlimeE plugin, PaginatedGui gui, boolean shifted) {

        Player player = (Player) e.getWhoClicked();
        ItemStack itemStack;

        if (shifted) {
            itemStack = e.getCurrentItem();
        } else {
            itemStack = player.getItemOnCursor();
        }

        boolean isEQ = ContainerStorage.isCraftable(itemStack, plugin);
        SlimefunItem sfItem = null;
        if (SlimeE.getInstance().getManagerSupportedPlugins().isInstalledSlimefun()) {
            sfItem = SlimefunItem.getByItem(itemStack);
        }

        if (itemStack.hasItemMeta() && !isEQ && sfItem == null) {
            player.sendMessage(Messages.messageGuiItemMeta(plugin));
            return;
        }

        Material material = itemStack.getType();
        Double emcValue = Utils.getEMC(plugin, itemStack);

        boolean mustClose = false;
        if (emcValue != null) {
            double totalEmc = emcValue * itemStack.getAmount();
            String entryName;
            if (isEQ) {
                entryName = Utils.eqNameConfig(itemStack.getItemMeta().getDisplayName());
            } else if (sfItem != null) {
                entryName = sfItem.getId();
            } else {
                entryName = material.toString();
            }
            if (!ConfigMain.getLearnedItems(plugin, player.getUniqueId().toString()).contains(entryName)) {
                ConfigMain.addLearnedItem(plugin, player.getUniqueId().toString(), entryName);
                player.sendMessage(Messages.messageGuiItemLearned(plugin));
                mustClose = true;
            }
            ConfigMain.addPlayerEmc(plugin, player, emcValue, totalEmc, itemStack.getAmount());
            itemStack.setAmount(0);
        } else {
            player.sendMessage(Messages.msgCmdEmcNone(plugin));
        }
        if (mustClose) {
            gui.close(player);
        }
    }

    private static void emcItemClicked(InventoryClickEvent e, SlimeE plugin) {
        e.setCancelled(true);
        switch (e.getClick()) {
            case LEFT:
                emcWithdrawOne(e, plugin);
                break;
            case RIGHT:
                emcWithdrawStack(e, plugin);
                break;
            default:
                break;
        }
    }

    private static void emcWithdrawOne(InventoryClickEvent e, SlimeE plugin) {

        Player player = (Player) e.getWhoClicked();

        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(Messages.messageGuiNoSpace(plugin));
            return;
        }

        ItemStack clickedItem = e.getCurrentItem();
        boolean isEQ = ContainerStorage.isCraftable(clickedItem, plugin);
        SlimefunItem sfItem = null;
        if (SlimeE.getInstance().getManagerSupportedPlugins().isInstalledSlimefun()) {
            sfItem = SlimefunItem.getByItem(clickedItem);
        }
        double playerEmc = ConfigMain.getPlayerEmc(plugin, player);
        Double emcValue = Utils.getEMC(plugin, clickedItem);
        String itemName;

        if (isEQ) {
            itemName = Utils.eqNameConfig(clickedItem.getItemMeta().getDisplayName());
        } else if (sfItem != null) {
            itemName = sfItem.getId();
        } else {
            itemName = clickedItem.getType().toString();
        }

        if (playerEmc >= emcValue) {
            ItemStack itemStack;
            if (isEQ) {
                itemStack = plugin.getEqItems().getEqItemMap().get(itemName).clone();
            } else if (sfItem != null) {
                itemStack = sfItem.getItem().clone();
            } else {
                itemStack = new ItemStack(e.getCurrentItem().getType());
            }
            player.getInventory().addItem(itemStack);
            ConfigMain.removePlayerEmc(plugin, player, emcValue);
            player.sendMessage(Messages.messageGuiEmcRemoved(plugin, player, emcValue, emcValue, 1));
        } else {
            player.sendMessage(Messages.messageGuiEmcNotEnough(plugin, player));
        }
    }

    private static void emcWithdrawStack(InventoryClickEvent e, SlimeE plugin) {

        Player player = (Player) e.getWhoClicked();
        double playerEmc = ConfigMain.getPlayerEmc(plugin, player);
        ItemStack clickedItem = e.getCurrentItem();
        Material material = clickedItem.getType();

        boolean isEQ = ContainerStorage.isCraftable(e.getCurrentItem(), plugin);
        SlimefunItem sfItem = null;
        if (SlimeE.getInstance().getManagerSupportedPlugins().isInstalledSlimefun()) {
            sfItem = SlimefunItem.getByItem(clickedItem);
        }

        String itemName;
        Double emcValue = Utils.getEMC(plugin, clickedItem);

        if (isEQ) {
            itemName = Utils.eqNameConfig(e.getCurrentItem().getItemMeta().getDisplayName());
        } else if (sfItem != null) {
            itemName = sfItem.getId();
        } else {
            itemName = material.toString();
        }

        int amount = clickedItem.getMaxStackSize();
        if (emcValue != null) {
            double emcValueStack = emcValue * amount;
            if (player.getInventory().firstEmpty() == -1) {
                player.sendMessage(Messages.messageGuiNoSpace(plugin));
                return;
            }
            if (playerEmc < emcValueStack) {
                double maxPossible = playerEmc / emcValue;
                maxPossible = Math.floor(maxPossible);
                amount = (int) maxPossible;
                emcValueStack = emcValue * amount;
            }
            if (amount == 0) {
                player.sendMessage(Messages.messageGuiEmcNotEnough(plugin, player));
                return;
            }

            ItemStack itemStack;
            if (isEQ) {
                itemStack = plugin.getEqItems().getEqItemMap().get(itemName).clone();
            } else if (sfItem != null) {
                itemStack = sfItem.getItem().clone();
            } else {
                itemStack = new ItemStack(e.getCurrentItem().getType());
            }

            itemStack.setAmount(amount);
            player.getInventory().addItem(itemStack);
            ConfigMain.removePlayerEmc(plugin, player, emcValueStack);
            player.sendMessage(Messages.messageGuiEmcRemoved(plugin, player, emcValue, emcValueStack, amount));
        }
    }



}

