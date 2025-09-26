package jogo_de_trap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class LaserGrande extends Objeto {
    boolean active = true;
    boolean debug = false; // altera para true se quiser ver as armadilhas
    public boolean visible = true;

    private BufferedImage sprite;

    public LaserGrande(int x, int y, int width, int height, int tipo) {
        super(x, y, width, height);

        String path = switch (tipo) {
            case 15 -> "/assets/laserSprites/laserGrandeH.png";
            case 16 -> "/assets/laserSprites/laserGrandeV.png";
            default -> throw new IllegalArgumentException("Tipo" + tipo + "é inválido");
        };

        if (path != null) {
            try {
                sprite = ImageIO.read(getClass().getResourceAsStream(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static Dimension getImageDimension(int tipo) {
        String path = switch (tipo) {
            case 15 -> "/assets/laserSprites/laserGrandeH.png";
            case 16 -> "/assets/laserSprites/laserGrandeV.png";
            default -> throw new IllegalArgumentException("Tipo" + tipo + " é inválido");
        };

        try {
            BufferedImage img = ImageIO.read(LaserGrande.class.getResourceAsStream(path));
            if (img != null) {
                return new Dimension(img.getWidth(), img.getHeight());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }


    public boolean checkCollision(Player player) {
        Rectangle playerBounds = new Rectangle(player.x, player.y, player.width, player.height);
        return getBounds().intersects(playerBounds);
    }


    @Override
    public void draw(Graphics g) {
        if (visible || debug && sprite != null) {
            g.drawImage(sprite, x, y, width, height, null);
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

    public boolean isVisible() {
        return visible;
    }

    public int getWidth() {
        return sprite != null ? sprite.getWidth() : 0;
    }

    public int getHeight() {
        return sprite != null ? sprite.getHeight() : 0;
    }
}
