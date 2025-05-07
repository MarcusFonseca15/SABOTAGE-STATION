package levelGroup;

import jogo_de_trap.Gravity;
import jogo_de_trap.Level;
import jogo_de_trap.Platform;
import jogo_de_trap.Pistao;

import jogo_de_trap.Player;
import jogo_de_trap.Gravity;

public class Level1 extends Level {

    private Player player;

    public Gravity g;

    private static int[][] mapa = {
            { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0 },
            { 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 4, 0, 0, 0, 0, 9, 7, 7, 7, 8, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 11, 0, 0, 0, 0, 6, 0, 0, 5, 0, 0, 0, 0 },
            { 1, 2, 3, 1, 1, 6, 1, 6, 1, 1, 1, 1, 9, 7, 8, 1 }
    };

    @Override
    protected int[][] getMapa() {
        return mapa;
    }

    public Level1(Player player) {
        super(1);
        this.player = player;
        designTraps();
    }

    @Override
    protected void designTraps() {
        Platform p1 = (Platform) mapaObjetos[0][0];
        Platform p2 = (Platform) mapaObjetos[0][1];

        sobeDesce(p1, 150, 250, 2);
        sobeDesce(p2, 150, 250, 2);

        Pistao pt1 = (Pistao) mapaObjetos[11][5];
        pt1.forca = 3;
        Pistao pt2 = (Pistao) mapaObjetos[11][7];
        pt2.forca = 3;

        // normal
        player.g.setGravity(1.0);
        player.g.setPulo(-15);

        // zero
        // player.g.setGravity(0);
        // player.g.setPulo(-15);

        // invertida
        // player.g.setGravity(-1);
        // player.g.setPulo(15);

        // solar (forte)
        // player.g.setGravity(5);
        // player.g.setPulo(-5);
    }// FIM DO DESIGNTRAPS

    private void sobeDesce(Platform plataforma, int altuMin, int altMax, int vel) {
        new Thread(() -> {
            try {
                boolean subindo = true;
                while (true) {
                    if (subindo) {
                        plataforma.setY(plataforma.getY() - vel);
                        if (plataforma.getY() <= altuMin) {
                            subindo = false;
                        }
                    } else {
                        plataforma.setY(plataforma.getY() + vel);
                        if (plataforma.getY() >= altMax) {
                            subindo = true;
                        }
                    }
                    Thread.sleep(20); // suavidade
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
