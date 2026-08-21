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

    private EspinhosP espInv1;
    private EspinhosP espInv2;
    private EspinhosP espInv3;
    private EspinhosP espInv4;

    private static int[][] mapa = {
            { 1, 1, 9, 9, 9, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 24, 24, 24, 24, 24, 24, 24, 24, 24, 9 },
            { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 8 },
            { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 32, 0, 0, 1, 1, 0, 30, 8 },
            { 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 30, 8 },
            { 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0 },
            { 0, 0, 0, 33, 0, 8, 0, 0, 0, 0, 8, 8, 8, 8, 0, 0 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
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

        this.titulo = formatarTitulo("Gravitons!", gamePanel.getNumFase());
        setShowExit(false);
    }

    @Override
    protected void designTraps() {

        espInv1 = (EspinhosP) mapaObjetos[10][5];
        espInv1.setVisible(false);

        espInv2 = (EspinhosP) mapaObjetos[2][15];
        espInv2.setVisible(false);

        espInv3 = (EspinhosP) mapaObjetos[4][15];
        espInv3.setVisible(false);

        espInv4 = (EspinhosP) mapaObjetos[6][15];
        espInv4.setVisible(false);

    } // -----------------------------

}
