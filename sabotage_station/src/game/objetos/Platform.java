package game.objetos;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Platform extends Objeto {
    private BufferedImage sprite;
    private boolean debug = false;
    public boolean visible = true;
    public boolean showGlow = false;

    // Tile spritesheet
    private static final int TILE_FRAME_W = 114;
    private static final int TILE_FRAME_H = 116;
    private static final int TILE_FRAME_COUNT = 5;
    private static final BufferedImage[] sprites = new BufferedImage[TILE_FRAME_COUNT];

    // Glow animation
    private static final int GLOW_FRAME_COUNT = 10;
    private static final int GLOW_FRAME_W = 113;
    private static final int GLOW_FRAME_H = 116;
    private static BufferedImage glowSheet;

    private static final long WAIT_DURATION_MS = 5000; // 5s de espera entre ciclos
    private static final long ANIM_DURATION_MS = 1000; // 1s de duração da animação
    private static final long FRAME_DURATION_MS = ANIM_DURATION_MS / GLOW_FRAME_COUNT; // 100ms por frame

    private long glowCycleStart = System.currentTimeMillis();
    private boolean animating = false;

    static {
        try {
            BufferedImage tileSheet = ImageIO.read(
                    Platform.class.getResourceAsStream("/assets/metal_Tiles/metalTile-sheet.png"));
            for (int i = 0; i < TILE_FRAME_COUNT; i++) {
                sprites[i] = tileSheet.getSubimage(i * TILE_FRAME_W, 0, TILE_FRAME_W, TILE_FRAME_H);
            }

            // Carregamento do spritesheet de glow
            glowSheet = ImageIO.read(Platform.class.getResourceAsStream("/assets/effects/showglow-sheet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Platform(int x, int y, int width, int height, int tipo) {
        super(x, y, width, height);
        if (tipo >= 1 && tipo <= TILE_FRAME_COUNT) {
            this.sprite = sprites[tipo - 1];
        }
    }

    public void setState(boolean visible, boolean showGlow) {
        this.visible = visible;
        this.showGlow = showGlow;
        if (showGlow) {
            glowCycleStart = System.currentTimeMillis();
            animating = false;
        }
    }

    public void updateGlow() {
        if (!showGlow)
            return;

        long elapsed = System.currentTimeMillis() - glowCycleStart;

        if (!animating) {
            if (elapsed >= WAIT_DURATION_MS) {
                animating = true;
                glowCycleStart = System.currentTimeMillis(); // Inicia tempo
            }
        } else {
            if (elapsed >= ANIM_DURATION_MS) {
                animating = false;
                glowCycleStart = System.currentTimeMillis(); // Reinicia espera
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        if (visible) {
            if (sprite != null) {
                g.drawImage(sprite, x, y, width, height, null);
            }
        } else if (debug) {
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
        }

        if (showGlow && animating && glowSheet != null) {
            long elapsed = System.currentTimeMillis() - glowCycleStart;
            int frame = (int) (elapsed / FRAME_DURATION_MS);
            if (frame >= GLOW_FRAME_COUNT)
                frame = GLOW_FRAME_COUNT - 1;

            int sx = frame * GLOW_FRAME_W;
            g.drawImage(glowSheet,
                    x, y, x + width, y + height,
                    sx, 0, sx + GLOW_FRAME_W, GLOW_FRAME_H,
                    null);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}