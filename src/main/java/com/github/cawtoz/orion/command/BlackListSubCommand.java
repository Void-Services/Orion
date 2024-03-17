package com.github.cawtoz.orion.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import com.github.cawtoz.orion.lootbox.LootBox;
import com.github.cawtoz.orion.lootbox.enums.LootBoxType;
import com.github.cawtoz.orion.util.cawtoz.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandAlias("orion")
public class BlackListSubCommand extends BaseCommand {

    @Subcommand("blacklist") @CommandPermission("orion.blacklist") @CommandCompletion("@types @players")
    public void onToggle(CommandSender sender, String[] args) {

        if (args.length != 4) {
            CC.send(sender, "&cUsage /orion blacklist <type> <player> <view/use> <add/remove>");
            return;
        }

        LootBoxType type;
        String playerName = args[1];
        Player target = Bukkit.getPlayerExact(playerName);

        try {
            type = LootBoxType.valueOf(args[0].toUpperCase());
        } catch (Exception e) {
            CC.send(sender, "&cThe " + args[0] + " type does not exist.");
            return;
        }

        if (target == null) {
            CC.send(sender, "&cThe player &f" + playerName + " &cis not online.");
            return;
        }

        blackList(target, type, args[2], args[3]);

    }

    public void blackList(Player player, LootBoxType type, String action, String value) {

        List<Player> blackListPlayers = new ArrayList<>();

        switch (action.toLowerCase()) {
            case "view":
                blackListPlayers = LootBox.blackListView.get(type);
                break;
            case "use":
                blackListPlayers = LootBox.blackListUse.get(type);
        }

        switch (value.toLowerCase()) {
            case "add":
                if (!blackListPlayers.contains(player)) {
                    blackListPlayers.add(player);
                }
                break;
            case "remove":
                blackListPlayers.remove(player);
                break;
        }


    }

}
