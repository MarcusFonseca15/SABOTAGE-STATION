package jogo_de_trap;

import java.awt.*;



public class Trap {
    int x, y, width, height;
    boolean active = true;
    boolean debug = false; // altere para true se quiser ver as armadilhas
    public boolean visible = false; // <-- adiciona isso aqui

    public Trap(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
//
//    public void draw(Graphics g) {
//        if (active && debug) {
//            g.setColor(Color.GRAY);
//            g.fillRect(x, y, width, height);
//        }
//    }

    public boolean checkCollision(Player player) {
        Rectangle trapBounds = new Rectangle(x, y, width, height);
        Rectangle playerBounds = new Rectangle(player.x, player.y, player.width, player.height);
        return trapBounds.intersects(playerBounds);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    public void draw(Graphics g) {
        if (visible || debug) {
            g.setColor(Color.GRAY);
            g.fillRect(x, y, width, height);
        }
    }


}

