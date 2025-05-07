package jogo_de_trap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.awt.Rectangle;
import javax.imageio.ImageIO;

public class EspinhosP extends Objeto {
	
	private BufferedImage sprite;
    private boolean active = true;
    private boolean debug = false;
    public boolean visible = true;
    int tipo;

    public EspinhosP(int x, int y, int width, int height) {
        super(x, y, width, height);
   
        try {
            sprite = ImageIO.read(getClass().getResource("/assets/espinhoSprite/espinho4.png"));
        } catch (IOException e) {
            
        }
    }      
    public boolean checkCollision(Player player) {
        Rectangle playerBounds = new Rectangle(player.x, player.y, player.width, player.height);
        return getBounds().intersects(playerBounds);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void draw(Graphics g) {
        if (visible) {
            if (sprite != null) {
                g.drawImage(sprite, x, y, width, height, null);
            } else if (debug) {
                g.setColor(Color.RED);
                g.fillRect(x, y, width, height);
            }
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
