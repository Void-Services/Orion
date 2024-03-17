package com.github.cawtoz.orion.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.*;
import co.aikar.commands.BaseCommand;

import com.github.cawtoz.orion.lootbox.LootBoxManager;
import com.github.cawtoz.orion.lootbox.enums.LootBoxType;
import com.github.cawtoz.orion.util.cawtoz.CC;
import com.github.cawtoz.orion.util.cawtoz.CSound;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
@CommandAlias("orion")
public class GiveSubCommand extends BaseCommand {

    @CommandPermission("orion.give")
    public void onGive(CommandSender sender) {
        if (sender instanceof Player) CSound.ENTITY_EXPERIENCE_ORB_PICKUP.play((Player) sender);
        CC.send(sender, "&cUsage /orion give <type> <name> <player> <amount>");
    }

    @Subcommand("give instant") @CommandPermission("orion.give") @CommandCompletion("@instant-boxes @players")
    public void onGiveInstant(CommandSender sender, String[] args) { give(sender, args, LootBoxType.INSTANT); }

    @Subcommand("give mystery") @CommandPermission("orion.give") @CommandCompletion("@mystery-boxes @players")
    public void onGiveMystery(CommandSender sender, String[] args) { give(sender, args, LootBoxType.MYSTERY); }

    @Subcommand("give airdrop") @CommandPermission("orion.give") @CommandCompletion("@airdrop-boxes @players")
    public void onGiveAirdrop(CommandSender sender, String[] args) { give(sender, args, LootBoxType.AIRDROP); }

    public void give(CommandSender sender, String[] args, LootBoxType type) {

        if (sender instanceof Player) CSound.ENTITY_EXPERIENCE_ORB_PICKUP.play((Player) sender);

        if (args.length != 3) {
            CC.send(sender, "&cUsage /orion give <type> <name> <player> <amount>");
            return;
        }

        String boxName = args[0];
        String playerName = args[1];
        String amount = args[2];

        if (LootBoxManager.getBox(boxName, type) == null) {
            CC.send(sender, "&cThe " + type.getName() + "Box &f" + boxName + " &cname does not exist.");
            return;
        }


        if (Bukkit.getPlayerExact(playerName) == null) {
            CC.send(sender, "&cThe player &f" + playerName + " &cis not online.");
            return;
        }

        try {
            LootBoxManager.getBox(boxName, type).give(Bukkit.getPlayerExact(playerName), Integer.parseInt(amount));
        } catch (NumberFormatException e) {
            CC.send(sender, "&cIncorrect amount");
        }

    }

}
