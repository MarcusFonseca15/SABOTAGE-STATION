package jogo_de_trap;

import java.awt.*;
import java.util.ArrayList;

public class Level {
    ArrayList<Platform> platforms;
    ArrayList<Trap> traps;

    public Level(int number) {
        platforms = new ArrayList<>();
        traps = new ArrayList<>();

        // chão
        platforms.add(new Platform(0, 550, 800, 50));

        if (number == 1) {
            platforms.add(new Platform(200, 450, 100, 20));
            platforms.add(new Platform(400, 350, 100, 20));

            traps.add(new Trap(300, 530, 40, 20));
            traps.add(new Trap(600, 530, 40, 20));
        } else if (number == 2) {
            platforms.add(new Platform(150, 450, 100, 20));
            platforms.add(new Platform(350, 400, 100, 20));
            platforms.add(new Platform(560, 250, 100, 20));
            platforms.add(new Platform(500, 300, 10, 10));

            traps.add(new Trap(265, 390, 40, 160));
            traps.add(new Trap(440, 530, 230, 20));
            traps.add(new Trap(750, 180, 50, 280));
        }
        // Adicione mais níveis se quiser
    }

    public void draw(Graphics g) {
        for (Platform p : platforms)
            p.draw(g);

        for (Trap t : traps)
            t.draw(g);
    }

    public boolean checkTrapCollision(Player player) {
        for (Trap t : traps) {
            if (t.checkCollision(player)) return true;
        }
        return false;
    }

    public void checkPlatformCollision(Player player) {
        Rectangle playerBounds = new Rectangle(player.x, player.y, player.width, player.height);
        player.onGround = false;

        for (Platform p : platforms) {
            Rectangle platformBounds = p.getBounds();

            if (playerBounds.intersects(platformBounds)) {
                if (player.y + player.height - player.velY <= p.y) {
                    player.y = p.y - player.height;
                    player.velY = 0;
                    player.jumping = false;
                    player.onGround = true;
                } else if (player.y - player.velY >= p.y + p.height) {
                    player.y = p.y + p.height;
                    player.velY = 0;
                }
            }
        }
    }
}
