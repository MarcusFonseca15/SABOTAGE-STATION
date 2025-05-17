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

    private Laser lTR1, lTR2, lTR3, LTR4, LTR5;

    // PRA GUARDAR A POSIÇÕA DOS LASERS
    private Laser[] lasersMoveis;
    private int[] lasersPosInitX;
    private boolean[] lasersVisivInit;

    // PRA PARAR AS THREADS
    private Thread[] laserThreads;
    private boolean[] laserAtivos;

    private boolean ativo = true;

    private final int vel = 1;

    private static int[][] mapa = {
            { 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 },
            { 0, 4, 0, 0, 0, 0, 0, 111, 111, 0, 0, 4, 111, 111, 111, 4 },
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

        this.titulo = "09. Oops, fiz de novo rs";
    }

    @Override
    protected void designTraps() {

        Laser l1 = (Laser) mapaObjetos[3][14];
        Laser l2 = (Laser) mapaObjetos[4][14];
        Laser l3 = (Laser) mapaObjetos[5][14];
        Laser l4 = (Laser) mapaObjetos[6][14];
        Laser l5 = (Laser) mapaObjetos[7][14];
        laserAnda(l1, 0, l1.getX(), vel);
        laserAnda(l2, 0, l2.getX(), vel);
        laserAnda(l3, 0, l3.getX(), vel);
        laserAnda(l4, 0, l1.getX(), vel);
        laserAnda(l5, 0, l1.getX(), vel);

        lTR1 = (Laser) mapaObjetos[3][15];
        lTR2 = (Laser) mapaObjetos[4][15];
        lTR3 = (Laser) mapaObjetos[5][15];
        LTR4 = (Laser) mapaObjetos[6][15];
        LTR5 = (Laser) mapaObjetos[7][15];

        lasersMoveis = new Laser[] { l1, l2, l3, l4, l5, lTR1, lTR2, lTR3, LTR4, LTR5 };
        lasersPosInitX = new int[lasersMoveis.length];
        lasersVisivInit = new boolean[lasersMoveis.length];

        for (int i = 0; i < lasersMoveis.length; i++) {
            lasersPosInitX[i] = lasersMoveis[i].getX();
            lasersVisivInit[i] = lasersMoveis[i].isVisible();
        }

        laserThreads = new Thread[lasersMoveis.length];
        laserAtivos = new boolean[lasersMoveis.length];

        invisibilizarLasers(lTR1, lTR2, lTR3, LTR4, LTR5);

        Pistao pistaoForte = (Pistao) mapaObjetos[11][8];
        // pistaoForte.forca = 3;

        Pistao pistaoFin = (Pistao) mapaObjetos[9][13];
        Pistao pistaoFin2 = (Pistao) mapaObjetos[9][14];

        monitorarPistao(pistaoFin, pistaoFin2, lTR1, lTR2, lTR3, LTR4, LTR5);

        // se player morrer (if (colision com laser ou espinho), reset os lasers para
        // posição inicial

        monitorarMorte();

        for (int i = 0; i < 5; i++) {
            iniTMovLaser(i, lasersMoveis[i], 0, lasersPosInitX[i], vel);
        }

    }// ---------------

    private void laserAnda(Laser laser, int posMin, int posMax, int vel) {
        System.out.println("EEntrou no metodo");
        new Thread(() -> {
            try {
                boolean indo = true;
                System.out.println("EEntrou nA THREAD");
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

    private void invisibilizarLasers(Laser... lasers) {
        for (Laser l : lasers) {
            l.setVisible(false);
        }
    }

    private void visibleLaser(Laser... lasers) {
        for (Laser l : lasers) {
            l.setVisible(true);
        }
    }

    private void monitorarPistao(Pistao p1, Pistao p2, Laser... lasers) {
        new Thread(() -> {
            while (ativo) {
                if (player.getBounds().intersects(p1.getBounds()) || player.getBounds().intersects(p2.getBounds())) {
                    System.out.println("Player pisou no pistão");

                    for (int i = 5; i <= 9; i++) {
                        lasersMoveis[i].setVisible(true);
                        iniTMovLaser(i, lasersMoveis[i], 0, lasersPosInitX[i], vel);
                    }

                    /*
                     * for (int j = 0; j < lasers.length; j++) {
                     * Laser l = lasers[j];
                     * visibleLaser(l);
                     * // laserAnda(l, 0, l.getX(), 2);
                     * // iniTMovLaser(j, l, 0, l.getX(), 2);
                     * iniTMovLaser(5, lTR1, 0, lasersPosInitX[5], 2);
                     * iniTMovLaser(6, lTR2, 0, lasersPosInitX[6], 2);
                     * iniTMovLaser(7, lTR3, 0, lasersPosInitX[7], 2);
                     * iniTMovLaser(8, LTR4, 0, lasersPosInitX[8], 2);
                     * iniTMovLaser(9, LTR5, 0, lasersPosInitX[9], 2);
                     * }
                     */

                    break;
                }

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void resetarLasers() {
        for (int i = 0; i < laserThreads.length; i++) {
            laserAtivos[i] = false;
            lasersMoveis[i].setX(lasersPosInitX[i]);
            lasersMoveis[i].setVisible(lasersVisivInit[i]);
            try {
                if (laserThreads[i] != null && laserThreads[i].isAlive()) {
                    laserThreads[i].join(10); // espera encerrar
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Reativa apenas os lasers principais (0 a 4)
        for (int i = 0; i < 5; i++) {
            iniTMovLaser(i, lasersMoveis[i], 0, lasersPosInitX[i], vel);
        }
    }

    private void monitorarMorte() {
        new Thread(() -> {
            while (ativo) {
                if (checkLaserCollision(player) || checkEspinhosPCollision(player)) {
                    System.out.println("Player morreu, resetando lasers.");
                    resetarLasers();
                }

                try {
                    Thread.sleep(50); // verifica a cada 50ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void iniTMovLaser(int index, Laser laser, int posMin, int posMax, int vel) {
        laserAtivos[index] = true;
        laserThreads[index] = new Thread(() -> {
            boolean indo = true;
            while (laserAtivos[index]) {
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

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        laserThreads[index].start();
    }

    @Override
    public void pararThread() {
        ativo = false;
    }

}
