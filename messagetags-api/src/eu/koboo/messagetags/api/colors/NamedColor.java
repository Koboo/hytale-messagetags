package eu.koboo.messagetags.api.colors;

import java.util.Locale;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NamedColor{

    @Nonnull
    public final String name;
    public final char colorCode;
    @Nonnull
    public final String hexCode;
    public final int value;

    public NamedColor(String name, char colorCode, String hexCode) {
        this.name = name.toLowerCase(Locale.ROOT).trim();
        this.colorCode = colorCode;
        this.hexCode = hexCode.toLowerCase(Locale.ROOT).trim();
        this.value = ColorUtils.hexToRGB(hexCode);
    }

    public static final NamedColor Black = new NamedColor("Black", '0', "#000000");
    public static final NamedColor DarkBlue = new NamedColor("DarkBlue", '1', "#0000AA");
    public static final NamedColor DarkGreen = new NamedColor("DarkGreen", '2', "#00AA00");
    public static final NamedColor DarkAqua = new NamedColor("DarkAqua", '3', "#00AAAA");
    public static final NamedColor DarkRed = new NamedColor("DarkRed", '4', "#AA0000");
    public static final NamedColor DarkPurple = new NamedColor("DarkPurple", '5', "#AA00AA");
    public static final NamedColor Gold = new NamedColor("Gold", '6', "#FFAA00");
    public static final NamedColor Gray = new NamedColor("Gray", '7', "#AAAAAA");
    public static final NamedColor DarkGray = new NamedColor("DarkGray", '8', "#555555");
    public static final NamedColor Blue = new NamedColor("Blue", '9', "#5555FF");
    public static final NamedColor Green = new NamedColor("Green", 'a', "#55FF55");
    public static final NamedColor Aqua = new NamedColor("Aqua", 'b', "#55FFFF");
    public static final NamedColor Red = new NamedColor("Red", 'c', "#FF5555");
    public static final NamedColor LightPurple = new NamedColor("LightPurple", 'd', "#FF55FF");
    public static final NamedColor Yellow = new NamedColor("Yellow", 'e', "#FFFF55");
    public static final NamedColor White = new NamedColor("White", 'f', "#FFFFFF");
}
