package levelGroup;

import game.Level;
import game.objetos.Espinhos;
import game.objetos.EspinhosP;
import game.objetos.Laser;

import game.objetos.Objeto;
import game.objetos.Pistao;
import game.objetos.Platform;
import game.objetos.Player;

import java.awt.Color;
import java.util.ArrayList;

import game.GamePanel;
import game.Gravity;

public class Level06 extends Level {

    Player player;
    private GamePanel gamePanel;
    Gravity g;
    private boolean ativo = true;

    private static int[][] mapa = {
            { 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4 },
            { 4, 14, 12, 12, 12, 12, 12, 13, 0, 0, 0, 0, 0, 0, 0, 8 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8 },
            { 1, 1, 1, 1, 1, 8, 8, 8, 8, 8, 8, 8, 1, 1, 5, 1 }
    };

    @Override
    protected int[][] getMapa() {
        return mapa;
    }

    public Level06(Player player, GamePanel gamePanel) {
        super(6);
        this.player = player;
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

        int baseY = 1 * 50;
        int yMin = 20, yMax = 450;
        for (int col = 1; col <= 7; col++) {
            Laser l = (Laser) mapaObjetos[1][col];
            int dy = l.getY() - baseY;
            l.configMovVertical(yMin + dy, yMax + dy, 2, 0);
        }

    } // -----------------------------

    private void monitorarPulo() {
        new Thread(() -> {
            while (ativo) {
                if (player.wantToJump && player.onGround) {
                    if (g.getGravity() != 0) {
                        g.setGravity(0.0); // força 0 = "desliga" gravidade neste momento
                        g.setPulo(500.0); // magnitude positiva; sentido calculado pelo Player
                        setTitulo(gamePanel.getNumFase() + "." + " Eita, um bug?... Não, é o jogo!");
                    }
                    player.velY = -15; // hardcoded intencional do Level06 (gravidade normal sempre)

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
