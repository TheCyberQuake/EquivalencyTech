package io.github.thecyberquake.slimee.statics;

import io.github.thecyberquake.slimee.SlimeE;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

public final class DebugLogs {

    private DebugLogs() {
        throw new IllegalStateException("Utility class");
    }

    public static final String NEST = " >";

    // Logs
    public static void logEmcBaseValueLoaded(SlimeE plugin, String materialName, Double emcValue) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"EMC BASE LOADED : {0} : {1}", new Object[]{materialName, emcValue});
        }
    }

    public static void logEmcTestingItemStack(SlimeE plugin, String itemStackName, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}EMC TESTING : {1}", new Object[]{StringUtils.repeat(NEST, nestLevel), itemStackName});
        }
    }

    public static void logEmcNoRecipes(SlimeE plugin, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}No recipes - not condensable", StringUtils.repeat(NEST, nestLevel));
        }
    }

    public static void logEmcIsBase(SlimeE plugin, Double emcValue, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}Is Base : passing EMC value up : {1}", new Object[]{StringUtils.repeat(NEST, nestLevel), emcValue});
        }
    }

    public static void logEmcIsRegisteredExtended(SlimeE plugin, Double emcValue, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}Is Registered in extended : passing EMC value up : {1}", new Object[]{StringUtils.repeat(NEST, nestLevel), emcValue});
        }
    }

    public static void logCheckingRecipe(SlimeE plugin, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}Recipe found : starting recursion.", StringUtils.repeat(NEST, nestLevel));
        }
    }

    public static void logEmcPosted(SlimeE plugin, Double emcValue, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}All items completed : EMC : {1}", new Object[]{StringUtils.repeat(NEST, nestLevel), emcValue});
        }
    }

    public static void logEmcNull(SlimeE plugin, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}All items completed : One or more items returned null base EMC. Item not added.", StringUtils.repeat(NEST, nestLevel));
        }
    }

    public static void logRecipeType(SlimeE plugin, String type, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}Recipe type : {1}", new Object[]{StringUtils.repeat(NEST, nestLevel), type});
        }
    }

    public static void logRecipeMultipleOutputs(SlimeE plugin, Double emcTotal, Integer outputVolume, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}Recipe has multiple output items. Using : ({1}/{2}", new Object[]{StringUtils.repeat(NEST, nestLevel), emcTotal, outputVolume});
        }
    }

    public static void logEmcRecipeResult(SlimeE plugin, Double emcValue, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}Recipe resulted in EMC of: {1}", new Object[]{StringUtils.repeat(NEST, nestLevel), emcValue});
        }
    }

    public static void logRecipeCheaper(SlimeE plugin, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}Recipe is either first/only or cheapest found so far.", StringUtils.repeat(NEST, nestLevel));
        }
    }

    public static void logRecipeNotCheaper(SlimeE plugin, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}Recipe is valid but a previous recipe is cheaper - this recipe ignored.", StringUtils.repeat(NEST, nestLevel));
        }
    }

    public static void logEQStart(SlimeE plugin, Integer nestLevel, ItemStack itemStack) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}EQ Recipe check starting : ItemStack is : {1}", new Object[]{StringUtils.repeat(NEST, nestLevel), itemStack.getType()});
        }
    }

    public static void logEQisCrafting(SlimeE plugin, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}ItemStack is a EQ craftable - ", new Object[]{StringUtils.repeat(NEST, nestLevel), });
        }
    }

    public static void logEQisNotCrafting(SlimeE plugin, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}ItemStack is NOT EQ craftable - moving to extended/base - ", new Object[]{StringUtils.repeat(NEST, nestLevel)});
        }
    }

    public static void logBoring(SlimeE plugin, String string) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,string);
        }
    }

}
