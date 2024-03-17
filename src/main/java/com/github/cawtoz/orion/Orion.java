package com.github.cawtoz.orion;

import co.aikar.commands.BukkitCommandCompletionContext;
import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.CommandCompletions;
import com.github.cawtoz.orion.command.*;
import com.github.cawtoz.orion.config.Config;
import com.github.cawtoz.orion.config.Lang;
import com.github.cawtoz.orion.listener.*;
import com.github.cawtoz.orion.listener.CreationListener;
import com.github.cawtoz.orion.lootbox.LootBoxManager;
import com.github.cawtoz.orion.lootbox.enums.LootBoxType;
import com.github.cawtoz.orion.util.cawtoz.CC;
import com.github.cawtoz.orion.util.cawtoz.file.FileConfig;
import com.github.cawtoz.orion.util.cawtoz.menu.MenuListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
public class Orion extends JavaPlugin {

    @Override
    public void onEnable() {
        Logger.getLogger("NBTAPI").setLevel(Level.OFF);
        CC.sendConsole(
                " ",
                "&8--------- &5" + Orion.getInstance().getDescription().getName() + " &fv" + Orion.getInstance().getDescription().getVersion() + " &8---------",
                "&5 Authors&8: &f" + Orion.getInstance().getDescription().getAuthors(),
                "&5 Version&8: &f [1.8x - 1.20]",
                "&5 Support&8: &fdiscord.gg/7KA9892Y9q",
                "&8-----------------------------------------",
                " "
        );
        loadFiles();
        loadEvents();
        loadCommands();
    }

    public void loadFiles() {
        new Lang();
        new Config();
        for (LootBoxType type : LootBoxType.values()) new FileConfig(type.getFileName());
        new LootBoxManager().loadBoxes();
    }

    public void loadEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new CreationListener(), this);
        pm.registerEvents(new LootBoxListener(), this);
        pm.registerEvents(new AirdropListener(), this);
        pm.registerEvents(new MenuListener(), this);
    }

    public void loadCommands() {
        BukkitCommandManager cm = new BukkitCommandManager(Orion.getInstance());
        cm.registerCommand(new OrionCommand());
        cm.registerCommand(new GiveSubCommand());
        cm.registerCommand(new GiveAllSubCommand());
        cm.registerCommand(new BlackListSubCommand());
        CommandCompletions<BukkitCommandCompletionContext> cc = cm.getCommandCompletions();
        cc.registerStaticCompletion("instant-boxes", LootBoxManager.getBoxNames(LootBoxType.INSTANT));
        cc.registerStaticCompletion("mystery-boxes", LootBoxManager.getBoxNames(LootBoxType.MYSTERY));
        cc.registerStaticCompletion("airdrop-boxes", LootBoxManager.getBoxNames(LootBoxType.AIRDROP));
        cc.registerStaticCompletion("types", Arrays.stream(LootBoxType.values()).map(value -> value.name().toLowerCase()).collect(Collectors.toList()));
    }

    public static Orion getInstance() {
        return getPlugin(Orion.class);
    }

}
