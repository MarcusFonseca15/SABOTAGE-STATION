package levelGroup;

import jogo_de_trap.Gravity;
import jogo_de_trap.Laser;
import jogo_de_trap.Level;
import jogo_de_trap.Platform;
import jogo_de_trap.Player;

public class Level09 extends Level {

    Player player;
    Gravity g;

    private static int[][] mapa = {
            { 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 },
            { 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 4 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 35, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 37, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 4, 0, 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 4, 0, 0 },
            { 0, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11 }
    };

    @Override
    protected int[][] getMapa() {
        return mapa;
    }

    public Level09(Player player) {
        super(9);
        this.player = player;
        this.player.level = this;
        this.g = new Gravity(1);
        designTraps();
        this.titulo = "09. Desafiar a Gravidade!";
    }

    @Override
    protected void designTraps() {

        Laser l1 = (Laser) mapaObjetos[4][14];
        Laser l2 = (Laser) mapaObjetos[5][14];
        Laser l3 = (Laser) mapaObjetos[6][14];
        Laser l4 = (Laser) mapaObjetos[7][14];
        Laser l5 = (Laser) mapaObjetos[8][14];
        laserAnda(l1, 0, l1.getX(), 2);
        laserAnda(l2, 0, l2.getX(), 2);
        laserAnda(l3, 0, l3.getX(), 2);
        laserAnda(l4, 0, l1.getX(), 2);
        laserAnda(l5, 0, l1.getX(), 2);

        player.g.setGravity(1);

        if (player.wantToJump) {
            player.g.setGravity(-1);
        }
    }

    private void laserAnda(Laser laser, int posMin, int posMax, int vel) {
        new Thread(() -> {
            try {
                boolean indo = true;
                while (true) {
                    if (indo) {
                        laser.setX(laser.getX() + vel);
                        if (laser.getX() >= posMax) {
                            indo = false;
                        }
                    } else {
                        laser.setX(laser.getX() - vel);
                        if (laser.getX() <= posMin) {
                            indo = true;
                        }
                    }

                    Thread.sleep(20); // suavidade do movimento
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
