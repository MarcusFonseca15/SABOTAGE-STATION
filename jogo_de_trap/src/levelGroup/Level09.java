package levelGroup;

import jogo_de_trap.Gravity;
import jogo_de_trap.Laser;
import jogo_de_trap.Level;
import jogo_de_trap.Pistao;
import jogo_de_trap.Platform;
import jogo_de_trap.Player;

public class Level09 extends Level {

    Player player;
    Gravity g;

    private boolean ativo = true;

    private static int[][] mapa = {
            { 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 },
            { 0, 4, 0, 0, 0, 0, 0, 4, 0, 0, 0, 4, 111, 111, 111, 4 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 35, 35 },
            { 0, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 36 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 36 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 36 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 37, 37 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11 },
            { 5, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 6, 6, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 0, 0, 0 },
            { 1, 1, 1, 1, 1, 1, 1, 3, 6, 1, 1, 1, 1, 1, 1, 1 }
    };

    @Override
    protected int[][] getMapa() {
        return mapa;
    }

    public Level09(Player player) {
        super(9);
        this.player = player;
        this.player.level = this;
        this.g = player.g;

        designTraps();
        monitorarPulo();

        this.titulo = "09. Oops, fiz de novo rs";
    }

    @Override
    protected void designTraps() {

        Laser l1 = (Laser) mapaObjetos[3][14];
        Laser l2 = (Laser) mapaObjetos[4][14];
        Laser l3 = (Laser) mapaObjetos[5][14];
        Laser l4 = (Laser) mapaObjetos[6][14];
        Laser l5 = (Laser) mapaObjetos[7][14];
        laserAnda(l1, 0, l1.getX(), 2);
        laserAnda(l2, 0, l2.getX(), 2);
        laserAnda(l3, 0, l3.getX(), 2);
        laserAnda(l4, 0, l1.getX(), 2);
        laserAnda(l5, 0, l1.getX(), 2);

        Laser lTR1 = (Laser) mapaObjetos[3][15];
        Laser lTR2 = (Laser) mapaObjetos[4][15];
        Laser lTR3 = (Laser) mapaObjetos[5][15];
        Laser LTR4 = (Laser) mapaObjetos[6][15];
        Laser LTR5 = (Laser) mapaObjetos[7][15];

        Pistao pistaoFin = (Pistao) mapaObjetos[9][13];
        Pistao pistaoFin2 = (Pistao) mapaObjetos[9][14];

        if (player.getBounds().intersects(pistaoFin.getBounds()) ||
                player.getBounds().intersects(pistaoFin2.getBounds())) {

            laserAnda(lTR1, 0, lTR1.getX(), 3);
            laserAnda(lTR2, 0, lTR2.getX(), 3);
            laserAnda(lTR3, 0, lTR3.getX(), 3);
            laserAnda(LTR4, 0, lTR1.getX(), 3);
            laserAnda(LTR5, 0, lTR1.getX(), 3);
        }

        player.g.setGravity(1);
        if (player.wantToJump) {
            player.g.setGravity(1);
        }

    }// ---------------

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

    private void monitorarPulo() {
        new Thread(() -> {
            while (ativo) {
                if (player.wantToJump && player.onGround) {
                    if (g.getGravity() != 0) {
                        g.setGravity(0);
                        g.setPulo(-500);
                    }
                    player.velY = -15;
                    player.jumping = true;
                    player.onGround = false;
                    player.wantToJump = false;

                    // RESET DE ESTADO
                    new Thread(() -> {
                        try {
                            Thread.sleep(100);
                            g.setGravity(1);
                            player.onGround = true;
                            player.jumping = false;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void pararThread() {
        ativo = false;
    }

}
