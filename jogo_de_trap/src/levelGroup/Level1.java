package levelGroup;

import jogo_de_trap.Level;
import jogo_de_trap.Platform;

public class Level1 extends Level {

    private static int[][] mapa = {
            { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 2, 3, 1, 1, 6, 6, 9, 7, 7, 7, 8, 5, 1, 1, 1 }
    };

    @Override
    protected int[][] getMapa() {
        return mapa;
    }

    public Level1() {
        super(1);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void designTraps() {
        Platform p1 = (Platform) mapaObjetos[0][0];
        Platform p2 = (Platform) mapaObjetos[0][1];

        sobeDesce(p1, 150, 250, 2);
        sobeDesce(p2, 150, 250, 2);
    }

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
