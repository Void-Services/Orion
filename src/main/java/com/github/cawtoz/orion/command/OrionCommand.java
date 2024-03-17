package com.github.cawtoz.orion.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.*;
import co.aikar.commands.BaseCommand;

import com.github.cawtoz.orion.creation.Creation;
import com.github.cawtoz.orion.creation.CState;
import com.github.cawtoz.orion.profile.*;
import com.github.cawtoz.orion.util.cawtoz.CC;
import com.github.cawtoz.orion.util.cawtoz.CSound;
import com.github.cawtoz.orion.Orion;


/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
@CommandAlias("orion")
public class OrionCommand extends BaseCommand {

    @Default
    @HelpCommand
    public void onOrion(CommandSender sender) {
        CC.send(sender,
                "&8&m                                                                                ",
                "&5&l ORION LOOTBOX",
                "&5&l ┏━▶ &d/orion info &fto view info.",
                "&5&l ┣━▶ &d/orion edit &fto edit boxes.",
                "&5&l ┣━▶ &d/orion create &fto create box.",
                "&5&l ┣━▶ &d/orion reload &fto reload config.",
                "&5&l ┣━▶ &d/orion give <type> <name> <player> <amount> &fto get a box.",
                "&5&l ┣━▶ &d/orion giveall <type> <name> <amount> &fto get all the boxes.",
                "&5&l ┗━▶ &d/orion blacklist <type> <player> <view/use> <add/remove> &fto block action.",
                "&8&m                                                                                "
        );
        if (sender instanceof Player) CSound.ENTITY_EXPERIENCE_ORB_PICKUP.play((Player) sender);
    }

    @Subcommand("info")
    public void onInfo(CommandSender sender) {
        CC.send(sender,
                "&8&m                                                                      ",
                "&5&l ORION INFO",
                "&5&l ┏━▶ &dAuthors &f" + Orion.getInstance().getDescription().getAuthors(),
                "&5&l ┣━▶ &dVersion &f" + Orion.getInstance().getDescription().getVersion(),
                "&5&l ┣━▶ &dSupport &fhttps://discord.gg/7KA9892Y9q",
                "&5&l ┗━▶ &d/orion help &fto see all commands.",
                "&8&m                                                                      "
        );
        if (sender instanceof Player) CSound.ENTITY_EXPERIENCE_ORB_PICKUP.play((Player) sender);
    }

    @Subcommand("reload") @CommandPermission("orion.reload")
    public void onReload(CommandSender sender) {
        Orion.getInstance().loadFiles();
        CC.send(sender,"&aThe configuration has been reloaded successfully");
        if (sender instanceof Player) CSound.ENTITY_EXPERIENCE_ORB_PICKUP.play((Player) sender);
    }

    @Subcommand("create") @CommandPermission("orion.create")
    public void onCreate(Player player, String[] args) {
        Profile profile = PUtil.get(player) == null ? PUtil.create(player) : PUtil.get(player);
        if (profile.isCreating()) {
            CSound.BLOCK_CHEST_OPEN.play(player);
            profile.getCreation().setState(CState.CREATING);
            return;
        }
        new Creation(player);
        profile.setEditing(false);
        profile.setCreating(true);
        profile.getCreation().setState(CState.NAME);
    }

    @Subcommand("edit") @CommandPermission("orion.edit")
    public void onEdit(Player player) {
        Profile profile = PUtil.create(player);
        new Creation(player);
        profile.setEditing(true);
        profile.setCreating(true);
        profile.getCreation().setState(CState.EDITING);
    }

}
