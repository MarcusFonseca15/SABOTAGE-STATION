package jogo_de_trap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Platform {
    int x, y, width, height;
    private static BufferedImage sprite;

    static {
        try {
            sprite = ImageIO.read(Platform.class.getResourceAsStream("/assets/metal_Tiles/metalTile1.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Platform(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {
        /*
         * g.setColor(new Color(139, 69, 19)); // marrom
         * g.fillRect(x, y, width, height);
         */
        g.drawImage(sprite, x, y, width, height, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
