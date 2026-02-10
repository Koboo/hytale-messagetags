package eu.koboo.messagetags.api.colors;

import eu.koboo.messagetags.api.MessageParser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

public final class ColorParser {

    private static final NamedColorRegistry INSTANCE = new NamedColorRegistry();

    @Nullable
    public static String parseColor(@Nullable String colorString) {
        if (colorString == null) {
            return null;
        }
        colorString = colorString.toLowerCase(Locale.ROOT).trim();
        if (colorString.isEmpty()) {
            return null;
        }
        int length = colorString.length();
        char firstCharacter = colorString.charAt(0);

        // #ffffff
        if (firstCharacter == MessageParser.COLOR_PREFIX && length == 7) {
            return colorString;
        }

        // white -> #ffffff
        NamedColor namedColor = INSTANCE.getNamedColorByName(colorString);
        if (namedColor != null) {
            return namedColor.hexCode();
        }

        // &f -> #ffffff
        // §f -> #ffffff
        boolean startsWithColorToken = firstCharacter == MessageParser.COLOR_AMPERSAND
            || firstCharacter == MessageParser.COLOR_SECTION;
        if (length == 2 && startsWithColorToken) {
            char colorChar = colorString.substring(1).charAt(0);
            namedColor = INSTANCE.getNamedColorByChar(colorChar);
            if (namedColor != null) {
                return namedColor.hexCode();
            }
        }

        // 255,255,255 -> #ffffff
        return ColorUtils.rgbToHex(colorString);
    }
}
