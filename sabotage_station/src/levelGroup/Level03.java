package levelGroup;

import game.Level;
import game.objetos.Espinhos;
import game.objetos.EspinhosP;
import game.objetos.Laser;
import game.objetos.Pistao;
import game.objetos.Platform;
import game.objetos.Player;

import java.util.ArrayList;

import game.GamePanel;
import game.Gravity;

public class Level03 extends Level {

    Player player;
    private GamePanel gamePanel;
    Gravity g;

    private EspinhosP espInv1;
    private EspinhosP espInv2;

    private static int[][] mapa = {
    { 14, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 4 },
    { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
    { 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 4 },
    { 0, 0, 0, 4, 0, 0, 0, 1, 11, 0, 0, 3, 0, 0, 0, 4 },
    { 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 4 },
    { 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 0 },
    { 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 4 },
    { 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 4 },
    { 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 4 },
    { 0, 0, 5, 4, 8, 5, 8, 4, 5, 8, 0, 4, 8, 8, 5, 4 },
    { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
};

    @Override
    protected int[][] getMapa() {
        return mapa;
    }

    public Level03(Player player, GamePanel gamePanel) {
        super(3);
        this.player = player;
        designTraps();
        this.titulo = formatarTitulo("Um pouco mais pra direita", gamePanel.getNumFase());
        this.setTitlePos(titleX, 70);

        setShowExit(false);
    }

    @Override
    protected void designTraps() {

        Pistao p1 = (Pistao) mapaObjetos[10][2];
        p1.forca = 1.35f;
        Pistao p2 = (Pistao) mapaObjetos[10][5];
        p2.forca = 1.47f;
        Pistao p3 = (Pistao) mapaObjetos[10][8];
        p3.forca = 1.45f;

        espInv1 = (EspinhosP) mapaObjetos[4][8];

        espInv2 = (EspinhosP) mapaObjetos[3][11];

        espInv1.setVisible(false);
        espInv2.setVisible(false);

        player.g.setPulo(-90);
    } // -----------------------------

}
