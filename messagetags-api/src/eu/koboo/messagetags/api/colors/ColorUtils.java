package eu.koboo.messagetags.api.colors;

import com.hypixel.hytale.math.util.MathUtil;
import eu.koboo.messagetags.api.MessageParser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class ColorUtils {

    private static final char[] HEX = "0123456789ABCDEF".toCharArray();

    private ColorUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static int rgbToInt(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }

    public static int hexToRGB(@Nonnull String hex) {
        if (hex.indexOf(MessageParser.COLOR_PREFIX) == 0) {
            hex = hex.substring(1);
        }
        return Integer.parseInt(hex, 16);
    }

    @Nonnull
    public static String rgbToHex(int rgb) {
        //FIXME: Make it faster
        return String.format("#%06X", rgb & 0xFFFFFF);
    }

    public static int interpolateColor(int[] colors, float progress) {
        progress = MathUtil.clamp(progress, 0, 1);
        int colorsAmount = colors.length;
        float scaled = progress * (colorsAmount - 1);
        int index = Math.min((int) scaled, colorsAmount - 2);
        float local = scaled - index;

        int firstColor = colors[index];
        int secondColor = colors[index + 1];

        int r1 = (firstColor >> 16) & 0xFF;
        int g1 = (firstColor >> 8) & 0xFF;
        int b1 = firstColor & 0xFF;

        int r2 = (secondColor >> 16) & 0xFF;
        int g2 = (secondColor >> 8) & 0xFF;
        int b2 = secondColor & 0xFF;

        short r = (short) (r1 + (r2 - r1) * local);
        short g = (short) (g1 + (g2 - g1) * local);
        short b = (short) (b1 + (b2 - b1) * local);

        //return rgbToHex(r, g, b);
        return rgbToInt(r, g, b);
    }

    public static short parseShort(String string) {
        try {
            return Short.parseShort(string);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
