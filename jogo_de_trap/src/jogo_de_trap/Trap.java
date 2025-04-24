package jogo_de_trap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.awt.Rectangle;

import javax.imageio.ImageIO;

public class Trap extends Objeto {
    // int x, y, width, height;
    boolean active = true;
    boolean debug = false; // altere para true se quiser ver as armadilhas
    public boolean visible = false; // <-- adiciona isso aqui
    private BufferedImage sprite;

    // Laser possui 4 sprites (um no começo e no final, e um no meio, e as suas
    // versões em azul)
    // private static final Map<Integer, BufferedImage> sprites = new HashMap<>();
    /*
     * Vai precisar disso depois:
     * static {
     * try {
     * sprites.put(5, ImageIO.read(Platform.class.getResourceAsStream(
     * "/assets/laserSprites/laser1.png")));
     * sprites.put(6, ImageIO.read(Platform.class.getResourceAsStream(
     * "/assets/laserSprites/laser2.png")));
     * sprites.put(7, ImageIO.read(Platform.class.getResourceAsStream(
     * "/assets/laserSprites/laserBase1.png")));
     * sprites.put(8, ImageIO.read(Platform.class.getResourceAsStream(
     * "/assets/laserSprites/laserBase3.png")));
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * }
     */

    public Trap(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.height = height;

        try {
            sprite = ImageIO.read(getClass().getResourceAsStream("/assets/laserSprites/laser1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //
    // public void draw(Graphics g) {
    // if (active && debug) {
    // g.setColor(Color.GRAY);
    // g.fillRect(x, y, width, height);
    // }
    // }

    public boolean checkCollision(Player player) {
        Rectangle playerBounds = new Rectangle(player.x, player.y, player.width, player.height);
        return getBounds().intersects(playerBounds);
    }
    /*
     * public boolean checkCollision(Player player) {
     * Rectangle trapBounds = new Rectangle(x, y, width, height);
     * Rectangle playerBounds = new Rectangle(player.x, player.y, player.width,
     * player.height);
     * return trapBounds.intersects(playerBounds);
     * }
     */

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void draw(Graphics g) {
        if (visible || debug && sprite != null) {
            g.drawImage(sprite, x, y, width, height, null);
        }
    }

    /*
     * public void draw(Graphics g) {
     * if (visible || debug) {
     * g.setColor(Color.GRAY);
     * g.fillRect(x, y, width, height);
     * }
     * }
     */
}
