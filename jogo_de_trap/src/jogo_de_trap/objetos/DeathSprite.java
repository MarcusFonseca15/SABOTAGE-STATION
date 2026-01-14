package jogo_de_trap.objetos;

import javax.swing.*;
import java.awt.*;

public class DeathSprite {

    private int x, y;
    private Image sprite;
    private long startTime;
    private float alpha = 1.0f;
    private static final long DURATION = 2000;

    public DeathSprite(int x, int y, Image sprite) {
        this.x = x;
        this.y = y;
        this.startTime = System.currentTimeMillis();
        this.sprite = new ImageIcon(getClass().getResource("/assets/effects/blood.png")).getImage();
    }

    public void update() {
        long elapsed = System.currentTimeMillis() - startTime;
        if (elapsed >= DURATION) {
            alpha = 0;
        } else {
            alpha = 1.0f - (elapsed / (float) DURATION);
        }
    }

    public boolean isFinished() {
        return alpha <= 0;
    }

    public void draw(Graphics2D g2d) {
        if (alpha > 0 && sprite != null) {
            Graphics2D g = (Graphics2D) g2d.create();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g.drawImage(sprite, x, y, 50, 50, null);
        }
    }

}
