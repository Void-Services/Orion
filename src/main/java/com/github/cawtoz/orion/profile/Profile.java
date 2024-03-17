package com.github.cawtoz.orion.profile;

import lombok.Getter;
import lombok.Setter;

import com.github.cawtoz.orion.lootbox.LootBox;
import com.github.cawtoz.orion.creation.Creation;

import java.util.UUID;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
@Getter @Setter
public class Profile {

    private final UUID id;
    private boolean using;
    private boolean looking;
    private LootBox lootBox;

    private boolean toggleInstant;
    private boolean toggleMystery;
    private boolean toggleAirdrop;

    private boolean editing;
    private boolean creating;
    private Creation creation;

    public Profile(UUID id) {
        this.id = id;
    }

}
