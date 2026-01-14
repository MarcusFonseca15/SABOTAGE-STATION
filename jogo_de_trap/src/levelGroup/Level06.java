package levelGroup;

import jogo_de_trap.Level;
import jogo_de_trap.objetos.Espinhos;
import jogo_de_trap.objetos.EspinhosP;
import jogo_de_trap.objetos.Laser;
import jogo_de_trap.objetos.LaserGrande;
import jogo_de_trap.objetos.Objeto;
import jogo_de_trap.objetos.Pistao;
import jogo_de_trap.objetos.Platform;
import jogo_de_trap.objetos.Player;

import java.awt.Color;
import java.util.ArrayList;

import jogo_de_trap.GamePanel;
import jogo_de_trap.Gravity;

public class Level06 extends Level {

    Player player;
    private GamePanel gamePanel;
    Gravity g;
    private boolean ativo = true;

    private static int[][] mapa = {
            { 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4 },
            { 4, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11 },
            { 1, 1, 1, 1, 1, 11, 11, 11, 11, 11, 11, 11, 1, 1, 5, 1 }
    };

    @Override
    protected int[][] getMapa() {
        return mapa;
    }

    public Level06(Player player, GamePanel gamePanel) {
        super(6);
        this.player = player;
        this.player.level = this;
        this.g = player.g;
        this.gamePanel = gamePanel;

        designTraps();
        monitorarPulo();

        this.titulo = formatarTitulo("Eita, um bug?...", gamePanel.getNumFase());
        setShowExit(false);
    }

    @Override
    protected void designTraps() {

        Pistao pF = (Pistao) mapaObjetos[11][14];
        pF.forca = 2f;

        EspinhosP esp = (EspinhosP) mapaObjetos[10][15];
        esp.visible = false;

        EspinhosP esp2 = (EspinhosP) mapaObjetos[1][15];
        esp2.visible = false;

        LaserGrande lg1 = (LaserGrande) mapaObjetos[1][1];
        laserDesce(lg1, 20, 450, 2);

    } // -----------------------------

    private void laserDesce(LaserGrande laser, int altuMin, int altMax, int vel) {
        new Thread(() -> {
            try {
                boolean subindo = true;
                while (true) {
                    if (subindo) {
                        laser.setY(laser.getY() - vel);
                        if (laser.getY() <= altuMin) {
                            subindo = false;
                            System.out.println(laser.getY());
                        }
                    } else {
                        laser.setY(laser.getY() + vel);
                        if (laser.getY() >= altMax) {
                            subindo = true;
                        }
                    }

                    Thread.sleep(20); // suavidade
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }// -----------------------------

    private void monitorarPulo() {
        new Thread(() -> {
            while (ativo) {
                if (player.wantToJump && player.onGround) {
                    if (g.getGravity() != 0) {
                        g.setGravity(0);
                        g.setPulo(-500);
                        setTitulo(gamePanel.getNumFase() + "." + " Eita, um bug?... Não, é o jogo!");
                    }
                    player.velY = -15;
                    player.jumping = true;
                    player.onGround = false;
                    player.wantToJump = false;

                    // RESET DE ESTADO APÓS 2 SEGUNDOS
                    new Thread(() -> {
                        try {
                            Thread.sleep(4500);
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
