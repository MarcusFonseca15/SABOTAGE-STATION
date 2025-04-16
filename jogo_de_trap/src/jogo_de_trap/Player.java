package jogo_de_trap;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    int x, y;
    int width = 50, height = 50;

    int velX = 0;
    int velY = 0;

    boolean jumping = false;
    boolean onGround = true;

    final int GRAVITY = 1;
    final int FLOOR_Y = 500;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }

    public void update() {
        x += velX;

        if (!onGround) {
            velY += GRAVITY;
        }

        y += velY;

        // Impede sair da tela
        if (x < 0) x = 0;
        if (x + width > 800) x = 800 - width;

        if (y + height >= 600) {
            y = 600 - height;
            velY = 0;
            jumping = false;
            onGround = true;
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            velX = 5;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            velX = -5;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE && onGround) {
            jumping = true;
            onGround = false;
            velY = -15;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
            velX = 0;
        }
    }
        
    public void reset() {
        this.x = 100;
        this.y = 500;
        this.velX = 0;
        this.velY = 0;
        this.jumping = false;
        this.onGround = true;
    }

}
