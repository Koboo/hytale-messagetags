package eu.koboo.messagetags.api.colors;

import eu.koboo.messagetags.api.taghandler.MessageBuilder;

public final class LegacyColorCodes {

    private LegacyColorCodes() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static boolean isLegacyColorCode(char code) {
        return (code >= '0' && code <= '9')
            || (code >= 'a' && code <= 'f')
            || code == 'l'
            || code == 'm'
            || code == 'n'
            || code == 'o'
            || code == 'r';
    }

    public static void applyLegacyColorCode(MessageBuilder state, char legacyCode) {
        if (state.strip) {
            return;
        }
        switch (legacyCode) {
            // Colors 0-9
            case '0' -> state.color = NamedColor.Black.hexCode();
            case '1' -> state.color = NamedColor.DarkBlue.hexCode();
            case '2' -> state.color = NamedColor.DarkGreen.hexCode();
            case '3' -> state.color = NamedColor.DarkAqua.hexCode();
            case '4' -> state.color = NamedColor.DarkRed.hexCode();
            case '5' -> state.color = NamedColor.DarkPurple.hexCode();
            case '6' -> state.color = NamedColor.Gold.hexCode();
            case '7' -> state.color = NamedColor.Gray.hexCode();
            case '8' -> state.color = NamedColor.DarkGray.hexCode();
            case '9' -> state.color = NamedColor.Blue.hexCode();
            // Colors a-f
            case 'a' -> state.color = NamedColor.Green.hexCode();
            case 'b' -> state.color = NamedColor.Aqua.hexCode();
            case 'c' -> state.color = NamedColor.Red.hexCode();
            case 'd' -> state.color = NamedColor.LightPurple.hexCode();
            case 'e' -> state.color = NamedColor.Yellow.hexCode();
            case 'f' -> state.color = NamedColor.White.hexCode();
            // Text formatting codes
            case 'l' -> state.bold = true;
            case 'm' -> state.italic = true;
            case 'n' -> state.monospace = true;
            case 'o' -> state.underline = true;
            // reset
            case 'r' -> state.resetStyle();
        }
    }
}
