package levelGroup;

import game.Level;
import game.objetos.Espinhos;
import game.objetos.EspinhosP;
import game.objetos.Pistao;
import game.objetos.Platform;
import game.objetos.Player;

import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.event.ActionEvent;

import game.GamePanel;

public class Level05 extends Level {

    private Player player;
    private GamePanel gamePanel;
    private Level level;

    private static int[][] mapa = {
            { 4, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 34 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 4, 0, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 0, 0 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 1 },
            { 4, 0, 1, 1, 0, 0, 0, 8, 8, 8, 0, 0, 0, 0, 10, 4 },
            { 4, 14, 12, 13, 2, 0, 0, 1, 0, 3, 0, 0, 0, 0, 0, 4 },
            { 34, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
            { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
            { 0, 0, 0, 0, 0, 8, 8, 8, 0, 0, 0, 0, 0, 0, 0, 4 },
            { 1, 1, 1, 5, 5, 1, 1, 1, 1, 1, 6, 1, 1, 1, 5, 4 }
    };

    @Override
    protected int[][] getMapa() {
        return mapa;
    }

    public Level05(Player player, GamePanel gamePanel) {
        super(2);
        this.player = player;
        designTraps();
        this.titulo = formatarTitulo("Nem tudo se vê", gamePanel.getNumFase());
    }

    @Override
    protected void designTraps() {

        // TITULO: "05. NÃO É COMO VOCE ACHA"

        Pistao pistaoForte = (Pistao) mapaObjetos[11][14];
        pistaoForte.forca = 3.5f;

        Pistao pistCam = (Pistao) mapaObjetos[11][10];
        pistCam.forca = 1.5f;

        EspinhosP spInv1 = (EspinhosP) mapaObjetos[5][7];
        EspinhosP spInv2 = (EspinhosP) mapaObjetos[5][8];
        EspinhosP spInv3 = (EspinhosP) mapaObjetos[5][9];

        EspinhosP spInv4 = (EspinhosP) mapaObjetos[5][14];

        spInv1.setVisible(false);
        spInv2.setVisible(false);
        spInv3.setVisible(false);
        spInv4.setVisible(false);

    }

}
