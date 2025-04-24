package jogo_de_trap;

import java.awt.*;
import java.util.ArrayList;
import java.awt.Rectangle;

public class Level {
    private static final int TILE_SIZE = 50;
    private static final int LIN = 600 / TILE_SIZE; // 12
    private static final int COLS = 800 / TILE_SIZE; // 16
    ArrayList<Platform> platforms;
    ArrayList<Trap> traps;

    private static final int[][] mapa1 = {
            { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 2, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 4, 4, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 4, 4, 4, 0, 2, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 2, 3, 1, 1, 2, 2, 3, 3, 1, 2, 1, 2, 1, 1, 1 }
    };

    private static final int[][] mapa2 = {
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
    };

    private static final int[][] mapa3 = {
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
    };

    public Level(int number) {
        platforms = new ArrayList<>();
        traps = new ArrayList<>();

        /*
         * // chão
         * platforms.add(new Platform(0, 550, 800, 50));
         */
        if (number == 1) {
            carregarMapa(mapa1);
            /*
             * 
             * platforms.add(new Platform(200, 450, 100, 20));
             * platforms.add(new Platform(400, 350, 100, 20));
             * 
             * traps.add(new Trap(300, 530, 40, 20));
             * traps.add(new Trap(600, 530, 40, 20));
             */
        } else if (number == 2) {
            carregarMapa(mapa2);
            /*
             * 
             * platforms.add(new Platform(150, 450, 100, 20));
             * platforms.add(new Platform(350, 400, 100, 20));
             * platforms.add(new Platform(560, 250, 100, 20));
             * platforms.add(new Platform(500, 300, 10, 10));
             * 
             * traps.add(new Trap(265, 390, 40, 160));
             * traps.add(new Trap(440, 530, 230, 20));
             * traps.add(new Trap(750, 180, 50, 280));
             */
        } else if (number == 3) {
            carregarMapa(mapa3);
            /*
             * 
             * platforms.add(new Platform(150, 450, 100, 20));
             * platforms.add(new Platform(350, 400, 100, 20));
             * platforms.add(new Platform(560, 250, 100, 20));
             * platforms.add(new Platform(500, 300, 10, 10));
             * 
             * traps.add(new Trap(265, 390, 40, 160));
             * traps.add(new Trap(440, 530, 230, 20));
             * traps.add(new Trap(750, 180, 50, 280));
             */
        }
        // Adicione mais níveis se quiser
    }

    private void carregarMapa(int[][] mapa) {
        for (int row = 0; row < LIN; row++) {
            for (int col = 0; col < COLS; col++) {
                int valor = mapa[row][col];
                int x = col * TILE_SIZE;
                int y = row * TILE_SIZE;

                switch (valor) {
                    // PLATAFORMA
                    case 1, 2, 3, 4:
                        platforms.add(new Platform(x, y, TILE_SIZE, TILE_SIZE, valor));
                        break;
                    // TRAPS
                    case 5:
                        traps.add(new Trap(x, y, TILE_SIZE, TILE_SIZE));
                        break;
                }
            }
        }
    }

    public void draw(Graphics g) {
        for (Platform p : platforms)
            p.draw(g);

        for (Trap t : traps)
            t.draw(g);
    }

    public boolean checkTrapCollision(Player player) {
        for (Trap t : traps) {
            if (t.checkCollision(player))
                return true;
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
