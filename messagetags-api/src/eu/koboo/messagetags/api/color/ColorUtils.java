package eu.koboo.messagetags.api.color;

import com.hypixel.hytale.math.util.MathUtil;
import eu.koboo.messagetags.api.MessageParser;

import javax.annotation.Nonnull;

public final class ColorUtils {

    private ColorUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static int rgbToInt(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }

    public static int hexToRGB(@Nonnull String hex) {
        int len = hex.length();
        int start = 0;

        // skip '#' if present
        if (len > 0 && hex.charAt(0) == MessageParser.COLOR_PREFIX) {
            start = 1;
        }

        int rgb = 0;
        for (int i = start; i < len; i++) {
            char c = hex.charAt(i);
            int value;

            if (c >= '0' && c <= '9') {
                value = c - '0';
            } else if (c >= 'a' && c <= 'f') {
                value = 10 + (c - 'a');
            } else if (c >= 'A' && c <= 'F') {
                value = 10 + (c - 'A');
            } else {
                throw new IllegalArgumentException("Invalid hex character: " + c);
            }

            rgb = (rgb << 4) | value;
        }

        return rgb;
    }

    @Nonnull
    public static String rgbToHex(int rgb) {
        // Only use the lower 24 bits
        int value = rgb & 0xFFFFFF;

        // Pre-allocate a 7-char array: # + 6 hex digits
        char[] hex = new char[7];
        hex[0] = '#';

        for (int i = 0; i < 6; i++) {
            int shift = (5 - i) * 4;
            int nibble = (value >> shift) & 0xF;
            hex[i + 1] = (char) (nibble < 10 ? '0' + nibble : 'A' + (nibble - 10));
        }

        return new String(hex);
    }

    public static String interpolateColor(String[] colors, float progress) {
        if (colors == null || colors.length == 0) {
            return null;
        }
        progress = MathUtil.clamp(progress, 0, 1);
        int colorsAmount = colors.length;
        float scaled = progress * (colorsAmount - 1);
        int index = Math.min((int) scaled, colorsAmount - 2);
        float local = scaled - index;

        int firstColor = ColorUtils.hexToRGB(colors[index]);
        int secondColor = ColorUtils.hexToRGB(colors[index + 1]);

        int r1 = (firstColor >> 16) & 0xFF;
        int g1 = (firstColor >> 8) & 0xFF;
        int b1 = firstColor & 0xFF;

        int r2 = (secondColor >> 16) & 0xFF;
        int g2 = (secondColor >> 8) & 0xFF;
        int b2 = secondColor & 0xFF;

        short r = (short) (r1 + (r2 - r1) * local);
        short g = (short) (g1 + (g2 - g1) * local);
        short b = (short) (b1 + (b2 - b1) * local);

        int rgb = rgbToInt(r, g, b);

        return rgbToHex(rgb);
    }
}
