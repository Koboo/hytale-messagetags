package eu.koboo.messagetags.api.colors;

import eu.koboo.messagetags.api.MessageParser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class NamedColorRegistry {

    private static final List<Character> DISALLOWED_COLOR_CODES = List.of(
        MessageParser.COLOR_SECTION,
        MessageParser.COLOR_AMPERSAND,
        MessageParser.COLOR_PREFIX,
        MessageParser.TAG_OPEN,
        MessageParser.TAG_CLOSE,
        MessageParser.TAG_SEPARATOR,
        MessageParser.TAG_SLASH
    );

    private final Map<Character, NamedColor> characterToColor = new HashMap<>();
    private final Map<String, NamedColor> nameToColor = new HashMap<>();
    private final Map<String, NamedColor> hexCodeToColor = new HashMap<>();

    NamedColorRegistry() {
        registerNamedColor(NamedColor.Black);
        registerNamedColor(NamedColor.DarkBlue);
        registerNamedColor(NamedColor.DarkGreen);
        registerNamedColor(NamedColor.DarkAqua);
        registerNamedColor(NamedColor.DarkRed);
        registerNamedColor(NamedColor.DarkPurple);
        registerNamedColor(NamedColor.Gold);
        registerNamedColor(NamedColor.Gray);
        registerNamedColor(NamedColor.DarkGray);
        registerNamedColor(NamedColor.Blue);
        registerNamedColor(NamedColor.Green);
        registerNamedColor(NamedColor.Aqua);
        registerNamedColor(NamedColor.Red);
        registerNamedColor(NamedColor.LightPurple);
        registerNamedColor(NamedColor.Yellow);
        registerNamedColor(NamedColor.White);
    }

    @Nullable
    public NamedColor getNamedColorByChar(char colorCharacter) {
        return characterToColor.get(colorCharacter);
    }

    @Nullable
    public NamedColor getNamedColorByName(@Nonnull String colorName) {
        colorName = colorName.toLowerCase(Locale.ROOT);
        return nameToColor.get(colorName);
    }

    @Nullable
    public NamedColor getNamedColorByHexCode(@Nonnull String hexCode) {
        if (hexCode.charAt(0) != MessageParser.COLOR_PREFIX) {
            hexCode = MessageParser.COLOR_PREFIX + hexCode;
        }
        if (hexCode.length() != 7) {
            return null;
        }
        hexCode = hexCode.toLowerCase(Locale.ROOT);
        return hexCodeToColor.get(hexCode);
    }

    public void registerNamedColor(@Nonnull NamedColor namedColor) {
        Character colorCode = namedColor.colorCode();
        if (colorCode != null) {
            if (DISALLOWED_COLOR_CODES.contains(colorCode)) {
                throw new IllegalArgumentException("colorCode " + colorCode + " is preserved as tag token, you can't use it!");
            }
            characterToColor.put(colorCode, namedColor);
        }
        String name = namedColor.name().toLowerCase(Locale.ROOT).trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        nameToColor.put(name, namedColor);
        String hexCode = namedColor.hexCode();
        if (hexCode.trim().isEmpty()) {
            throw new IllegalArgumentException("HexCode cannot be empty!");
        }
        if (hexCode.length() == 6) {
            throw new IllegalArgumentException("HexCode has to bge prefixed with '#'");
        }
        hexCode = hexCode.toLowerCase(Locale.ROOT);
        hexCodeToColor.put(hexCode, namedColor);
    }
}
