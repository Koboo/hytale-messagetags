package eu.koboo.messagetags.api.color;

import javax.annotation.Nonnull;
import java.util.Locale;

public record NamedColor(@Nonnull String name, @Nonnull String hexCode) {

    public NamedColor(String name, String hexCode) {
        this.name = name.toLowerCase(Locale.ROOT).trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        this.hexCode = hexCode.toLowerCase(Locale.ROOT).trim();
        if (hexCode.isEmpty()) {
            throw new IllegalArgumentException("HexCode cannot be empty!");
        }
        if (hexCode.length() == 6) {
            throw new IllegalArgumentException("HexCode has to be prefixed with '#'");
        }
    }

    public static final NamedColor Black = new NamedColor("Black", "#000000");
    public static final NamedColor DarkBlue = new NamedColor("Dark_Blue", "#0000AA");
    public static final NamedColor DarkGreen = new NamedColor("Dark_Green", "#00AA00");
    public static final NamedColor DarkAqua = new NamedColor("Dark_Aqua", "#00AAAA");
    public static final NamedColor DarkRed = new NamedColor("Dark_Red", "#AA0000");
    public static final NamedColor DarkPurple = new NamedColor("Dark_Purple", "#AA00AA");
    public static final NamedColor Gold = new NamedColor("Gold", "#FFAA00");
    public static final NamedColor Gray = new NamedColor("Gray", "#AAAAAA");
    public static final NamedColor DarkGray = new NamedColor("Dark_Gray", "#555555");
    public static final NamedColor Blue = new NamedColor("Blue", "#5555FF");
    public static final NamedColor Green = new NamedColor("Green", "#55FF55");
    public static final NamedColor Aqua = new NamedColor("Aqua", "#55FFFF");
    public static final NamedColor Red = new NamedColor("Red", "#FF5555");
    public static final NamedColor LightPurple = new NamedColor("Light_Purple", "#FF55FF");
    public static final NamedColor Yellow = new NamedColor("Yellow", "#FFFF55");
    public static final NamedColor White = new NamedColor("White", "#FFFFFF");
}
