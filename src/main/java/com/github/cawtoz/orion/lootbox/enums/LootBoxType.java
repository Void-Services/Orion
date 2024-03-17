package com.github.cawtoz.orion.lootbox.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum LootBoxType {

    INSTANT("Instant", "boxes/instant-boxes.yml"),
    MYSTERY("Mystery", "boxes/mystery-boxes.yml"),
    AIRDROP("Airdrop", "boxes/airdrop-boxes.yml");

    private final String name;
    private final String fileName;

    LootBoxType (String name, String fileName) {
        this.name = name;
        this.fileName = fileName;
    }

    public static List<String> getNames() {
        return Arrays.stream(LootBoxType.values()).map(LootBoxType::getName).collect(Collectors.toList());
    }

}
