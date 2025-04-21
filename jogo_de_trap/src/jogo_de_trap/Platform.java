package jogo_de_trap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Platform {
    int x, y, width, height;
    private BufferedImage sprite;

    // Mapeia números para sprites. Carrega apeans uma vez
    private static final Map<Integer, BufferedImage> sprites = new HashMap<>();

    static {
        try {
            sprites.put(1, ImageIO.read(Platform.class.getResourceAsStream("/assets/metal_Tiles/metalTile1.png")));
            sprites.put(2, ImageIO.read(Platform.class.getResourceAsStream("/assets/metal_Tiles/metalTile2.png")));
            sprites.put(3, ImageIO.read(Platform.class.getResourceAsStream("/assets/metal_Tiles/metalTile3.png")));
            sprites.put(4, ImageIO.read(Platform.class.getResourceAsStream("/assets/metal_Tiles/metalTile4.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Platform(int x, int y, int width, int height, int tipo) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprites.get(tipo);
    }

    public void draw(Graphics g) {
        if (sprite != null) {
            g.drawImage(sprite, x, y, width, height, null);
        } else {
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
