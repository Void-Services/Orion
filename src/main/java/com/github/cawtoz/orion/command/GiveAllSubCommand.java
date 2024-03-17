package com.github.cawtoz.orion.command;

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
public class GiveAllSubCommand extends BaseCommand {

    @Subcommand("giveall") @CommandPermission("orion.giveall")
    public void onGive(CommandSender sender) {
        if (sender instanceof Player) CSound.ENTITY_EXPERIENCE_ORB_PICKUP.play((Player) sender);
        CC.send(sender, "&cUsage /orion give <type> <name> <amount>");
    }

    @Subcommand("giveall instant") @CommandPermission("orion.giveall") @CommandCompletion("@instant-boxes @players")
    public void onGiveInstant(CommandSender sender, String[] args) {
        onGive(sender, args, LootBoxType.INSTANT);
    }

    @Subcommand("giveall mystery") @CommandPermission("orion.giveall") @CommandCompletion("@mystery-boxes @players")
    public void onGiveMystery(CommandSender sender, String[] args) {
        onGive(sender, args, LootBoxType.MYSTERY);
    }

    @Subcommand("giveall airdrop") @CommandPermission("orion.giveall") @CommandCompletion("@airdrop-boxes @players")
    public void onGiveAirdrop(CommandSender sender, String[] args) {
        onGive(sender, args, LootBoxType.AIRDROP);
    }

    public void onGive(CommandSender sender, String[] args, LootBoxType type) {

        if (sender instanceof Player) CSound.ENTITY_EXPERIENCE_ORB_PICKUP.play((Player) sender);

        if (args.length != 2) {
            CC.send(sender, "&cUsage /orion give <type> <player> <amount>");
            return;
        }

        String name = args[0];
        String amount = args[1];

        if (LootBoxManager.getBox(name, type) == null) {
            CC.send(sender, "&cThe " + type.getName() + "Box &f" + name + " &cname does not exist.");
            return;
        }

        try {
            LootBoxManager.getBox(name, type).giveAll(Integer.parseInt(amount));
        } catch (NumberFormatException e) {
            CC.send(sender, "&cIncorrect amount");
        }

    }

}
