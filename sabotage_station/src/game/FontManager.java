package game;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontManager {
    private static final String RESOURCE_PATH = "/assets/fontes/eas-vhs.ttf";
    private static Font vhsFont;

    private FontManager() {
        // Utility class
    }

    public static synchronized Font getVHSFont(float size) {
        ensureFontLoaded();
        if (vhsFont != null) {
            try {
                return vhsFont.deriveFont(size);
            } catch (Exception e) {
                // Fallback para Arial caso a derivação falhe
            }
        }
        return new Font("Arial", Font.PLAIN, Math.round(size));
    }

    private static void ensureFontLoaded() {
        if (vhsFont != null) {
            return;
        }

        try (InputStream stream = loadFontStream()) {
            if (stream == null) {
                throw new IOException("Fonte não encontrada: " + RESOURCE_PATH);
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, stream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            vhsFont = font;
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            vhsFont = new Font("Arial", Font.PLAIN, 12);
        }
    }

    private static InputStream loadFontStream() {
        InputStream stream = FontManager.class.getResourceAsStream("src/assets/fontes/eas-vhs.ttf");
        if (stream == null) {
            stream = FontManager.class.getResourceAsStream(RESOURCE_PATH);
        }
        return stream;
    }
}
