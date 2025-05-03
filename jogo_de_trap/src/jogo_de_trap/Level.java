package jogo_de_trap;

import java.awt.*;
import java.util.ArrayList;
import java.awt.Rectangle;

public abstract class Level {
    private static final int TILE_SIZE = 50;
    private static final int LIN = 600 / TILE_SIZE; // 12
    private static final int COLS = 800 / TILE_SIZE; // 16
    protected Objeto[][] mapaObjetos = new Objeto[LIN][COLS];

    ArrayList<Platform> platforms = new ArrayList<>();
    ArrayList<Trap> traps = new ArrayList<>();
    ArrayList<Pistao> pistoes = new ArrayList<>();

    public Level(int number) {
        carregarMapa(getMapa());
        designTraps();
    }

    private void carregarMapa(int[][] mapa) {
        for (int row = 0; row < LIN; row++) {
            for (int col = 0; col < COLS; col++) {
                int valor = mapa[row][col];
                int x = col * TILE_SIZE;
                int y = row * TILE_SIZE;

                Objeto obj = criarObjetoPorCodigo(valor, x, y);
                if (obj != null) {
                    mapaObjetos[row][col] = obj;

                    if (obj instanceof Platform) {
                        platforms.add((Platform) obj);
                    } else if (obj instanceof Trap) {
                        traps.add((Trap) obj);
                    } else if (obj instanceof Pistao) {
                        pistoes.add((Pistao) obj);
                    }
                }
            }
        }
    }

    public void draw(Graphics g) {
        for (Platform p : platforms)
            p.draw(g);

        for (Trap t : traps)
            t.draw(g);

        for (Pistao pistao : pistoes)
            pistao.draw(g);
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

    private Objeto criarObjetoPorCodigo(int tipo, int x, int y) {
        switch (tipo) {
            case 1, 2, 3, 4:
                return new Platform(x, y, TILE_SIZE, TILE_SIZE, tipo);
            case 5:
                return new Trap(x, y, TILE_SIZE, TILE_SIZE);
            case 6: // pistao normal
                return new Pistao(x, y, 1, false);
            case 7: // pistao camuflado
                return new Pistao(x, y, 3, true);
            default:
                return null;
        }
    }

    public void updatePistaos(Player p) {
        for (Pistao pistao : pistoes) {
            pistao.update(p);
        }
    }

    protected abstract int[][] getMapa();

    protected abstract void designTraps();
}
