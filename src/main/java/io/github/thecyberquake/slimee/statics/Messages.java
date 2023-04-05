package io.github.thecyberquake.slimee.statics;

import io.github.thecyberquake.slimee.SlimeE;
import io.github.thecyberquake.slimee.configuration.ConfigMain;
import io.github.thecyberquake.slimee.misc.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public final class Messages {

    public static final String PREFIX = "" + ChatColor.GREEN + "[SlimeE] ";
    public static final String SUFFIX = "" + ChatColor.GRAY + "";

    public static final ChatColor THEME_WARNING = ChatColor.YELLOW;
    public static final ChatColor THEME_ERROR = ChatColor.RED;
    public static final ChatColor THEME_NOTICE = ChatColor.WHITE;
    public static final ChatColor THEME_PASSIVE = ChatColor.GRAY;
    public static final ChatColor THEME_SUCCESS = ChatColor.GREEN;
    public static final ChatColor THEME_EMC_PURPLE = ChatColor.of("#5d2999");
    public static final ChatColor THEME_ITEM_NAME_GENERAL = ChatColor.of("#cfab1d");
    public static final ChatColor THEME_PASSIVE_GRAY = ChatColor.of("#a3a3a3");
    public static final ChatColor THEME_CLICK_INSTRUCTION = ChatColor.of("#cfab1d");
    public static final ChatColor THEME_PASSIVE_CONGRATULATE = ChatColor.of("#fff41f");

    private Messages() {
        throw new IllegalStateException("Utility class");
    }

    // region Commands

    public static String msgCmdSubcommand(SlimeE plugin) {
        return PREFIX + THEME_NOTICE + plugin.getConfigMainClass().getStrings().getCommandSubcommand();
    }
    public static String msgCmdEmcMustHold(SlimeE plugin) {
        return PREFIX + THEME_WARNING + plugin.getConfigMainClass().getStrings().getCommandEmcMustHold();
    }
    public static String msgCmdEmcNone(SlimeE plugin) {
        return PREFIX + THEME_WARNING + plugin.getConfigMainClass().getStrings().getCommandEmcNone();
    }
    public static String msgCmdEmcDisplay(Material m, Double emc) {
        return PREFIX + THEME_WARNING + Utils.materialFriendlyName(m) + " x 1 = EMC " + emc;
    }
    public static String msgCmdEmcDisplay(String s, Double emc) {
        return PREFIX + THEME_WARNING + s + " x 1 = EMC " + emc;
    }
    public static String msgCmdEmcDisplayStack(Material m, Integer amount, Double emc) {
        return PREFIX + THEME_WARNING + Utils.materialFriendlyName(m) + " x " + amount + " = EMC " + emc;
    }
    public static String msgCmdEmcDisplayStack(String s, Integer amount, Double emc) {
        return PREFIX + THEME_WARNING + s + " x " + amount + " = EMC " + emc;
    }
    public static String messageCommandSelectItem(SlimeE plugin) {
        return PREFIX + THEME_NOTICE + plugin.getConfigMainClass().getStrings().getCommandSelectItem();
    }

    public static String messageCommandItemGiven(SlimeE plugin, String itemName) {
        return PREFIX + THEME_NOTICE + MessageFormat.format(plugin.getConfigMainClass().getStrings().getCommandItemGiven(), itemName);
    }

    public static String messageCommandEmc(SlimeE plugin, Player player) {
        return PREFIX + THEME_NOTICE + "You have " + THEME_SUCCESS + ConfigMain.getPlayerEmc(plugin, player) + THEME_NOTICE + " EMC.";
    }

    // endregion

    // region GUI

    public static String messageGuiItemLearned(SlimeE plugin) {
        return PREFIX + THEME_PASSIVE_CONGRATULATE + plugin.getConfigMainClass().getStrings().getGuiItemLearned();
    }

    public static String messageGuiEmcGiven(SlimeE plugin, Player player, double emcBase, double emcTotal, int itemAmt, int burnRate) {
        return PREFIX + THEME_SUCCESS + "+" + emcTotal + " EMC " + THEME_PASSIVE + "(" + emcBase + " * " + itemAmt + ")" + THEME_ERROR + " burn rate = " + burnRate + "%" + THEME_NOTICE + " : [EMC : " + ConfigMain.getPlayerEmc(plugin, player) + "]";
    }

    public static String messageGuiEmcRemoved(SlimeE plugin, Player player, double emcBase, double emcTotal, int itemAmt) {
        return PREFIX + THEME_ERROR + "-" + emcTotal + " EMC " + THEME_PASSIVE + "(" + emcBase + " * " + itemAmt + ") : " + THEME_NOTICE + " [EMC : " + ConfigMain.getPlayerEmc(plugin, player) + "]";
    }

    public static String messageGuiEmcNotEnough(SlimeE plugin, Player player) {
        return PREFIX + THEME_ERROR + plugin.getConfigMainClass().getStrings().getGuiNotEnoughEmc() + THEME_NOTICE + " [EMC : " + ConfigMain.getPlayerEmc(plugin, player) + "]";
    }

    public static String messageGuiNoSpace(SlimeE plugin) {
        return PREFIX + THEME_ERROR + plugin.getConfigMainClass().getStrings().getGeneralNoInvSpace();
    }

    public static String messageGuiItemMeta(SlimeE plugin) {
        return PREFIX + THEME_ERROR + plugin.getConfigMainClass().getStrings().getGuiItemMeta();
    }

    // endregion

    // region Events

    public static String messageEventEMCChestPlace(SlimeE plugin) {
        return PREFIX + THEME_ERROR + plugin.getConfigMainClass().getStrings().getEventAdjPlacement();
    }

    public static String messageEventCantOpenNotOwner(SlimeE plugin) {
        return PREFIX + THEME_ERROR + plugin.getConfigMainClass().getStrings().getEventCantOpenNotOwner();
    }

    public static String messageEventItemSet(SlimeE plugin) {
        return PREFIX + THEME_SUCCESS + plugin.getConfigMainClass().getStrings().getEventItemSet();
    }

    public static String messageEventItemUnset(SlimeE plugin) {
        return PREFIX + THEME_SUCCESS + plugin.getConfigMainClass().getStrings().getEventItemUnset();
    }

    public static List<String> messageEMC2Installed(SlimeE plugin) {
        List<String> message = new ArrayList<>();
        message.add(THEME_ERROR + "You have both EquiTech and EMC2 installed.");
        message.add(THEME_NOTICE + "Be warned that this may cause issues. Visit the sites below for more. This message does NOT mean you need to remove EMC2/EquiTech.");
        message.add("");
        message.add(THEME_NOTICE + "Disable this message in the EquiTech config.");
        message.add("");
        message.add( THEME_WARNING + "https://github.com/Sefiraat/EquivalencyTech/");
        message.add(THEME_WARNING + "https://github.com/Seggan/EMC2");
        return message;
    }

    // endregion

}
