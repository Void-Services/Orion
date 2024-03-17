package com.github.cawtoz.orion.lootbox.enums;

import lombok.Getter;
import org.bukkit.Color;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum ColorEnum {

    RED(Color.fromRGB(255, 0, 0)),
    ORANGE(Color.fromRGB(255, 165, 0)),
    YELLOW(Color.fromRGB(255, 255, 0)),
    GREEN(Color.fromRGB(0, 255, 0)),
    BLUE(Color.fromRGB(0, 0, 255)),
    PURPLE(Color.fromRGB(128, 0, 128)),
    PINK(Color.fromRGB(255, 192, 203)),
    CYAN(Color.fromRGB(0, 255, 255)),
    WHITE(Color.fromRGB(255, 255, 255)),
    BLACK(Color.fromRGB(0, 0, 0)),
    GRAY(Color.fromRGB(128, 128, 128)),
    BROWN(Color.fromRGB(139, 69, 19)),
    MAGENTA(Color.fromRGB(255, 0, 255)),
    LIME(Color.fromRGB(0, 255, 0)),
    TEAL(Color.fromRGB(0, 128, 128)),
    NAVY(Color.fromRGB(0, 0, 128)),
    OLIVE(Color.fromRGB(128, 128, 0)),
    SKY(Color.fromRGB(135, 206, 235)),
    LAVENDER(Color.fromRGB(230, 230, 250)),
    GOLD(Color.fromRGB(255, 215, 0)),
    AQUAMARINE(Color.fromRGB(127, 255, 212)),
    INDIGO(Color.fromRGB(75, 0, 130)),
    VIOLET(Color.fromRGB(148, 0, 211)),
    SILVER(Color.fromRGB(192, 192, 192)),
    MAROON(Color.fromRGB(128, 0, 0)),
    DARK_GREEN(Color.fromRGB(0, 100, 0)),
    TURQUOISE(Color.fromRGB(64, 224, 208)),
    HOT_PINK(Color.fromRGB(255, 105, 180)),
    DARK_CYAN(Color.fromRGB(0, 139, 139)),
    DARK_ORCHID(Color.fromRGB(153, 50, 204)),
    TOMATO(Color.fromRGB(255, 99, 71)),
    DARK_BLUE(Color.fromRGB(0, 0, 139)),
    FIREBRICK(Color.fromRGB(178, 34, 34)),
    MEDIUM_PURPLE(Color.fromRGB(147, 112, 219)),
    DARK_MAGENTA(Color.fromRGB(139, 0, 139)),
    LIGHT_SEA_GREEN(Color.fromRGB(32, 178, 170)),
    INDIAN_RED(Color.fromRGB(205, 92, 92)),
    DARK_SLATE_BLUE(Color.fromRGB(72, 61, 139)),
    SEA_GREEN(Color.fromRGB(46, 139, 87)),
    OLIVE_DRAB(Color.fromRGB(107, 142, 35)),
    DARK_KHAKI(Color.fromRGB(189, 183, 107)),
    CHOCOLATE(Color.fromRGB(210, 105, 30)),
    ROSY_BROWN(Color.fromRGB(188, 143, 143)),
    DARK_OLIVE_GREEN(Color.fromRGB(85, 107, 47)),
    STEEL_BLUE(Color.fromRGB(70, 130, 180)),
    CADET_BLUE(Color.fromRGB(95, 158, 160)),
    DARK_TURQUOISE(Color.fromRGB(0, 206, 209)),
    CRIMSON(Color.fromRGB(220, 20, 60)),
    ORCHID(Color.fromRGB(218, 112, 214)),
    DARK_SALMON(Color.fromRGB(233, 150, 122)),
    SADDLE_BROWN(Color.fromRGB(139, 69, 19)),
    GOLDENROD(Color.fromRGB(218, 165, 32)),
    DARK_VIOLET(Color.fromRGB(148, 0, 211)),
    LIGHT_CORAL(Color.fromRGB(240, 128, 128));

    private final Color color;

    ColorEnum(Color color) {
        this.color = color;
    }

    public static List<Color> getColors() {
        List<Color> colors = new ArrayList<>();
        for (ColorEnum color : values()) colors.add(color.getColor());
        return colors;
    }

}
