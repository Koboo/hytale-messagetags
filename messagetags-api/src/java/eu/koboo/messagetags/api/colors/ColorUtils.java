package eu.koboo.messagetags.api.colors;

import com.hypixel.hytale.math.util.MathUtil;
import eu.koboo.messagetags.api.MessageParser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public final class ColorUtils {

    private static final char[] HEX = "0123456789ABCDEF".toCharArray();

    private ColorUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static int hexToRGB(@Nonnull String hex) {
        if (hex.indexOf(MessageParser.COLOR_PREFIX) == 0) {
            hex = hex.substring(1);
        }
        return Integer.parseInt(hex, 16);
    }

    @Nullable
    public static String rgbToHex(@Nonnull String hex) {
        short[] rgb = stringToRGB(hex);
        return rgbToHex(rgb);
    }

    @Nonnull
    public static String rgbToHex(int rgb) {
        return String.format("#%06X", rgb & 0xFFFFFF);
    }

    @Nullable
    public static String rgbToHex(short r, short g, short b) {
        if (r < 0 || g < 0 || b < 0) {
            return null;
        }
        if(r > 255 || g > 255 || b > 255) {
            return null;
        }
        char[] out = new char[7];
        out[0] = MessageParser.COLOR_PREFIX;

        out[1] = HEX[(r >> 4) & 0xF];
        out[2] = HEX[r & 0xF];
        out[3] = HEX[(g >> 4) & 0xF];
        out[4] = HEX[g & 0xF];
        out[5] = HEX[(b >> 4) & 0xF];
        out[6] = HEX[b & 0xF];

        return new String(out);
    }

    @Nullable
    public static String rgbToHex(short[] rgb) {
        if(rgb == null || rgb.length != 3) {
            return null;
        }
        short r = rgb[0];
        short g = rgb[1];
        short b = rgb[2];
        return rgbToHex(r, g, b);
    }

    @Nullable
    public static short[] stringToRGB(@Nonnull String string) {
        if (string.indexOf(',') == -1) {
            return null;
        }
        if (string.indexOf(' ') != -1) {
            string = string.replace(" ", "");
        }
        String[] splitRGB = string.split(",");
        if (splitRGB.length != 3) {
            return null;
        }
        short r = parseShort(splitRGB[0]);
        short g = parseShort(splitRGB[1]);
        short b = parseShort(splitRGB[2]);
        return new short[]{r, g, b};
    }

    @Nullable
    public static String interpolateColor(List<String> colors, float progress) {
        progress = MathUtil.clamp(progress, 0, 1);
        int colorsAmount = colors.size();
        float scaled = progress * (colorsAmount - 1);
        int index = Math.min((int) scaled, colorsAmount - 2);
        float local = scaled - index;

        int firstColor = hexToRGB(colors.get(index));
        int secondColor = hexToRGB(colors.get(index + 1));

        int r1 = (firstColor >> 16) & 0xFF;
        int g1 = (firstColor >> 8) & 0xFF;
        int b1 = firstColor & 0xFF;

        int r2 = (secondColor >> 16) & 0xFF;
        int g2 = (secondColor >> 8) & 0xFF;
        int b2 = secondColor & 0xFF;

        short r = (short) (r1 + (r2 - r1) * local);
        short g = (short) (g1 + (g2 - g1) * local);
        short b = (short) (b1 + (b2 - b1) * local);

        return rgbToHex(r, g, b);
    }

    private static short parseShort(String string) {
        try {
            return Short.parseShort(string);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
