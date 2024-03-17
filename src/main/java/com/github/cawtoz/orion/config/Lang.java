package com.github.cawtoz.orion.config;

import com.github.cawtoz.orion.util.cawtoz.file.FileConfig;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
public class Lang {

    public static String RELOAD;
    public static String USE_NO_LOOT;

    public static String CREATION_EXIST;
    public static String CREATION_ACCEPT;
    public static String CREATION_CANCEL;
    public static String CREATION_RETURN;
    public static String CREATION_EDITING_NAME;
    public static String CREATION_EDITING_DISPLAY_NAME;

    public Lang() {
        FileConfig file = new FileConfig("messages.yml");

        RELOAD = file.getString("RELOAD");
        USE_NO_LOOT = file.getString("USE_NO_LOOT");

        CREATION_EXIST = file.getString("CREATION.EXIST");
        CREATION_ACCEPT = file.getString("CREATION.ACCEPT");
        CREATION_CANCEL = file.getString("CREATION.CANCEL");
        CREATION_RETURN = file.getString("CREATION.RETURN");
        CREATION_EDITING_NAME = file.getString("CREATION.EDITING_NAME");
        CREATION_EDITING_DISPLAY_NAME = file.getString("CREATION.EDITING_DISPLAY_NAME");
    }

}
